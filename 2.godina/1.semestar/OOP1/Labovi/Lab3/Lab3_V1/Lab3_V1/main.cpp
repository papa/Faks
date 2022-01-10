#include <iostream>
#include "korisnik.h"
#include "elektronska_poruka.h"
#include "elektronska_poruka_tekst.h"
#include "vremenska_oznaka.h"
#include "lista.h"

using namespace std;

void testLista()
{
	Lista<int> l;
	l.dodajElem(1);
	l.dodajElem(-2);
	l.dodajElem(-3);

	cout << "br elem " << l.getBrElem() << endl;

	for (l.naPrvi(); l.imaTek(); l.naSledeci())
		cout << l.getTek() << " ";
	cout << endl;
	l.naPrvi();
	int& x = l.getTek();
	x++;
	for (l.naPrvi(); l.imaTek(); l.naSledeci())
		cout << l.getTek() << " ";

	//cout << l.getTek() << endl; //za izuzetak
}

void testKorisnik()
{
	Korisnik k1("Ime1", "Email1");
	//Korisnik k2 = k1; // ne moze kopiranje
	cout << k1.getIme() << endl;
	cout << k1.getMail() << endl;
	cout << k1 << endl; 
}

void testVreme()
{
	VremenskaOznaka v1(1, 2, 3, 4, 5);
	VremenskaOznaka v2 = v1;
	cout << v1 << endl;
}

void testPoruka()
{
	Korisnik pos("Posiljalac", "Email1");
	Korisnik prim("Primalac", "Email2");
	ElektronskaPorukaSaTekstom poruka(pos, prim, "Naslov");
	poruka.postaviTekst("Tekst poruke");
	poruka.posaljiPoruku();
	cout << poruka << endl;
	poruka.postaviTekst("Tekst poruke 2"); // za izuzetak
}

void staticTest()
{
	testPoruka();
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