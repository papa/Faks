#include "privilegovani_tim.h"

void PrivilegovaniTim::pisi(ostream& os) const
{
	Tim::pisi(os);
	os << "(" << minVrednost << ")";
}

PrivilegovaniTim::PrivilegovaniTim(const string& naz, int br, double minV)
	:Tim(naz,br), minVrednost(minV)
{

}

PrivilegovaniTim::PrivilegovaniTim(const PrivilegovaniTim& t)
	: Tim(t)
{
	minVrednost = t.minVrednost;
}

PrivilegovaniTim::PrivilegovaniTim(PrivilegovaniTim&& t)
	:Tim(t)
{
	minVrednost = t.minVrednost;
}

PrivilegovaniTim& PrivilegovaniTim::operator=(const PrivilegovaniTim& pt)
{
	if (this != &pt) 
	{
		Tim::operator=(pt);
		minVrednost = pt.minVrednost;
	}
	return *this;
}

PrivilegovaniTim& PrivilegovaniTim::operator=(PrivilegovaniTim&& pt)
{
	if (this != &pt) 
	{
		Tim::operator=(pt);
		minVrednost = pt.minVrednost;
	}
	return *this;
}

void PrivilegovaniTim::prikljuciIgraca(Igrac& igrac, int pos)
{
	if (igrac.getVrednost() < minVrednost) throw GMinVrednost();
	Tim::prikljuciIgraca(igrac, pos);
}

PrivilegovaniTim::~PrivilegovaniTim()
{
}
