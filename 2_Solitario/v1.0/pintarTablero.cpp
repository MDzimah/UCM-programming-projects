#include <iostream>
#include <iomanip>
#include "pintarTablero.h"


void Colores::color_fondo(int color) {
   if (color == DEFAULT_COLOR)
      std::cout << RESET;
   else if (color == NULA)
      std::cout << BG_BLACK;
   else if (color == FICHA)
      std::cout << BG_LBLUE;
   else // color == VACIA
      std::cout << BG_ORANGE;
}

void Colores::pinta_cabecera()  {
   std::cout << std::setw(2) << "    "; // margen inicial
   std::cout << std::setw(5) << 1;
   for (int i = 2; i <= tab.num_columnas(); i++) {
       std::cout << std::setw(7) << i;
   }
   std::cout << std::endl;
}

void Colores::pinta_linea(char esquinaIzda, char cruce, char esquinaDer) {
    std::cout << "    "; // margen inicial
    std::cout << esquinaIzda;
   for (int i = 0; i < tab.num_columnas() - 1; i++) {
      std::cout << std::string(6, char(196)) << cruce;
   }
   std::cout << std::string(6, char(196)) << esquinaDer << std::endl;
}

void Colores::pinta_borde_celda(int fila) {
   std::cout << "    "; // margen inicial
   for (int k = 0; k < tab.num_columnas(); k++) { // cada columna
      std::cout << char(179);
      color_fondo(tab.valorCeldas(fila,k));
      std::cout << "      ";
      color_fondo(DEFAULT_COLOR);
   }
   std::cout << char(179) << std::endl; // lateral derecho
}

void Colores::pinta_centro_celda(int fila)  {
   std::cout << "  " << std::setw(2) << fila + 1; // margen inicial
   for (int k = 0; k < tab.num_columnas(); k++) { // cada columna
      std::cout << char(179);
      // el color de fondo depende del contenido
      color_fondo(tab.valorCeldas(fila, k));

      if (fila == Fmeta-1 && k == Cmeta-1) { // meta
         std::cout << YELLOW;
         std::cout << "  " << char(219) << char(219) << "  ";
      }
      else {
         std::cout << "      ";
      }
      color_fondo(DEFAULT_COLOR);
   }
   std::cout << char(179) << std::endl; // lateral derecho
}

void Colores::pintar() {
  
    system("cls"); // borrar consola
   std::cout << RESET;

   // borde superior
   pinta_cabecera();
   pinta_linea(char(218), char(194), char(191));
   // para cada fila
   for (int fil = 0; fil < tab.num_filas(); fil++) {
      // primera línea
      pinta_borde_celda(fil);
      // segunda línea, con la meta posiblemente
      pinta_centro_celda(fil);
      // tercera línea
      pinta_borde_celda(fil);
      // separación entre filas
      if (fil < tab.num_filas() - 1) {
         pinta_linea(char(195), char(197), char(180));
      }
      else {
         pinta_linea(char(192), char(193), char(217));
      }
   }

}
