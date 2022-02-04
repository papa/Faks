#include <stdio.h>
#include <stdlib.h>

#define SAFE_MALLOC(p,n,tip)\
p = malloc(n*sizeof(tip));\
if(!p)\
{\
	printf("Malloc neuspesan.\n");\
	exit(-1);\
}\

#define FREE_MEMORY(p,n)\
for (int i = 0;i < n;i++)\
	free(p[i]);\
free(p);\
p = NULL;

#define INF 1000

typedef struct graph_node
{
	char character;
}node;

typedef struct graph
{
	int** adj_matrix;
	node** nodes;
	int num_of_nodes;
}graph;

graph* new_graph(int n)
{
	graph* g;
	SAFE_MALLOC(g, 1, graph);
	g->num_of_nodes = n;
	SAFE_MALLOC(g->nodes, g->num_of_nodes, node*)
	for (int i = 0; i < g->num_of_nodes;i++)
	{
		SAFE_MALLOC(g->nodes[i], 1, node);
	}
	SAFE_MALLOC(g->adj_matrix, g->num_of_nodes, int*)
	for (int i = 0; i < g->num_of_nodes;i++)
	{
		SAFE_MALLOC(g->adj_matrix[i], g->num_of_nodes, int)
		for (int j = 0; j < g->num_of_nodes;j++)
		{
			g->adj_matrix[i][j] = 0;
		}
	}
	return g;
}

void free_graph(graph** g)
{
	if (*g == NULL) return;
	FREE_MEMORY((*g)->nodes, (*g)->num_of_nodes)
	FREE_MEMORY((*g)->adj_matrix, (*g)->num_of_nodes)
	free(*g);
	*g = NULL;
}

typedef struct queue_node // red
{
	node* info;
	struct queue_node* next;
}queue;

queue* new_queue_el() // kreira novi el reda
{
	queue* qn;
	SAFE_MALLOC(qn, 1, queue)
		qn->info = NULL;
	qn->next = NULL;
	return qn;
}

int queue_empty(queue* q) // da li je red prazan
{
	if (q == NULL)
		return 1;
	return 0;
}

void queue_insert(queue** q, queue** rear, node* x) // ubacuje cvor u red
{
	queue* qn = new_queue_el();
	qn->info = x;
	if (*rear == NULL)
		* q = qn;
	else
		(*rear)->next = qn;
	*rear = qn;
}

node* queue_delete(queue** q, queue** rear) // vadi cvor iz reda
{
	if (queue_empty(*q))
		return NULL;
	else
	{
		queue* qn = *q;
		node* x = qn->info;
		*q = qn->next;
		if (*q == NULL)
			* rear = NULL;
		free(qn);
		return x;
	}
}

typedef struct stack_node //stek
{
	node* info; // cvor koji se cuva
	struct stack_node* next; // sledeci
	int mark;
}stack;

int stack_empty(stack* s) // da li je prazan
{
	if (s == NULL)
		return 1;
	return 0;
}

stack* new_stack_el() // kreira novi el steka
{
	stack* sn;
	SAFE_MALLOC(sn, 1, stack)
		sn->info = NULL;
	sn->next = NULL;
	return sn;
}

void push(stack** s, node* n,int mark) // stavlja cvor na stek
{
	stack* sn = new_stack_el();
	sn->mark = mark;
	sn->info = n;
	sn->next = *s;
	*s = sn;
}

node* pop(stack** s,int *mark) // skida cvor sa steka 
{
	if (stack_empty(*s))
		return NULL;
	else
	{
		stack* sn = *s;
		*s = sn->next;
		node* x = sn->info;
		(*mark) = sn->mark;
		free(sn);
		return x;
	}
}

void print_arr(node** arr, int n)
{
	for (int i = 0;i < n;i++)
	{
		printf("%c ", arr[i]->character);
	}
	printf("\n");
}

