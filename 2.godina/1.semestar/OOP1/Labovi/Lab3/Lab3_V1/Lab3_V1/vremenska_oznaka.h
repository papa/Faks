#ifndef _vremenska_oznaka_h_
#define _vremenska_oznaka_h_

#include <iostream>

using namespace std;

class VremenskaOznaka
{
	int god, mes, dan, sat, min;

	void pisi(ostream& os) const;
public:
	VremenskaOznaka(int g, int m, int d, int s, int mi);
	
	friend ostream& operator << (ostream& os, const VremenskaOznaka& vo);
};

#endif
