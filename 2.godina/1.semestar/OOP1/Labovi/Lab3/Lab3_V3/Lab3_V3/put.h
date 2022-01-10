#ifndef _put_h_
#define _put_h_

#include "tacka.h"
#include "lista.h"
#include "greske.h"
#include <iostream>

class Put
{
	Lista<Tacka> tacke;
	
	void pisi(ostream& os) const;
public:
	Put() = default;

	Put& operator += (const Tacka& t);

	double duzinaPuta() const;

	friend ostream& operator << (ostream& os, const Put& p);
};

#endif