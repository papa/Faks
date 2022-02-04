#include <iostream>
#include "rec.h"
#include "skup.h"

using namespace std;

void testSkup()
{
	cout << "Unesite string kojim se inicijalizuje skup: ";
	string s; 
	getline(cin, s); 
	Skup skup{ s };
	while (1)
	{
		cout << "Unesite broj za operaciju\n0-kraj\n1-dodavanje karaktera\n2-provera da li je karakter u skupu" << endl;
		int x;
		cin >> x; 
		getchar();
		if (x == 1)
		{
			cout << "Unesite karakter: ";
			char c; 
			cin >> c; 
			skup += c;
		}
		else if(x == 2)
		{
			cout << "Unosite karakter za proveru: ";
			char c; 
			cin >> c; 
			if (skup(c)) cout << "Skup sadrzi karakter " << c << endl;
			else cout << "Skup ne sadrzi karakter " << c << endl;
		}
		else
		{
			cout << "Kraj\n";
			break;
		}
	}
}

void testRec()
{
	cout << "Unesite string kojom se inicijalizuje rec: ";
	string s; 
	getline(cin, s);
	Rec rec = Rec(s);
	int x = -1;
	string rec2;
	int k;
	while (x != 0)
	{
		cout << "Unesite broj za operaciju\n0-kraj\n1-duzina reci\n2-broj slogova u reci\n3-proveri rimu\n4-ispis reci\n5-unos reci\n6-k-ti slog" << endl;
		cin >> x; 
		getchar();
		switch (x)
		{
		case 1:
			cout << "Duzina reci je " << +rec << endl;
			break;
		case 2: 
			cout << "Broj slogova je " << ~rec << endl;
			break;
		case 3: 
			cout << "Unesite rec za rimu : "; 
			getline(cin, rec2); 
			if (rec ^ Rec(rec2)) cout << "Rimuju se" << endl;
			else cout << "Ne rimuju se" << endl;
			break;
		case 4:
			cout << rec << endl;
			break;
		case 5:
			cout << "Unesite novu vrednost za rec: ";
			cin >> rec;
			break;
		case 6:
			cout << "Uneti k: "; 
			cin >> k; 
			getchar();
			cout << "Pozicija k-tog sloga (noseci) : "<< rec(k) << endl;
			break;
		case 0:
			cout << "Kraj" << endl;
		}
	}
}


void test()
{
	int x = -1;
	while (x != 0)
	{
		cout << "1-testiraj skup\n2-testiraj rec\n0-kraj" << endl;
		cin >> x; 
		getchar();
		switch (x)
		{
		case 1:
			testSkup();
			break;
		case 2:
			testRec();
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