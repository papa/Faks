#ifndef _par_h_
#define _par_h_

#include <iostream>

using namespace std;

template <typename T>
class Par
{
	T* first = nullptr;
	T* second = nullptr;
public:
	Par(T* fi, T* se);

	T* getPrvi() const;
	T* getDrugi() const;

	void setPrvi(const T* prvi);
	void setDrugi(const T* drugi);

	friend ostream& operator << (ostream& os, const Par<T>& par)
	{
		os << "[";
		if (par.first) os << *par.first;
		os << "-";
		if (par.second) os << *par.second;
		os << "]";
		return os;
	}

	friend bool operator == (const Par<T>& par1, const Par<T>& par2)
	{

		if ((par1.first == nullptr && par2.first != nullptr)
			|| (par1.first != nullptr && par2.first == nullptr))
			return false;

		if ((par1.second == nullptr && par2.second != nullptr)
			|| (par1.second != nullptr && par2.second == nullptr))
			return false;

		if (par1.first != nullptr && par2.first != nullptr &&
			!(*par1.first == *par2.first))
			return false;

		if (par1.second != nullptr && par2.second != nullptr &&
			!(*par1.second == *par2.second))
			return false;

		return true;
	}
	friend bool operator != (const Par<T>& par1, const Par<T>& par2)
	{
		return !(par1 == par2);
	}
};

template<typename T>
inline Par<T>::Par(T* fi, T* se)
	:first(fi), second(se)
{
}

template<typename T>
inline T* Par<T>::getPrvi() const
{
	return first;
}

template<typename T>
inline T* Par<T>::getDrugi() const
{
	return second;
}

template<typename T>
inline void Par<T>::setPrvi(const T* prvi)
{
	first = prvi;
}

template<typename T>
inline void Par<T>::setDrugi(const T* drugi)
{
	second = drugi;
}

#endif