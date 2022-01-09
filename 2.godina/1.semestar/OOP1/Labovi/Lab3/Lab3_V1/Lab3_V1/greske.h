#ifndef _greske_h_
#define _greske_h_

#include <exception>

class GNemaTekuci : public std::exception
{
public:
	GNemaTekuci() : std::exception("Nema tekuci element")
	{

	}
};

class GPoslataPoruka : public std::exception
{
public:
	GPoslataPoruka() : std::exception("Poruka je vec poslata")
	{

	}
};

#endif
