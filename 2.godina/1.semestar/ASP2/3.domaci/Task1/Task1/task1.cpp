#include <iostream>
#include <vector>
#include <fstream>
#include <sstream>
#include <ctime>
#include <queue>

using namespace std;

const int prime1 = (1e9) + 7;
const int prime2 = (1e9) + 7;

class AdressFunction;
class SplitSequenceLinearHashing;
class Student;
class HashTable;

class Bucket
{
	int bucketSize;
	vector<pair<int, Student*> > data;
	vector<int> mark;

public:
	static const int EMPTY = -1;
	static const int DELETED = -2;
	static const int FULL = 0;
	Bucket(int bs) : bucketSize(bs)
	{
		data.resize(bs);
		mark.resize(bs);
		for (int i = 0; i < bs;i++)
		{
			data[i].second = nullptr;
			mark[i] = EMPTY;
		}
	}

	int getFirstKey()
	{
		for (int i = 0; i < data.size();i++)
			if (mark[i] == FULL)
				return data[i].first;
		return -1;
	}

	Student* findStudent(int key)
	{
		for (int i = 0; i < data.size();i++)
		{
			if (data[i].first == key && mark[i] == FULL)
				return data[i].second;
		}
		return nullptr;
	}

	bool allEmpty()
	{
		for (int i = 0; i < data.size();i++)
			if (mark[i] != EMPTY)
				return false;
		return true;
	}

	bool addStudent(int key, Student* student)
	{

		if (findKey(key))
			return false;

		for (int i = 0; i < data.size();i++)
		{
			if (mark[i] == EMPTY || mark[i] == DELETED)
			{
				mark[i] = FULL;
				data[i].first = key;
				data[i].second = student;
				return true;
			}
		}

		return false;
	}

	bool findKey(int key)
	{
		return findStudent(key) != nullptr;
	}

	bool removeStudent(int key)
	{
		for (int i = 0; i < data.size();i++)
		{
			if (data[i].first == key && mark[i] == FULL)
			{
				mark[i] = DELETED;
				delete data[i].second;
				data[i].second = nullptr;
				return true;
			}
		}
		return false;
	}

	int getSize()
	{
		return data.size();
	}

	void print()
	{
		for (int i = 0; i < data.size();i++)
		{
			if (mark[i] == EMPTY)
				cout << "EMPTY ";
			else if (mark[i] == DELETED)
				cout << "DELETED ";
			else
				cout << data[i].first << " ";
		}
		cout << endl;
	}

	void clear()
	{
		for (int i = 0; i < data.size();i++)
		{
			delete data[i].second;
			data[i].second = nullptr;
			mark[i] = EMPTY;
		}
	}

	~Bucket()
	{
		clear();
	}
};

class HashTable
{
	int hashTableSize = 0;
	int bucketSize = 0;
	int numberOfKeys = 0;
	int p;
	vector<Bucket*> values;
	SplitSequenceLinearHashing* collisionH = nullptr;
public:
	friend SplitSequenceLinearHashing;
	static const int EMPTY = -1;
	static const int DELETED = 0;
	static const int FULL = 1;

	HashTable(int k, int p, int bucketSize);

	Student* findKey(int key);

	int originalHash(int key)
	{
		return (key % (1 << p)) % hashTableSize;
	}

	bool insertKey(int key, Student* student);

	bool deleteKey(int key);

	void clear();

	int keyCount()
	{
		return numberOfKeys;
	}

	int tableSize()
	{
		return hashTableSize;
	}

	friend ostream& operator << (ostream& os, const HashTable& hashTable)
	{
		os << "HashTable print" << endl;
		for (int i = 0; i < hashTable.hashTableSize;i++)
		{
			cout << "pos " << i << " ";
			hashTable.values[i]->print();
		}
		return os;
	}

	double fillRatio()
	{
		return (double)numberOfKeys / (bucketSize* hashTableSize) * 100.0;
	}

	~HashTable()
	{
		delete collisionH;
		clear();
	}
};

class AdressFunction
{
public:
	virtual int getAdress(int key, int adr, int attempt, int sz, const HashTable& hastTable, int step) = 0;
};

class SplitSequenceLinearHashing : public AdressFunction
{
public:
	int s1, s2;
	SplitSequenceLinearHashing(int s1, int s2) : s1(s1), s2(s2) {}

