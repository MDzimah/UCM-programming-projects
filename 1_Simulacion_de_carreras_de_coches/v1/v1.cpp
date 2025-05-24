//Nombre y apellidos: Manuel Kwabenanro Dzimah Castro

#include <iostream>
#include <fstream>
#include <string>
#include <iomanip>


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



tTipoPosicion stringToEnum (std::string s) { //Transforma el string "s" en su correspondiente elemento del tipo enumerado "tTipoPosicion".

	if (s == "CLAVO") return CLAVO;
	else if (s == "SORPRESA") return SORPRESA;
	else return NORMAL;

}

bool cargaCarretera (tCarretera carretera) {

	std::cout << "Introduzca el nombre del archivo de la carretera: ";
	std::string nombreDelArchivo;
	std::cin >> nombreDelArchivo; //Pide el nombre del archivo al usuario.

	std::ifstream entrada;
	entrada.open(nombreDelArchivo);


	if (entrada.is_open()) { //Si se abre el archivo, se cargan los datos en "carretera" y se devuelve "true". De lo contrario, se informa del error, se devuelve "false" y se da al usuario otra oportunidad.

		std::string objEnCarretera;
		int numObj, posObj;

		entrada >> objEnCarretera; //Leo esto fuera del bucle para evitar hacer una vuelta innecesaria.
		while (objEnCarretera != "XX") {
			entrada >> numObj;
			for (int i = 0; i < numObj; ++i) {
				entrada >> posObj;
				carretera[posObj] = stringToEnum(objEnCarretera); //Se recurre a la funcion "stringToEnum" para realizar la transformacion necesaria.
			}
			entrada >> objEnCarretera;
		}

		return true;

	}
	else {
		std::cout << "ERROR: No se pudo abrir.\n";
		return false;
	}

}


//Dibujo de los tramos de la carretera.
void dibujaLineaHorizontalSuperior() {
	std::cout << CHAR_ESQUINA_SUPERIOR_IZQUIERDA << std::setw(LONG_CARRETERA) << std::setfill(CHAR_LINEA_HORIZONTAL) << CHAR_LINEA_HORIZONTAL << CHAR_ESQUINA_SUPERIOR_DERECHA << '\n';
}

void dibujaLineaHorizontalInferior() {
	std::cout << CHAR_ESQUINA_INFERIOR_IZQUIERDA << std::setw(LONG_CARRETERA) << std::setfill(CHAR_LINEA_HORIZONTAL) << CHAR_LINEA_HORIZONTAL << CHAR_ESQUINA_INFERIOR_DERECHA << '\n';
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


//Dibujo global de la carretera.
void dibujaCarretera(const tCarretera carretera, int posCocche) {
	dibujaLineaHorizontalSuperior();
	dibujaCarril(carretera, posCocche);
	dibujaLineaHorizontalInferior();
}



int main() {

	int posicionCoche = 0;
	tCarretera carretera;

	while (!cargaCarretera(carretera)); //Se cargan los datos a "carretera"

	dibujaCarretera(carretera, posicionCoche); //Con los datos de "carretera", se dibuja la carretera

	return 0;

}