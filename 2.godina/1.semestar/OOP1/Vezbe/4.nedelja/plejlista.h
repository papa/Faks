#ifndef _plejlista_h_
#define _plejlista_h_

#include "pesma.h"

class Plejlista
{
	int min = 0;
	int sec = 0;
	struct Elem
	{
		Pesma* pes;
		Elem* nxt;

		Elem(Pesma* p, Elem* nx = nullptr) : pes(p), nxt(nx) {}
	};
	Elem* first = nullptr;
public:

	Plejlista() = default;
	~Plejlista();
	Plejlista(const Plejlista& plej);
	Plejlista(Plejlista&& plej);

	void add(Pesma* p);
	void sort();
	void print() const;
};

#endif // !_plejlista_h_

