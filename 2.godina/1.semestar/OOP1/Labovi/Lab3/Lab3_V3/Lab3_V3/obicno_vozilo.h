#ifndef _obicno_vozilo_h_
#define _obicno_vozilo_h_

#include "vozilo.h"

class ObicnoVozilo : public Vozilo
{
	virtual double startnaCena() const override;
public:
	ObicnoVozilo(const string& naz);
};

#endif