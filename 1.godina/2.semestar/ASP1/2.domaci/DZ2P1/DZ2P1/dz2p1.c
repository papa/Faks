#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define SAFE_MALLOC(p,n,tip)\
p = malloc(n*sizeof(tip));\
if(!p)\
{\
	printf("Malloc neuspesan.\n");\
	exit(-1);\
}

typedef struct tree_node // cvor stabla
{
	int **magic_square; // magicni kvadrat
	struct tree_node **successors; // sinovi
	int* cnt; // niz zauzetosti
	int num_of_successors; // broj sinova
	int mark; // mark za postorder
}node;

node* new_node()// kreira novi cvor sa default vrednostima
{
	node* nnode;
	SAFE_MALLOC(nnode, 1, node)
		nnode->magic_square = NULL;
	nnode->num_of_successors = 0;
	nnode->successors = NULL;
	nnode->mark = 0;
	nnode->cnt = NULL;
	return nnode;
}

node* create_node(int** mat, int n, int num_suc) //kreira cvor sa zadatim parametrima 
{
	node* nnode = new_node();
	nnode->magic_square = mat;
	nnode->num_of_successors = num_suc;
	SAFE_MALLOC(nnode->cnt, n * n, int)
		for (int i = 0;i < n * n;i++)
			nnode->cnt[i] = 0;
	SAFE_MALLOC(nnode->successors, nnode->num_of_successors, node*)
	for (int i = 0;i < num_suc;i++)
		nnode->successors[i] = NULL;
	return nnode;
}

typedef struct decision_tree // stablo odlucivanja
{
	node* root; // koren
	int n; // n
	int* set; // set brojeva
	int magic_sum; // karakteristicna suma mag kvadrata
}tree;

tree* new_tree()// kreira novo stablo
{
	tree* t;
	SAFE_MALLOC(t, 1, tree)
	t->root = NULL;
	t->n = 0;
	t->magic_sum = 0;
	return t;
}

typedef struct stack_node //stek
{
	node* info; // cvor koji se cuva
	struct stack_node* next; // sledeci
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

void push(stack** s, node* n) // stavlja cvor na stek
{
	stack* sn = new_stack_el();
	sn->info = n;
	sn->next = *s;
	*s = sn;
}

node* pop(stack** s) // skida cvor sa steka 
{
	if (stack_empty(*s))
		return NULL;
	else
	{
		stack* sn = *s;
		*s = sn->next;
		node* x = sn->info;
		free(sn);
		return x;
	}
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
		*q = qn;
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
			*rear = NULL;
		free(qn);
		return x;
	}
}

void free_node(node* x, tree* t) // oslobadja memoriju koju zauzima cvor
{
	free(x->cnt);
	if(x->successors != NULL )
		free(x->successors);

	for (int i = 0;i < t->n;i++) 
		free(x->magic_square[i]);
	free(x->magic_square);

	free(x);
}

int good_input(int lo, int hi) // obezbedjuje unos broj izmedju lo i hi
{
	int x;
	scanf("%d", &x);
	while (x < lo || x > hi)
	{
		printf("Uneti broj nije u granicama(%d-%d).Unesite ponovo: ", lo, hi);
		scanf("%d", &x);
	}
	return x;
}

int** new_magic_square(int n) // kreira novi magicni kvadrat
{
	int** magic_square;
	SAFE_MALLOC(magic_square, n, int*)
		for (int i = 0;i < n;i++)
		{
			SAFE_MALLOC(magic_square[i], n, int)
		}
	return magic_square;
}

int** copy_elements(int** mat1, int n)
{
	int** mat2 = new_magic_square(n);
	for (int i = 0;i < n;i++)
		for (int j = 0;j < n;j++)
			mat2[i][j] = mat1[i][j];
	return mat2;
}


