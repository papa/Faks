#ifndef _rukovalac_h_
#define _rukovalac_h_

class Posiljka;

class Rukovalac
{
public:
	Rukovalac() = default;

	virtual void obradiPosiljku(Posiljka& p) = 0;

	virtual ~Rukovalac();
};

#endif