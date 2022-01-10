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

void testTim()
{
	Tim tim("Partizan", 5);
	Igrac i2("Kevin Punter", 99);
	Igrac i1("Yam Madar", 96);
	tim.prikljuciIgraca(i1, 1);
	tim.prikljuciIgraca(i2, 2);

	Tim tim2("Partizan", 5);
	Igrac i3("Yam Madar", 96);
	Igrac i4("Kevin Punter", 99);
	tim2.prikljuciIgraca(i3, 1);
	tim2.prikljuciIgraca(i4, 2);

	cout << tim << endl;
	cout << tim2 << endl;

	if (tim == tim2)
		cout << "Jednaki su" << endl;
	else
		cout << "Nisu jednaki" << endl;
	cout << tim.getTrenutniBrojIgraca() << endl;
	cout << tim.getSrednjaVrednostTima() << endl;
	cout << tim[3] << endl;
}

void testMec()
{
	Igrac i1 = Igrac("Kevin Punter", 99);
	Igrac i2 = Igrac("Yam Madar", 95);
	Igrac i3 =  Igrac("Rade Zagorac", 90);
	Igrac i4 = Igrac("Zach Leday", 97);
	Igrac i5 = Igrac("Balsa Koprivica", 93);
	Tim* tim1 = new PrivilegovaniTim("Partizan", 5, 80);

	tim1->prikljuciIgraca(i1, 1);
	tim1->prikljuciIgraca(i2, 2);
	tim1->prikljuciIgraca(i3, 3);
	tim1->prikljuciIgraca(i4, 4);
	tim1->prikljuciIgraca(i5, 5);

	Igrac i1_2 =  Igrac("Nate Wolters", 98);
	Igrac i2_2 =  Igrac("Austin Hollins", 95);
	Igrac i3_2 =  Igrac("Aaron White", 90);
	Igrac i4_2 =  Igrac("Luka Mitrovic", 97);
	Igrac i5_2 =  Igrac("Nikola Kalinic", 93);
	Tim* tim2 = new Tim("CZ", 5);

	tim2->prikljuciIgraca(i1_2, 1);
	tim2->prikljuciIgraca(i2_2, 2);
	tim2->prikljuciIgraca(i3_2, 3);
	tim2->prikljuciIgraca(i4_2, 4);
	tim2->prikljuciIgraca(i5_2, 5);

	Mec derbi(tim1, tim2);

	cout << derbi << endl;

	cout << (derbi.daLiJeOdigran() ? "Odigran" : "Nije odigran") << endl;

	//Par<int> bodovi = derbi.getPoeni();

	Par<Tim> timovi = derbi.getPar();
	Par<Tim> timovi2(tim1, tim2);
	if (timovi == timovi2)
		cout << "Jednaki" << endl;

	derbi.odigrajMec();

	Par<int> bodovi = derbi.getPoeni();
	cout << bodovi << endl;

	cout << derbi << endl;
}

void staticTest()
{
	//testIgrac();
	//testTim();
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