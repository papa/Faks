#ifndef _lista_h_
#define _lista_h_

#include "greske.h"

template <typename T>
class Lista
{
	struct Elem
	{
		Elem* next;
		T data;
		Elem(const T& p, Elem* s = nullptr) : data(p), next(s) {}
	};
	Elem* first = nullptr;
	Elem* last = nullptr;
	mutable Elem* curr = nullptr;
	mutable Elem* prev = nullptr;

	int numElem = 0;

	void brisi();
	void premesti(Lista& l);
	void kopiraj(const Lista& l);
public:
	Lista();

	Lista(const Lista& l);
	Lista(Lista&& l);
	
	Lista& operator = (const Lista& l);
	Lista& operator = (Lista&& l);

	int getBrElem() const;

	Lista& dodajElem(const T& data);

	Lista& naPrvi();
	const Lista& naPrvi() const;

	Lista& naSledeci();
	const Lista& naSledeci() const;

	T& getTek();
	const T& getTek() const;

	bool imaTek() const;

	~Lista();
};

template<typename T>
inline bool Lista<T>::imaTek() const
{
	return curr != nullptr;
}

template<typename T>
inline Lista<T>::~Lista()
{
	brisi();
}

template<typename T>
inline void Lista<T>::brisi()
{
	Elem* temp = first;
	while (temp)
	{
		Elem* stari = temp;
		temp = temp->next;
		delete stari;
	}
	first = last = curr = prev = nullptr;
}

template<typename T>
inline void Lista<T>::premesti(Lista& l)
{
	first = l.first;
	last = l.last;
	curr = l.curr;
	prev = l.prev;
	l.first = l.last = l.curr = l.prev = nullptr;

	numElem = l.numElem;
}

template<typename T>
inline void Lista<T>::kopiraj(const Lista& l)
{
	first = last = curr = prev = nullptr;

	for (Elem* temp = l.first; temp; temp = temp->next) {
		Elem* newElem = new Elem(temp->data);
		last = (!first ? first : last->next) = newElem;

		if (temp == l.curr) curr = newElem;
		if (temp == l.prev) prev = newElem;
	}
}

template<typename T>
inline Lista<T>::Lista()
{
}

template<typename T>
inline Lista<T>::Lista(const Lista& l)
{
	kopiraj(l);
}

template<typename T>
inline Lista<T>::Lista(Lista&& l)
{
	premesti(l);
}

template<typename T>
inline Lista<T>& Lista<T>::operator=(const Lista<T>& l)
{
	if (this != &l)
	{
		brisi();
		kopiraj(l);
	}
	return *this;
}

template<typename T>
inline Lista<T>& Lista<T>::operator=(Lista<T>&& l)
{
	if (this != &l)
	{
		brisi();
		premesti(l);
	}
	return *this;
}

template<typename T>
inline int Lista<T>::getBrElem() const
{
	return numElem;
}

template<typename T>
inline Lista<T>& Lista<T>::dodajElem(const T& data)
{
	last = (!first ? first : last->next) = new Elem(data);
	numElem++;
	return *this;
}

template<typename T>
inline Lista<T>& Lista<T>::naPrvi()
{
	curr = first;
	prev = nullptr;
	return *this;
}

template<typename T>
inline const Lista<T>& Lista<T>::naPrvi() const
{
	curr = first;
	prev = nullptr;
	return *this;
}

template<typename T>
inline Lista<T>& Lista<T>::naSledeci()
{
	prev = curr;
	if (curr) curr = curr->next;
	return *this;
}

template<typename T>
inline const Lista<T>& Lista<T>::naSledeci() const
{
	prev = curr;
	if (curr) curr = curr->next;
	return *this;
}

template<typename T>
inline T& Lista<T>::getTek()
{
	if (!imaTek()) throw GNemaTekuci();
	return curr->data;
}

template<typename T>
inline const T& Lista<T>::getTek() const
{
	if (!imaTek()) throw GNemaTekuci();
	return curr->data;
}

#endif