#ifndef _tim_h_
#define _tim_h_

#include "igrac.h"
#include "greske.h"

class Tim
{
protected:
	string naziv;
	int maksBrIgraca;
	Igrac** igraci;

	virtual void pisi(ostream& os) const;

	void brisi();
	void kopiraj(const Tim& t);
	void premesti(Tim& t);
public:
	Tim(const string& naz, int br);

	Tim(const Tim& t);
    Tim(Tim&& t);

	Tim& operator = (const Tim& t);
	Tim& operator = (Tim&& t);

	Igrac* operator [] (int pos);
	const Igrac* operator [] (int pos) const;

	virtual void prikljuciIgraca(Igrac* igrac, int pos);

	int getTrenutniBrojIgraca() const;

	double getSrednjaVrednostTima() const;

	friend bool operator == (const Tim& t1, const Tim& t2);
	friend bool operator != (const Tim& t1, const Tim& t2);

	friend ostream& operator << (ostream& os, const Tim& t);

	int getKap() const;

	virtual ~Tim();
};

#endif
