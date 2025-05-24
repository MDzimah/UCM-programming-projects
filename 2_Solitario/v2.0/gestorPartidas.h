//Manuel Dzimah Castro
#pragma once
#include "lista.h"
#include "juego.h"
#include <iomanip>

using Usuario = std::string;

class GestorPartidas {
public:
	GestorPartidas() : usuario_activo(NOLOGIN) {}
	~GestorPartidas();
	bool cargar(std::istream&/*ent/sal*/ entrada);
	bool salvar(std::ostream&/*ent/sal*/ salida);
	void login(Usuario const& usuario);
	void logout();
	bool tiene_partidas() const {
		return usuarios[usuario_activo]->partidas.size() != 0;
	}
	int num_partidas() const {
		return usuarios[usuario_activo]->partidas.size();
	}
	void mostrar_partidas() const;
	int insertar_aleatoria(int movimientos);
	Juego& partida(int part) { 
		return *(usuarios[usuario_activo]->partidas[part-1]);
	}
	void eliminar_partida(int part);

private:
	static const int NOLOGIN = -1;
	int usuario_activo; //Posición del usuario que ha hecho login
	struct UsuarioPartidas { //Usuarios aparecen en orden de menor a mayor id
		Usuario user;
		Lista<Juego*> partidas;
	};
	Lista<UsuarioPartidas*> usuarios; //Lista de todos los usuarios con sus correspondientes partidas
	
	//Para realizar el "login" y "logout"
	bool bs(Usuario const& userID, int& pos /*ent/sal*/);
	void insertar(UsuarioPartidas* const& u, int pos);
	void eliminar_usuario_de_la_lista();
};