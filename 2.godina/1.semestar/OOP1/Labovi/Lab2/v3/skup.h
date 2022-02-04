#ifndef _skup_h_
#define _skup_h_

#include <iostream>
#include <string>

using namespace std;

class Skup
{
private:
	struct Node
	{
		Node* next = nullptr;
		char karakter;
		Node(char c) : karakter(c) {}
	};
	Node* first = nullptr;
	Node* last = nullptr;
	void dodajKarakter(const char c);
public:
	Skup(const string& s);

	~Skup();

	Skup(const Skup& s) = delete;
	Skup& operator = (const Skup& s) = delete;

	Skup& operator += (const char c);

	bool operator () (const char c) const;
};

#endif