void print_adj_matrix(int** adj, node** arr, int n)
{
	printf("  ");
	print_arr(arr, n);
	for (int i = 0; i < n;i++)
	{
		printf("%c ", arr[i]->character);
		for (int j = 0; j < n;j++)
		{
			printf("%d ", adj[i][j]);
		}
		printf("\n");
	}
}

int find_character(node** arr, int n, char ch)
{
	for (int i = 0; i < n;i++)
	{
		if (arr[i]->character == ch)
		{
			return i;
		}
	}
	return -1;
}

void delete_at_index(node*** arr, int n, int index)
{
	for (int i = index + 1;i < n;i++)
	{
		(*arr)[i - 1]->character = (*arr)[i]->character;
	}
	node** temp = realloc((*arr), (n - 1) * sizeof(node*));
	if (temp == NULL)
	{
		printf("Neuspesan realloc\n");
		exit(1);
	}
	else
	{
		(*arr) = temp;
	}
}

void delete_matrix_index(int*** matrix, int n, int index)
{
	free((*matrix)[index]);
	for (int i = index + 1;i < n;i++)
		(*matrix)[i - 1] = (*matrix)[i];
	for (int i = 0; i < n - 1;i++)
	{
		for (int j = index + 1;j < n;j++)
			(*matrix)[i][j - 1] = (*matrix)[i][j];
		(*matrix)[i] = realloc((*matrix)[i], (n - 1) * sizeof(int));
	}
	(*matrix) = realloc(*matrix, (n - 1) * sizeof(int*));
}

void add_one_element(node*** arr, int n, char ch)
{
	node** node_arr = (*arr);
	node** temp = realloc(node_arr, n * sizeof(node*));
	if (temp == NULL)
	{
		printf("Neuspesan realloc");
		exit(1);
	}
	else
	{
		node_arr = temp;
		SAFE_MALLOC(node_arr[n-1], 1, node);
		node_arr[n-1]->character = ch;
		(*arr) = node_arr;
	}

}

void add_matrix_element(int*** mat, int n)
{
	int** matrix = (*mat);
	matrix = realloc(matrix, n * sizeof(int*));
	matrix[n - 1] = NULL;
	for (int i = 0; i < n;i++)
	{
		matrix[i] = realloc(matrix[i], n * sizeof(int));
	}
	for (int i = 0;i < n;i++)
	{
		matrix[n-1][i] = 0;
		matrix[i][n-1] = 0;
	}
	(*mat) = matrix;
}

void error_message()
{
	printf("Cvorovi sa unetim oznakama se ne nalaze u grafu\n");
}

void add_edge2(graph* g, int u, int v)
{
	g->adj_matrix[u][v] = 1;
}

void remove_edge2(graph* g, int u, int v)
{
	g->adj_matrix[u][v] = 0;
}

void add_edge(graph* g, char x, char y)
{
	int u = find_character(g->nodes, g->num_of_nodes, x);
	int v = find_character(g->nodes, g->num_of_nodes, y);
	if (u == -1 || v == -1)
		error_message();
	else
		add_edge2(g, u, v);
}

void remove_edge(graph* g, char x, char y)
{
	int u = find_character(g->nodes, g->num_of_nodes, x);
	int v = find_character(g->nodes, g->num_of_nodes, y);
	if (u == -1 || v == -1)
		error_message();
	else
		remove_edge2(g, u, v);
}


int bfs_maja(graph* g, int start_index, int end_index,int **prev_path)
{
	queue* q = NULL;
	queue* rear = NULL;
	queue_insert(&q, &rear, g->nodes[start_index]);
	int* dist = NULL;
	int* prev = NULL;
	int n = g->num_of_nodes;
	SAFE_MALLOC(dist, n, int)
	SAFE_MALLOC(prev,n,int)
	for (int i = 0; i < n;i++)
	{
		prev[i] = -1;
		dist[i] = -1;
	}
	dist[start_index] = 0;
	while (!queue_empty(q))
	{
		node* cur = queue_delete(&q, &rear);
		int cur_index = find_character(g->nodes, n, cur->character);
		for (int i = 0; i < n;i++)
		{
			if (g->adj_matrix[cur_index][i] && dist[i] == -1)
			{
				prev[i] = cur_index;
				dist[i] = dist[cur_index] + 1;
				queue_insert(&q, &rear, g->nodes[i]);
			}
		}
	}
	(*prev_path) = prev;
	return dist[end_index];
}

