#ifndef _privilegovani_tim_h_
#define _privilegovani_tim_h_

#include "tim.h"
#include <iostream>

using namespace std;

class PrivilegovaniTim : public Tim
{
protected:
	double minVrednost;

	void pisi(ostream& os) const override;
public:
	PrivilegovaniTim(const string& naz, int br, double minV);

	PrivilegovaniTim(const PrivilegovaniTim& t);
	PrivilegovaniTim(PrivilegovaniTim&& t);

	PrivilegovaniTim& operator = (const PrivilegovaniTim& pt);
	PrivilegovaniTim& operator = (PrivilegovaniTim&& pt);

	void prikljuciIgraca(Igrac* igrac, int pos) override;

	virtual ~PrivilegovaniTim();
};

#endif