int free_spots(int** mat,int n) // broj slobodnih mesta u matrici
{
	int cnt = 0;
	for (int i = 0; i < n;i++)
		for (int j = 0;j < n;j++)
			if (mat[i][j] == 0)
				cnt++;	
	return cnt;
}

void print_matrix(int** mat, int n) // stampa matricu
{
	for (int i = 0; i < n;i++)
	{
		for (int j = 0; j < n;j++)
		{
			printf("%d ", mat[i][j]);
		}
		printf("\n");
	}
}

int find_element(int* set, int* cnt, int n, int el) // trazi slobodni element u setu
{
	for (int i = 0; i < n;i++)
	{
		if (set[i] == el && cnt[i] == 0)
			return i;
	}
	return -1;
}

void print_array(int* a, int n) //stampa niz
{
	for (int i = 0;i < n;i++)
		printf("%d ", a[i]);
	printf("\n");
}

void print_node_info(node* nd, int n) // stampa podatke o cvoru
{
	printf("\n");
	print_matrix(nd->magic_square, n);
	printf("\n");
}

tree* create_tree(int** mat, int n,int *set,int magic_sum) // kreira stablo sa korenom
{
	tree* t = new_tree();
	t->n = n;
	t->magic_sum = magic_sum;
	node* root = create_node(mat, n, free_spots(mat,n));
	t->set = set;
	t->root = root;
	for (int i = 0; i < n*n;i++) 
		root->cnt[i] = 0;
	for (int i = 0; i < n;i++)
	{
		for (int j = 0;j < n;j++)
		{
			if (mat[i][j] != 0)
			{
				int ind = find_element(set, t->root->cnt, n*n, mat[i][j]);
				if (ind == -1)
					continue;
				t->root->cnt[ind] = 1;
			}
		}
	}

	return t;
}

int array_sum(int* arr, int n) // suma niza
{
	int s = 0;
	for (int i = 0; i < n;i++)
		s += arr[i];
	return s;
}

void input_array(int* arr, int n) // ucitava niz
{
	for (int i = 0;i < n;i++)
	{
		scanf("%d", &arr[i]);
	}
}

void input_matrix(int** mat, int n) // ucitava matricu
{
	for (int i = 0; i < n;i++)
	{
		for (int j = 0;j < n;j++)
		{
			scanf("%d", &mat[i][j]);
		}
	}
}


int get_magic_sum(int* set, int n) // za dati set brojeva racuna karakteristicnu sumu 
{
	int sum = array_sum(set, n*n);
	int s1 = sum / n;
	double s2 = (double)sum / n;
	if (s2 == (double)s1) 
		return s1;
	else 
		return -1;
}

int parallel_to_main(int** mat, int n, int si, int sj) // suma na dijagonali paralelnoj glavnoj sa pocetkom  si sj
{
	int s = 0;
	while (sj < n && si < n)
	{
		s += mat[si][sj];
		si++;
		sj++;
	}
	return s;
}

int parallel_to_other(int** mat, int n, int si, int sj) // suma na dijagonali paralelnoj sporednoj sa pocetkom  si sj
{
	int s = 0;
	while (si >= 0 && sj < n)
	{
		s += mat[si][sj];
		si--;
		sj++;
	}
	return s;
}

int is_regular_solution(int** mat, int n, int magic_sum) // proverava da li je magicni kvadrat normalno resenje
{
	for (int i = 0;i < n;i++)
	{
		int s = 0;
		for (int j = 0; j < n;j++)
		{
			s += mat[i][j];
		}

		if (s != magic_sum)
			return 0;
	}

	for (int j = 0;j < n;j++)
	{
		int s = 0;
		for (int i = 0; i < n;i++)
		{
			s += mat[i][j];
		}

		if (s != magic_sum)
			return 0;
	}

	int s = parallel_to_main(mat, n, 0, 0);
	if (s != magic_sum)
		return 0;

	s = parallel_to_other(mat, n, n - 1, 0);
	if (s != magic_sum)
		return 0;

	return 1;
}

