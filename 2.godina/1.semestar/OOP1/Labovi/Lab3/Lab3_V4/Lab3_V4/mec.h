#ifndef _mec_h_
#define _mec_h_

#include "tim.h"
#include "par.h"

class Mec
{
	Par<Tim> timovi;
	static enum ISHOD{POBEDA_DOMACIN, NERESENO, POBEDA_GOST};
	ISHOD ishod;
	bool odigranMec = false;
	int pobednik = -1;

	void pisi(ostream& os) const;

public:
	Mec(Tim* tim1, Tim* tim2);

	Par<Tim> getPar() const;

	void odigrajMec();

	Par<int> getPoeni() const;

	bool daLiJeOdigran() const;

	friend ostream& operator << (ostream& os, const Mec& m);
};

#endif


