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

	int numElem = 0;

	void brisi();
	void premesti(Lista& l);
	void kopiraj(const Lista& l);
	Lista& dodajElem(const T& data);
public:
	Lista();

	Lista(const Lista& l);
	Lista(Lista&& l);

	Lista& operator = (const Lista& l);
	Lista& operator = (Lista&& l);

	int getBrElem() const;

	Lista& operator += (const T& data);

	T& operator [] (int pos);

	const T& operator [] (int pos) const;

	~Lista();
};

template<typename T>
inline const T& Lista<T>::operator[](int pos) const
{
	if (pos >= numElem || pos < 0) throw GLosaPozicija();
	int p = 0;
	Elem* curr = first;
	while (curr)
	{
		if (p == pos)
			return curr->data;
		p++;
		curr = curr->next;
	}
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
	first = last = nullptr;
}

template<typename T>
inline void Lista<T>::premesti(Lista& l)
{
	first = l.first;
	last = l.last;
	l.first = l.last = nullptr;

	numElem = l.numElem;
}

template<typename T>
inline void Lista<T>::kopiraj(const Lista& l)
{
	first = last = nullptr;

	for (Elem* temp = l.first; temp; temp = temp->next) {
		dodajElem(temp->data);
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
inline Lista<T>& Lista<T>::operator+=(const T& data)
{
	(*this).dodajElem(data);
	return *this;
}

template<typename T>
inline T& Lista<T>::operator[](int pos)
{
	return const_cast<T&>((const_cast<const Lista&>(*this))[pos]);
}

template<typename T>
inline Lista<T>& Lista<T>::dodajElem(const T& data)
{
	last = (!first ? first : last->next) = new Elem(data);
	numElem++;
	return *this;
}

#endif