int is_perfect_solution(int** mat, int n, int magic_sum) // proverava da li je resenje savrseno
{
	if (n == 1) 
		return 1;

	int row1 = 1;
	int col1 = 0;
	int row2 = 0;
	int col2 = n - 1;

	//paralelno glavnoj
	while (row1 < n)
	{
		int s1 = parallel_to_main(mat, n, row1, col1);
		int s2 = parallel_to_main(mat, n, row2, col2);
		if (s1 + s2 != magic_sum)
			return 0;
		row1++;
		col2--;
	}

	row1 = n - 2;
	col1 = 0;
	row2 = n - 1;
	col2 = n - 1;
	//paralelno sporednoj
	while (row1 >= 0)
	{
		int s1 = parallel_to_other(mat, n, row1, col1);
		int s2 = parallel_to_other(mat, n, row2, col2);
		if (s1 + s2 != magic_sum)
			return 0;
		row1--;
		col2--;
	}

	return 1;
}

int valid_state(int** mat, int n, int* set, int sum) // da li je stanje validno 
{
	int* cnt;
	SAFE_MALLOC(cnt, n * n, int)
	for (int i = 0;i < n * n;i++)
		cnt[i] = 0;

	//proveravamo da li su svi elementi iz matrice u setu
	//kao i da li je suma u nekom redu neodgovarajuca
	for (int i = 0; i < n;i++)
	{
		int row_sum = 0;
		int row_cnt = 0;
		for (int j = 0;j < n;j++)
		{
			if (mat[i][j] < 0)
				return 0;

			if (mat[i][j] == 0) 
				continue;

			int ind = find_element(set, cnt, n*n, mat[i][j]);
			
			if (ind == -1) 
				return 0;
			
			cnt[ind] = 1;

			row_cnt++;
			row_sum += mat[i][j];
		}
		
		if (row_sum > sum || (row_cnt == n && row_sum < sum) || (row_cnt < n && row_sum == sum))
			return 0;
		
	}

	//sada proveravamo sume po kolonama
	for (int j = 0; j < n;j++)
	{
		int col_sum = 0;
		int col_cnt = 0;
		for (int i = 0;i < n;i++)
		{
			if (mat[i][j] == 0) continue;

			col_cnt++;
			col_sum += mat[i][j];
		}
		
		if (col_sum > sum || (col_cnt == n && col_sum < sum) || (col_cnt < n && col_sum == sum))
			return 0;
	}

	int cnt_dig = 0;
	int sum_dig = 0;
	for(int i =0 ;i < n;i++)
	{
		if (mat[i][i] == 0) continue;
		cnt_dig++;
		sum_dig += mat[i][i];
	}

	if (sum_dig > sum || (cnt_dig == n && sum_dig < sum) || (cnt_dig < n && sum_dig == sum))
		return 0;

	cnt_dig = 0;
	sum_dig = 0;
	for (int i = n - 1;i >= 0;i--)
	{
		if (mat[i][n - 1 - i] == 0) continue;
		cnt_dig++;
		sum_dig += mat[i][n - 1 - i];
	}

	if (sum_dig > sum || (cnt_dig == n && sum_dig < sum) || (cnt_dig < n && sum_dig == sum))
		return 0;

	return 1; // mozda ce moci da se izgradi magicni kvadrat ali ne garantujemo
}


node* create_next_state(tree* t, node* cur, int ind, int row, int col) //pokusava da napravi novo stanje
{
	int n = t->n;
	int** new_mat = copy_elements(cur->magic_square, n);
	new_mat[row][col] = t->set[ind];

	if (!valid_state(new_mat, n, t->set, t->magic_sum))
		return NULL;

	//kreiranje novog cvora
	node* nnode = create_node(new_mat, t->n, cur->num_of_successors - 1);
	memcpy(nnode->cnt, cur->cnt, n * n * sizeof(int));
	nnode->cnt[ind] = 1;
	return nnode;
}

