#ifndef _knjiga_h_
#define _knjiga_h_

#include <string>
#include <iostream>

using namespace std;

class Knjiga
{
private:
	static int ID;
	int id = ID++;
	string naziv;
	string autor;
	void pisi(ostream& os) const;
public:
	Knjiga(const string& naz, const string& aut);

	Knjiga(const Knjiga& k) = delete;
	Knjiga& operator = (const Knjiga& k) = delete;

	Knjiga* operator !() const;
	
	friend ostream& operator << (ostream& os, const Knjiga& k);

	string dohvatiNaziv() const { return naziv; }
	string dohvatiAutor() const { return autor; }
	int dohvatiID() const { return id; }
};

#endif
