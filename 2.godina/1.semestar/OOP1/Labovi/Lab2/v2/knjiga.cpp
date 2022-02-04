#include "knjiga.h"

int Knjiga::ID = 0;

void Knjiga::pisi(ostream& os) const
{
	os << "KNJIGA " << id << ":" << naziv << "-" << autor;
}

Knjiga::Knjiga(const string& naz, const string& aut) : naziv(naz), autor(aut){}

Knjiga* Knjiga::operator!() const
{
	return new Knjiga(naziv, autor);
}

ostream& operator<<(ostream& os, const Knjiga& k)
{
	k.pisi(os);
	return os;
}
