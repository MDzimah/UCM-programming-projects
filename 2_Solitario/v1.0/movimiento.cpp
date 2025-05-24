#include "movimiento.h"

std::string to_string(Direccion d) {
	switch (d) {
	case ARRIBA: return "ARRIBA"; break;
	case ABAJO: return "ABAJO"; break;
	case IZQUIERDA: return "IZQUIERDA"; break;
	case DERECHA: return "DERECHA"; break;
	case INDETERMINADA: return "INDETERMINADA"; break;
	}
}