int bfs_sanja(graph* g, int start_index, int end_index,int**d1,int**d2,int**p1,int**p2,int**px)
{
	queue* q = NULL;
	queue* rear = NULL;
	int* dist1 = NULL,*dist2 = NULL,*mark1 = NULL,*mark2 = NULL,*prev1=NULL,*prev2 = NULL,*prevx=NULL;
	int n = g->num_of_nodes;
	SAFE_MALLOC(dist1, n, int)		
	SAFE_MALLOC(dist2, n,  int)
	SAFE_MALLOC(mark1, n, int)
	SAFE_MALLOC(mark2, n, int)
	SAFE_MALLOC(prev1,n,int)
	SAFE_MALLOC(prev2,n,int)
	SAFE_MALLOC(prevx,n,int)
	for (int i = 0; i < n;i++)
	{
		prev1[i] = prev2[i] = -1;
		prevx[i] = -1;
		mark1[i] = mark2[i] = 0;
		dist1[i] = INF;
		dist2[i] = INF;
	}
	dist2[start_index] = 0;
	mark2[start_index] = 1;
	queue_insert(&q, &rear, g->nodes[start_index]);
	while (!queue_empty(q))
	{
		node* cur = queue_delete(&q, &rear);
		int cur_index = find_character(g->nodes, n, cur->character);
		if (mark2[cur_index] == 1)
		{
			for (int i = 0; i < n;i++)
			{
				if (g->adj_matrix[cur_index][i] && mark1[i] == 0)
				{
					mark1[i] = 1;
					prev1[i] = cur_index;
					dist1[i] = dist2[cur_index] + 1;
					queue_insert(&q, &rear, g->nodes[i]);
				}
			}
		}
		if(mark1[cur_index] == 1)
		{
			for (int i = 0; i < n;i++)
			{
				if (g->adj_matrix[cur_index][i])
				{
					for (int j = 0; j < n;j++)
					{
						if (g->adj_matrix[i][j] && mark2[j] == 0)
						{
							mark2[j] = 1;
							prevx[j] = i;
							prev2[j] = cur_index;
							dist2[j] = dist1[cur_index] + 2;
							queue_insert(&q, &rear, g->nodes[j]);
						}
					}
				}
			}
		}
	}
	(*p1) = prev1; (*p2) = prev2; (*d1) = dist1; (*d2) = dist2; ; (*px) = prevx;
	return min(dist1[end_index],dist2[end_index]);
}

void print_paths(graph* g, int start_index, int end_index, int length)
{
	stack* st = NULL;
	push(&st, g->nodes[start_index],0);
	int path[1005];
	int index = 0;
	int n = g->num_of_nodes;
	while (!stack_empty(st))
	{
		int mark = 0;
		node* cur = pop(&st,&mark);
		int cur_index = find_character(g->nodes, n, cur->character);
		if (mark == 0)
		{
			path[index] = cur_index;
			index++;
			push(&st, g->nodes[cur_index], 1);
			if (index < length+1)
			{
				for (int i = 0; i < n;i++)
				{
					if (g->adj_matrix[cur_index][i])
					{
						push(&st, g->nodes[i], 0);
					}
				}
			}
		}
		else
		{
			index--;
		}
		if (index == length+1 && path[index-1] == end_index)
		{
			for (int i = 0; i < index;i++)
			{
				printf("%c", g->nodes[path[i]]->character);
				if (i != index - 1) printf("->");
			}
			printf("\n");
		}
	}
}

