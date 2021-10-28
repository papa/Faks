#ifndef _flota_h_
#define _flota_h_

#include <iostream>
#include "avion.h"

using namespace std;

class Flota
{
private:
	string naziv;
	struct list_node
	{
		struct list_node* next;
		Avion* a;
		list_node(Avion* a, list_node* nx = nullptr) : a(a), next(nx) {}
	};
	list_node* first = nullptr, * last = nullptr;
public:
	Flota(string naziv)
	{
		this->naziv = naziv;
	}
	Flota(const Flota& f);
	Flota(Flota&& f);
	~Flota();
	void dodaj_avion(Avion* a);
	int dohvati_ukupan_broj_aviona_u_floti() const;
	int dohvati_ukupan_broj_putnika() const;
	Avion* dohvati_avion_najvise_putnika() const;
	void pisi();
};

#endif