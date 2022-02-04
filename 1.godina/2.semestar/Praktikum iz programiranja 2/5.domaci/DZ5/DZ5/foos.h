#pragma once
#include "main.c"
#include "code.c"

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