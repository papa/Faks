#ifndef _queue_h_
#define _queue_h_

#include <iostream>

using namespace std;

class Queue
{
	int* arr, cap, len, first, last;

public:

	explicit Queue(int cap); // explicit means constructor should be called, not with conversions
	Queue(const Queue& q); // copy constructor
	Queue(Queue&& q); // class which uses dynamic memory, got to have moving constructor
	//moving constructor makes shell copy

	~Queue(); // destructor, no parameters

	void insert(int x);
	int get();

	bool is_full() const { return this->len == this->cap; };
	bool is_empty() const { return this->len == 0; }

	void print_queue() const;

	void empty_the_queue() { this->len = this->first = this->last = 0; }
};

#endif // !_queue_h_

