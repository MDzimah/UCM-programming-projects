#include "juego.h"
#include <cstdlib>
#include <time.h>

bool Juego::cargar(std::istream& entrada) {
	int filas, cols;
	entrada >> filas >> cols;
	if (!entrada) return false;
	tablero = Tablero(filas, cols, NULA);
	for (int f = 1; f <= tablero.num_filas(); ++f)
		for (int c = 1; c <= tablero.num_columnas(); ++c) {
			
			short int val;
			entrada >> val;
			
			if (val == 2) { 
				Coord cor;
				cor.f = f;
				cor.c = c;
				posFichas.push_back(cor);
			}

			tablero.escribir(f, c, tablero.conv_intAenum(val));
		}
	entrada >> f_meta >> c_meta;
	++f_meta;
	++c_meta;
	//Para que la posición meta sea coherente con el sistema de referencia escogido (1,1) ~ (0,0)

	estado_int = JUGANDO;

	return true;
}

bool Juego::posicion_valida(int f, int c) const {
	return tablero.correcta(f,c) && tablero.leer(f, c) == FICHA;
}

void Juego::posibles_movimientos(Movimiento& mov) const {
	
	/*Primero se analiza la posición para ver si hay una ficha. 
	Tras ello, se analizan las posiciones adyacentes para ver si hay una ficha.
	Finalmente, se analizan las posiciones adyacentes a las anteriores con igual
	dirección respecto de la posición inicial para ver si está vacío.*/
	if (posicion_valida(mov.fila(),mov.columna())) {
		for (int i = 0; i < 4; ++i) {
			int posicionF = mov.fila() + dirs[i].first;
			int posicionC = mov.columna() + dirs[i].second;
			if (
				posicion_valida(posicionF,posicionC) &&
				tablero.correcta(posicionF + dirs[i].first, posicionC + dirs[i].second) &&
				tablero.leer(posicionF + dirs[i].first, posicionC + dirs[i].second) == VACIA
				)
				mov.insertar_dir((Direccion)i);
		}
	}
	
}

void Juego::jugar(Movimiento const& mov) {
	if (estado_int == JUGANDO) {
		ejecuta_movimiento(mov);
	}
	nuevo_estado();
}

void Juego::ejecuta_movimiento(Movimiento const& mov) {
	
	int opDireccion = mov.dir_activa();
	
	tablero.escribir(mov.fila(), mov.columna(), VACIA);
	elim_ficha_de_lista_fichas(mov.fila(), mov.columna());

	Coord cor;
	cor.f = mov.fila() + dirs[opDireccion].first;
	cor.c = mov.columna() + dirs[opDireccion].second;

	tablero.escribir(cor.f, cor.c, VACIA);
	elim_ficha_de_lista_fichas(cor.f, cor.c);

	cor.f += dirs[opDireccion].first;
	cor.c += dirs[opDireccion].second;

	tablero.escribir(cor.f, cor.c, FICHA);
	posFichas.push_back(cor);
}

void Juego::nuevo_estado() {
	if (hay_ganador()) estado_int = GANADOR;
	else if (hay_movimientos()) estado_int = JUGANDO;
	else estado_int = BLOQUEO;
}

bool Juego::hay_ganador() const {
	return posFichas.size() == 1 && posicion_valida(f_meta, c_meta);
}

bool Juego::hay_movimientos() const {

	int i = 0;
	while (i < posFichas.size()) {
		Movimiento m(posFichas[i].f, posFichas[i].c);
		posibles_movimientos(m);
		if (m.num_dirs() > 0) return true; //Se para el bucle al encontrar una ficha movible
		++i;
	}

	return false;
}


