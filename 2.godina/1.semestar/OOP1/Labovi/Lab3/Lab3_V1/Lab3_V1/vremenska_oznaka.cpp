#include "vremenska_oznaka.h"

ostream& operator<<(ostream& os, const VremenskaOznaka& vo)
{
	vo.pisi(os);
	return os;
}

void VremenskaOznaka::pisi(ostream& os) const
{
	os << dan << "." << mes << "." << god << "-" << sat << ":" << min;
}

VremenskaOznaka::VremenskaOznaka(int g, int m, int d, int s, int mi)
	: god(g), mes(m), dan(d), sat(s), min(mi)
{
}
