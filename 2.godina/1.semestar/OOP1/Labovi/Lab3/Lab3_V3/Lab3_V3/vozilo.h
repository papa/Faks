#ifndef _vozilo_h_
#define _vozilo_h_

#include "put.h"
#include <iostream>
#include <string>

using namespace std;

class Vozilo
{
protected:
	string naziv;
	static double cenaJed;

	virtual void pisi(ostream& os) const;

	virtual double startnaCena() const = 0;
public:
	Vozilo(const string& naz);

	double cenaPuta(const Put& p) const;

	friend ostream& operator << (ostream& os, const Vozilo& v);
};

#endif