#include "cvet.h"
#include <iostream>

void Cvet::pisi(ostream& os) const
{
	cout << this->naziv;
}

Cvet::Cvet(const string& naz, int nC, int pC) : naziv(naz), nabavnaCena(nC), prodajnaCena(pC){}

int Cvet::zarada() const
{
	return this->prodajnaCena - this->nabavnaCena;
}

int Cvet::getNCena() const
{
	return nabavnaCena;
}

int Cvet::getPCena() const
{
	return prodajnaCena;
}

string Cvet::getNaziv() const
{
	return naziv;
}

Cvet Cvet::citajCvet()
{
	string naziv;
	int nc, pc;
	cout << "Unesite naziv, nabavnu i prodajnu cenu za novi cvet:" << endl;
	cout << "Naziv: "; getline(cin, naziv);
	cout << "Nabavna cena: "; cin >> nc; getchar();
	cout << "Prodajna cena: "; cin >> pc;getchar();
	return Cvet{ naziv, nc, pc };
}

bool operator==(const Cvet& c1, const Cvet& c2)
{
	return c1.naziv == c2.naziv;
}

bool operator!=(const Cvet& c1, const Cvet& c2)
{
	return !(c1 == c2);
}

ostream& operator<<(ostream& os, const Cvet& c)
{
	c.pisi(os);
	return os;
}
