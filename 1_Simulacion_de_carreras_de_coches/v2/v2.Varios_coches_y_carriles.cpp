//Nombre y apellidos: Manuel Kwabenanro Dzimah Castro


#include <iostream>
#include <fstream>
#include <string>
#include <iomanip>
#include <cstdlib>
#include <ctime>


//CONSTANTES y VARIABLES

const int LONG_CARRETERA = 10,
MAX_PASOS = 3,
TIEMPO_PARADO = 1,
NUM_CARRILES = 3,
MAX_CARRERAS = 3;

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
std::string CENTINELA = "XX";

enum tTipoPosicion { NORMAL, CLAVO, SORPRESA };

struct tCoche {
	int pos;
	int tiempoParado;
};

struct tCarril {
	tCoche coche;
	tTipoPosicion posiciones[LONG_CARRETERA];
};

using tCarretera = tCarril[NUM_CARRILES];

struct tClasificacion {
	std::string	idCarrera;
	int	clasificacion[NUM_CARRILES];
	int	cont;
};
struct tListaClasificacion {
	tClasificacion lista[MAX_CARRERAS];
	int	cont;
};



//FUNCIONES

//Inicializan el coche con su carril correspondiente
void iniciaCoche(tCoche& coche) {
	coche.pos = coche.tiempoParado = 0;
}
void iniciaCarril(tCarril& carril) {
	for (int k = 0; k < LONG_CARRETERA; ++k) carril.posiciones[k] = NORMAL;
	iniciaCoche(carril.coche);
}


//Leen el archivo dado por el usuario y cargan la carretera
tTipoPosicion stringToEnum(std::string s) { //Transforma el string "s" en su correspondiente elemento del tipo enumerado "tTipoPosicion".

	if (s == "CLAVO") return CLAVO;
	else if (s == "SORPRESA") return SORPRESA;
	else return NORMAL;

}
void leeCarril(std::ifstream& archivo, tCarril& carril) {

	std::string objEnCarretera;
	int numObj, posObj;

	archivo >> objEnCarretera;
	while (objEnCarretera != CENTINELA) { //Se lee hasta el centinela porque nos indica que ya no hay más objetos especiales sobre el carril en cuestión
		archivo >> numObj;
		for (int i = 0; i < numObj; ++i) {
			archivo >> posObj;
			carril.posiciones[posObj] = stringToEnum(objEnCarretera); //Se recurre a la funcion "stringToEnum" para realizar la transformacion necesaria
		}
		archivo >> objEnCarretera;
	}
}
bool cargaCarretera(tCarretera& carriles) {

	std::cout << "Introduzca el nombre del archivo de la carretera: ";
	std::string nombreDelArchivo;
	std::cin >> nombreDelArchivo;

	char saltoDeLinea;
	std::cin.get(saltoDeLinea);

	std::ifstream entrada;
	entrada.open(nombreDelArchivo);

	//Si se abre el archivo, se cargan los datos en "carretera" y se devuelve "true". De lo contrario, se informa del error, se devuelve "false" y se da al usuario otra oportunidad
	if (entrada.is_open()) {

		//En cada vuelta se inicializa un carril y se cargan los datos leídos del fichero a ese mismo carril
		for (int i = 0; i < NUM_CARRILES; ++i) {
			iniciaCarril(carriles[i]);
			leeCarril(entrada, carriles[i]);
		}

		entrada.close();

		return true;

	}
	else {
		std::cout << "ERROR: no se pudo abrir.\n";
		return false;
	}

}


