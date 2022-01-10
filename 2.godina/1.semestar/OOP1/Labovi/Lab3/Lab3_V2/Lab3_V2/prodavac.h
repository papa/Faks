#ifndef _prodavac_h_
#define _prodavac_h_

#include <tuple>
#include <string>

#include "rukovalac.h"
#include "lista.h"
#include "artikal.h"

using namespace std;

class Prodavac : public Rukovalac
{
protected:
	struct Trojka
	{
		Artikal art;
		double marza;
		int dani;
		Trojka(const Artikal& a, double m, int d) : art(a), marza(m), dani(d) {}
	};
	string naziv;
	Lista<Trojka> katalog;
public:
	Prodavac(const string& naz);

	void dodajUKatalog(const Artikal& a, double m, int d);

	void obradiPosiljku(Posiljka& p) override;

	string getNaziv() const;
};

#endif
