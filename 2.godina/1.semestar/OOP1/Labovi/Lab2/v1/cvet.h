#ifndef _cvet_h_
#define _cvet_h_

#include <string>

using namespace std;

class Cvet
{
private:
	string naziv;
	int nabavnaCena;
	int prodajnaCena;
	void pisi(ostream& os) const;
public:
	Cvet(const string& naz, int nC, int pC);

	int zarada() const;

	friend bool operator == (const Cvet& c1, const Cvet& c2);
	friend bool operator != (const Cvet& c1, const Cvet& c2);

	friend ostream& operator << (ostream& os, const Cvet& c);

	int getNCena() const;
	int getPCena() const;
	string getNaziv() const;

	static Cvet citajCvet();
};

#endif