Juego::Juego (int movimientos) : estado_int(BLOQUEO), tablero(0,0,NULA) {

	establecer_tablero_y_pos_meta();

	Coord cor;
	cor.f = f_meta;
	cor.c = c_meta;
	
	//Para el primer movimiento inverso no hace falta llamar a "elegir_pos_ficha_aleatoria()"
	Movimiento mov(cor.f, cor.c);
	posibles_mov_inverso(mov);

	//De la lista de direcciones del movimiento inverso se elige una aleatoriamente
	int opDireccion = mov.direccion(elegir_dir_aleatoria(mov)); 

	tablero.escribir(cor.f, cor.c, VACIA);

	cor.f += dirs[opDireccion].first;
	cor.c += dirs[opDireccion].second;
	posFichas.push_back(cor);
	posFichasMovInv.push_back(cor);
	tablero.escribir(cor.f, cor.c, FICHA);
	
	cor.f += dirs[opDireccion].first;
	cor.c += dirs[opDireccion].second;
	posFichas.push_back(cor);
	posFichasMovInv.push_back(cor);
	tablero.escribir(cor.f, cor.c, FICHA);

	/*Observación: que el algoritmo haga todos los "pasos" depende 
	del tamaño del tablero y de la selección aleatoria de las fichas*/
	
	int i = 1;
	//Si posFichasMovInv.size() = 0, entonces no hay ninguna ficha que pueda hacer movimientos inversos
	while (i < movimientos && posFichasMovInv.size() > 0) {

		//Mismo código, pero se recurre al método "elegir_pos_ficha_aleatoria()"
		int posFicha = elegir_pos_ficha_aleatoria();
		Movimiento movi(posFichasMovInv[posFicha].f, posFichasMovInv[posFicha].c);
		posibles_mov_inverso(movi);

		if (movi.num_dirs() > 0) {
			opDireccion = movi.direccion(elegir_dir_aleatoria(movi));

			tablero.escribir(posFichasMovInv[posFicha].f, posFichasMovInv[posFicha].c, VACIA);
			
			cor.f = posFichasMovInv[posFicha].f + dirs[opDireccion].first;
			cor.c = posFichasMovInv[posFicha].c + dirs[opDireccion].second;
			posFichas.push_back(cor);
			posFichasMovInv.push_back(cor);
			tablero.escribir(cor.f, cor.c, FICHA);

			cor.f += dirs[opDireccion].first;
			cor.c += dirs[opDireccion].second;
			posFichas.push_back(cor);
			posFichasMovInv.push_back(cor);
			tablero.escribir(cor.f, cor.c, FICHA);

			elim_ficha_de_lista_fichas(posFichasMovInv[posFicha].f, posFichasMovInv[posFicha].c);
			elim_ficha_de_lista_fichas_mov_inv(posFichasMovInv[posFicha].f, posFichasMovInv[posFicha].c);
		//

			++i;
		}
		else elim_ficha_de_lista_fichas_mov_inv(posFichasMovInv[posFicha].f, posFichasMovInv[posFicha].c);
		//Se eliminan las fichas que no puedan hacer movimiento inverso
	}

	estado_int = JUGANDO;
}

void Juego::posibles_mov_inverso(Movimiento& mov) const {

	//Para una posición con ficha se calculan los movimientos inversos posibles
	for (int i = 0; i < 4; ++i) {
		int posicionF = mov.fila() + dirs[i].first;
		int posicionC = mov.columna() + dirs[i].second;
		if (
			(
			tablero.correcta(posicionF, posicionC) && 
			tablero.correcta(posicionF + dirs[i].first, posicionC + dirs[i].second)
			) 
			&& 
			(
			(tablero.leer(posicionF, posicionC) == NULA && tablero.leer(posicionF + dirs[i].first, posicionC + dirs[i].second) == NULA)
			||
			(tablero.leer(posicionF, posicionC) == VACIA && tablero.leer(posicionF + dirs[i].first, posicionC + dirs[i].second) == NULA)
				//Hay dos formas de realizar un movimiento inverso como mostrado en el guión
			)
		   )
		{
			posicion_valida(posicionF, posicionC);
			mov.insertar_dir((Direccion)i);
		}
	}

}

void Juego::establecer_tablero_y_pos_meta() {

	srand(time(NULL));

	int f = 4 + rand() % (11-4);
	int c = 4 + rand() % (11-4);

	f_meta = 1 + rand() % f;
	c_meta = 1 + rand() % c;

	tablero = Tablero(f, c, NULA);
}

int Juego::elegir_dir_aleatoria(Movimiento const& mov) const {

	srand(time(NULL));

	return rand() % mov.num_dirs();
}

int Juego::elegir_pos_ficha_aleatoria() {

	srand(time(NULL));

	return rand() % posFichasMovInv.size();

}

void Juego::elim_ficha_de_lista_fichas(int f, int c) {

	int i = 0;
	while (posFichas[i].f != f || posFichas[i].c != c)
		++i;
	
	for (int j = i; j < posFichas.size()-1; ++j) 
		posFichas[j] = posFichas[j + 1];

	posFichas.pop_back();
}

void Juego::elim_ficha_de_lista_fichas_mov_inv(int f, int c) {
	
	int i = 0;
	while (posFichasMovInv[i].f != f || posFichasMovInv[i].c != c)
		++i;

	for (int j = i; j < posFichasMovInv.size() - 1; ++j)
		posFichasMovInv[j] = posFichasMovInv[j + 1];

	posFichasMovInv.pop_back();
}


void Juego::mostrar() const {
	Colores pintarTablero(tablero, f_meta, c_meta);
	pintarTablero.pintar();
}



