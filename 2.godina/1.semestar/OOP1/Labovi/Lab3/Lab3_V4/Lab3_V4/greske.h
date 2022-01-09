#ifndef _greske_h_
#define _greske_h_

#include <exception>

class GMinVrednost : public std::exception
{
public:
	GMinVrednost() : std::exception("Vrednost igraca je manja od minimalne vrednosti")
	{

	}
};

class GIgracNijePrikljucen : public std::exception {
public:
	GIgracNijePrikljucen() : std::exception("Igrac na datoj poziciji nije prikljucen")
	{

	}
};

class GIgracVecPrikljucen : public std::exception {
public:
	GIgracVecPrikljucen() : std::exception("Igrac na datoj poziciji je vec prikljucen")
	{

	}
};

class GNeodigranMec : public std::exception {
public:
	GNeodigranMec() : std::exception("Mec jos uvek nije odigran, da bi se sakupili bodovi")
	{

	}
};

#endif