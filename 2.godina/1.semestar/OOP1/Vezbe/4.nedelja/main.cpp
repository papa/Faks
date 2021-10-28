#include <iostream>
#include "Kvadar.h"
#include "A.h"
#include "plejlista.h"

using namespace std;

//friend function of class Kvadar
//doesn't have parameter this
bool bigger_V(const Kvadar& k1, const Kvadar& k2)
{
	//return k1.V() > k2.V();
	double V1 = k1.a * k1.b * k1.c;
	double V2 = k2.a * k2.b * k2.c;
	return V1 > V2;
}

void testing_kvadar(int argc, char* argv[])
{
	//Kvadar::set_Vmax(300);
	//cout << Kvadar::get_Vmax() << endl;
	//Kvadar* k1 = Kvadar::make(2, 4, 6);

	Kvadar::set_Vmax(atof(argv[1]));
	Kvadar* k1 = Kvadar::make(1, 2, 3);
	//A a;
	//a.print_kvadar(*k1);

	//struct similar to class (constructor, destructor...)
	struct Elem
	{
		Kvadar* kvadar;
		Elem* nxt;
		Elem(Kvadar* kv) { kvadar = kv; nxt = nullptr; }
		~Elem() { delete kvadar; }
	};

	for (char more = 'y'; more == 'y' || more == 'Y';cout << "\More?", cin >> more)
	{
		Elem* first = nullptr;
		Elem* last = nullptr;

		while (true)
		{
			cout << "a,b,c,? ";
			Kvadar* kv = Kvadar::read();
			if (kv != nullptr)
			{
				last = (!first ? first : last->nxt) = new Elem(kv);
			}
			else
			{
				break;
			}
		}

		cout << "Volumes: ";
		for (Elem* cur = first;cur;cur = cur->nxt)
		{
			cout << cur->kvadar->V() << " ";
		}
		cout << endl;
		while (first)
		{
			Elem* old = first;
			first = first->nxt;
			delete old;
		}
	}
}

void testing_plejlista()
{
	Izvodjac iz1("Petar", Zanr::REP);
	Izvodjac iz2("Zika", Zanr::POP);
	Izvodjac iz3("Laza", Zanr::ROK);

	Pesma p1(2, 45, "Pesma1", 1);
	Pesma p2(3, 5, "Pesma2", 2);
	
	p1.add_izvodjac(&iz1);
	p2.add_izvodjac(&iz2); 
	p2.add_izvodjac(&iz3);

	Plejlista pl;
	pl.add(&p1);
	pl.add(&p2);
	p1.print();

}

int main(int argc, char* argv[])
{
	testing_kvadar(argc, argv);

	return 0;
}