#include "pesma.h"

void Pesma::print() const
{
	cout << "P(" << naziv << "-" << min << ":" << sec << ")" << endl;
	cout << "Izvodjaci:" << endl;
	for (int i = 0; i < br;i++)
	{
		izv[i]->print();
	}
}