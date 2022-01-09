#include "rukovalac.h"
#include "posiljka.h"

void Rukovalac::rukujPosiljkom(Posiljka& p, int dani, double cena)
{
	p.detalji.dani += dani;
	p.detalji.cena += cena;
}
