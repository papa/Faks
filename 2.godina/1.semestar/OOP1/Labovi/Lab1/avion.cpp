#include "avion.h"

void Avion::pisi() const
{
	cout << "AVION:" << naziv << "-" << maks_putnici << endl;
}

string Avion::dohvati_naziv() const
{
	return naziv;
}

int Avion::dohvati_maks_putnici() const
{
	return maks_putnici;
}

Pilot* Avion::get_kapetan() const
{
	return kapetan;
}

Pilot* Avion::get_kopilot() const
{
	return kopilot;
}

void Avion::postavi_kapetana(Pilot* p)
{
	if (p->dohvati_br_sati() < 100)
	{
		cout << "Pilot " << p->dohvati_ime() << " se ne moze postaviti za kapetana" << endl;
	}
	else
	{
		kapetan = p;
		kapetan->postavi_na_letu(true);
	}
}

void Avion::postavi_kopilota(Pilot* p)
{
	kopilot = p;
	kopilot->postavi_na_letu(true);
}
