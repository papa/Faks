#include <iostream>

#include "cvet.h"
#include "buket.h"
#include "cvecara.h"

using namespace std;

void testCvet()
{
	Cvet cvet = Cvet::citajCvet();
	int x = -1;
	while (x != 0)
	{
		cout << "Unesite broj za operaciju\n0-kraj\n1-poredi cvetove\n2-ispis cveta" << endl;
		cin >> x; getchar();
		if (x == 1)
		{
			Cvet cvet2 = Cvet::citajCvet();
			if (cvet == cvet2) cout << "Jednaki su" << endl;
			else cout << "Nisu jedanki" << endl;
		}
		else if (x == 2)
		{
			cout << cvet << endl;
		}
		else
		{
			cout << "Kraj" << endl;
		}
	}
}

void dodajCvetoveUBuket(Buket& b)
{
	cout << "Koliko cvetova zelite: "; 
	int n; cin >> n; getchar();
	for (int i = 0; i < n;i++)
	{
		b.dodajCvet(Cvet::citajCvet());
	}
}

void testBuket()
{
	Buket buket = Buket();
	int x = -1;
	while (x != 0)
	{
		cout << "Unesite broj za operaciju\n0-kraj\n1-dodavanje cveta\n2-racunanje nabavne cene\n3-racunanje prodajne cene\n4-racunanje zarade\n5-uporedjivanje buketa\n6-ispis" << endl;
		cin >> x; getchar();
		if (x == 1)
		{
			buket.dodajCvet(Cvet::citajCvet());
		}
		else if (x == 2)
		{
			cout << "Nabavna cena " << buket.izracunajNCenu() << endl;
		}
		else if (x == 3)
		{
			cout << "Prodajna cena " << buket.izracunajPCenu() << endl;
		}
		else if (x == 4)
		{
			cout << "Zarada " << buket.izracunajZaradu() << endl;
		}
		else if (x == 5)
		{
			Buket buket2 = Buket();
			dodajCvetoveUBuket(buket2);
			if (buket == buket2) cout << "Jednake prodajne cene" << endl;
			else cout << "Razlicite prodajne cene" << endl;
		}
		else if (x == 6)
		{
			cout << buket << endl;
		}
		else
		{
			cout << "Kraj" << endl;
		}
	}
}

void testCvecara()
{
	Cvecara cvecara{};
	int x = -1;
	while (x != 0)
	{
		cout << "Unesite broj za operaciju\n0-kraj\n1-dodavanje buketa\n2-prodaja buketa sa zadate pozicije\n3-ispis cvecare" << endl;
		cin >> x; getchar();
		if (x == 1)
		{
			Buket b{};
			dodajCvetoveUBuket(b);
			cvecara.dodajBuket(b);
		}
		else if (x == 2)
		{
			cout << "Unesti poziciju: ";
			int pos; 
			cin >> pos; getchar();
			cvecara.prodajBuket(pos);
		}
		else if (x == 3)
		{
			cout << cvecara << endl;
		}
		else
		{
			cout << "Kraj" << endl;
		}
	}
}

void test()
{
	int x = -1;
	while (x != 0)
	{
		cout << "1-testiraj cvet\n2-testiraj buket\n3-testiraj cvecaru\n0-kraj" << endl;
		cin >> x; getchar();
		switch (x)
		{
		case 1:
			testCvet();
			break;
		case 2:
			testBuket();
			break;
		case 3:
			testCvecara();
			break;
		case 0:
			cout << "kraj" << endl;
		}
	}
}


int main()
{
	test();
	return 0;
}