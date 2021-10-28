#include <iostream>

using namespace std;

//Task
//Creating, printing, deleting single linked list

struct Elem
{
    int number;
    Elem* nxt;
};

//reading the list
Elem* read()
{
    Elem* cur = nullptr, *first = nullptr;
    while(true)
    {
        int num;
        cin >> num;
        if(num == 9999) break; //custom invalid case
        Elem* new_node = new Elem;
        new_node->number = num;
        new_node->nxt = nullptr;

        cur = (first == nullptr ? first : cur->nxt) = new_node;
    }
    return first;
}

//printing the list
void print_list(Elem* first)
{
    Elem* cur = first;
    while(cur)
    {
        cout << cur->number << " ";
        cur = cur->nxt;
    }
    cout << endl;
}

//deleting the list
void delete_list(Elem* first)
{
    while(first)
    {
        Elem* old = first;
        first = first->nxt;
        delete old;
    }
}

//demonstrating passing of parameters
void function(int i, int& j)
{
    i++; // value of i doens't change outside of this function
    j++; // value of j changes outside of the function
}

int main()
{


    return 0;
}
