#include "korisnik.h"

Korisnik::Korisnik(const string& i, const string& e) : ime(i), email(e)
{

}

string Korisnik::getIme() const
{
	return ime;
}

string Korisnik::getMail() const
{
	return email;
}

ostream& operator<<(ostream& os, const Korisnik& k)
{
	k.pisi(os);
	return os;
}

void Korisnik::pisi(ostream& os) const
{
	os << "(" << ime << ")" << email;
}
