#include "buket.h"

bool operator<(const Buket& b1, const Buket& b2)
{
	return b1.izracunajPCenu() < b2.izracunajPCenu();
}

bool operator<=(const Buket& b1, const Buket& b2)
{
	return b1 < b2 || b1 == b2;
}

bool operator>(const Buket& b1, const Buket& b2)
{
	return !(b1 <= b2);
}

bool operator>=(const Buket& b1, const Buket& b2)
{
	return !(b1 < b2);
}

bool operator==(const Buket& b1, const Buket& b2)
{
	return b1.izracunajPCenu() == b2.izracunajPCenu();
}

bool operator!=(const Buket& b1, const Buket& b2)
{
	return !(b1 == b2);
}

ostream& operator<<(ostream& os, const Buket& b)
{
	b.pisi(os);
	return os;
}

Buket::~Buket()
{
	brisi();
}

int Buket::izracunajNCenu() const
{
	int suma = 0;
	Node* curr = first;
	while (curr)
	{
		suma += curr->cvet->getNCena();
		curr = curr->next;
	}
	return suma;
}

int Buket::izracunajPCenu() const
{
	int suma = 0;
	Node* curr = first;
	while (curr)
	{
		suma += curr->cvet->getPCena();
		curr = curr->next;
	}
	return suma;
}

int Buket::izracunajZaradu() const
{
	return izracunajPCenu() - izracunajNCenu();
}

void Buket::dodajCvet(const Cvet& c)
{
	if (first)
	{
		Node* newNode = new Node(c);
		last->next = newNode;
		last = newNode;
	}
	else
	{
		Node* newNode = new Node(c);
		first = last = newNode;
	}
}

void Buket::brisi()
{
	Node* cur = first;
	while (cur)
	{
		Node* old = cur;
		cur = cur->next;
		delete old;
	}
	first = last = nullptr;
}

void Buket::kopiraj(const Buket& b)
{
	Node* cur = b.first;
	while (cur)
	{
		dodajCvet(*(cur->cvet));
		cur = cur->next;
	}
}

void Buket::premesti(Buket& b)
{
	first = b.first;
	last = b.last;
	b.first = b.last = nullptr;
}

void Buket::pisi(ostream& os) const
{
	os << "(";
	for (Node* i = first;i;i = i->next)
	{
		bool bio = false;
		for (Node* j = first; i != j && !bio;j = j->next)
		{
			if (*(i->cvet) == *(j->cvet)) bio = true;
		}
		if (!bio)
		{
			if (i != first) os << ", ";
			os << *(i->cvet);
		}
	}
	os << ")";
	os << izracunajPCenu() << "RSD";
}

Buket::Buket(const Buket& b)
{
	kopiraj(b);
}

Buket::Buket(Buket&& b)
{
	premesti(b);
}

Buket& Buket::operator=(const Buket& b)
{
	if(this != &b)
	{
		brisi();
		kopiraj(b);
	}
	return *this;
}

Buket& Buket::operator=(Buket&& b)
{
	if (this != &b)
	{
		brisi();
		premesti(b);
	}
	return *this;
}
