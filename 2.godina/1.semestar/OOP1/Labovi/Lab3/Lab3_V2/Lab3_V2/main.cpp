#include <iostream>
#include "artikal.h"
#include "posiljka.h"
#include "rukovalac.h"
#include "prodavac.h"

using namespace std;

void testArtikal()
{
	Artikal a1("naziv", 123, 100);
	cout << a1.getBarkod() << " " << a1.getCena() << " " << a1.getNaziv() << endl;
	Artikal a2("naziv 2", 123, 200);
	Artikal a3("naziv", 234, 100);
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
	Artikal a1("naziv artikla 1", 123, 2);
	Artikal a2("naziv artikla 2", 234, 2);

	Posiljka posiljka(a1);

	Prodavac* p1 = new Prodavac("Naziv prodavca");
	p1->dodajUKatalog(a2, 200, 10);
	p1->dodajUKatalog(a1, 200,15);

	posiljka += p1;

	Prodavac* p2 = new Prodavac("Naziv prodavca2");
	p2->dodajUKatalog(a1,50,10);

	posiljka += p2;

	//Posiljka::Detalji det = posiljka.getDetalji(); // za izuzetak

	posiljka.izracunajDetaljePosiljke();

	cout << posiljka << endl;
	
	//posiljka += p1; // za izuzetak
}

void staticTest()
{
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