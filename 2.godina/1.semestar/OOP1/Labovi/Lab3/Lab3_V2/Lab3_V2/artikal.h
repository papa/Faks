#ifndef _artikal_h_
#define _artikal_h_

#include <string>

using namespace std;

class Artikal
{
	string naziv;
	int barkod;
	double cena;
public:
	Artikal(const string& naz, int b, double c);

	int getBarkod() const;
	double getCena() const;
	string getNaziv() const;

	friend bool operator == (const Artikal& a1, const Artikal& a2);
	friend bool operator != (const Artikal& a1, const Artikal& a2);
};

#endif
