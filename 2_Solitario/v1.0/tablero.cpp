#include "tablero.h"
#include <iostream>
#include <assert.h>

bool Tablero::correcta(int f, int c) const {
	--f; --c;
	return f >= 0 && f < filas && c >= 0 && c < columnas;
}

Celda Tablero::leer(int f, int c) const {
	--f; --c;
	return celdas[f][c];
}

void Tablero::escribir(int f, int c, Celda valor) {
	--f; --c;
	celdas[f][c] = valor;
}