void is_solution(node* x,tree* t) // da li je cvor x resenje
{
	if (is_regular_solution(x->magic_square, t->n, t->magic_sum))
	{
		printf("Nadjeno resenje!\n");
		if (is_perfect_solution(x->magic_square, t->n, t->magic_sum))
			printf("Savrseno!\n");
		print_matrix(x->magic_square, t->n);
	}
}

void postorder_i(tree* t, void (*f)(node*,tree*)) // postorder obilazak stabla koji odradjuje f-ju f( obrada ili oslobadjanje memorije)
{
	node* next = t->root;
	stack* s = NULL;
	push(&s, next);
	while (!stack_empty(s))
	{
		next = pop(&s);
		if (next->mark == 0)
		{
			next->mark = 1; //markiranje
			push(&s, next);
			//ubaci sve njegove sinove s desna na levo 
			//na stek
			for (int i = next->num_of_successors - 1;i >= 0;i--)
			{
				node* suc = next->successors[i];
				if (suc == NULL) continue;
				push(&s, suc);
			}

		}
		else if (next->mark == 1)
		{
			//obrada datog cvora
			f(next, t);
			next->mark = 0; // demarkiranje
		}
	}
}

void first_free_pos(int** mat, int n, int* row, int* col)
{
	for (int i = 0;i < n;i++)
	{
		for (int j = 0;j < n;j++)
		{
			if (mat[i][j] == 0)
			{
				*row = i;
				*col = j;
				return;
			}
		}
	}
}

void build_decision_tree(tree* t) // pravi stablo odlucivanja
{
	node* cur = t->root;	
	stack* s = NULL;
	push(&s, cur);
	int n = t->n;
	while (!stack_empty(s))
	{
		cur = pop(&s);
		int free_row = -1;
		int free_col = -1;
		first_free_pos(cur->magic_square, n, &free_row, &free_col);

		if (free_row == -1) 
			continue;

		int next_suc = 0;
		
		for (int i = 0; i < n * n;i++)
		{
			if (cur->cnt[i] == 0)
			{
				//ovaj deo sluzi da na istu poziciju ne ubacuje 2 puta isti element
				int flag = 0;
				for (int j = i - 1;j >= 0 && !flag;j--)
				{
					if (cur->cnt[j] == 0 && t->set[j] == t->set[i])
						flag = 1;
				}
				if (flag) 
					continue;

				node* next = create_next_state(t, cur, i, free_row, free_col);
				if (next == NULL)
					continue;
				
				cur->successors[next_suc] = next;
				next_suc++;
				push(&s, next);
			}
		}

		//realociranje niza sinova
		if (next_suc > 0) 
		{
			node** help_suc = realloc(cur->successors, next_suc * sizeof(node*));
			if (help_suc == NULL)
			{
				printf("Neuspesan realloc\n");
			}
			else
			{
				cur->num_of_successors = next_suc;
				cur->successors = help_suc;
			}
		}
		else
		{
			free(cur->successors);
			cur->successors = NULL;
			cur->num_of_successors = 0;
		}
	}
}

void free_tree(tree* t) // oslobadja stablo
{
	postorder_i(t, &free_node);
	free(t);
}

void level_order(tree* t)
{
	node* cur = t->root;
	queue* q = NULL;
	queue* rear = NULL;
	queue_insert(&q, &rear, cur);
	int cur_level = -1;
	while (!queue_empty(q))
	{
		node* cur = queue_delete(&q, &rear);
		int level = free_spots(t->root->magic_square, t->n) - free_spots(cur->magic_square, t->n);
		if (level != cur_level) 
		{
			cur_level++;
			printf("Level %d\n", level);
		}
		printf("Novi cvor na datom nivou.\n");
		print_node_info(cur, t->n);
		
		printf("Sinovi ovog cvora su:\n");

		for (int i = 0; i < cur->num_of_successors;i++)
		{
			if (cur->successors[i])
			{
				print_node_info(cur->successors[i], t->n);
				queue_insert(&q, &rear, cur->successors[i]);
			}
		}
	}
}

