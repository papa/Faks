#include <iostream>
#include "artikal.h"
#include "posiljka.h"
#include "rukovalac.h"
#include "prodavac.h"

using namespace std;

void testArtikal()
{
	Artikal a1(123, 100, "naziv");
	cout << a1.getBarkod() << " " << a1.getCena() << " " << a1.getNaziv() << endl;
	Artikal a2(123, 200, "naziv 2");
	Artikal a3(234, 100, "naziv");
	if (a1 == a2)
		cout << "Jednakost a1 i a2" << endl;
	else
		cout << "Nejednakost a1 i a2" << endl;

	if (a1 == a3)
		cout << "Jednakost a1 i a3" << endl;
	else
		cout << "Nejednakost a1 i a3" << endl;
}

void testPosiljka()
{
	Artikal a1(123, 2, "naziv");
	Artikal a2(234, 2, "naziv2");

	Posiljka p(a1);

	Prodavac* p1 = new Prodavac("Naziv prodavca");
	p1->dodajUKatalog(a1, 200, 10);
	p1->dodajUKatalog(a2,200,15);

	p += p1;

	Prodavac* p2 = new Prodavac("Naziv prodavca2");
	p2->dodajUKatalog(a1,50,10);
	p2->dodajUKatalog(a1,50,15);

	p += p2;

	p.izracunajDetaljePosiljke();

	cout << p << endl;
	
	//p += p1;
}

void staticTest()
{
	testArtikal();
	testPosiljka();
}

int main()
{
	try
	{
		staticTest();
	}
	catch (exception& e)
	{
		cout << e.what() << endl;
	}
	return 0;
}