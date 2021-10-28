#include <iostream>
#include <string>

using namespace std;

//Task
//Program to sort dynamic array of strings

const int MAX_CITIES = 100;

int main()
{
    int cnt = 0 ;
    string* cities = new string[MAX_CITIES];

    //getting city from standard input
    //and sorting it
    do
    {
        string city;
        getline(cin, city);
        if(city == "") break;
        int i;
        for(i = cnt - 1; i>=0;i--)
        {
            if(cities[i] > city)
                cities[i+1] = cities[i];
            else
                break;
        }

        cities[i + 1] = city;

        cnt++;
    } while(cnt <= MAX_CITIES);

    string* cities2 = new string[cnt];//we want to have only cnt strings, not MAX_CITIES
    for(int i = 0 ;i < cnt;i++)
        cities2[i] = cities[i];
    delete[] cities;

    //printing the array
    cout << "Sorted cities" << endl;
    for(int i = 0; i < cnt;i++)
        cout << cities2[i] << endl;

    delete[] cities2;

    return 0;
}
