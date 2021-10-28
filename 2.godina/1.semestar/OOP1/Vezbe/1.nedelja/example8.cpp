#include <iostream>

using namespace std;

//return type of this function is int&
//return value of this function
//can be used as left value
int& double_number(int& x)
{
    x*=2;
    return x;
}

int& double_number_2(int x)
{
    x*=2;
    return x;
}

int main()
{

    int x = 1;
    //decltype returns type of variable
    //expression in decltype() doesn't change the value of x
    decltype(x++) y = x;
    /*
    decltype(++x) would be int&, so reference
    and it can't contain literal
    ++x - returns reference
    */
    decltype(++x) z = x; // int& z = x;

    cout << x << endl; // 1, value is not changed

    double_number(x);
    cout << x << endl;

    double_number(x) = 10; // left value - int&
    cout << x << endl; //10
    cout << z << endl; //10

    double_number_2(x) = 20;
    cout << x << endl; //10, remains the same because x is passed by value to the function

    return 0;
}
