//Manuel Dzimah Castro
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
   else //Color == VACIA
      std::cout << BG_ORANGE;
}

void Colores::pinta_cabecera()  {
   std::cout << std::setw(2) << "    "; //Margen inicial
   std::cout << std::setw(5) << 1;
   for (int i = 2; i <= tab.num_columnas(); i++) {
       std::cout << std::setw(7) << i;
   }
   std::cout << std::endl;
}

void Colores::pinta_linea(char esquinaIzda, char cruce, char esquinaDer) {
    std::cout << "    "; //Margen inicial
    std::cout << esquinaIzda;
   for (int i = 0; i < tab.num_columnas() - 1; i++) {
      std::cout << std::string(6, char(196)) << cruce;
   }
   std::cout << std::string(6, char(196)) << esquinaDer << std::endl;
}

void Colores::pinta_borde_celda(int fila) {
   std::cout << "    "; //Margen inicial
   for (int k = 0; k < tab.num_columnas(); k++) { //Cada columna
      std::cout << char(179);
      color_fondo(tab.valorCeldas(fila,k));
      std::cout << "      ";
      color_fondo(DEFAULT_COLOR);
   }
   std::cout << char(179) << std::endl; //Lateral derecho
}

void Colores::pinta_centro_celda(int fila)  {
   std::cout << "  " << std::setw(2) << fila + 1; //Margen inicial
   for (int k = 0; k < tab.num_columnas(); k++) { //Cada columna
      std::cout << char(179);
      //El color de fondo depende del contenido
      color_fondo(tab.valorCeldas(fila, k));

      if (fila == Fmeta-1 && k == Cmeta-1) { //Meta
         std::cout << YELLOW;
         std::cout << "  " << char(219) << char(219) << "  ";
      }
      else {
         std::cout << "      ";
      }
      color_fondo(DEFAULT_COLOR);
   }
   std::cout << char(179) << std::endl; //Lateral derecho
}

void Colores::pintar() {

   std::cout << RESET;

   //Borde superior
   pinta_cabecera();
   pinta_linea(char(218), char(194), char(191));
   //Para cada fila
   for (int fil = 0; fil < tab.num_filas(); fil++) {
      //Primera línea
      pinta_borde_celda(fil);
      //Segunda línea, con la meta posiblemente
      pinta_centro_celda(fil);
      //Tercera línea
      pinta_borde_celda(fil);
      //Separación entre filas
      if (fil < tab.num_filas() - 1) {
         pinta_linea(char(195), char(197), char(180));
      }
      else {
         pinta_linea(char(192), char(193), char(217));
      }
   }

}