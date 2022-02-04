#ifndef _buket_h_
#define _buket_h_

#include "cvet.h"
#include <iostream>

class Buket
{
private:
	struct Node
	{
		Cvet* cvet;
		Node* next = nullptr;
		Node(const Cvet& c) { cvet = new Cvet(c); };
		~Node() { delete cvet; }
	};
	Node* first = nullptr;
	Node* last = nullptr;
	void brisi();
	void kopiraj(const Buket& b);
	void premesti(Buket& b);
	void pisi(ostream& os) const;
public:
	Buket() = default;

	Buket(const Buket& b);
	Buket(Buket&& b);

	Buket& operator = (const Buket& b);
	Buket& operator = (Buket&& b);

	~Buket();

	int izracunajNCenu() const;
	int izracunajPCenu() const;
	int izracunajZaradu() const;
	
	void dodajCvet(const Cvet& c);

	friend bool operator < (const Buket& b1, const Buket& b2);
	friend bool operator <= (const Buket& b1, const Buket& b2);
	friend bool operator > (const Buket& b1, const Buket& b2);
	friend bool operator >= (const Buket& b1, const Buket& b2);
	friend bool operator == (const Buket& b1, const Buket& b2);
	friend bool operator != (const Buket& b1, const Buket& b2);

	friend ostream& operator << (ostream& os, const Buket& b);
};

#endif
