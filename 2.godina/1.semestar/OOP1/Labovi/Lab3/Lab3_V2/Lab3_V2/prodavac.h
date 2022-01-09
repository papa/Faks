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
	string naziv;
	Lista<tuple<Artikal*, double, int> > katalog;
public:
	Prodavac(const string& naz);

	void dodajUKatalog(tuple<Artikal*, double, int> t);

	void obradiPosiljku(Posiljka& p) override;

	string getNaziv() const;
};

#endif
