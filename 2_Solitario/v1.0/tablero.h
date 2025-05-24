#pragma once
#include <vector>
enum Celda { NULA, VACIA, FICHA };

/*Añado una variable bool a la lista de parámetros de las funciones "correcta", "leer" y "escribir"
porque, si se crea un tablero aleatorio, es necesario obviar la condición "celdas[f][c] != NULA" de
"correcta" (por las condiciones necesarias para realizar un movimiento inverso). Las otras dos lo 
llevan porque hacen un assert(correcta(...)) como se indica en el guión*/

class Tablero {
public:
	Tablero(int fils/*ent*/, int cols/*ent*/, Celda inicial/*ent*/) : filas(fils), columnas(cols), celdas(fils, std::vector<Celda>(cols, inicial)) {}
	int num_filas() const { return filas; }
	int num_columnas() const { return columnas; }
	bool correcta(int f/*ent*/, int c/*ent*/) const;
	Celda leer(int f/*ent*/, int c/*ent*/) const;
	Celda conv_intAenum(int i/*ent*/) {
		Celda cel = (Celda)i;
		return cel;
	}
	void escribir(int f/*ent*/, int c/*ent*/, Celda valor/*ent*/);
	Celda valorCeldas(int f/*ent*/, int c/*ent*/) { return celdas[f][c]; }
private:
	int filas, columnas;
	std::vector<std::vector<Celda>> celdas;
};