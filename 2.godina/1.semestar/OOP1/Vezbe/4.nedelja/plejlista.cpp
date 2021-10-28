#include "plejlista.h"

Plejlista::~Plejlista()
{
	Elem* curr = first, * old = nullptr;
	while (curr)
	{
		old = curr;
		curr = curr->nxt;
		delete old;
	}
}

Plejlista::Plejlista(const Plejlista& plej)
{
	min = plej.min;
	sec = plej.sec;
	Elem* curr = plej.first;
	Elem* nw = nullptr, * last = nullptr;
	while (curr)
	{
		nw = new Elem(curr->pes);
		if (!first) first = nw;
		else last->nxt = nw;
		last = nw;
		curr = curr->nxt;
	}
}

Plejlista::Plejlista(Plejlista&& plej)
{
	min = plej.min;
	sec = plej.sec;
	first = plej.first;
	plej.first = nullptr;
}

void Plejlista::add(Pesma* p)
{
	first = new Elem(p, first);
	min += p->get_mins() + (sec + p->get_sec()) / 60;
	sec = (sec + p->get_sec()) % 60;

}

void Plejlista::sort()
{
	for (Elem* t1 = first;t1;t1 = t1->nxt)
	{
		for (Elem* t2 = t1->nxt;t2;t2 = t2->nxt)
		{
			if (longer(*t1->pes, *t2->pes))
			{
				Pesma* p = t1->pes;
				t1->pes = t2->pes;
				t2->pes = p;
			}
		}
	}
}

void Plejlista::print() const
{
	cout << "Lista - trajanje: " << min << ":" << sec << endl;
	for (Elem* curr = first;curr;curr = curr->nxt)
	{
		curr->pes->print();
	}
}
