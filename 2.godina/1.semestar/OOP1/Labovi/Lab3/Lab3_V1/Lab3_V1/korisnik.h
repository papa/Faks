#ifndef _korisnik_h_
#define _korisnik_h_

#include <string>
#include <iostream>

using namespace std;

class Korisnik
{
	string ime;
	string email;
	
	void pisi(ostream& os) const;
public:
	Korisnik(const string& i, const string& e);

	Korisnik(const Korisnik& k) = delete;
	Korisnik& operator = (const Korisnik& k) = delete;

	friend ostream& operator << (ostream& os, const Korisnik& k);

	string getIme() const;
	string getMail() const;
};

#endif