	int getAdress(int key, int adr, int attempt, int sz, const HashTable& hashTable, int step) override
	{
		return (adr + 1LL * step * attempt) % sz;

		int keyOther = hashTable.values[adr]->getFirstKey();
		if (keyOther < key)
			return (1LL * adr + 1LL * s1 * attempt) % sz;

		return (adr + 1LL * s2 * attempt) % sz;
	}
};

class Student
{
private:
	int indeks;
	vector<string> predmeti;
	string ime;
public:
	Student(int ind, const string& im, const vector<string>& p) :
		indeks(ind), ime(im)
	{
		for (int i = 0; i < p.size();i++)
			predmeti.push_back(p[i]);
	}

	int getIndeks()
	{
		return indeks;
	}

	friend ostream& operator << (ostream& os, const Student& s)
	{
		cout << "Podaci o studentu" << endl;
		cout << "indeks: " << s.indeks << endl;
		cout << "Ime i prezime: " << s.ime << endl;
		cout << "Predmeti koje slusa: " << endl;
		for (int i = 0;i < s.predmeti.size();i++)
			cout << s.predmeti[i] << endl;
		return os;
	}
};

HashTable::HashTable(int k, int p, int bucketSize) : hashTableSize(k), p(p)
{
	this->bucketSize = bucketSize;
	collisionH = new SplitSequenceLinearHashing(prime1, prime2); // podesi ove korake
	values.resize(hashTableSize);
	for (int i = 0; i < values.size();i++)
		values[i] = new Bucket(bucketSize);
}

Student* HashTable::findKey(int key)
{
	int att = 0;
	int adr;
	do
	{
		if (att == 0)
		{
			adr = originalHash(key);
		}
		else
		{
			adr = collisionH->getAdress(key, adr, att, hashTableSize, *this, collisionH->s1);
		}

		Student* stud = values[adr]->findStudent(key);
		if (stud != nullptr)
			return stud;

		att++;
	} while (!values[adr]->allEmpty() && att < hashTableSize);

	att = 0;
	do
	{
		if (att == 0)
		{
			adr = originalHash(key);
		}
		else
		{
			adr = collisionH->getAdress(key, adr, att, hashTableSize, *this, collisionH->s2);
		}

		Student* stud = values[adr]->findStudent(key);
		if (stud != nullptr)
			return stud;

		att++;
	} while (!values[adr]->allEmpty() && att < hashTableSize);


	return nullptr;
}

bool HashTable::deleteKey(int key)
{
	int att = 0;
	int adr;
	do
	{
		if (att == 0)
		{
			adr = originalHash(key);
		}
		else
		{
			adr = collisionH->getAdress(key, adr, att, hashTableSize, *this, collisionH->s1);
		}

		if (values[adr]->removeStudent(key))
		{
			numberOfKeys--;
			return true;
		}

		att++;
	} while (!values[adr]->allEmpty() && att < hashTableSize);

	att = 0;
	do
	{
		if (att == 0)
		{
			adr = originalHash(key);
		}
		else
		{
			adr = collisionH->getAdress(key, adr, att, hashTableSize, *this, collisionH->s2);
		}

		if (values[adr]->removeStudent(key))
		{
			numberOfKeys--;
			return true;
		}

		att++;
	} while (!values[adr]->allEmpty() && att < hashTableSize);

	return false;
}

void HashTable::clear()
{
	for (int i = 0; i < hashTableSize;i++)
	{
		delete values[i];
		values[i] = new Bucket(bucketSize);
	}
	numberOfKeys = 0;
}

bool HashTable::insertKey(int key, Student* student)
{
	int att = 0;
	int adr;
	int step = -1;
	do
	{
		if (att == 0)
		{
			adr = originalHash(key);
		}
		else
		{
			adr = collisionH->getAdress(key, adr, att, hashTableSize, *this, step);
		}
		
		if (values[adr]->findKey(key))
			return false;

		if (values[adr]->addStudent(key,student))
		{
			numberOfKeys++;
			return true;
		}
		else if (att == 0)
		{
			int keyOther = values[adr]->getFirstKey();
			if (keyOther < key) step = collisionH->s1;
			else step = collisionH->s2;
		}

		att++;
	} while (att < hashTableSize);
	return false;
}

void printMenu()
{
	cout << endl;
	cout << "1 - Ubaci studenta" << endl;
	cout << "2 - Izbrisi studenta" << endl;
	cout << "3 - Pronadji studenta sa indeksom" << endl;
	cout << "4 - Isprazni tabelu" << endl;
	cout << "5 - Broj studenata u tabeli" << endl;
	cout << "6 - Ispisi tabelu" << endl;
	cout << "7 - Popunjenost" << endl;
	cout << "0 - Kraj" << endl;
	cout << "Uneti izbor: " << endl;
}

