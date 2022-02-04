#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct list_node //cvor 
{
	char* name;
	char* surname;
	char* id;
	int study_year;
	struct list_node* next;
}node;

typedef struct list_header // heder
{
	node* head, * tail;
	int num_elem;
}header;

header* create_header(int cnt)// kreira novi heder
{
	header* h;
	h = (struct list_header*)malloc(sizeof(struct list_header));
	h->head = NULL;
	h->tail = NULL;
	h->num_elem = cnt;
	return h;
}

int only_header(header* h)
{
	//return h == NULL || h->head == NULL;
	return h->head == NULL;
}

node* new_node(char* name, char* surname, char* id, int study)// kreira novi cvor
{
	node* nnode;
	nnode = (struct list_node*)malloc(sizeof(struct list_node));
	nnode->name = name;
	nnode->surname = surname;
	nnode->id = id;
	nnode->study_year = study;
	nnode->next = NULL;
	return nnode;
}

void insert_node_to_end(header* h, node* q) // dodaje cvor na kraj
{
	if (only_header(h))
	{
		h->head = q;
	}
	else
	{
		h->tail->next = q;
	}
	h->tail = q;
	q->next = h->head;
	h->num_elem++;
}

void insert_to_end(header* h, char* name, char* surname, char* id, int study) // dodaje na kraj liste
{
	node* q = new_node(name, surname, id, study);
	insert_node_to_end(h, q);
}

node* delete_from_head(header* h) // brise sa pocetka liste
{
	if (only_header(h)) return NULL;

	node* t = h->head;
	if (h->num_elem == 1)
	{
		h->head = h->tail = NULL;
	}
	else
	{
		node* q = t->next;
		h->head = q;
		h->tail->next = q;
	}
	h->num_elem--;
	return t;
}

node* delete_node(header* h, node* t, node* p)// brise cvor t, ako mu je prethodnik p
{
	if (p == NULL)
	{
		return delete_from_head(h);
	}
	else
	{
		p->next = t->next;
		if (t == h->tail) h->tail = p;
		h->num_elem--;
		return t;
	}
}

node* get_at_position(header* h, int pos)// vraca element sa pozicije pos
{
	if (h->num_elem <= pos) return NULL;

	node* temp = h->head;
	node* prev = NULL;
	for (int i = 0;i < pos;i++)
	{
		prev = temp;
		temp = temp->next;
	}
	return delete_node(h, temp, prev);
}

double get_rand()
{
	return (double)rand() / RAND_MAX;
}

int get_random_index(int k)
{
	return rand() % k;
}

void print_menu()
{
	printf("1 - Pokrenite simulaciju\n");
	printf("0 - Zavrsite program\n");
	printf("Unesite svoj izbor: ");
}

void print_student_info(node* student)// informacije o studentu
{
	printf("Student sa indeksom %s je uspesno upisan.\n", student->id);
	printf("Ime: %s\n", student->name);
	printf("Prezime: %s\n", student->surname);
	printf("Godina: %d\n", student->study_year);
}

void print_list(header* h)// stampa listu, funckija se ne trazi ali sam je koristio za provere
{
	if (!only_header(h))
	{
		node* temp = h->head;
		do
		{
			print_student_info(temp);
			temp = temp->next;
		} while (temp != h->head);
	}
	printf("\n");
}

int queue_full(header* h, int ns)// da li je red pun
{
	return h->num_elem == ns;
}

int queue_empty(header* h)// da li je red prazan
{
	return only_header(h);
}

int good_input(int low,int high) // obezbedjuje unos u odredjenom range-u
{
	int choice;
	scanf("%d", &choice);
	while (choice < low || choice > high)
	{
		printf("Uneti broj se ne nalazi u zadatim granicama (%d-%d). Unesite ponovo : ",low,high);
		scanf("%d", &choice);
	}
	return choice;
}

int main()
{
	int choice = 1;
	header* list_h = create_header(0); // kreira heder lste
	header* queue_h = create_header(0);// kreira heder liste koja predstavlja red
	while (choice != 0)
	{
		print_menu();
		choice = good_input(0, 1);
		if (choice == 1)
		{
			int num_stud;
			printf("Uneti broj studenata: ");
			scanf("%d", &num_stud);
			while (num_stud <= 0)
			{
				printf("Unesti korektan broj za broj studenata(veci od 0): ");
				scanf("%d", &num_stud);
			}
			for (int i = 0; i < num_stud;i++) // ucitava sve studente
			{
				char name[32], surname[32], id[32];
				char* name2, * surname2, * id2;
				int sy;
				printf("Unesti podatke o %d. studentu\n", i + 1);
				printf("Ime: "); scanf("%s", name); name2 = malloc(strlen(name) + 1); strcpy(name2, name);
				printf("Prezime: "); scanf("%s", surname); surname2 = malloc(strlen(surname) + 1); strcpy(surname2, surname);
				printf("Broj indeksa: "); scanf("%s", id); id2 = malloc(strlen(id) + 1); strcpy(id2, id);
				printf("Godina studija(1-5): "); scanf("%d", &sy);
				while (sy < 1 || sy > 5)
				{
					printf("Unesite korektnu godinu studija(1-5): ");
					scanf("%d", &sy);
				}
				insert_to_end(list_h, name2, surname2, id2, sy); // stavlja svakog studenta u listu
			}
			printf("Svi studenti su uspeno ubaceni u listu.\n");
			int num_in_list = num_stud;

			while (num_in_list > 0) // prebacuje studente iz liste u red
			{
				int pos = get_random_index(num_in_list); // generise se random broj izmedju 0 i num_in_list
				node* ret_stud = get_at_position(list_h, pos); // uzima studenta sa pozicije pos
				printf("Student sa indeksom %s se sa pozicije %d prebacuje iz liste u red cekanja.\n", ret_stud->id,pos);
				insert_node_to_end(queue_h, ret_stud); // prebacuje ga u red
				num_in_list--;
			}
			printf("Svi studenti su uspeno prebaceni iz liste u red.\n");
			printf("Uneti granicu X za upis na godinu: ");
			double X;
			scanf("%lf", &X);
			int cnt = 0; // ukupan broj koraka
			while (!only_header(queue_h))
			{
				double randd = get_rand();// random realan broj od 0 do 1
				printf("Generisani broj za upis je %lf.\n",randd);
				node* ret_stud = delete_from_head(queue_h); // vraca studenta ako uspe da ga upise
				if (randd > X) // uspesno upisan
				{
					print_student_info(ret_stud);
					free(ret_stud);
				}
				else // neuspesno
				{
					insert_node_to_end(queue_h, ret_stud);
					printf("Student sa indeksom %s nije uspesno upisan. Pomera se na kraj reda.\n", ret_stud->id);
				}
				cnt++;
			}
			printf("Izvrsilo se %d koraka.\n", cnt);
		}
	}
	printf("Program se zavrsio.\n");
	free(list_h);
	free(queue_h);
	return 0;
}

//TESTIRANJE
//nije toliko zahtevno kao kod polinoma
//sama simulacija je test, lakse se uocava da li radi