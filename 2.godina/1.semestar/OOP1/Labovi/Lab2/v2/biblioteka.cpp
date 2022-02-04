#include "biblioteka.h"

void Biblioteka::dodajKnjigu(Knjiga* k)
{
	if (br == kapacitet)
	{
		cout << "Biblioteka je ispunjena do maksimalnog kapaciteta" << endl;
		delete k;
		return;
	}
	knjige[br] = k;
	br++;
}

void Biblioteka::pisi(ostream& os) const
{
	os << "BIBLIOTEKA " << naziv << " " << br << "/" << kapacitet << endl;
	for (int i = 0; i < br;i++)
		cout << *(knjige[i]) << endl;
}

void Biblioteka::brisi()
{
	for (int i = 0; i < br;i++) delete knjige[i];
	delete[] knjige;
}

void Biblioteka::kopiraj(const Biblioteka& b)
{
	kapacitet = b.kapacitet;
	br = b.br;
	naziv = b.naziv;
	knjige = new Knjiga * [kapacitet];
	for (int i = 0; i < br;i++)
	{
		(*this) += *(knjige[i]);
	}
}

void Biblioteka::premesti(const Biblioteka& b)
{
	kapacitet = b.kapacitet;
	br = b.br;
	naziv = b.br;
	knjige = new Knjiga * [kapacitet];
	for (int i = 0; i < br;i++)
	{
		knjige[i] = b.knjige[i];
		b.knjige[i] = nullptr;
	}
}

Biblioteka::Biblioteka(const string& naz, int kap) : naziv(naz), kapacitet(kap)
{
	knjige = new Knjiga * [kap];
}

Biblioteka::Biblioteka(const Biblioteka& b)
{
	kopiraj(b);
}

Biblioteka::Biblioteka(Biblioteka&& b)
{
	premesti(b);
}

Biblioteka& Biblioteka::operator=(const Biblioteka& b)
{
	if (this != &b)
	{
		brisi();
		kopiraj(b);
	}
	return *this;
}

Biblioteka& Biblioteka::operator=(Biblioteka&& b)
{
	if (this != &b)
	{
		brisi();
		premesti(b);
	}
	return *this;
}

Biblioteka::~Biblioteka()
{
	for (int i = 0; i < br;i++) delete knjige[i];
	delete[] knjige;
}

Knjiga* Biblioteka::dohvatiKnjigu(int idK)
{
	for (int i = 0;i < br;i++)
		if (idK == knjige[i]->dohvatiID()) 
			return knjige[i];
	return nullptr;
}

Biblioteka& Biblioteka::operator+=(const Knjiga& k)
{
	dodajKnjigu(!k);
	return *this;
}

Biblioteka& Biblioteka::operator+=(Knjiga&& k)
{
	dodajKnjigu(!k);
	return *this;
}

ostream& operator << (ostream& os, const Biblioteka& b)
{
	b.pisi(os);
	return os;
}