void static_test(int k, int p, int bs, deque<Student*>& dq)
{
	int start = clock();

	HashTable* hashTable = new HashTable(k, p, bs);
	vector<int> keys;
	while (!dq.empty())
	{
		Student* stud = dq.front();
		dq.pop_front();
		bool ok = hashTable->insertKey(stud->getIndeks(), stud);
		if (!ok) exit(-2);
		keys.push_back(stud->getIndeks());
	}
	int duration = (std::clock() - start) / (double)CLOCKS_PER_SEC;
	cout << duration << endl;
	for (int i = 0; i < keys.size();i++)
	{
		Student* ok = hashTable->findKey(keys[i]);
	}
	for (int i = 0; i < keys.size();i++)
	{
		bool ok = hashTable->deleteKey(keys[i]);
	}
	//cout << *hashTable << endl;
	duration = (std::clock() - start) / (double)CLOCKS_PER_SEC;
	cout << duration << endl;
	exit(0);
}

int main()
{
	std::clock_t start;
	double duration;

	start = std::clock();

	string fname = "students_150.csv";

	vector<vector<string>> content;
	vector<string> row;
	string line, word;

	fstream file(fname, ios::in);
	if (file.is_open())
	{
		while (getline(file, line))
		{
			row.clear();

			stringstream str(line);

			while (getline(str, word, ','))
				row.push_back(word);
			content.push_back(row);
		}
	}
	else
		cout << "Nije moguce otvoriti fajl" << endl;

	deque<Student*> dq;

	for (int i = 1; i < content.size();i++)
	{
		int indeks = stoi(content[i][0]);
		string ime = content[i][1];
		vector<string> vec;
		if (content[i].size() > 2) vec.push_back(content[i][2]);
		Student* stud = new Student(indeks, ime, vec);
		dq.push_back(stud);
	}
	HashTable* hashTable = nullptr;
	int k, p, bs;

	//static_test(1000, 20, 200, dq);

	cout << "Uneti parametar p (stepen dvojke): "; cin >> p;
	cout << "Unesite velicinu tabele: "; cin >> k;
	cout << "Velicina baketa: "; cin >> bs;

	hashTable = new HashTable(k, p, bs);

	while (1)
	{
		printMenu();
		int izb; cin >> izb;
		if (izb == 1)
		{
			cout << "Izabrali ste ubacivanje studenta" << endl;
			if (dq.size() == 0)
			{
				cout << "Nema vise studenata" << endl;
			}
			else
			{
				Student* stud = dq.front();
				dq.pop_front();
				bool ok = hashTable->insertKey(stud->getIndeks(), stud);
				if (ok)
					cout << "Student je ubacen" << endl;
				else
				{
					dq.push_front(stud);
					cout << "Nije moguce ubaciti studenta" << endl;
				}
			}
		}
		else if (izb == 2)
		{
			cout << "Unesite indeks studenta koji se brise: ";
			int indeks; cin >> indeks;
			bool del = hashTable->deleteKey(indeks);
			if (del)
				cout << "Student je uspesno obrisan" << endl;
			else
				cout << "Student sa indeksom nije ni postojao u hes tabeli." << endl;
		}
		else if (izb == 3)
		{
			cout << "Unesite indeks za pretragu: " << endl;
			int indeks; cin >> indeks;
			Student* stud = hashTable->findKey(indeks);
			if (stud == nullptr)
				cout << "Nema zadatog studenta u tabeli" << endl;
			else
				cout << *stud << endl;
		}
		else if (izb == 4)
		{
			cout << "Tabela ce biti ispraznjena" << endl;
			hashTable->clear();
		}
		else if (izb == 5)
		{
			cout << "Broj studenata u tabeli je " << hashTable->keyCount() << endl;
		}
		else if (izb == 6)
		{
			//cout << "Hes tabela" << endl;
			cout << *hashTable << endl;
		}
		else if (izb == 7)
		{
			cout << "Popunjenost je " << hashTable->fillRatio() << "%" << endl;
		}
		else if (izb == 0)
		{
			hashTable->clear();
			delete hashTable;
			hashTable = nullptr;
			cout << "Kraj programa" << endl;
			break;
		}
		else
		{
			cout << "Pogresan unos";
		}
	}
	return 0;
}