#include "obicno_vozilo.h"

double ObicnoVozilo::startnaCena() const
{
	return 120;
}

ObicnoVozilo::ObicnoVozilo(const string& naz)
	:Vozilo(naz)
{
}
