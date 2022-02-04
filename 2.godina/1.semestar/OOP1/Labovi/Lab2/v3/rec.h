#ifndef _rec_h_
#define _rec_h_

#include <string>
#include <iostream>

using namespace std;

class Rec
{
private:
	string karakteri;
	void pisi(ostream& os) const;
	void citaj(istream& is);
	bool nosilacSloga(int pos) const;
	static bool jeSamoglasnik(const char c);
	static bool jeNoseciSonant(const char c);
	static char velikoSlovo(const char c);
	static char maloSlovo(const char c);
	static bool jeMaloSlovo(const char c);
	static bool jeVelikoSlovo(const char c);
	static string malaRec(const string& s);
	void kreirajRec(const string& s);
public:
	Rec(const string& s);
	int operator + () const;
	int operator ~ () const;
	int operator ()(int pos) const;
	friend bool operator ^ (const Rec& r1, const Rec& r2);
	friend ostream& operator << (ostream& os, const Rec& r);
	friend istream& operator >> (istream& is, Rec& r);

	string poslednjiSlog() const;
	string poslednjaDvaSloga() const;
};
#endif