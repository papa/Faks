#include "pilot.h"

string Pilot::dohvati_ime() const
{
	return ime;
}
int Pilot::dohvati_br_sati() const
{
	return broj_sati_letenja;
}
bool Pilot::dohvati_na_letu() const
{
	return na_letu;
}

void Pilot::dodaj_br_sati(int br_sati)
{
	broj_sati_letenja += br_sati;
}

void Pilot::postavi_na_letu(bool na_letu)
{
	this->na_letu = na_letu;
}

void Pilot::pisi() const
{
	cout << ime << "(" << broj_sati_letenja << ")-";
	if (na_letu) cout << "L";
	else cout << "N";
	cout << endl;
}