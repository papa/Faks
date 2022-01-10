#ifndef _posiljka_h_
#define _posiljka_h_

#include "artikal.h"
#include "lista.h"
#include "rukovalac.h"
#include "greske.h"
#include <iostream>

using namespace std;

class Posiljka
{
public:
	struct Detalji
	{
		int dani;
		double cena;
		Detalji(int d, double c) : dani(d), cena(c) {}
	};
private:
	static int ID;

	int idPos = ++ID;
	
	const Artikal& artikal;

	Lista<Rukovalac* > rukovaoci;
	
	Detalji detalji;
	bool izracunaoDetalje = false;

	void pisi(ostream& os) const;
public:

	Posiljka(const Artikal& a);
	
	Posiljka& operator += (Rukovalac* r);

	void izracunajDetaljePosiljke();

	int getID() const;

	const Artikal& getArtikal() const;

	Detalji getDetalji() const;

	friend ostream& operator << (ostream& os, const Posiljka& p);

	friend Rukovalac;
	friend class Prodavac;
};


#endif