//Dibujo de los tramos individuales de la carretera
void dibujaLineaHorizontalSuperior() {
	std::cout << CHAR_ESQUINA_SUPERIOR_IZQUIERDA << std::setw(LONG_CARRETERA) << std::setfill(CHAR_LINEA_HORIZONTAL) << CHAR_LINEA_HORIZONTAL << CHAR_ESQUINA_SUPERIOR_DERECHA << '\n';
}
void dibujaCarril(const	tCarril& carril) {

	std::cout << CHAR_LINEA_VERTICAL;

	for (int j = 0; j < LONG_CARRETERA; ++j) { //Según lo cargado en las posiciones de cada carril se muestran los caracteres "clavo", "sorpresa" y "normal" por encima del carril en el que va a circular el coche 
		if (carril.posiciones[j] == CLAVO) std::cout << CHAR_CLAVO;
		else if (carril.posiciones[j] == SORPRESA) std::cout << CHAR_SORPRESA;
		else std::cout << CHAR_NORMAL;
	}

	std::cout << CHAR_LINEA_VERTICAL << '\n' << CHAR_LINEA_VERTICAL;

	//Se muestra el carril por donde circula el coche teniendo en cuenta la posición del coche
	for (int k = 0; k < carril.coche.pos; ++k) std::cout << ' ';
	std::cout << CHAR_COCHE;
	for (int l = 0; l < LONG_CARRETERA - (carril.coche.pos + 1); ++l) std::cout << ' ';
	std::cout << CHAR_LINEA_VERTICAL << '\n';

}
void dibujaLineaDiscontinua() {

	std::cout << CHAR_LINEA_VERTICAL;
	for (int v = 0; v < LONG_CARRETERA / 2; ++v) std::cout << CHAR_LINEA_HORIZONTAL << ' ';

	if (LONG_CARRETERA % 2 == 0) std::cout << CHAR_LINEA_VERTICAL << '\n';
	else std::cout << CHAR_LINEA_HORIZONTAL << CHAR_LINEA_VERTICAL << '\n';

}
void dibujaLineaHorizontalInferior() {
	std::cout << CHAR_ESQUINA_INFERIOR_IZQUIERDA << std::setw(LONG_CARRETERA) << std::setfill(CHAR_LINEA_HORIZONTAL) << CHAR_LINEA_HORIZONTAL << CHAR_ESQUINA_INFERIOR_DERECHA << "\n\n";
}

//Dibujo global de la carretera
void dibujaCarretera(const tCarretera carriles) {

	dibujaLineaHorizontalSuperior();

	for (int carril = 0; carril < NUM_CARRILES; ++carril) {
		dibujaCarril(carriles[carril]);
		if (carril < NUM_CARRILES - 1) dibujaLineaDiscontinua();
	}

	dibujaLineaHorizontalInferior();
}


//Verifican si una posición es de un tipo especial ("sorpresa" o "clavo"), la última verifica si una posición está en la carretera
bool esSorpresa(const tTipoPosicion pos[], int p) {
	return pos[p] == SORPRESA;
}
bool esClavo(const tTipoPosicion pos[], int p) {
	return pos[p] == CLAVO;
}
bool enCarretera(int n) {
	return n >= 0 && n < LONG_CARRETERA;
}


//Busca la posicion sorpresa más cercana. Si el modo DEBUG es "true" (modo depuración) entonces pregunta al usuario si quiere avanzar o retroceder a la sorpresa más cercana. Del contrario (modo normal), esto último se hará de manera aleatoria
int	buscaSiguientePosSorpresa(const	tCarril& carril, int incr) {

	int m = carril.coche.pos;
	m += incr; //Como la posicion del coche es una posicion sorpresa, para no saltarse el "while", modifico "m" sumándole "incremento"
	while (enCarretera(m) && carril.posiciones[m] != SORPRESA)
		m += incr;

	if (enCarretera(m)) return m;
	else return 0; //En el caso de que no haya una posicion sorpresa delante o detrás de la que nos hallamos, se coloca el coche en la posición "0", en el principio
}


//Calcula la nueva posición del coche. En el modo depuración, el número de pasos es introducido por el usuario. En el modo normal, el número de pasos es generado aleatoriamente
int avanza(int posCoche) {

	int numPasos, posNuevaCoche;

	if (!DEBUG) {

		numPasos = 1 + rand() % MAX_PASOS; //Genera un número aleatorio entre 1 y MAX_PASOS, que es sumado luego a la posición del coche
		posNuevaCoche = posCoche + numPasos;

		if (numPasos > 1) std::cout << "El coche avanza " << numPasos << " pasos\n\n";
		else std::cout << "El coche avanza " << numPasos << " paso\n\n";

		char respuestaModoNormal;
		std::cout << "Pulse la tecla \"Enter\" para continuar";
		std::cin.get(respuestaModoNormal);
		std::cout << '\n';

		while (respuestaModoNormal != '\n') {
			std::cout << "Pulse la tecla \"Enter\" para continuar";
			std::cin.get(respuestaModoNormal);
			std::cout << '\n';
		}

	}
	else {

		std::cout << "Pasos que avanza o retrocede el coche: ";
		std::cin >> numPasos;
		std::cout << '\n';

		//Si se introduce algo que no sea un número, se interpreta como un 0
		if (std::cin.fail()) {
			std::cin.clear();
			std::cin.ignore();
			numPasos = 0;
			std::cout << "No se ha introducido un numero. El coche no avanza\n\n";
		}

		char saltoDeLinea;
		std::cin.get(saltoDeLinea);

		posNuevaCoche = posCoche + numPasos;

		if (posNuevaCoche < 0) return 0;
	}

	return posNuevaCoche;
}


