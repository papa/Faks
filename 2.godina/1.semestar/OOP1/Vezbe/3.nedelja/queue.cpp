#include "queue.h"
#include <cstdlib>
#include <iostream>

Queue::Queue(int cap)
{
	this->arr = new int[cap];
	this->cap = cap;
	this->len = this->first = this->last = 0;
}

//has to make deep copy, not shell copy
Queue::Queue(const Queue& q)
{
	/*
	//not good, shell copy
	this->arr = q.arr;
	this->len = q.len;
	this->first = q.first;
	this->last = q.last;
	this->cap = q.cap;
	*/

	//deep copy
	this->arr = new int[q.cap];
	for (int i = 0; i < q.cap;i++)
		this->arr[i] = q.arr[i];
	this->len = q.len;
	this->first = q.first;
	this->last = q.last;
	this->cap = q.cap;
}

//has to free memory
//not written rule:
//if constructor has new,malloc...destructor should free that memory
Queue::~Queue()
{
	delete[] this->arr;
}

void Queue::insert(int x)
{
	if (len == cap) exit(1);

	arr[last++] = x;
	if (last == cap) last = 0;
	len++;
}

int Queue::get()
{
	if(len == 0) exit(1);

	int b = arr[first++];
	if (first == cap) first = 0;
	len--;
	return b;
}

void Queue::print_queue() const
{
	for (int i = 0; i < len;i++)
	{
		cout << arr[(first + i)%cap] << " ";
	}
	cout << endl;
}

//q is soon going to be deleted 
Queue::Queue(Queue&& q)
{
	this->arr = q.arr;
	this->len = q.len;
	this->first = q.first;
	this->last = q.last;
	this->cap = q.cap;

	q.arr = nullptr;
}
