#include "tim.h"

void Tim::pisi(ostream& os) const
{
	os << naziv << "[";
	for (int i = 0; i < maksBrIgraca; i++)
	{
		if (igraci[i])
			cout << *igraci[i];
		if (i != maksBrIgraca - 1) cout << ",";
	}
	cout << "]";
}

void Tim::brisi()
{
	delete[] igraci;
}

void Tim::kopiraj(const Tim& t)
{
	maksBrIgraca = t.maksBrIgraca;
	naziv = t.naziv;
	igraci = new Igrac * [maksBrIgraca];
	for (int i = 0; i < maksBrIgraca; i++)
		igraci[i] = new Igrac(*t.igraci[i]);
}

void Tim::premesti(Tim& t)
{
	igraci = t.igraci;
	naziv = t.naziv;
	maksBrIgraca = t.maksBrIgraca;

	t.igraci = nullptr;
}

Tim::Tim(const string& naz, int br)
	:naziv(naz), maksBrIgraca(br)
{
	igraci = new Igrac * [maksBrIgraca];
	for (int i = 0; i < maksBrIgraca; i++)
		igraci[i] = nullptr;
}

Tim::Tim(const Tim& t)
{
	kopiraj(t);
}

Tim::Tim(Tim&& t)
{
	premesti(t);
}

Tim& Tim::operator=(const Tim& t)
{
	if (this != &t)
	{
		brisi();
		kopiraj(t);
	}
	return *this;
}

Tim& Tim::operator=(Tim&& t)
{
	if (this != &t)
	{
		brisi();
		premesti(t);
	}
	return *this;
}

Igrac* Tim::operator[](int pos)
{
	return igraci[pos];
}

const Igrac* Tim::operator[](int pos) const
{
	return igraci[pos];
}

void Tim::prikljuciIgraca(Igrac& igrac, int pos)
{
	igraci[pos] = new Igrac(igrac);
}

int Tim::getTrenutniBrojIgraca() const
{
	int br = 0;
	for (int i = 0; i < maksBrIgraca;i++)
		if (igraci[i])
			br++;
	return br;
}

double Tim::getSrednjaVrednostTima() const
{
	double sum = 0;
	int cnt = 0;
	for (int i = 0; i < maksBrIgraca; i++)
	{
		if (igraci[i])
		{
			sum += igraci[i]->getVrednost();
			cnt++;
		}
	}
	if (cnt == 0) return 0.;

	return sum / cnt;
}

int Tim::getKap() const
{
	return maksBrIgraca;
}

Tim::~Tim()
{
	for (int i = 0; i < maksBrIgraca;i++)
	{
		delete igraci[i];
		igraci[i] = nullptr;
	}
	delete[] igraci;
}

bool operator==(const Tim& t1, const Tim& t2)
{
	if (t1.naziv != t2.naziv || t1.maksBrIgraca != t2.maksBrIgraca) return false;

	for (int i = 0; i < t1.maksBrIgraca; i++)
	{
		if (t1.igraci[i] == nullptr && t2.igraci[i] == nullptr) 
			continue;

		if ((t1.igraci[i] == nullptr && t2.igraci[i] != nullptr)
			|| (t1.igraci[i] != nullptr && t2.igraci[i] == nullptr)
			|| (*t1.igraci[i] != *t2.igraci[i]))
			return false;
	}
	return true;
}

bool operator!=(const Tim& t1, const Tim& t2)
{
	return !(t1 == t2);
}

ostream& operator<<(ostream& os, const Tim& t)
{
	t.pisi(os);
	return os;
}