//Analizan la posición del coche, una si el coche del carril en cuestión ha llegado a la meta y la otra si se halla en una posición especial
bool haLlegado(int posCoche) {
	return posCoche >= LONG_CARRETERA;
}
bool calculaPosicion(tCarril& carril) {

	int posOriginCoche = 0;

	/*Como se indica que esta funcion tiene que devolver "true" solo si la posicion del coche es sorpresa, hace falta almacenar la posicion original del coche antes
	de modificarla. De este modo, al volver a la funcion "avanzaCarril", "calculaPosicion" es "true" y se dibuja la carretera correspondiente en lugar de saltarlo*/
	posOriginCoche = carril.coche.pos;

	if (esClavo(carril.posiciones, carril.coche.pos) && carril.coche.tiempoParado == 0) carril.coche.tiempoParado = TIEMPO_PARADO + 1; //Para que un coche ya pinchado en un turno anterior no vuelva a estar inmovilizado "TIEMPO_PARADO" de turnos, se añade la condición de que el tiempoParado de coche ha de ser 0 necesariamente
	else if (esSorpresa(carril.posiciones, carril.coche.pos)) {

		int incremento = 1; //Lo inicializo a 1 en lugar de a 0 ya que evita hacer una comparación extra si estamos en modo debug

		if (!DEBUG) {
			incremento = rand() % 2;

			if (incremento == 0) --incremento;

		}
		else {

			std::string respModoDebug;

			std::cout << char(168) << "Desea avanzar o retroceder hacia la posicion de la sorpresa mas cercana (1/-1)? ";
			std::cin >> respModoDebug;

			//Si las respuestas son diferentes de "1" y "-1" se pregunta al usuario de nuevo
			while (respModoDebug != "1" && respModoDebug != "-1") {
				std::cout << char(168) << "Desea avanzar o retroceder hacia la posicion de la sorpresa mas cercana (1/-1)? ";
				std::cin >> respModoDebug;
				std::cout << '\n';
			}

			char saltoDeLinea;
			std::cin.get(saltoDeLinea);
			std::cout << '\n';

			if (respModoDebug == "-1") incremento -= 2;

		}

		carril.coche.pos = buscaSiguientePosSorpresa(carril, incremento);

		std::cout << "POSICION SORPRESA\n\n";


		char respuestaModoNormal;
		std::cout << "Pulse la tecla \"Enter\" para continuar";
		std::cin.get(respuestaModoNormal);
		std::cout << '\n';

		while (respuestaModoNormal != '\n') {
			std::cout << "Pulse la tecla \"Enter\" para continuar";
			std::cin.get(respuestaModoNormal);
			std::cout << '\n';
		}


		if (carril.coche.pos == 0) std::cout << char(173) << "Vaya, no hay sorpresas en tu camino! Empiezas de nuevo :(";
		else if (incremento == 1) std::cout << char(173) << "Has tenido suerte! :) Vas a avanzar a la posicion " << carril.coche.pos;
		else std::cout << char(173) << "Mala suerte! :( Vas a retroceder a la posicion " << carril.coche.pos;

		std::cout << "\n\n";
	}

	return esSorpresa(carril.posiciones, posOriginCoche);
}


