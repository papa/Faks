#include <iostream>
#include <string>

//Task
//Program to sort dynamic array

using namespace std;

int main()
{
    while(true)
    {
        int num_of_elements;
        cout << "Number of elements : ";
        cin >> num_of_elements;

        if(num_of_elements <= 0) break; //invalid number of elements

        int* array = new int[num_of_elements];//alocation of memory
        cout << "Array elements : ";
        for(int i = 0 ; i < num_of_elements; i++)
            cin >> array[i];

        //sorting the array, O(n^2) sorting
        for(int i = 0 ; i < num_of_elements-1; i++)
        {
            int& ref = array[i];
            for(int j = i + 1; j < num_of_elements;j++)
            {
                if(array[j] < ref) // array[j] < array[i]
                {
                    int tmp = ref;
                    ref = array[j];
                    array[j] = tmp;
                }
            }
        }

        cout << "Sorted array : ";
        for(int i = 0 ;i < num_of_elements;i++)
            cout << array[i] << " ";
        cout << endl;

        delete[] array; //deleting array from memory, freeing the memory



    }

    return 0;
}
