#include <iostream>

using namespace std;

int main()
{
    //standard input
    string name;
    string surname;
    cout << "Name : ";
    cin >> name;
    cout << "Surname : ";
    cin >> surname;
    string full_name = name + " " + surname;
    cout << full_name << endl;
    return 0;
}
