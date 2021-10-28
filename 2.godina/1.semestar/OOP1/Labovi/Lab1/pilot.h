#ifndef _pilot_h_
#define _pilot_h_

#include <iostream>

using namespace std;

class Pilot
{
private:
	string ime;
	int broj_sati_letenja;
	bool na_letu;
public:
	Pilot(string ime, int bsl, bool na_letu = false)
	{
		this->ime = ime;
		this->broj_sati_letenja = bsl;
		this->na_letu = na_letu;
	}

	Pilot(const Pilot& p) = delete;

	string dohvati_ime() const;
	int dohvati_br_sati() const;
	bool dohvati_na_letu() const;

	void dodaj_br_sati(int br_sati);
	void postavi_na_letu(bool na_letu);

	void pisi() const;


};

#endif 