#ifndef _elektronska_poruka_h_
#define _elektronska_poruka_h_

#include <iostream>
#include <string>
#include "korisnik.h"

using namespace std;

class ElektronskaPoruka
{
protected:
	Korisnik& posiljalac;
	Korisnik& primalac;
	string naslov;

	static enum stanje {U_PRIPREMI, POSLATA, PRIMLJENA};

	stanje stanjePoruke = stanje::U_PRIPREMI;

	virtual void pisi(ostream& os) const;
public:
	ElektronskaPoruka(Korisnik& pos, Korisnik& prim, const string& nasl);

	ElektronskaPoruka(const ElektronskaPoruka& ep) = default;
	ElektronskaPoruka& operator = (const ElektronskaPoruka& ep) = default;

	virtual void posaljiPoruku() = 0;

	friend ostream& operator << (ostream& os, const ElektronskaPoruka& ep);

	string getNaslov() const;
	Korisnik& getPrimalac() const;
	Korisnik& getPosiljalac() const;

	virtual ~ElektronskaPoruka();
};

#endif