//Avanza cada carril individual y analiza las posiciones especiales gracias a las funciones "calculaPosicion" y "haLlegado"
bool avanzaCarril(tCarretera carretera, int i) {

	int posOriginalCoche = 0;
	posOriginalCoche = carretera[i].coche.pos; //Se almacena la posición del coche antes de mover el coche

	std::cout << std::setw(120) << std::setfill('-') << "-\n\n"; //Para facilitar la lectura de la simulación

	std::cout << "CARRIL " << i << "\n\n";

	if (!haLlegado(carretera[i].coche.pos) && carretera[i].coche.tiempoParado == 0) carretera[i].coche.pos = avanza(carretera[i].coche.pos); //Se actualiza la posición del coche

	if (haLlegado(carretera[i].coche.pos)) {
		carretera[i].coche.pos = LONG_CARRETERA;
		dibujaCarretera(carretera); //Se dibuja la carretera con el coche ya en la meta. Como "avanza carril" solo se invoca en "simulaCarrera", si la posición del coche es menor que "LONG_CARRETERA", se sigue con lo que hay después en el main
		std::cout << "El coche " << i << " ha llegado a la meta\n\n";
	}
	else {
		dibujaCarretera(carretera);

		/*Con la carretera dibujada, la posición que se observa en la pantalla es analizada como posición "sorpresa" o "clavo".
		Si no es ninguna de las dos, indirectamente se analiza la posción como normal invocando de nuevo a "avanzaCarril" desde "simulaCarrera" */

		if (esClavo(carretera[i].posiciones, carretera[i].coche.pos) || carretera[i].coche.tiempoParado > 0) { //Evalúo primero si la posición en cuestión es "clavo" para evitar llamar a "calculaPosicion" inecesariamente

			calculaPosicion(carretera[i]);

			char respuestaModoNormal;

			std::cout << "Pulse la tecla \"Enter\" para continuar";
			std::cin.get(respuestaModoNormal);
			std::cout << '\n';

			while (respuestaModoNormal != '\n') {
				std::cout << "Pulse la tecla \"Enter\" para continuar";
				std::cin.get(respuestaModoNormal);
				std::cout << '\n';
			}

			if (carretera[i].coche.tiempoParado == TIEMPO_PARADO + 1) {
				if (TIEMPO_PARADO == 1) std::cout << "El coche se ha pinchado. Va estar " << carretera[i].coche.tiempoParado - 1 << " turno sin moverse (sin incluir este)...";
				else std::cout << "El coche se ha pinchado. Va estar " << carretera[i].coche.tiempoParado - 1 << " turnos sin moverse (sin incluir este)...";
			}
			else {
				if (carretera[i].coche.tiempoParado > 1) std::cout << "Coche en carril " << i << " pinchado. Le quedan " << carretera[i].coche.tiempoParado << " turnos para moverse...";
				else std::cout << "Coche en carril " << i << " pinchado. Le queda " << carretera[i].coche.tiempoParado << " turno para moverse...";
			}
			--carretera[i].coche.tiempoParado;

			std::cout << "\n\n";

		}
		else if (calculaPosicion(carretera[i])) dibujaCarretera(carretera);

	}

	return posOriginalCoche != LONG_CARRETERA && haLlegado(carretera[i].coche.pos); //Es necesario saber si anteriormente ya estaba el coche en la meta para evitar que se actualice la clasificación erróneamente
}

//Con la función anterior, aquí se avanzan todos los carriles
void avanzaCarriles(tCarretera carretera, tClasificacion& clasificacion) {

	for (int carril = 0; carril < NUM_CARRILES; ++carril) {

		//En cualquier caso se avanzan los carriles (y siempre en orden ascendente). No obstante, en el momento en el que un coche llega a la meta se actualiza la clasificación
		if (avanzaCarril(carretera, carril)) {
			clasificacion.clasificacion[clasificacion.cont] = carril;
			++clasificacion.cont;
		}
	}

}

//Mantiene la simulación en curso solo si todos los coches no han llegado al final
tClasificacion simulaCarrera(tCarretera carretera) {

	tClasificacion clasificacion;
	clasificacion.cont = 0;

	std::cout << "\nIntroduzca el identificador para la carrera: ";

	std::getline(std::cin, clasificacion.idCarrera);

	dibujaCarretera(carretera);

	while (clasificacion.cont < NUM_CARRILES) avanzaCarriles(carretera, clasificacion);

	return clasificacion;
}


