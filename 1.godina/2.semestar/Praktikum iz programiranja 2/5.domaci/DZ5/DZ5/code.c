#include <stdlib.h>
#include "functions.h"

node* new_node()
{
	node* nnode;
	SAFE_MALLOC(nnode, 1, node)
		nnode->next = NULL;
	nnode->prev = NULL;
}

double to_radians(double x)
{
	return x * PI / 180;
}

double haversine(double lat1, double lon1, double lat2, double lon2)
{
	lat1 = to_radians(lat1); lon1 = to_radians(lon1);
	lat2 = to_radians(lat2); lon2 = to_radians(lon2);
	double latdiff = lat1 - lat2;
	double londiff = lon1 - lon2;
	double t1 = sin(latdiff / 2) * sin(latdiff / 2);
	double t2 = sin(londiff / 2) * sin(londiff / 2);
	double d = 2 * R * asin(sqrt(t1 + t2 * cos(lat1) * cos(lat2)));
	return d;
}

double distance(point p1, point p2)
{
	return haversine(p1.lat, p1.lon, p2.lat, p2.lon);
}

void add_to_end(node** head, bus_stop* s)
{
	node* nnode = new_node();
	nnode->bs = s;
	if ((*head) == NULL)
	{
		(*head) = nnode;
	}
	else
	{
		node* cur = (*head);
		while (cur->next)
			cur = cur->next;
		cur->next = nnode;
		nnode->prev = cur;
		nnode->next = NULL;
	}
}

node* find_path(node* head, point start, point end)
{
	node* cur = head;
	node* min_start = NULL;
	node* min_end = NULL;
	double min_dist_start = -1;
	double min_dist_end = -1;
	int ok = 0;
	while (cur)
	{
		bus_stop* bsp = cur->bs;
		double dist_start = distance(start, (*bsp).p);
		double dist_end = distance(end, (*bsp).p);

		if (dist_start < min_dist_start || min_dist_start == -1)
		{
			min_dist_start = dist_start;
			min_start = cur;
			ok = 0;
		}

		if (dist_end < min_dist_end || min_dist_end == -1)
		{
			min_dist_end = dist_end;
			min_end = cur;
			ok = 1;
		}
		cur = cur->next;
	}
	node* new_head = NULL;
	if (ok)
	{
		cur = min_start;
		while (cur)
		{
			add_to_end(&new_head, cur->bs);
			if (cur == min_end) break;
			cur = cur->next;
		}
	}
	return new_head;
}

node* form_list(node* headA, node* headB, point start, point end, char* ch)
{
	node* new_head = NULL;
	new_head = find_path(headA, start, end);
	if (new_head)
	{
		*ch = 'A';
		return new_head;
	}
	*ch = 'B';
	new_head = find_path(headB, start, end);
	return new_head;
}

void print_file(node* head, char smer, char* line, FILE* out)
{
	fprintf(out, "%s!%c\n", line, smer);
	node* cur = head;
	while (cur)
	{
		fprintf(out, "%d!%s!%lf!%lf!%d\n", cur->bs->id, cur->bs->name, cur->bs->p.lat, cur->bs->p.lon, cur->bs->zone);
		cur = cur->next;
	}
}

bus_stop* read_one_bus_stop(FILE* file)
{
	char* format = "%d!%[^!]!%lf!%lf!%d\n";
	bus_stop* bs;
	SAFE_MALLOC(bs, 1, bus_stop)
		if (fscanf(file, format, &(bs->id), bs->name, &(bs->p.lat), &(bs->p.lon), &(bs->zone)) == 5)
		{
			//printf("%d %s %lf %lf\n", bs->id, bs->name, bs->p.lat, bs->p.lon);
			return bs;
		}
	free(bs);
	return NULL;
}

node* read_bus_stops(FILE* file)
{
	node* head = NULL;
	bus_stop* bs_pointer;
	while (bs_pointer = read_one_bus_stop(file))
		add_to_end(&head, bs_pointer);
	return head;
}

void free_list(node* head)
{
	node* cur = head;
	while (cur)
	{
		node* old = cur;
		cur = cur->next;
		free(old->bs);
		free(old);
	}
}

void free_memory(node* h1, node* h2)
{
	free_list(h1);
	free_list(h2);
}