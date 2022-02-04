#include "rec.h"

void Rec::pisi(ostream& os) const
{
	os << karakteri;
}

void Rec::citaj(istream& is)
{
	string s;
	getline(is, s);
	kreirajRec(s);
}

bool Rec::nosilacSloga(int pos) const
{
	if (Rec::jeSamoglasnik(karakteri[pos])) return true;

	if (Rec::jeNoseciSonant(karakteri[pos]))
	{
		if (pos > 0)
		{
			if (Rec::jeSamoglasnik(karakteri[pos - 1]))
				return false;
		}

		if (pos < karakteri.size() - 1)
		{
			if (Rec::jeSamoglasnik(karakteri[pos + 1]))
				return false;
		}
		return true;
	}

	return false;
}

bool Rec::jeSamoglasnik(const char c)
{
	char samoglasnici[] = { 'a', 'e','i','o','u' };
	for (int i = 0; i < 5;i++)
		if (c == samoglasnici[i] || c == Rec::velikoSlovo(samoglasnici[i]))
			return true;
	return false;
}

bool Rec::jeNoseciSonant(const char c)
{
	char noseciS[] = { 'l','r','n' };
	for (int i = 0; i < 3;i++)
		if (c == noseciS[i] || c == Rec::velikoSlovo(noseciS[i]))
			return true;
	return false;
}

char Rec::velikoSlovo(const char c)
{
	if (Rec::jeVelikoSlovo(c)) return c;
	return c - ('a' - 'A');
}

char Rec::maloSlovo(const char c)
{
	if (Rec::jeMaloSlovo(c)) return c;
	return c + ('a' - 'A');
}

bool Rec::jeMaloSlovo(const char c)
{
	return c >= 'a' && c <= 'z';
}

bool Rec::jeVelikoSlovo(const char c)
{
	return c >= 'A' && c <= 'Z';
}

string Rec::malaRec(const string& s)
{
	string sol = "";
	for (int i = 0;i < s.size();i++)
		sol += Rec::maloSlovo(s[i]);
	return sol;
}

void Rec::kreirajRec(const string& s)
{
	karakteri = "";
	for (int i = 0; i < s.size();i++)
	{
		if (Rec::jeMaloSlovo(s[i])
			|| Rec::jeVelikoSlovo(s[i]))
			karakteri += s[i];
	}
}

Rec::Rec(const string& s) {kreirajRec(s);}

int Rec::operator+() const
{
	return int(karakteri.size());
}

int Rec::operator~() const
{
	int cnt = 0;
	for (int i = 0; i < karakteri.size();i++)
	{
		if (nosilacSloga(i))
		{
			cnt++;
		}
	}
	return cnt;
}

int Rec::operator()(int pos) const
{
	int brSlogova = ~(*this);
	if (brSlogova == 0) return -1;
	if (pos < 0) pos = brSlogova + pos;
	int cnt = -1;
	for (int i = 0; i < karakteri.size();i++)
	{
		if (nosilacSloga(i))
		{
			cnt++;
			if (cnt == pos) return i;
		}
	}

	return -1;
}

string Rec::poslednjiSlog() const
{
	int pos = (*this)(-1);
	return karakteri.substr(pos);
}

string Rec::poslednjaDvaSloga() const
{
	int pos = (*this)(-2);
	return karakteri.substr(pos);
}

bool operator^(const Rec& r1, const Rec& r2)
{
	int slogoviR1 = ~r1;
	int slogoviR2 = ~r2;
	if (slogoviR1 == 0 || slogoviR2 == 0) return false;
	if (slogoviR1 > 1 && slogoviR2 > 1)
	{
		string krajR1 = r1.poslednjaDvaSloga();
		string krajR2 = r2.poslednjaDvaSloga();
		return Rec::malaRec(krajR1) == Rec::malaRec(krajR2);
	}
	else
	{
		string krajR1 = r1.poslednjiSlog();
		string krajR2 = r2.poslednjiSlog();
		return Rec::malaRec(krajR1) == Rec::malaRec(krajR2);
	}
}

ostream& operator<<(ostream& os, const Rec& r)
{
	r.pisi(os);
	return os;
}

istream& operator>>(istream& is, Rec& r)
{
	r.citaj(is);
	return is;
}