//Subprogramas encargados de gestionar y mostrar la clasificación
std::ostream& operator<<(std::ostream& salida, tClasificacion const& cl) {

	for (int y = 0; y < NUM_CARRILES; ++y)
		salida << "    Puesto " << y + 1 << ": coche en el carril " << cl.clasificacion[y] << '\n';

	return salida;
}
void iniciaListaClasificacion(tListaClasificacion& listaC) {
	listaC.cont = 0;
}

void eliminaClasificacion(tListaClasificacion& listaC, int pos) {

	for (int b = pos; b < MAX_CARRERAS - 1; ++b) listaC.lista[b] = listaC.lista[b + 1];
}
void insertaClasificacion(tListaClasificacion& listaC, const tClasificacion& clasificacion) {

	/*Si la lista no está todavía llena, las clasificaciones van entrando en la lista de clasificaciones en orden
	ascendente. En caso contrario, se elimina la primera clasificación de la lista y entra la nueva al final de la lista*/

	if (listaC.cont < MAX_CARRERAS)
		listaC.lista[listaC.cont] = clasificacion;
	else {
		eliminaClasificacion(listaC, 0);
		listaC.lista[MAX_CARRERAS - 1] = clasificacion;
	}

}

void guardarListaClasificacion(const tListaClasificacion& listaC) {

	std::ofstream archGuardarListaClasf;

	/*Si la lista de clasificaciones se está llenando, las clasificaciones de las carreras son añadidas sucesivamente a "clasificacion.txt".
	Cuando la lista se llena completamente, no queda más remedio que, en cada carrera sucesiva de la simulación, guardar la lista de clasificaciones entera en "clasificacion.txt"*/
	if (listaC.cont < MAX_CARRERAS) {
		archGuardarListaClasf.open("clasificacion.txt", std::ios::app);
		archGuardarListaClasf << listaC.lista[listaC.cont].idCarrera << '\n' << (archGuardarListaClasf, listaC.lista[listaC.cont]);
	}
	else {
		archGuardarListaClasf.open("clasificacion.txt");
		for (int p = 0; p < MAX_CARRERAS; ++p)
			archGuardarListaClasf << listaC.lista[p].idCarrera << '\n' << (archGuardarListaClasf, listaC.lista[p]);
	}

	archGuardarListaClasf.close();

}


int main() {

	tCarretera carretera;
	while (!cargaCarretera(carretera));

	tListaClasificacion lista;
	iniciaListaClasificacion(lista);

	std::string respOtraSim = "S";

	//Se abre el fichero "clasificacion.txt" y se vacía su contenido cada vez que se hace una nueva simulación
	std::ofstream archGuardarListaClasf;
	archGuardarListaClasf.open("clasificacion.txt");


	while (respOtraSim == "S") {

		srand(time(NULL));
		char saltoDeLinea;

		insertaClasificacion(lista, simulaCarrera(carretera));

		std::cout << "FIN DE LA SIMULACION\n\n";

		/*Se imprime la clasificación de la carrera en la pantalla y se guarda la lista de clasificaciones en el fichero "clasificacion.txt" */
		if (lista.cont < MAX_CARRERAS) {
			std::cout << "Clasificacion de la carrera\n" << lista.lista[lista.cont];
			guardarListaClasificacion(lista);
			++lista.cont;
		}
		else {
			std::cout << "Clasificacion de la carrera\n" << lista.lista[MAX_CARRERAS - 1];
			guardarListaClasificacion(lista);
		}


		std::cout << '\n' << char(168) << "Desea realizar otra simulacion (S/N)? ";
		std::cin >> respOtraSim;

		//Si las respuestas son diferentes de "S" y "N" se pregunta al usuario de nuevo
		while (respOtraSim != "S" && respOtraSim != "N") {
			std::cout << char(168) << "Desea realizar otra simulacion (S/N)? ";
			std::cin >> respOtraSim;
		}

		std::cout << std::setw(120) << std::setfill(CHAR_LINEA_HORIZONTAL) << CHAR_LINEA_HORIZONTAL << '\n';

		std::cin.get(saltoDeLinea);

		//Se inicializan los coches de todos los carriles
		if (respOtraSim == "S") {
			for (int n = 0; n < NUM_CARRILES; ++n)
				iniciaCoche(carretera[n].coche);
		}
	}


	return 0;
}