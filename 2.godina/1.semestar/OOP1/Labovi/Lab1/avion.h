#ifndef _avion_h_
#define _avion_h_

#include <iostream>
#include "pilot.h"

using namespace std;

class Avion
{
private:
	string naziv;
	Pilot* kapetan = nullptr;
	Pilot* kopilot = nullptr;
	int maks_putnici;
public:

	Avion(string naziv, int putnici)
	{
		this->naziv = naziv;
		this->maks_putnici = putnici;
	}
	Avion(const Avion& a) = delete;
	void pisi() const;
	string dohvati_naziv() const;
	int dohvati_maks_putnici() const;
	Pilot* get_kapetan() const;
	Pilot* get_kopilot() const;
	void postavi_kapetana(Pilot* p);
	void postavi_kopilota(Pilot* p);

};

#endif 