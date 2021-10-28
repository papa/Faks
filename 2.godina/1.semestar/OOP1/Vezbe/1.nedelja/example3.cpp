#include <iostream>

using namespace std;

int main()
{
    int i = 2;

    //pointer to i
    int *pointer_i = &i;
    cout << "Pointer " << *pointer_i << endl; // 2

    // reference to i, ref_i is just new name for i
    //ref can only be connected to one variable through whole program
    int& ref_i = i; // value has to be assigned
    cout << "Reference " << ref_i << endl;// 2

    ref_i++;// i is incremented

    cout << "Pointer " << *pointer_i << endl; // 3
    cout << "Reference " << ref_i << endl;// 3

    int j  = 4;
    pointer_i = &j;

    cout << "Pointer " << *pointer_i << endl; // 4
    cout << "Reference " << ref_i << endl;// 3

    cout << "i " << i << endl; //3

    return 0;
}
