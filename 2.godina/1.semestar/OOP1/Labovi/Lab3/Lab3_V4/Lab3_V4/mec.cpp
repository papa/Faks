#include "mec.h"

void Mec::pisi(ostream& os) const
{
	cout << timovi << " ";
	if (odigranMec)
	{
		if (ishod == POBEDA_DOMACIN) cout << "POBEDA_DOMACIN";
		else if (ishod == NERESENO) cout << "NERESENO";
		else cout << "POBEDA_GOST";
	}
}

Mec::Mec(Tim* tim1, Tim* tim2)
	:timovi(tim1, tim2)
{
}

Par<Tim> Mec::getPar() const
{
	return timovi;
}

void Mec::odigrajMec()
{
	double v1 = timovi.getPrvi()->getSrednjaVrednostTima();
	double v2 = timovi.getDrugi()->getSrednjaVrednostTima();
	if (v1 > v2)
	{
		ishod = POBEDA_DOMACIN;
		pobednik = 1;
		timovi.getPrvi()->uvecajVrednost(10);
		timovi.getDrugi()->smanjiVrednost(10);
	}
	else if (v1 == v2)
	{
		ishod = NERESENO;
		pobednik = -1;
	}
	else
	{
		ishod = POBEDA_GOST;
		pobednik = 2;
		timovi.getDrugi()->uvecajVrednost(10);
		timovi.getPrvi()->smanjiVrednost(10);
	}
	odigranMec = true;
}

Par<int> Mec::getPoeni() const
{
	if (!odigranMec) throw GNeodigranMec();
	int bodoviDomacin, bodoviGost;
	if (pobednik == 1)
	{
		bodoviDomacin = 3;
		bodoviGost = 0;
	}
	else if(pobednik == 2)
	{
		bodoviDomacin = 0;
		bodoviGost = 3;
	}
	else
	{
		bodoviDomacin = bodoviGost = 1;
	}
	return Par<int>(new int(bodoviDomacin), new int(bodoviGost));
}

bool Mec::daLiJeOdigran() const
{
	return odigranMec;
}

ostream& operator<<(ostream& os, const Mec& m)
{
	m.pisi(os);
	return os;
}
