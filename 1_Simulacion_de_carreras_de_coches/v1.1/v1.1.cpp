//Nombre y apellidos: Manuel Kwabenanro Dzimah Castro

#include <iostream>
#include <fstream>
#include <string>
#include <iomanip>
#include <cstdlib>
#include <ctime>

//VARIABLES
const int LONG_CARRETERA = 10,
MAX_PASOS = 2,
TIEMPO_PARADO = 2;

const bool DEBUG = false;

const char CHAR_LINEA_HORIZONTAL = char(205),
CHAR_ESQUINA_SUPERIOR_IZQUIERDA = char(201),
CHAR_ESQUINA_SUPERIOR_DERECHA = char(187),
CHAR_ESQUINA_INFERIOR_IZQUIERDA = char(200),
CHAR_ESQUINA_INFERIOR_DERECHA = char(188),
CHAR_LINEA_VERTICAL = char(186),
CHAR_COCHE = char(254),
CHAR_CLAVO = char(193),
CHAR_SORPRESA = char(63),
CHAR_NORMAL = char(32);

enum tTipoPosicion { NORMAL, CLAVO, SORPRESA };
using tCarretera = tTipoPosicion[LONG_CARRETERA];

struct tCoche {
	int pos;
	int tiempoParado;
};


//FUNCIONES

//Inicializan el coche y la carretera
void iniciaCoche(tCoche& coche) {
	coche.pos = coche.tiempoParado = 0;
}
void iniciaCarretera(tCarretera carretera) {
	for (int n = 0; n < LONG_CARRETERA; ++n) carretera[n] = NORMAL;
}



//Leen el archivo dado por el usuario y cargan la carretera
tTipoPosicion stringToEnum(std::string s) { //Transforma el string "s" en su correspondiente elemento del tipo enumerado "tTipoPosicion".

	if (s == "CLAVO") return CLAVO;
	else if (s == "SORPRESA") return SORPRESA;
	else return NORMAL;

}
bool cargaCarretera(tCarretera carretera) {
	
	std::cout << "Introduzca el nombre del archivo de la carretera: ";
	std::string nombreDelArchivo;
	std::cin >> nombreDelArchivo; // Pide el nombre del archivo al usuario 

	char saltoDeLinea;
	std::cin.get(saltoDeLinea); //Evita que el salto de línea al introducir el nombre del archivo se capte en la función "avanza"

	std::ifstream entrada;
	entrada.open(nombreDelArchivo);


	if (entrada.is_open()) { //Si se abre el archivo, se cargan los datos en "carretera" y se devuelve "true". De lo contrario, se informa del error, se devuelve "false" y se da al usuario otra oportunidad

		std::string objEnCarretera;
		int numObj, posObj;

		entrada >> objEnCarretera; //Para evitar hacer una vuelta innecesaria
		while (objEnCarretera != "XX") {
			entrada >> numObj;
			for (int i = 0; i < numObj; ++i) {
				entrada >> posObj;
				carretera[posObj] = stringToEnum(objEnCarretera); //Se recurre a la funcion "stringToEnum" para realizar la transformacion necesaria
			}
			entrada >> objEnCarretera;
		}
		entrada.close();

		return true;

	}
	else {
		std::cout << "ERROR: No se pudo abrir.\n";
		return false;
	}

}



//Dibujo de los tramos individuales de la carretera
void dibujaLineaHorizontalSuperior() {
	std::cout << CHAR_ESQUINA_SUPERIOR_IZQUIERDA << std::setw(LONG_CARRETERA) << std::setfill(CHAR_LINEA_HORIZONTAL) << CHAR_LINEA_HORIZONTAL << CHAR_ESQUINA_SUPERIOR_DERECHA << '\n';
}
void dibujaLineaHorizontalInferior() {
	std::cout << CHAR_ESQUINA_INFERIOR_IZQUIERDA << std::setw(LONG_CARRETERA) << std::setfill(CHAR_LINEA_HORIZONTAL) << CHAR_LINEA_HORIZONTAL << CHAR_ESQUINA_INFERIOR_DERECHA << "\n\n";
}
void dibujaCarril(const tCarretera carretera, int posCoche) {

	std::cout << CHAR_LINEA_VERTICAL;

	for (int j = 0; j < LONG_CARRETERA; ++j) { //Segun lo cargado en las posiciones del array de "carretera", se muestran los caracteres "clavo", "sorpresa" y "normal" (declarados al principio) sobre el primer carril 
		if (carretera[j] == CLAVO) std::cout << CHAR_CLAVO;
		else if (carretera[j] == SORPRESA) std::cout << CHAR_SORPRESA;
		else std::cout << CHAR_NORMAL;
	}

	std::cout << CHAR_LINEA_VERTICAL << '\n' << CHAR_LINEA_VERTICAL;

	//Se muestra el segundo carril teniendo en cuenta la posicion del coche
	for (int k = 0; k < posCoche; ++k) std::cout << ' ';
	std::cout << CHAR_COCHE;
	for (int l = 0; l < LONG_CARRETERA - (posCoche + 1); ++l) std::cout << ' ';
	std::cout << CHAR_LINEA_VERTICAL << '\n';

}