tree* build_tree(int** mat, int n, int* set, int magic_sum)
{
	tree* t = create_tree(mat, n, set, magic_sum);
	build_decision_tree(t);
	return t;
}


void print_menu() // prvi meni
{
	printf("0 - prekinite izvrsavanje programa\n");
	printf("1 - pokrenitu simulaciju\n");
	printf("Uneti svoj izbor: ");
}

void print_menu2() // drugi meni
{
	printf("0 - prekinite izvrsavanje programa\n");
	printf("1 - ispis stabla\n");
	printf("2 - ispis svih resenja\n");
	printf("Uneti svoj izbor: ");
}

int all_positive(int* arr, int n)
{
	for (int i = 0;i < n;i++)
		if (arr[i] <= 0) 
			return 0;

	return 1;
}

int main()
{
	int choice = 1;
	int n;
	while (choice)
	{
		print_menu();
		choice = good_input(0, 1);
		if (choice)
		{
			printf("Unesite dimenziju magicnog kvadrata: ");
			n = good_input(1, INT_MAX);
			printf("Unesite skup elemenata od kojih ce se formirati magicni kvadrat(%d elemenata): ", n * n);
			
			int* set_numbers;
			SAFE_MALLOC(set_numbers, n * n, int)
			input_array(set_numbers, n * n);

			int magic_sum = get_magic_sum(set_numbers, n);
			if (magic_sum == -1 || !all_positive(set_numbers,n*n))
			{ 
				free(set_numbers);
				printf("Za dati skup brojeva nije moguce formirati magicni kvadrat.\n");
				continue;
			}
			
			printf("Karakteristicna suma za uneti skup brojeva je %d.\n", magic_sum);
			printf("Uneti pocetno stanje magicnog kvadrata: \n");
			int** magic_square = new_magic_square(n);
			input_matrix(magic_square, n);
			if (!valid_state(magic_square, n, set_numbers, magic_sum))
			{
				free(set_numbers);
				for (int i = 0;i < n;i++) free(magic_square[i]);
				free(magic_square);
				printf("Uneti magicni kvadrat nije ispravan. Simulacija se prekida.\n");
				continue;
			}
			
			printf("Pocetno stanje magicnog kvadrata je validno. Program nastavlja kreiranje stabla.\n");

			tree* t = build_tree(magic_square, n, set_numbers, magic_sum);

			printf("Kreirano je stablo odlucivanja.\n");
			choice = 1;
			while (choice)
			{
				print_menu2();
				choice = good_input(0, 2);
				if (choice == 0)
				{
					printf("Prekida se simulacija.\n");
					free_tree(t);
				}
				else if (choice == 1)
				{
					printf("Izabrali ste ispis stabla po nivoima.\n");
					level_order(t);
				}
				else if (choice == 2)
				{
					printf("Izabrali ste ispis resenja magicnog kvadrata.\n");
					postorder_i(t, is_solution);
				}
			}
			choice = 1;
		}
		else
		{
			printf("Program se prekida.\n");
		}
	}

	return 0;
}

/*
neki testovi:

1)za trazenje resenja
n = 3
set = 1,2,3,4,5,6,7,8,9
mat:
0 0 0
0 0 0
0 0 0

2)da se isti element ne ubacuje vise puta 
n = 2
set = 1, 1, 1, 1
mat:
0 0
0 0

3)da se proveri postorder
n = 2
set = 1,1,2,2
mat:
0 0
0 0

4)od nekog pocetnog stanja
(matrica dim 4 iz domaceg)

5)neko nevalidno stanje matrice da se unese
6)neki set brojeva da se unese tako da ne moze ni suma da se formira

*/