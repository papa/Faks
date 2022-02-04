#include <stdio.h>
#include <math.h>

typedef struct list_node // cvor liste 
{
	double exp; // eksponent
	double coef; // koeficijent
	struct list_node* next;
}node;

typedef struct list_header // heder liste
{
	node* head, * tail; 
	int num_elem;
}header;

header* create_header()// kreira novi heder
{
	header* h;
	h = (struct list_header*)malloc(sizeof(struct list_header));
	h->head = NULL;
	h->tail = NULL;
	h->num_elem = 0;
	return h;
}

node* new_node(double e, double c)// kreira novi cvor
{
	node* nnode;
	nnode = (struct list_node*)malloc(sizeof(struct list_node));
	nnode->coef = c;
	nnode->exp = e;
	nnode->next = NULL;
	return nnode;
}

int only_header(header* h)// proverava da li je samo heder u listi
{
	//return h==NULL || h->head == NULL;
	return h->head == NULL;
}

void delete_from_head(header* h) //brise sa pocetka liste
{
	if (only_header(h)) return;

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
	free(t);
}

void delete_node(header* h, node* t, node* p) 
{
	// brise cvor t ako mu je pretnodnik p, 
	//prethodnik se cuva kako bi se eventualno promenio tail hedera
	if (p == NULL)
	{
		delete_from_head(h);
	}
	else
	{
		p->next = t->next;
		if (t == h->tail) h->tail = p;
		h->num_elem--;
		free(t);
	}
}

