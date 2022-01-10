#include <iostream>
#include "igrac.h"
#include "tim.h"
#include "mec.h"
#include "privilegovani_tim.h"

using namespace std;

void testIgrac()
{
	Igrac i1("Steph Curry", 99);
	Igrac i2("Nikola Jokic", 96);
	Igrac i3 = i1;
	cout << i1 << endl;
	cout << i2 << endl;
	if (i1 == i2)
		cout << "Jednakost i1 i i2" << endl;
	else
		cout << "Nejednakost i1 i i2" << endl;

	if (i1 == i3)
		cout << "Jednakost i1 i i3" << endl;
	else
		cout << "Nejednakost i1 i i3" << endl;

	i1.smanjiVrednost(10);
	cout << i1.getVrednost() << endl; 
}

void testMec()
{
	Igrac i1 = Igrac("Kevin Punter", 99);
	Igrac i2 = Igrac("Yam Madar", 95);
	Igrac i3 =  Igrac("Rade Zagorac", 90);
	Igrac i4 = Igrac("Zach Leday", 97);
	Igrac i5 = Igrac("Balsa Koprivica", 93);
	Tim* tim1 = new Tim("Partizan", 5);
	
	tim1->prikljuciIgraca(i1, 0);
	tim1->prikljuciIgraca(i2, 1);
	tim1->prikljuciIgraca(i3, 2);
	tim1->prikljuciIgraca(i4, 3);
	tim1->prikljuciIgraca(i5, 4);
	
	Igrac i1_2 =  Igrac("Nate Wolters", 98);
	Igrac i2_2 =  Igrac("Austin Hollins", 95);
	Igrac i3_2 =  Igrac("Aaron White", 90);
	Igrac i4_2 =  Igrac("Luka Mitrovic", 97);
	Igrac i5_2 =  Igrac("Nikola Kalinic", 93);
	Tim* tim2 = new Tim("CZ", 5);

	tim2->prikljuciIgraca(i1_2, 0);
	tim2->prikljuciIgraca(i2_2, 1);
	tim2->prikljuciIgraca(i3_2, 2);
	tim2->prikljuciIgraca(i4_2, 3);
	tim2->prikljuciIgraca(i5_2, 4);
	
	Mec derbi(tim1, tim2);

	cout << derbi << endl;

	cout << (derbi.daLiJeOdigran() ? "Odigran" : "Nije odigran") << endl;

	derbi.odigrajMec();

	Par<int> bodovi = derbi.getPoeni();
	cout << bodovi << endl;

	cout << derbi << endl;

	Tim* tim3 = new Tim("Partizan 2", 5);
	tim3->prikljuciIgraca(i1, 0);
	tim3->prikljuciIgraca(i2, 1);
	tim3->prikljuciIgraca(i3, 2);
	tim3->prikljuciIgraca(i4, 3);
	tim3->prikljuciIgraca(i5, 4);

	Mec mec(tim3, tim1);

	//bodovi = mec.getPoeni(); // za izuzetak

	mec.odigrajMec();

	cout << mec << endl;

	delete tim1;
	delete tim2;
}

void staticTest()
{
	testMec();
}

int main()
{
	try
	{
		staticTest();
	}
	catch (exception e)
	{
		cout << e.what() << endl;
 	}
	return 0;
}