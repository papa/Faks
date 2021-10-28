#ifndef _pesma_h_
#define _pesma_h_

#include <iostream>
#include <string>
#include "izvodjac.h"

using namespace std;


class Pesma
{
	int min;
	int sec;
	string naziv;
	int kap;
	int br;
	Izvodjac** izv;
public:
	Pesma(int m, int s, string naz, int k) : min(m), sec(s), naziv(naz), kap(k)
	{
		izv = new Izvodjac * [kap = k];
	}

	Pesma(const Pesma& p) = delete; // not possible to use copy constructor, or moving constructor

	int get_mins() const { return min; }

	int get_sec() const { return sec; }

	friend bool longer(const Pesma& p1, const Pesma& p2)
	{
		if (p1.min > p2.min || (p1.min == p2.min && p1.sec > p2.sec))
			return true;
		return false;
	}

	void add_izvodjac(Izvodjac* i)
	{
		if (br < kap)
			izv[br++] = i;
	}

	void print() const;

};

#endif