//Dibujo global de la carretera
void dibujaCarretera(const tCarretera carretera, int posCocche) {
	dibujaLineaHorizontalSuperior();
	dibujaCarril(carretera, posCocche);
	dibujaLineaHorizontalInferior();
}



//Verifican si una posición es de un tipo especial (sorpresa o clavo), la última verifica si una posición está en la carretera
bool esSorpresa(const tCarretera carretera, int posCoche) {
	return carretera[posCoche] == SORPRESA;
}
bool esClavo(const tCarretera carretera, int posCoche) {
	return carretera[posCoche] == CLAVO;
}
bool enCarretera(int n) {
	return n >= 0 && n < LONG_CARRETERA;
}

//Busca la posicion sorpresa más cercana. Si el modo DEBUG es "true" (modo depuración) entonces pregunta al usuario si quiere avanzar o retroceder a la sorpresa más cercana. Del contrario (modo normal), esto último es de manera aleatoria
int	buscaPosicionSorpresa(const tCarretera carretera, int posIni, int incr) {

	int m = posIni;
	m += incr; //Puesto que "posIni" es inicialmente una posicion sorpresa, para no saltarse el "while", hay que modificar "m" sumándole "incremento"
	while (enCarretera(m) && carretera[m] != SORPRESA) m += incr;
	

	if (enCarretera(m)) return m;
	else return 0; //En el caso de que no haya una posicion sorpresa delante o detrás de la que nos hallamos, se coloca el coche en la posición "0", el principio
}

//Calcula la nueva posición del coche. En el modo depuración, el número de pasos es introducido por el usuario. En el modo normal, el número de pasos es generado aleatoriamente
int avanza(int posCoche) {

	int numPasos, posNuevaCoche;

	if (!DEBUG) {

		char respuestaModoNormal;
		
		std::cout << "Pulse la tecla " << "\"Enter\"" << " para continuar";
		std::cin.get(respuestaModoNormal); //Se capta el primer salto de línea ("Enter") después de imprimir la frase, no un salto de línea anterior
		std::cout << '\n';
		
		while (respuestaModoNormal != '\n') {
			std::cout << "Pulse la tecla " << "\"Enter\"" << " para continuar";
			std::cin.get(respuestaModoNormal);
			std::cout << '\n';
		}
		
		std::cout << std::setw(120) << std::setfill('-') << "-\n\n"; //Para facilitar la lectura de la simulación

		numPasos = 1 + rand() % MAX_PASOS; //Genera un número aleatorio entre 1 y MAX_PASOS, que es sumado luego a la posición del coche
		posNuevaCoche = posCoche + numPasos;

		if (numPasos > 1) std::cout << "El coche avanza " << numPasos << " pasos\n";
		else std::cout << "El coche avanza " << numPasos << " paso\n";

	}
	else {

		std::cout << std::setw(120) << std::setfill('-') << "-\n\n"; //Para facilitar la lectura de la simulación

		std::cout << "Pasos que avanza o retrocede el coche: ";
		std::cin >> numPasos;

		char saltoDeLinea;
		std::cin.get(saltoDeLinea);

		posNuevaCoche = posCoche + numPasos; 

		if (posNuevaCoche < 0) return 0;
	}

	return posNuevaCoche;
}

//Analiza el caso en el que la posición del coche es mayor o igual que la meta
bool haLlegado(int posCoche) {
	return posCoche >= LONG_CARRETERA;
}

//Analiza la posición del coche
bool calculaPosicion(const tCarretera carretera, tCoche& coche) {

	int posOriginCoche = 0;

	if (esClavo(carretera, coche.pos)) coche.tiempoParado = TIEMPO_PARADO;
	else if (esSorpresa(carretera, coche.pos)) {
		int incremento;

		if (!DEBUG) {
			incremento = rand() % 2;

			if (incremento == 1) incremento = 1;
			else incremento = -1;
		}
		else {

			int respuestaSorpresa;
			std::cout << char(168) << "Desea avanzar o retroceder hacia la posicion de la sorpresa mas cercana (1/-1)? ";
			std::cin >> respuestaSorpresa;
			
			char saltoDeLinea;
			std::cin.get(saltoDeLinea); //Evita que el salto de línea al introducir la respuesta se capte en la función "avanza"
			std::cout << '\n';


			//Si las respuestas son diferentes de "1" y "-1" se pregunta al usuario de nuevo
			while (respuestaSorpresa != 1 && respuestaSorpresa != -1) {
				std::cout << char(168) << "Desea avanzar o retroceder hacia la posicion de la sorpresa mas cercana (1/-1)? ";
				std::cin >> respuestaSorpresa;
				std::cin.get(saltoDeLinea);
				std::cout << '\n';
			}

			if (respuestaSorpresa == 1) incremento = 1;
			else incremento = -1;

		}

		/*Como se indica que esta funcion tiene que devolver "true" solo si la posicion del coche es sorpresa, hace falta almacenar la posicion original del coche antes 
		de modificarla. De este modo, al volver a la funcion "avanza carril", calclaPosicion es "true" y se dibuja la carretera correspondiente en lugar de saltarlo*/
		posOriginCoche = coche.pos; 

		coche.pos = buscaPosicionSorpresa(carretera, coche.pos, incremento);
	}
	
	return esSorpresa(carretera,posOriginCoche); //PARA QUE SIRVE ESTO (PREGUNTAR A PITA)
}



