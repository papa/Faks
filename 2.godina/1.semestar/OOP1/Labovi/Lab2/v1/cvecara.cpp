#include "cvecara.h"

void Cvecara::pisi(ostream& os) const
{
	os << "zarada:" << zarada << "RSD" << endl;
	Node* cur = first;
	while (cur)
	{
		os << *(cur->buket) << endl;
		cur = cur->next;
	}
}

void Cvecara::brisi()
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

void Cvecara::kopiraj(const Cvecara& c)
{
	Node* cur = c.first;
	while (cur)
	{
		dodajBuket(*(cur->buket));
		cur = cur->next;
	}
}

void Cvecara::premesti(Cvecara& c)
{
	first = c.first;
	last = c.last;
	c.first = c.last = nullptr;
}

void Cvecara::dodajBuketKraj(const Buket& b)
{
	Node* newNode = new Node(b);
	if (last == nullptr)
	{
		first = last = newNode;
	}
	else
	{
		last->next = newNode;
		last = newNode;
	}
}

void Cvecara::dodajBuketPocetak(const Buket& b)
{
	Node* newNode = new Node(b);
	if (first == nullptr)
	{
		first = last = newNode;
	}
	else
	{
		newNode->next = first;
		first = newNode;
	}
}

Cvecara::Cvecara(const Cvecara& c)
{
	kopiraj(c);
}

Cvecara::Cvecara(Cvecara&& c)
{
	premesti(c);
}

Cvecara& Cvecara::operator=(const Cvecara& c)
{
	if (this != &c)
	{
		brisi();
		kopiraj(c);
	}
	return *this;
}

Cvecara& Cvecara::operator=(Cvecara&& c)
{
	if (this != &c)
	{
		brisi();
		premesti(c);
	}
	return *this;
}

Cvecara::~Cvecara()
{
	brisi();
}

void Cvecara::dodajBuket(const Buket& b)
{
	if (b.izracunajZaradu() <= 0.2 * b.izracunajNCenu())
	{
		cout << "Zarada buketa nije veca od 20% nabavne cene" << endl;
		return;
	}

	Node* cur = first;
	Node* prev = nullptr;
	zarada -= b.izracunajNCenu();
	while (cur)
	{
		if (cur->buket->izracunajPCenu() > b.izracunajPCenu())
			break;
		prev = cur;
		cur = cur->next;
	}
	if (cur == nullptr)
	{
		dodajBuketKraj(b);
	}
	else if(prev == nullptr)
	{
		dodajBuketPocetak(b);
	}
	else
	{
		Node* newNode = new Node(b);
		prev->next = newNode;
		newNode->next = cur;
	}
}

void Cvecara::prodajBuket(int pos)
{
	Node* cur = first;
	Node* prev = nullptr;
	for (int i = 0; cur && i < pos-1;i++, prev = cur,cur = cur->next);
	if (cur)
	{
		zarada += cur->buket->izracunajPCenu();
		if (prev)
		{
			prev->next = cur->next;
			if (cur == last) last = prev;
			delete cur;
		}
		else
		{
			first = cur->next;
			if (cur == last) last = first;
			delete cur;
		}
	}
}

ostream& operator<<(ostream& os, const Cvecara& c)
{
	c.pisi(os);
	return os;
}
