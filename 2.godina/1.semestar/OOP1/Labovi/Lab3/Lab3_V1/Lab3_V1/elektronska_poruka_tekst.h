#ifndef _elektronska_poruka_tekst_h_
#define _elektronska_poruka_tekst_h_

#include "elektronska_poruka.h"
#include <iostream>
#include <string>
#include "greske.h"

using namespace std;

class ElektronskaPorukaSaTekstom : public ElektronskaPoruka
{
protected:
	string tekst = "";

	void pisi(ostream& os) const override;

public:
	ElektronskaPorukaSaTekstom(Korisnik& pos, Korisnik& prim, const string& nasl);

	ElektronskaPorukaSaTekstom(const ElektronskaPorukaSaTekstom& ep) = default;
	ElektronskaPorukaSaTekstom& operator = (ElektronskaPorukaSaTekstom&& ep) = default;

	void posaljiPoruku() override;

	ElektronskaPorukaSaTekstom* kopiraj() const override;

	void postaviTekst(const string& tekst);

	virtual ~ElektronskaPorukaSaTekstom();
};

#endif