//Actualiza la posición del coche con las datos conseguidos del análisis de la posición del coche en "calculaPosición"
void avanzaCarril(const tCarretera carretera, tCoche& coche) {
	
	if (!haLlegado(coche.pos) && coche.tiempoParado == 0) { //Se actualiza la posición del coche
		coche.pos = avanza(coche.pos); 
	}
	if (haLlegado(coche.pos)) {
		coche.pos = LONG_CARRETERA;
		dibujaCarretera(carretera, coche.pos); //Se dibuja la carretera con el coche ya en la meta. Como "avanza carril" solo se invoca en "simulaCarrera", si la posición del coche es menor que "LONG_CARRETERA", se sigue con lo que hay después en el main
	}
	else {
		dibujaCarretera(carretera, coche.pos);
		
		/*Con la carretera dibujada, la posición que se observa en la pantalla es analizada como posición "sorpresa" o "clavo". 
		Si no es ninguna de las dos, indirectamente se analiza la posción como normal invocando de nuevo a "avanzaCarril" desde "simulaCarrera" */
		if (calculaPosicion(carretera, coche)) {

			char respuestaModoNormal;

			std::cout << "Pulse la tecla " << "\"Enter\"" << " para continuar";
			std::cin.get(respuestaModoNormal);
			std::cout << '\n';

			while (respuestaModoNormal != '\n') {
				std::cout << "Pulse la tecla " << "\"Enter\"" << " para continuar";
				std::cin.get(respuestaModoNormal);
				std::cout << '\n';
			}

			std::cout << "POSICION SORPRESA :) El coche ahora se halla en la posicion " << coche.pos << '\n';
			dibujaCarretera(carretera, coche.pos);
			
		}
		else if (esClavo(carretera, coche.pos)) {
			calculaPosicion(carretera, coche);

			char respuestaModoNormal;

			std::cout << "Pulse la tecla " << "\"Enter\"" << " para continuar";
			std::cin.get(respuestaModoNormal);
			std::cout << '\n';

			while (respuestaModoNormal != '\n') {
				std::cout << "Pulse la tecla " << "\"Enter\"" << " para continuar";
				std::cin.get(respuestaModoNormal);
				std::cout << '\n';
			}

			std::cout << "El coche se ha pinchado. Va estar inmovilizado " << coche.tiempoParado << " turnos\n";
			for (int g = coche.tiempoParado; g > 1; --g) std::cout << "Le quedan " << g << " turnos para moverse\n";
			std::cout << "Le queda 1 turno para moverse\n\n";
			coche.tiempoParado = 0;
		}
	}
}

//Mantiene la simulación en curso mientras se de la condición dada
void simulaCarrera(const tCarretera carretera, tCoche& coche) {
	dibujaCarretera(carretera, coche.pos);
	while (coche.pos < LONG_CARRETERA) avanzaCarril (carretera,coche);
}




int main() {
	srand(time(NULL));

	tCarretera carretera;
	tCoche coche;

	iniciaCarretera(carretera);

	while (!cargaCarretera(carretera)); //Carga los datos a "carretera", un array de tipo "tCarretera" (enumerado)

	iniciaCoche(coche); //Inicializa un coche

	simulaCarrera(carretera,coche); //Se simula la carrera

	std::string respuestaOtraSimulacion;
	std::cout << char(168) << "Desea realizar otra simulacion (S/N)? ";
	std::cin >> respuestaOtraSimulacion;
	std::cout << '\n';

	//Si las respuestas son diferentes de "S" y "N" se pregunta al usuario de nuevo
	while (respuestaOtraSimulacion != "S" && respuestaOtraSimulacion != "N") {
		std::cout << char(168) << "Desea realizar otra simulacion (S/N)? ";
		std::cin >> respuestaOtraSimulacion;
		std::cout << '\n';
	} 

	if (respuestaOtraSimulacion == "S") main();
	else return 0;
	
}