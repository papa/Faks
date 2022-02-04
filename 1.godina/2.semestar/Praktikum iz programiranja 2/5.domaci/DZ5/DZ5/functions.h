#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <math.h>

#define PI 3.14
#define R 6371

#define SAFE_MALLOC(p,n,tip)\
p = (tip*)malloc(n*sizeof(tip));\
if(!p)\
{\
	printf("MEM_GRESKA");\
	exit(0);\
}

#define FOPEN(file,name,mode)\
{\
	file = fopen(name, mode);\
	if (!file)\
	{\
		printf("DAT_GRESKA");\
		exit(0);\
	}\
}

typedef struct Point
{
	double lat;
	double lon;
}point;

typedef struct bus_stop
{
	int id;
	char name[256];
	point p;
	int zone;
}bus_stop;

typedef struct list_node
{
	struct list_node* next;
	struct list_node* prev;
	bus_stop* bs;
}node;

node* new_node();

double to_radians(double x);

double haversine(double lat1, double lon1, double lat2, double lon2);

double distance(point p1, point p2);

void add_to_end(node** head, bus_stop* s);

node* find_path(node* head, point start, point end);

node* form_list(node* headA, node* headB, point start, point end, char* ch);

void print_file(node* head, char smer, char* line, FILE* out);

bus_stop* read_one_bus_stop(FILE*);

node* read_bus_stops(FILE*);

void free_list(node*);

void free_memory(node*, node*);