void print_one_sanja_path(graph* g, int start_index, int end_index, int* d1, int* d2, int* p1, int* p2,int *px)
{
	int move = 1;
	if (d2[end_index] < d1[end_index]) move = -1;
	int cur = end_index;
	stack* st = NULL;
	while (1)
	{
		push(&st, g->nodes[cur], 0);
		if (move == 1)
		{
			if (p1[cur] == -1) break;
			cur = p1[cur];
			move = -1;
		}
		else
		{
			if (p2[cur] == -1) break;
			push(&st, g->nodes[px[cur]], 0);
			cur = p2[cur];
			move = 1;
		}
	}
	while (!stack_empty(st))
	{
		node* temp = pop(&st, &cur);
		printf("%c", temp->character);
		if (!stack_empty(st)) printf("->");
	}
	printf("\n");
}

void print_one_maja_path(graph *g, int start_index, int end_index, int* prev)
{
	int cur = end_index;
	stack* st = NULL;
	while (1)
	{
		push(&st, g->nodes[cur], 0);
		if (prev[cur] == -1) break;
		cur = prev[cur];
	}
	while (!stack_empty(st))
	{
		node* temp = pop(&st, &cur);
		printf("%c", temp->character);
		if (!stack_empty(st)) printf("->");
	}
	printf("\n");
}

void print_menu()
{
	printf("1 - Kreirajte praznu strukturu grafa\n");
	printf("2 - Dodajte cvor u graf\n");
	printf("3 - Uklonite cvor iz grafa\n");
	printf("4 - Dodajte granu u graf\n");
	printf("5 - Uklonite granu iz grafa\n");
	printf("6 - Ispis reprezentacije grafa\n");
	printf("7 - Brisanje grafa iz memorije\n");
	printf("8 - odigrajte igru\n");
	printf("0 - Prekinite izvrsavanje programa\n");
}

