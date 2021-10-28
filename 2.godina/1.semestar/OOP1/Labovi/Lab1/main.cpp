#include <iostream>
#include "flota.h"
#include "avion.h"
#include "pilot.h"

using namespace std;

int main()
{
	Flota f1("Flota 1");

	Pilot p1("Pera Peric", 50);
	Pilot p2("Zika Zikic", 90);
	Pilot p3("Mika Mikic", 100);
	Pilot p4("Pavle Janevski", 200);
	Pilot p5("Pera Zikic", 300);
	Pilot p6("Mika Peric", 34);
	Pilot p7("Zika Mikic", 23);

	Avion a1("Avion 1", 100);
	Avion a2("Avion 2", 200);
	Avion a3("Avion 3", 300);

	a1.postavi_kapetana(&p1); // neuspesno
	a1.postavi_kapetana(&p3);
	a1.postavi_kopilota(&p1);

	a2.postavi_kapetana(&p4);
	a2.postavi_kopilota(&p2);

	a3.postavi_kapetana(&p5);
	a3.postavi_kopilota(&p6);

	f1.dodaj_avion(&a1);
	f1.dodaj_avion(&a2);
	f1.dodaj_avion(&a3);

	f1.pisi();

	Avion* a_maks = f1.dohvati_avion_najvise_putnika();
	a_maks->pisi();

	return 0;
}