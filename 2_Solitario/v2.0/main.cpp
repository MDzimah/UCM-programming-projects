//Manuel Dzimah Castro
#include "gestorPartidas.h"
#include <string>
#include <fstream>
#include <Windows.h>

bool string_es_num_entero_positivo(std::string const& s /*ent*/) {
	
	if (s.size() == 0) return false;

	for (int i = 0; i < s.size(); ++i)
		if (s[i] < -1 || s[i] > 255 || !std::isdigit(s[i])) return false;

	return true;
}

Movimiento leer_movimiento(Juego const& sol /*ent*/, char &seguirJugando) {
	
	while (true) {
		std::string f = "", c = "0";

		while (!string_es_num_entero_positivo(f) || !string_es_num_entero_positivo(c)) {
			std::cout << "\nSelecciona la ficha que quieras mover (fila y columna) o escribe \"0 0\" para salir: ";
			std::cin >> f;
			std::cin.get();
			std::getline(std::cin, c);
		}

		Movimiento mov(std::stoi(f), std::stoi(c));

		if (f == "0" || c == "0") {
			while (f != "S" && f != "N") {
				std::cout << char(168) << "Quieres seguir jugando [S/N]? ";
				std::getline(std::cin, f);
				std::cout << '\n';
			}
			if (f == "N") seguirJugando = false;
			else seguirJugando = true;

			return mov;
		}
		
		/*Dada una posición elegida por el usuario, se averigua las
		distintas posibilidades que se tiene para moverse*/
		sol.posibles_movimientos(mov);

		if (mov.num_dirs() > 1) {
			std::cout << "Selecciona una direccion\n";
			for (int i = 0; i < mov.num_dirs(); ++i)
				std::cout << '\t' << i + 1 << " - " << to_string(mov.direccion(i)) << '\n';

			std::cout << "Respuesta: ";
			std::string dirRespuesta;
			std::getline(std::cin, dirRespuesta);

			while (!string_es_num_entero_positivo(dirRespuesta) || std::stoi(dirRespuesta) > mov.num_dirs() || std::stoi(dirRespuesta) <= 0) {
				std::cout << "Respuesta: ";
				std::getline(std::cin, dirRespuesta);
			}

			mov.fijar_dir_activa(mov.direccion(std::stoi(dirRespuesta) - 1));
			return mov;
		}
		else if (mov.num_dirs() == 1) {
			mov.fijar_dir_activa(mov.direccion(0));
			return mov;
		}
		else {
			if (sol.posicion_valida(mov.fila(), mov.columna())) std::cout << "La ficha seleccionada no se puede mover.\n";
			else std::cout << "La posicion no es valida o es incorrecta.\n";
		}
	}
}

/*Devuelve "true" si se ha salido de una partida pero se desea seguir jugando. 
Si no se desea seguir jugando (hacer "logout"), se devuelve "false".*/
bool simulacion_juego(Juego& sol/*ent/sal*/) {
	do {
		char sJ = -1;
		//sJ = -1 -> No se ha dado respuesta a "seguir jugando"
		//sJ = 0 (false) -> El usuario no quiere seguir jugando
		//sJ = 1 (true) -> El usuario quiere seguir jugando

		Movimiento m = leer_movimiento(sol, sJ);
		
		if (sJ == 0) return false;
		else if (sJ == 1) return true;

		sol.jugar(m);
		system("cls"); //Borrar la consola
		sol.mostrar();
	} while (sol.estado() == JUGANDO);

	if (sol.estado() == GANADOR) std::cout  << '\n' << LGREEN << char(173) << "ENHORABUENA! Has ganado :)\n\n";
	else std::cout << '\n' << RED << char(173) << "GAME OVER! Te has quedado bloqueado\n\n";

	std::cout << RESET;

	std::string r = "";
	while (r != "S" && r != "N") {
		std::cout << char(168) << "Quieres seguir jugando [S/N]? ";
		std::getline(std::cin, r);
		std::cout << '\n';
	}

	return r == "S";
}

int main() {

	_CrtSetDbgFlag(_CRTDBG_ALLOC_MEM_DF | _CRTDBG_LEAK_CHECK_DF); //Para ver si hay "memory leaks"

	GestorPartidas g;
	std::ifstream archivoC;
	archivoC.open("partidas.txt", std::ios::app);

	if (archivoC.is_open()) g.cargar(archivoC);
	else {
		std::cout << "ERROR: No se pudo abrir el documento \"partidas.txt\"\n";
		return 0;
	}

	archivoC.close();

	std::cout << "Usuario (teclee \"FIN\" para terminar): ";
	std::string loginInfo;
	std::getline(std::cin, loginInfo);

	while (loginInfo != "FIN") {

		g.login(loginInfo);

		int indPart;
		if (g.tiene_partidas()) {
			g.mostrar_partidas();

			std::string res = "";
			while (!string_es_num_entero_positivo(res) || std::stoi(res) > g.num_partidas()) {
				std::cout << "Elige una partida o escribe \"0\" para crear una nueva partida aleatoria: ";
				std::getline(std::cin, res);
				std::cout << '\n';
			}

			if (res == "0") {
				std::string pasos = "";
				while (!string_es_num_entero_positivo(pasos)) {
					std::cout << "Indique el numero de pasos para crear el tablero aleatorio (puedes escribir \"MAX\"): ";
					std::getline(std::cin, pasos);
					if (pasos == "MAX") pasos = "777";
					std::cout << '\n';
				}

				/*Se suma 1 porque internamente el código está adaptado a la 
				elección del usuario, cuyas posibilidades van de 1 hasta n*/
				indPart = g.insertar_aleatoria(std::stoi(pasos)) + 1; 
			}
			else indPart = std::stoi(res);
		}
		else {
			std::cout << "No tienes partidas. Vamos a crear un juego aleatorio.\n\n";

			std::string pasos = "";
			while (!string_es_num_entero_positivo(pasos)) {
				std::cout << "Indique el numero de pasos para crear el tablero aleatorio (puedes escribir \"MAX\"): ";
				std::getline(std::cin, pasos);
				if (pasos == "MAX") pasos = "777";
				std::cout << '\n';
			}

			indPart = g.insertar_aleatoria(std::stoi(pasos)) + 1;
			
		}

		system("cls"); //Para borrar los tableros que se dan a elegir, si hay
		g.partida(indPart).mostrar();

		bool seguirJugando = simulacion_juego(g.partida(indPart));

		system("cls");

		if (g.partida(indPart).estado() == BLOQUEO || g.partida(indPart).estado() == GANADOR) g.eliminar_partida(indPart);

		if (!seguirJugando) {
			g.logout();
			//Por cuestiones estéticas
			std::cout << "LOGGING OUT";
			for (int i = 0; i < 3; ++i) {
				Sleep(300);
				std::cout << '.';
				
			}
			Sleep(250);
			//
			system("cls");
			std::cout << "Usuario (teclee \"FIN\" para terminar): ";
			std::getline(std::cin, loginInfo);
		}
	}

	std::ofstream archivoS;
	archivoS.open("partidas.txt");
	g.salvar(archivoS);
	archivoS.close();
	
	return 0;
}