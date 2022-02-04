#ifndef _cvecara_h_
#define _cvecara_h_

#include <iostream>
#include "buket.h"

class Cvecara
{
private:
	int zarada = 1000;
	struct Node
	{
		Node* next = nullptr;
		Buket* buket;
		Node(const Buket& b) { buket = new Buket(b); }
		~Node() { delete buket; }
	};
	Node* first = nullptr;
	Node* last = nullptr;
	void pisi(ostream& os) const;

	void brisi();
	void kopiraj(const Cvecara& c);
	void premesti(Cvecara& c);
	void dodajBuketKraj(const Buket& b);
	void dodajBuketPocetak(const Buket& b);
public:

	Cvecara() = default;

	Cvecara(const Cvecara& c);
	Cvecara(Cvecara&& c);

	Cvecara& operator = (const Cvecara& c);
	Cvecara& operator = (Cvecara&& c);

	~Cvecara();

	void dodajBuket(const Buket& b);
	void prodajBuket(int pos);

	friend ostream& operator << (ostream& os, const Cvecara& c);
};

#endif
