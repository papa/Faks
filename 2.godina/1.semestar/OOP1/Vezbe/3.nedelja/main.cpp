#include <iostream>
#include "angle.h"
#include "queue.h"

using namespace std;

void testing_queue_class()
{
	//testing of class Queue
	Queue* q = new Queue(5);
	bool end = false;

	while (!end)
	{
		cout << "\n1.Create queue\n"
			"2.Insert element\n"
			"3.Get front element"
			"4.Print queue\n"
			"5.Empty the queue\n"
			"0.End the program\n\n"
			"Your choice :";
		int choice; cin >> choice;

		switch (choice)
		{
		case 1:
			cout << "capacity : ";
			int cap; cin >> cap;
			if (cap > 0)
			{
				delete(q);
				q = new Queue(cap);
			}
			else
			{
				cout << "Wrong cap value!\n";
			}
			break;
		case 2:
			if (!q->is_full())
			{
				cout << "Element:";
				int x; cin >> x;
				q->insert(x);
			}
			else
			{
				cout << "Queue is full\n";
			}
			break;
		case 3:
			if (!q->is_empty())
			{
				int x = q->get();
				cout << "Front = " << x << endl;
			}
			else
			{
				cout << "Queue is empty\n";
			}
			break;
		case 4:
			cout << "Queue :";
			q->print_queue();
			break;
		case 5:
			q->empty_the_queue();
			break;
		case 0:
			end = true;
			break;
		default:
			cout << "Choice not correct\n";
			break;
		}
	}
}

void testing_angle_class()
{
	//testing of class Angle
	Angle angle1, angle2; //constructor with value zero is called
	Angle angle3(10);
	Angle angle4(30, 20, 10);// can also be called with {30,20,10}

	cout << "First angle [rad] :"; angle1.read_angle();
	cout << "Second angle [rad] :"; angle2.read_angle();

	angle1.add(angle2).multiple(2);
	angle1.print();

	//copy constructor is called here for angle4
	//Angle(angle1)
	Angle angle5 = Angle(angle1).add(angle2).multiple(0.5);

	cout << "Mid value "; angle5.print();
}

int main()
{
	return 0;
}