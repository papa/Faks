#include "flota.h"

Flota::Flota(const Flota& f)
{
	this->naziv = f.naziv;
	list_node* curr = f.first;
	while (curr)
	{
		dodaj_avion(curr->a);
		curr = curr->next;
	}
}

Flota::Flota(Flota&& f)
{
	this->naziv = f.naziv;
	this->first = f.first;
	this->last = f.last;
	f.first = nullptr;
	f.last = nullptr;
}

Flota::~Flota()
{
	list_node* old = nullptr;
	while (first)
	{
		old = first;
		first = first->next;
		delete old;
	}
}

void Flota::dodaj_avion(Avion* a)
{
	list_node* curr = new list_node(a);
	if (first == nullptr)
		first = curr;
	else 
		last->next = curr;
	last = curr;
}

int Flota::dohvati_ukupan_broj_aviona_u_floti() const
{
	int sum = 0;
	list_node* curr = first;
	while (curr)
	{
		sum++;
		curr = curr->next;
	}
	return sum;
}

int Flota::dohvati_ukupan_broj_putnika() const
{
	int sum = 0;
	list_node* curr = first;
	while (curr)
	{
		sum+=curr->a->dohvati_maks_putnici();
		curr = curr->next;
	}
	return sum;
}

Avion* Flota::dohvati_avion_najvise_putnika() const
{
	int maks = -1;
	Avion* a_maks = nullptr;
	list_node* curr = first;
	while (curr)
	{
		if (a_maks == nullptr)
		{
			a_maks = curr->a;
		}
		else if (a_maks->dohvati_maks_putnici() > curr->a->dohvati_maks_putnici())
		{
			a_maks = curr->a;
		}
		curr = curr->next;
	}
	return a_maks;
}

void Flota::pisi()
{
	cout << naziv << endl;
	list_node* curr = first;
	while (curr)
	{
		curr->a->pisi();
		curr = curr->next;
	}
}