int main()
{
	int choice = 1;
	graph* g = NULL;
	while (choice)
	{
		print_menu();
		scanf("%d", &choice);
		if (choice != 1 && g == NULL)
		{
			printf("Morate prvo kreirarti graf\n");
			continue;
		}
		if (choice == 1 && g != NULL)
		{
			printf("Graf je vec kreiran\n");
			continue;
		}
		if (choice == 1)
		{
			printf("Izabrali ste kreiranje grafa\n");
			printf("Unesite broj cvorova grafa : ");
			int n;
			scanf("%d", &n);
			g = new_graph(n);
			printf("Unesite %d cvorova datog grafa (slova koja ih obelezavaju): ", g->num_of_nodes);
			for (int i = 0;i < g->num_of_nodes;i++)
			{
				char character;
				scanf(" %c", &character);
				g->nodes[i]->character = character;
			}
			printf("Graf je uspesno kreiran\n");
		}
		else if (choice == 2)
		{
			printf("Izabrali ste dodavanje cvora u graf\n");
			printf("Unesti karakter koji obelezava cvor koji se dodaje: ");
			char ch;
			scanf(" %c", &ch);
			int index = find_character(g->nodes, g->num_of_nodes, ch);
			//printf("index %d char %c\n", index, g->nodes[index]);
			if (index != -1)
			{
				printf("Data oznaka se vec nalazi u grafu\n");
				continue;
			}
			g->num_of_nodes++;
			add_one_element(&(g->nodes), g->num_of_nodes, ch);
			add_matrix_element(&(g->adj_matrix), g->num_of_nodes);
			printf("Cvor sa datom oznakom je uspesno dodat\n");
		}
		else if (choice == 3)
		{
			printf("Izabrali ste brisanje cvora iz grafa\n");
			printf("Unesite karakter koji obelezava cvor koji zelite da obrisete: ");
			char ch;
			scanf(" %c", &ch);
			int index = find_character(g->nodes, g->num_of_nodes, ch);
			if (index == -1)
				error_message();
			else
			{
				if (g->num_of_nodes == 1)
					free_graph(&g);
				else
				{
					delete_at_index(&(g->nodes), g->num_of_nodes, index);
					delete_matrix_index(&(g->adj_matrix), g->num_of_nodes, index);
					g->num_of_nodes--;
				}
				printf("Obrisan je cvor sa datom oznakom\n");
			}
		}
		else if (choice == 4)
		{
			printf("Izabrali ste dodavanje grane u graf\n");
			char ch1, ch2;
			printf("Unesite oznake cvorova izmedju kojih zelite da dodate usmerenu granu: ");
			scanf(" %c", &ch1);
			scanf(" %c", &ch2);
			add_edge(g, ch1, ch2);
			printf("Grana je uspesno dodata\n");
		}
		else if (choice == 5)
		{
			printf("Izabrali ste brisanje grane iz grafa\n");
			printf("Unesite oznake cvorova izmedju kojih zelite da izbrisete usmerenu granu: ");
			char ch1, ch2;
			scanf(" %c", &ch1);
			scanf(" %c", &ch2);
			remove_edge(g, ch1, ch2);
			printf("Grana je uspesno obrisana\n");
		}
		else if (choice == 6)
		{
			printf("Izabrali ste ispis reprezentacije grafa\n");
			print_adj_matrix(g->adj_matrix, g->nodes, g->num_of_nodes);
		}
		else if (choice == 7)
		{
			printf("Izabrali ste brisanje grafa iz memorije\n");
			free_graph(&g);
		}
		else if (choice == 8)
		{
			char start;
			char end;
			printf("Unesti oznaku cvora koji predstavlja start i koji predstavlja cilj: ");
			scanf(" %c", &start);
			scanf(" %c", &end);
			int start_index = find_character(g->nodes, g->num_of_nodes, start);
			int end_index = find_character(g->nodes, g->num_of_nodes, end);
			if (start_index == -1 || end_index == -1)
				error_message();
			else
			{
				int* prev_maja; SAFE_MALLOC(prev_maja, g->num_of_nodes, int)
				int* dist1, * dist2, * prev1, * prev2,*prevx;
				SAFE_MALLOC(dist1, g->num_of_nodes, int*)
				SAFE_MALLOC(dist2, g->num_of_nodes, int*)
				SAFE_MALLOC(prev1, g->num_of_nodes, int*)
				SAFE_MALLOC(prev2, g->num_of_nodes, int*)
				SAFE_MALLOC(prevx,g->num_of_nodes,int*)
				int dist_end_maja = bfs_maja(g, start_index, end_index,&prev_maja);
				int dist_end_sanja = bfs_sanja(g, start_index, end_index,&dist1,&dist2,&prev1,&prev2,&prevx);
				int maja_moves = dist_end_maja;
				int sanja_moves = INF;
				if (dist_end_maja == -1)
				{
					printf("Nemoguce je stici od startnog do ciljnog cvora.\n");
				}
				else
				{
					printf("Maja broj poteza : %d\n", dist_end_maja);
					if (dist_end_sanja >= INF)
					{
						printf("Sanja ne moze da stigne iz startnog u ciljni cvor\n");
					}
					else
					{
						sanja_moves = 2 * (dist_end_sanja / 3) + dist_end_sanja % 3;
						printf("Sanja broj poteza : %d\n", sanja_moves);
					}

					printf("Jedan Majin put\n");
					print_one_maja_path(g, start_index, end_index, prev_maja);
					if (sanja_moves < INF)
					{
						printf("Jedan Sanjin put\n");
						print_one_sanja_path(g, start_index, end_index, dist1, dist2, prev1, prev2,prevx);
					}
					printf("Majini putevi\n");
					print_paths(g, start_index, end_index, dist_end_maja);
					if (sanja_moves < INF)
					{
						printf("Sanjini putevi\n");
						print_paths(g, start_index, end_index, dist_end_sanja);
					}
					if (maja_moves < sanja_moves)
						printf("Maja pobeduje\n");
					else if (sanja_moves < maja_moves)
						printf("Sanja pobedjuje\n");
					else
						printf("Nereseno\n");
				}
			}
		}
		else
		{
			printf("Izabrali ste zavrsavanje programa.\n");
		}
	}
	free_graph(&g);
	printf("Program se uspesno zavrsio.");

	return 0;
}
