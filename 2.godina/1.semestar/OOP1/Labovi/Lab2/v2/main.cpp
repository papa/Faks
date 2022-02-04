#include <iostream>
#include "knjiga.h"
#include "biblioteka.h"

using namespace std;

void testKnjiga()
{
	cout << "Unesite naziv i autora knjige: " << endl;
	string autor; 
	string naziv;
	cout << "Naziv: ";
	getline(cin, naziv);
	cout << "Autor: ";
	getline(cin, autor); 
	Knjiga knjiga{ naziv, autor };
	int x = -1;
	Knjiga* novaKnjiga;
	while (x != 0)
	{
		cout << "Unesite broj za operaciju\n0-kraj\n1-ispis knjige\n2-kopija knjige" << endl;
		cin >> x; getchar();
		switch(x)
		{
		case 1:
			cout << knjiga << endl;
			break;
		case 2:
			novaKnjiga = !knjiga;
			cout << "Nova knjiga" << endl;
			cout << *novaKnjiga << endl;
			delete novaKnjiga;
			break;
		case 0:
			cout << "Kraj" << endl;
		}
	}
}

void testBiblioteka()
{
	cout << "Unesite naziv i kapacitet biblioteke: " << endl;
	int kap; 
	string s; 
	cout << "Naziv: ";
	getline(cin, s);
	cout << "Kapacitet: ";
	cin >> kap; getchar();
	Biblioteka biblioteka = Biblioteka(s, kap);
	int x = -1;
	string autor;
	int id;
	Knjiga* k = nullptr;
	while (x != 0)
	{
		cout << "Unesite broj za operaciju\n0-kraj\n1-dodavanje knjige\n2-ispis biblioteke\n3-dohvati knjigu po id" << endl;
		cin >> x;
		getchar();
		switch (x)
		{
		case 1:
			cout << "Unesite naziv i autora: " << endl; 
			cout << "Naziv: ";getline(cin, s);
			cout << "Autor: ";getline(cin, autor);
			biblioteka += Knjiga(s,autor);
			break;
		case 2:
			cout << biblioteka << endl;
			break;
		case 3:
			cout << "Uneti id za pretragu: ";
			cin >> id; getchar();
			k = biblioteka.dohvatiKnjigu(id);
			if (k == nullptr) cout << "Ne postoji knjiga sa datim id" << endl;
			else cout << *k << endl;
		case 0:
			cout << "Kraj"; 
		}
	}
}


void test()
{
	int x = -1;
	while (x != 0)
	{
		cout << "1-testiraj knjigu\n2-testiraj biblioteku\n0-kraj" << endl;
		cin >> x;
		getchar();
		switch (x != 0)
		{
		case 1:
			testKnjiga();
			break;
		case 2:
			testBiblioteka();
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