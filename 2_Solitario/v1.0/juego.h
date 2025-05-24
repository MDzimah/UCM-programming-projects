#pragma once
#include "movimiento.h"
#include "pintarTablero.h"
#include <vector>

enum Estado { JUGANDO, GANADOR, BLOQUEO };

struct Coord {
	int f, c;
};

class Juego {
public:
	Juego() : estado_int(BLOQUEO), tablero(0, 0, NULA) {}
	Juego(int movimientos/*ent*/); //Constructor que genera el tablero aleatorio
	bool cargar(std::istream& entrada/*ent/sal*/);
	bool posicion_valida(int f /*ent*/, int c /*ent*/) const;
	void posibles_movimientos(Movimiento& mov/*ent/sal*/) const;
	Estado estado() const { return estado_int; }
	void jugar(Movimiento const& mov/*ent*/);
	void mostrar() const;
private:
	Tablero tablero;
	int f_meta, c_meta;
	Estado estado_int;

	/*Hacen falta dos listas para registrar las posiciones de las fichas. 
	La primera las registra todas mientras que la segunda registra aquellas con 
	las que se puede hacer un movimiento inverso*/
	std::vector <Coord> posFichas;
	std::vector <Coord> posFichasMovInv;

	//PARA SIMULAR EL JUEGO
	void ejecuta_movimiento(Movimiento const& mov /*ent*/);
	void nuevo_estado();
	bool hay_ganador() const;
	bool hay_movimientos() const;

	//PARA GENERAR TABLEROS ALEATORIOS
	//Se crea el tablero de un tamaño aleatorio y se coloca una posición meta válida
	void establecer_tablero_y_pos_meta();

	int elegir_dir_aleatoria(Movimiento const& mov /*ent*/) const; //Da un valor perteneciente al intervalo [0,mov.num_dirs()-1]
	int elegir_pos_ficha_aleatoria(); //Da un valor perteneciente al intervalo [0,posFichasMovInv.size()-1]
	
	//Para la eliminación de elementos de las listas de fichas
	void elim_ficha_de_lista_fichas(int f, int c);
	void elim_ficha_de_lista_fichas_mov_inv(int f, int c);

	//Método similar a "posibles movimientos" pero para generar un tablero aleatorio
	void posibles_mov_inverso(Movimiento& mov /*ent/sal*/) const;
};
