#include "skup.h"

void Skup::dodajKarakter(const char c)
{
	Node* newNode = new Node(c);
	if (first)
	{
		last->next = newNode;
		last = newNode;
	}
	else
	{
		first = last = newNode;
	}
}

Skup::Skup(const string& s)
{
	for (int i = 0; i < s.size();i++)
	{
		*this += s[i];
	}
}

Skup::~Skup()
{
	Node* cur = first;
	while (cur)
	{
		Node* old = cur;
		cur = cur->next;
		delete old;
	}
}

Skup& Skup::operator+=(const char c)
{
	if (!(*this)(c))
	{
		dodajKarakter(c);
	}
	return *this;

}

bool Skup::operator()(const char c) const
{
	Node* cur = first;
	while (cur)
	{
		if (cur->karakter == c)
			return true;
		cur = cur->next;
	}
	return false;
}
