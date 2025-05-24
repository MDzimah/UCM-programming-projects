//Manuel Dzimah Castro
#pragma once
#include <iostream>

template <typename T>
class Lista {
public:
	Lista(int N = 0, T vini = T());
	~Lista() { 
		delete[] datos;
		datos = nullptr; 
	}
	T& operator[](int i) { return datos[i]; }
	T const& operator[](int i) const { return datos[i]; }
	void push_back(T const& elem);
	void pop_back();
	int size() const { return num_elems; }
	bool empty() const { return size() == 0; }
private:
	T* datos;
	int num_elems;
	int capacidad;
	void redim(int new_cap);
};

template <typename T>
Lista<T>::Lista(int N, T vini) {
	capacidad = num_elems = N;
	if (N > 0) {
		datos = new T[N];
		for (size_t i = 0; i < N; ++i) {
			datos[i] = vini;
		}
	}
	else datos = nullptr;
}

template<typename T>
void Lista<T>::push_back(T const& elem) {
	if (num_elems == capacidad) {
		redim(capacidad == 0 ? 1 : 2 * capacidad);
	}
	datos[num_elems] = elem;
	++num_elems;
}

template<typename T>
void Lista<T>::pop_back() {
	if (num_elems > 0)
		--num_elems;
}

template<typename T>
void Lista<T>::redim(int new_cap) {
	T* viejo = datos;
	capacidad = new_cap;
	datos = new T[capacidad];

	for (int i = 0; i < num_elems; ++i)
		datos[i] = viejo[i];

	delete[] viejo;
}