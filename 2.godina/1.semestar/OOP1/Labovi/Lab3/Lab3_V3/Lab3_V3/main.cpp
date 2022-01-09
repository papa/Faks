#include <iostream>
#include "put.h"
#include "vozilo.h"
#include "tacka.h"

using namespace std;

void testLista()
{
	Lista<int> l;

	l += 2;
	l += 3;
	l += -3;

	cout << l[2] << endl;
	cout << l.getBrElem() << endl;

	Lista<int> l2 = l;

	l2 += 10;
	for (int i = 0; i < l2.getBrElem(); i++)
		cout << l2[i] << " ";
	cout << endl;
}

void testTacka()
{
	Tacka t1(1, 2);
	Tacka t2(3, 4);
	Tacka t3(1, 2);

	cout << t1 << endl;
	cout << t2 << endl;
	cout << "dist " << dist(t1, t2) << endl;

	if (t1 == t2)
		cout << "Jednakost t1 i t2" << endl;
	else
		cout << "Nejednakost t1 i t2" << endl;

	if (t1 == t3)
		cout << "Jednakost t1 i t3" << endl;
	else
		cout << "Nejednakost t1 i t3" << endl;
}

void testPut()
{
	Put put;
	Tacka* t1p = new Tacka(1, 2);
	Tacka* t2p = new Tacka(5, 2);
	Tacka* t3p = new Tacka(5, 5);
	Tacka* t4p = new Tacka(5, 2);

	put += t1p;
	put += t2p;
	put += t3p;
	//put += t4p;

	cout << put.duzinaPuta() << endl;

	cout << put << endl;
}

void testVozilo()
{
	//Vozilo vozilo("Vozilo test");
	//cout << vozilo << endl;
	//cout << vozilo.cenaPuta(put) << endl;
}

void staticTest()
{
	testLista();
	testTacka();
	testPut();
	testVozilo();
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