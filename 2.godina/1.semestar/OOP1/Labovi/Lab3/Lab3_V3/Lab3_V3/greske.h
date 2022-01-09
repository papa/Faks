#ifndef _greske_h_
#define _greske_h_

#include <exception>

class GLosaPozicija : public std::exception
{
public:
	GLosaPozicija() : std::exception("Nema podatka na datom indeksu")
	{

	}
};

class GIsteTacke : public std::exception
{
public:
	GIsteTacke() : std::exception("Pokusaj dodavanje tacke koja vec postoji na putu")
	{

	}
};

#endif