void insert_to_end(header* h, double e, double c)// dodaje na kraj liste
{
	node* q = new_node(e, c);
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

void insert_before(header* h, node* temp, double e, double c) // pravi novi cvor koji ubacuje nakon temp, i onda im razmeni podatke
{
	node* nnode = new_node(e, c);
	nnode->coef = temp->coef; nnode->exp = temp->exp; nnode->next = temp->next;
	temp->coef = c; temp->exp = e; temp->next = nnode;
	h->num_elem++;
	if (temp == h->tail) h->tail = nnode;
}

void insert_element(header* h, double e, double c)// dodaje element u listu
{
	if (only_header(h))
	{
		if (c != 0)
		{
			insert_to_end(h, e, c);
		}
	}
	else
	{
		node* temp = h->head;
		node* prev = NULL;
		int flag = 0;
		do // trazi prvi element tako da je eksponent <= e
		{
			if (temp->exp <= e) { flag = 1; break; }
			prev = temp;
			temp = temp->next;
		} while (temp != h->head);

		if (flag) // nasao je
		{
			if (temp->exp == e) // ako su jednaki
			{
				temp->coef += c; // menja koeficijent
				if (temp->coef == 0) // ako je koeficijent postao 0, brise se
				{
					delete_node(h, temp, prev);
				}
			}
			else if (c != 0) // ubacuje skroz novi element izmedju 2, ubacujemo ispred temp, pa im razmenimo vrednosti
			{
				insert_before(h, temp, e, c);
			}
		}
		else if (c != 0) // nije nasao, dodaje se na kraj
		{
			insert_to_end(h, e, c);
		}
	}
}

void delete_element(header* h, double e) // brise element sa eksponentom e
{
	if (only_header(h))
	{
		printf("Nije pronadjen dati eksponent\n");
	}
	else
	{
		node* prev = NULL;
		node* temp = h->head;
		int flag = 0;
		do//trazi prvi element ciji je eksponent <=e
		{
			if (temp->exp <= e) { flag = 1; break; }
			prev = temp;
			temp = temp->next;
		} while (temp != h->head);

		if (flag && temp->exp == e)// ako je taj eksponent == e brise se
		{
			delete_node(h, temp, prev);
			printf("Clan sa zadatim eksponentom je uspesno obrisan.\n");
		}
		else // ako je eksponent < e, znaci nema ga uopste
		{
			printf("Nije pronadjen dati eksponent.\n");
		}
	}
}

void print_polynom(header* h)// stampanje polinoma
{
	if (only_header(h))
	{
		printf("Polinom nema elemente.\n");
	}
	else
	{
		//za pozitivne koeficijente moramo rucno + da stampamo
		//za negativne ne moramo - se sam ispise
		//samo prolaz kroz listu
		node* temp = h->head;
		printf("P(x) = ");
		do
		{
			if (temp->exp == 0) 
			{
				if (temp->coef > 0)
				{
					if (temp != h->head) printf("+");
					printf("%.2lf", temp->coef);
				}
				else
				{
					printf("%.2lf", temp->coef);
				}
			}
			else if (temp->coef > 0) //formatiranje koje je uradjeno da se ne ispisuje +1*x^k nego samo x^k i da se k ne ispisuje ako je jednako 1
			{
				if (temp != h->head) printf("+");
				if (temp->coef != 1) printf("%.2lf*", temp->coef);
				printf("x");
				if (temp->exp != 1) printf("^(%.2lf)", temp->exp);
			}
			else //formatiranje da se ispisuje -x^k a ne -1*x^k
			{
				if (temp->coef == -1) printf("-");
				else printf("%.2lf*", temp->coef);
				printf("x");
				if (temp->exp != 1) printf("^(%.2lf)", temp->exp);
			}
			temp = temp->next;
		} while (temp != h->head);
		printf("\n");
	}
}

void add_polynomials(header* h1, header* h2, header* hres) // sabira polinome
{
	//tako sto ubacujemo prvo vece pa manje, garantujemo
	//da je i krajnja lista sortirana
	if (only_header(h1) || only_header(h2)) return;

	int pom1 = 0; //1 ako prvi pokazivac dodje na kraj
	int pom2 = 0; //1 ako drugi pokazivac dodje na kraj
	node* temp1 = h1->head;
	node* temp2 = h2->head;
	while (1)
	{
		if (temp1 == h1->head && pom1) // 1.dosao na kraj
		{
			if (temp2 == h2->head && pom2) break;
			insert_to_end(hres, temp2->exp, temp2->coef);
			temp2 = temp2->next;
			if (temp2 == h2->head) pom2 = 1;
		}
		else if (temp2 == h2->head && pom2)// 2.dosao na kraj
		{
			insert_to_end(hres, temp1->exp, temp1->coef);
			temp1 = temp1->next;
			if (temp1 == h1->head) pom1 = 1;
		}
		else
		{
			if (temp1->exp > temp2->exp)// prvo ide veci eksponent
			{
				insert_to_end(hres, temp1->exp, temp1->coef);
				temp1 = temp1->next;
				if (temp1 == h1->head) pom1 = 1;
			}
			else if (temp1->exp < temp2->exp)// isto prvo veci
			{
				insert_to_end(hres, temp2->exp, temp2->coef);
				temp2 = temp2->next;
				if (temp2 == h2->head) pom2 = 1;
			}
			else
			{
				//jednaki eksponenti, ubacujemo samo ako koeficijent nije 0
				if (temp1->coef + temp2->coef != 0) insert_to_end(hres, temp1->exp, temp1->coef + temp2->coef);
				temp1 = temp1->next;  temp2 = temp2->next;
				if (temp1 == h1->head) pom1 = 1;
				if (temp2 == h2->head) pom2 = 1;
			}
		}
	}
}

void multiply_polynomials(header* h1, header* h2, header* hres) //mnozi 2 polinoma
{
	if (only_header(h1) || only_header(h2)) return;

	//mnozimo svaki iz prve liste sa svakim iz druge liste
	node* temp1 = h1->head;
	node* temp2 = h2->head;
	do
	{
		temp2 = h2->head;
		do
		{
			insert_element(hres, temp1->exp + temp2->exp, temp1->coef * temp2->coef);
			temp2 = temp2->next;
		} while (temp2 != h2->head);
		temp1 = temp1->next;
	} while (temp1 != h1->head);
}

void clear_polynomial(header* h) // clear, odnosno uklanja sve elemente iz polinoma
{
	while (!only_header(h))
	{
		delete_from_head(h);
	}
}

void delete_polynomial(header **h) //brise polinom
{
	if (*h == NULL) return;
	clear_polynomial(*h);
	free(*h);
	*h = NULL;
}

double pol_value(header* h, double x)// racuna vrednost polinoma za vrednost x
{
	if (only_header(h)) return 0;
	double val = 0;
	node* temp = h->head;
	do
	{
		val += (temp->coef * pow(x, temp->exp));
		temp = temp->next;
	} while (temp != h->head);
	return val;
}

int first_free(header* h[], int len) //nalazi prvo mesto u nizu polinoma gde moze da se uspise rezultat
{
	for (int i = 0;i < len;i++)
		if (h[i] == NULL) return i;
	return -1;
}

void guide()
{
	printf("Na pocetku 5 pocetnih mesta je slobodno za ucitavanje polinoma. Ucitavanjem polinoma to mesto se zauzima sve dok se dati polinom ne obrise.\n");
	printf("Za operacije dodavanja, brisanja i stampanja polinoma, nemoguce je te operacije izvrsiti nad neucitanim polinomima.\n");
	printf("Kada vrsimo operacije nad 2 polinoma, rezultat se upisuje u prvo slobodno mesto, dok, ako takvo mesto ne postoji, rezultat se smesta u prvo mesto sleva na kojem se ne nalaze zadati polinomi.\n");
	printf("Brisanjem polinoma oslobadja se zadato mesto za dalje ucitavanje/smestanje rezultata.\n");
}

void print_menu()// meni
{
	printf("\n");
	printf("Ispod se nalazi meni. U zavisnosti od unetog broja program ce izvrsiti odredjene akcije.\n");
	printf("1 - ucitavanje novog polinoma\n");
	printf("2 - dodavanje clana polinoma\n");
	printf("3 - brisanje clana polinoma\n");
	printf("4 - ispis polinoma\n");
	printf("5 - sprovodjenje operacija\n");
	printf("6 - brisanje zadatog polinoma\n");
	printf("0 - prekid programa\n");
	printf("Uneti svoj izbor: ");
}

int get_spot(header* h[], int len, int p1, int p2) 
{
	// trazi mesto da upise rezultat operacije p1 op p2 
	//(prvo slobodno mesto ili neko mesto razlicito od p1 i p2)
	int ind = first_free(h, len);
	if (ind == -1)
	{
		for (int i = 0;i < 5;i++)
		{
			if (i != p1 - 1 && i != p2 - 1)
			{
				ind = i;
				break;
			}
		}
	}
	return ind;
}

int good_input(int lo, int hi) // obezbedjuje unos broj izmedju lo i hi
{
	int ind;
	scanf("%d", &ind);
	while (ind < lo || ind > hi)
	{
		printf("Uneti broj nije u granicama(%d-%d).Unesite ponovo: ", lo, hi);
		scanf("%d", &ind);
	}
	return ind;
}

int main()
{
	header* h[5] = { NULL };
	int choice = 1;
	int pol, p1, p2, ind, n;
	double e, c;
	guide();
	while (choice != 0)
	{
		print_menu();
		scanf("%d", &choice);
		if (choice == 0)// kraj
		{
			printf("Izabrali ste prekid programa.\n");
		}
		else if (choice == 1)// unos polinoma
		{
			printf("Izabrali ste unos polinoma.\n");
			printf("Unesite indeks polinoma koji zelite da ucitate(1-5): ");
			pol = good_input(1, 5);
			if (h[pol - 1])
			{
				printf("Izabrani polinom je vec ucitan. Ukoliko zelite da ga ponovo ucitate, prvo izbrisite postojeci polinom na datom indeksu.\n");
				continue;
			}
			h[pol - 1] = create_header();
			printf("Unesite koliko clanova zelite da ucitate: ");
			scanf("%d", &n);
			for (int i = 0;i < n;i++)
			{
				printf("Unesite eksponent i koeficijent clana koji dodajete: ");
				scanf("%lf%lf", &e, &c);
				insert_element(h[pol - 1], e, c);
			}
			while (h[pol - 1]->num_elem == 0)
			{
				printf("Morate uneti barem jedan element ciji koeficijent nije 0.Ucitajte eksponent i koeficijent: ");
				scanf("%lf%lf", &e, &c);
				insert_element(h[pol - 1], e, c);
			}
			printf("Uspesno ste ucitali polinom.\n");
		}
		else if (choice == 2)// dodavanje clana polinoma
		{
			printf("Izabrali ste dodavanje clana polinoma.\n");
			printf("Unesite indeks polinoma kojem zelite da dodate clan(1-5): ");
			pol = good_input(1, 5);
			if (h[pol - 1] == NULL)
			{
				printf("Izabrani polinom nije ucitan. Potrebno je da prvo ucitate polinom na datom indeksu.\n");
				continue;
			}
			printf("Unesite eksponent i koeficijent clana koji dodajete: ");
			scanf("%lf%lf", &e, &c);
			insert_element(h[pol - 1], e, c);
			printf("Uspesno ste dodali clan polinomu.\n");
			if (only_header(h[pol - 1]))
			{
				delete_polynomial(&h[pol - 1]);
				printf("Zadati polinom nema vise clanova pa je obrisan.\n");
			}

		}
		else if (choice == 3)// brisanje clana
		{
			printf("Izabrali ste brisanje clana polinoma.\n");
			printf("Unesite indeks polinoma ciji zlan zelite da izbrisete(1-5): ");
			pol = good_input(1, 5);
			if (h[pol - 1] == NULL)
			{
				printf("Izabrani polinom nije ucitan. Potrebno je da prvo ucitate polinom na datom indeksu.\n");
				continue;
			}
			printf("Unesite stepen clana koji zelite da obrisete: ");
			scanf("%lf", &e);
			delete_element(h[pol - 1], e);
			if (only_header(h[pol - 1]))
			{
				delete_polynomial(&h[pol - 1]);
				printf("Iz zadatog polinoma su svi obrisani pa tako i on ceo.\n");
			}
		}
		else if (choice == 4)// stampanje
		{
			printf("Izabrali ste ispis polinoma.\n");
			printf("Unesite indeks polinoma koji zelite da prikazete(1-5): ");
			pol = good_input(1, 5);
			if (h[pol - 1] == NULL)
			{
				printf("Izabrani polinom nije ucitan. Potrebno je da prvo ucitate polinom na datom indeksu.\n");
				continue;
			}
			print_polynom(h[pol - 1]);
		}
		else if (choice == 5)// + ili * polinoma, ili racunanje vrednosti
		{
			printf("Izabrali ste racunanje polinoma.Izaberite koju akciju zelite (0-vrednost polinoma za vrednost, 1 - sabiranje, 2 - mnozenje): ");
			int chop;
			chop = good_input(0, 2);
			if (chop == 0)
			{
				printf("Izabrali ste racunanje vrednosti polinoma.\n");
				printf("Unesite za koji polinom zelite da izracunate vrednost(1-5): ");
				pol = good_input(1, 5);
				if (h[pol - 1] == NULL)
				{
					printf("Izabrani polinom nije ucitan. Potrebno je da prvo ucitate polinom na datom indeksu.\n");
					continue;
				}
				printf("Unesite vrednost za koju zelite da izracunate vrednost polinoma: ");
				double x;
				scanf("%lf", &x);
				printf("P(%.2lf) = %.2lf\n", x, pol_value(h[pol - 1], x));
			}
			else if (chop == 1) //sabiraju se polinomi
			{
				printf("Polinomi nad kojima se vrsi operacija (sabiranje) : ");
				scanf("%d %d", &p1, &p2);
				while (p1 < 1 || p1 > 5 || p2 < 1 || p2 > 5)
				{
					printf("Uneti polinomi se ne nalaze u granicama. Unesite ponovo: ");
					scanf("%d %d", &p1, &p2);
				}
				if (h[p1 - 1] == NULL || h[p2 - 1] == NULL)
				{
					printf("Neki od unetih polinoma nisu ucitani.\n");
					continue;
				}
				ind = get_spot(h, 5, p1, p2);
				printf("Rezultat ce se upisati na mesto %d\n", ind + 1);
				delete_polynomial(&h[ind]);
				h[ind] = create_header();
				add_polynomials(h[p1 - 1], h[p2 - 1], h[ind]);
				if (only_header(h[ind]))
				{
					printf("Nakon sabiranja novi polinom nema nenulte koeficijente,pa ce biti obrisan.\n");
					delete_polynomial(&h[ind]);
				}
			}
			else if (chop == 2) //mnoze se polinomi
			{
				printf("Polinomi nad kojima se vrse operacije (mnozenje) : ");
				scanf("%d %d", &p1, &p2);
				while (p1 < 1 || p1 > 5 || p2 < 1 || p2 > 5)
				{
					printf("Uneti polinomi se ne nalaze u granicama. Unesite ponovo: ");
					scanf("%d %d", &p1, &p2);
				}
				if (h[p1 - 1] == NULL || h[p2 - 1] == NULL)
				{
					printf("Neki od unetih polinoma nisu ucitani.\n");
					continue;
				}
				ind = get_spot(h, 5, p1, p2);
				printf("Rezultat ce se upisati na mesto %d.\n", ind + 1);
				delete_polynomial(&h[ind]);
				h[ind] = create_header();
				multiply_polynomials(h[p1 - 1], h[p2 - 1], h[ind]);
				if (only_header(h[ind]))
				{
					printf("Nakon mnozenja novi polinom nema nenulte koeficijente,pa ce biti obrisan.\n");
					delete_polynomial(&h[ind]);
				}
			}
		}
		else if (choice == 6)// brisanje celog polinoma
		{
			printf("Izabrali ste brisanje polinoma. Odaberite koji polinom zelite da izbrisete(1-5): ");
			pol = good_input(1, 5);
			if (h[pol - 1] == NULL)
			{
				printf("Polinom na datom indeksu nije ni ucitan trenutno.\n");
				continue;
			}
			delete_polynomial(&h[pol - 1]);
			printf("Zadati polinom je uspesno obrisan.");
		}
	}
	for (int i = 0;i < 5;i++)
	{
		delete_polynomial(&h[i]);
	}
	return 0;
}

/*TESTIRANJE
-kreiranje polinoma ?
-brisanje polinoma ?
-pokusaj ubacivanja elementa u polinom koji nije kreiran ?
-pokusaj brisanja elementa u polinomu koji nije kreiran ?
-pokusaj ispisivanja polinoma koji nije kreiran ?
-pokusaj neke operacije nad polinomima koji nisu kreirani ?
-ubacivanje elementa ?
-ubacivanje elementa tako da je taj stepen najmanji ?
-ubacivanje elementa tako da je taj stepen najveci ?
-ubacivanje elementa tako da ponisti dati stepen (x,-x) ?
-ubacivanje elementa ciji je koeficijent 0 (sustinski se ne ubacuje) ?
-brisanje elementa ?
-brisanje najmanjeg stepena ?
-brisanje najveceg stepena ?
-brisanje nepostojeceg stepena ?
-brisi jedan po jedan element dok ga ne izbrises ceo, pa posle pokusaj da ga koristis ?
-ispis polinoma ?
-brisanje polinoma ?
-brisanje polinoma koji je vec obrisan/ne postoji ?
-operacije nad polinomima koji ne postoje ?
-sabiranje polinoma kada ima slobodnih mesta u nizu ?
-mnozenje polinoma kada ima slobodnih mesta u nizu ?
-sabiranje pol kada nema slobodnih mesta, potrebno je da se jedno isprazni ?
-mnozenje polinoma kada nema slobodnih mesta, potrebno je da se jedno isprazni ?
*/
