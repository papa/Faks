#ifndef _kvadar_h_
#define _kvadar_h_

#include "A.h"
#include <iostream>

using namespace std;

class Kvadar
{
	static double Vuk, Vmax; // static parameters, all objects can access it
	double a, b, c;

	//private constructor, not to break Vmax rules 
	Kvadar(double aa, double bb, double cc) { a = aa;b = bb;c = cc; }

public:

	//friend class, not defined yet
	friend class A;

	//disabling copy constructor
	Kvadar(const Kvadar& k) = delete;

	//static method not connected to any object
	//doesn't have hidden parameter this
	static double get_Vmax() { return Vmax; }

	static bool set_Vmax(double max)
	{
		if (max < Vuk) return false;
		Vmax = max;
		return true;
	}

	static double get_Vuk() { return Vuk; }

	static Kvadar* make(double a, double b, double c)
	{
		double V = a * b * c;
		if (a <= 0 || b <= 0 || c <= 0 || V + Vuk > Vmax) 
			return nullptr;
		
		Vuk += V;
		return new Kvadar(a, b, c);
	}

	static Kvadar* read()
	{
		double a, b, c;
		cin >> a >> b >> c;
		return make(a, b, c);
	}

	double get_a() const { return a; }
	double get_b() const { return b; }
	double get_c() const { return c; }

	double V() const { return a * b * c; }

	void print() const
	{
		cout << "kvadarp[" << a << "," << b << "," << c << "]" << endl;
	}

	~Kvadar()
	{
		Vuk -= V();
	}

	friend bool bigger_V(const Kvadar& k1, const Kvadar& k2);
};

#endif _kvadar_h_
