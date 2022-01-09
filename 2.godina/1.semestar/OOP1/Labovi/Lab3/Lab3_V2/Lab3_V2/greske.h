#ifndef _greske_h_
#define _greske_h_

#include <exception>

class GLosaPozicija : public std::exception
{
public:
	GLosaPozicija() : std::exception("Ne postoji podatak na zadatoj poziciji")
	{

	}
};

class GDodavanjeRukovaoca : public std::exception
{
public:
	GDodavanjeRukovaoca() : std::exception("Detalji posiljke su izracunati u trenutku dodavana rukovaoca")
	{

	}
};

class GNeizracunatiDetalji : public std::exception
{
public:
	GNeizracunatiDetalji() : std::exception("Detalji posiljke jos uvek nisu izracunati")
	{

	}
};

#endif
