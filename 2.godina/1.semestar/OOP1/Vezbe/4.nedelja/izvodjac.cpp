#include "izvodjac.h"

string Izvodjac::str_zanr[] = { "pop", "rep", "rok" };

void Izvodjac::print() const
{
	cout << naziv << "(" << str_zanr[zanr] << ")" << endl;
}