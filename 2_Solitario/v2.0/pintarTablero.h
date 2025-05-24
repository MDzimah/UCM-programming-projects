//Manuel Dzimah Castro
#include "tablero.h"

//Color de las letras
#define RED     "\x1B[38;2;255;0;0m"
#define YELLOW  "\x1B[38;2;255;255;0m"
#define LGREEN  "\x1B[38;2;17;245;120m"
#define RESET   "\x1b[0m"

//Color del fondo
#define BG_BLACK   "\x1B[40m"
#define BG_BLUE    "\x1B[44m"
#define BG_ORANGE  "\x1B[48;2;204;102;0m"
#define BG_LBLUE   "\x1B[48;2;53;149;240m"

#define BG_VERDELIMA    "\x1B[48;2;204;255;0m"
#define BG_ROSA    "\x1B[48;2;255;0;255m"

const int DEFAULT_COLOR = -1;

class Colores {
public:
	Colores(Tablero tablero, int f_meta, int c_meta) : tab(tablero), Fmeta(f_meta), Cmeta(c_meta) {}
	void pintar();
private:
	Tablero tab;
	int Fmeta, Cmeta;
	void color_fondo(int color);
	void pinta_cabecera();
	void pinta_linea(char esquinaIzda, char cruce, char esquinaDer);
	void pinta_borde_celda(int fila);
	void pinta_centro_celda(int fila);
};