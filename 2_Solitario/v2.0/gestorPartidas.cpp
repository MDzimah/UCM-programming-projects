//Manuel Dzimah Castro
#include "gestorPartidas.h"
#include "memoryleaks.h"

GestorPartidas::~GestorPartidas() {
	for (int i = 0; i < usuarios.size(); ++i) {
		for (int j = 0; j < usuarios[i]->partidas.size(); ++j) 
			delete usuarios[i]->partidas[j];

		delete usuarios[i];
	}
}

bool GestorPartidas::cargar(std::istream& entrada) {

	int i;
	entrada >> i;

	if (!entrada) return false;

	while (i > 0) {
		UsuarioPartidas* u = new UsuarioPartidas;
		int numPartidasUser;
		entrada >> u->user >> numPartidasUser;
		//u->user ~ (*u).user

		while (numPartidasUser > 0) {
			Juego* puntJ = new Juego;
			puntJ->cargar(entrada);
			u->partidas.push_back(puntJ);
			--numPartidasUser;
		}
		usuarios.push_back(u);
		--i;
	}

	return true;
}

bool GestorPartidas::salvar(std::ostream& salida) {
	
	salida << usuarios.size() << '\n';

	if (!salida) return false;

	for (int i = 0; i < usuarios.size(); ++i){

		salida << usuarios[i]->user << '\n' << usuarios[i]->partidas.size() << '\n';

		for (int j = 0; j < usuarios[i]->partidas.size(); ++j)
			usuarios[i]->partidas[j]->salvar(salida);
	}

	return true;
}

void GestorPartidas::login(Usuario const& usuario) {
	int p;
	if (!bs(usuario, p)) {
		UsuarioPartidas* u = new UsuarioPartidas;
		u->user = usuario;
		insertar(u, p);
	}
	usuario_activo = p;
}

void GestorPartidas::logout() {
	if (!tiene_partidas()) 
		eliminar_usuario_de_la_lista();
	
	usuario_activo = NOLOGIN;
}

void GestorPartidas::mostrar_partidas() const {
	for (int j = 0; j < usuarios[usuario_activo]->partidas.size(); ++j) {
		std::cout << '\n' << j + 1 << ' ' << std::setw(100) << std::setfill('-') << '-' << std::setfill(' ') << "\n\n";
		usuarios[usuario_activo]->partidas[j]->mostrar();
	}
	std::cout << '\n';
}

int GestorPartidas::insertar_aleatoria(int movimientos) {
	Juego* j = new Juego(movimientos);
	usuarios[usuario_activo]->partidas.push_back(j);
	return usuarios[usuario_activo]->partidas.size() - 1;
}

void GestorPartidas::eliminar_partida(int part) {
	--part;
	delete usuarios[usuario_activo]->partidas[part];
	for (int i = part; i < usuarios[usuario_activo]->partidas.size() - 1; ++i)
		usuarios[usuario_activo]->partidas[i] = usuarios[usuario_activo]->partidas[i+1];

	usuarios[usuario_activo]->partidas.pop_back();
}

bool GestorPartidas::bs(Usuario const& userID, int &pos) {
	int iz = -1;
	int d = usuarios.size();

	while (iz + 1 != d) {
		int mitad = (iz + d) / 2;
		if (usuarios[mitad]->user < userID) iz = mitad;
		else d = mitad;
	}

	pos = d;

	if (d == usuarios.size()) return false;
	else return usuarios[d]->user == userID;
}

void GestorPartidas::insertar(UsuarioPartidas* const& u, int pos) {
	usuarios.push_back({});
	for (int i = usuarios.size() - 1; i > pos; --i)
		usuarios[i] = usuarios[i - 1];
	
	usuarios[pos] = u;
}

void GestorPartidas::eliminar_usuario_de_la_lista() {
	delete usuarios[usuario_activo];
	for (int i = usuario_activo; i < usuarios.size()-1; ++i)
		usuarios[i] = usuarios[i + 1];

	usuarios.pop_back();
}