#ifndef _biblioteka_h_
#define _biblioteka_h_

#include <iostream>
#include <string>
#include "knjiga.h"

using namespace std;

class Biblioteka
{
private:
	string naziv;
	int kapacitet;
	int br = 0;
	Knjiga** knjige;

	void dodajKnjigu(Knjiga* k);

	void pisi(ostream& os) const;

	void brisi();
	void kopiraj(const Biblioteka& b);
	void premesti(const Biblioteka& b);
public:
	Biblioteka(const string& naz, int kap);

	Biblioteka(const Biblioteka& b);
	Biblioteka(Biblioteka&& b);

	Biblioteka& operator = (const Biblioteka& b);
	Biblioteka& operator = (Biblioteka&& b);

	~Biblioteka();

	Knjiga* dohvatiKnjigu(int idK);

	int dohvatiKap() const { return kapacitet; }
	int dohvatiBr() const { return br; }
	string dohvatiNaziv() const { return naziv; }

	Biblioteka& operator += (const Knjiga& k);
	Biblioteka& operator += (Knjiga&& k);

	friend ostream& operator << (ostream& os, const Biblioteka& b);
};

#endif
