#include <iostream>
#include <cmath>

using namespace std;

//Task
//program to calc are of triangle
//purpose :
//two functions can have same name,
//but if prototypes (arguments) are different
//they can both exist
//name overloading

//area of triangle
double P(double a, double b, double c)
{
    if(a > 0 && b > 0 && c > 0 && a + b > c &&
    a + c > b && b + c > a)
    {
        double s = (a + b + c) / 2;
        double p = sqrt(s * (s-a) * (s-b) * (s-c));
        return p;
    }
    else
    {
        return -1;
    }
}

//triangle with two sides equal
double P(double a, double b)
{
    return P(a,b,b);
}

//triangle with all sides equal
double P(double a)
{
    return P(a,a,a);
}

int foo(int b = 5) //given value if parameter is not given in function call
{
    return b;
}

int main()
{
    while(true)
    {
        int which_triangle;
        cin >> which_triangle;
        double p = -1;
        if(which_triangle == 1)
        {
            double a,b,c;
            cin >> a >> b >> c;
            p = P(a,b,c);
        }
        else if(which_triangle == 2)
        {
            double a,b;
            cin >> a >> b;
            p = P(a,b);
        }
        else if(which_triangle == 3)
        {
            double a;
            cin >> a;
            p = P(a);
        }

        if(p > 0)
            cout << p << endl;
        else
            break;
    }

    return 0;
}
