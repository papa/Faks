#include <iostream>
#include <vector>
#include <fstream>
#include <sstream>
#include <queue>

using namespace std;

//probaj ovde vise stvari
const int prime1 = (1e9) + 7;
const int prime2 = 1299709;

class AdressFunction;
class SplitSequenceLinearHashing;
class Student;
class HashTable;

class HashTable
{
	int hashTableSize = 0;
	int numberOfKeys = 0;
	int p;
	vector<Student*> values;
	SplitSequenceLinearHashing* collisionH = nullptr;
public:
	vector<int> keys;
	vector<int> mark;
	friend SplitSequenceLinearHashing;
	static const int EMPTY = -1;
	static const int DELETED = 0;
	static const int FULL = 1;

	HashTable(int k, int p);

	int findKeyPos(int key);

	Student* findKey(int key);

	int originalHash(int key)
	{
		return key % (1 << p);
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
			cout << i << " ";
			if (hashTable.mark[i] == HashTable::EMPTY)
			{
				cout << "EMPTY" << endl;
			}
			else if (hashTable.mark[i] == HashTable::DELETED)
			{
				cout << "DELETED" << endl;
			}
			else
			{
				cout << hashTable.keys[i] << endl;
			}
		}
		return os;
	}

	double fillRatio()
	{
		return (double)numberOfKeys / hashTableSize * 100.0;
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
	virtual int getAdress(int key, int adr, int attempt, int sz, const HashTable& hastTable) = 0;
};

class SplitSequenceLinearHashing : public AdressFunction
{
private:
	int s1, s2;
public:
	SplitSequenceLinearHashing(int s1, int s2) : s1(s1), s2(s2) {}

	int getAdress(int key, int adr, int attempt, int sz, const HashTable& hashTable) override
	{
		if (hashTable.keys[adr] < key)
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

HashTable::HashTable(int k, int p) : hashTableSize(k), p(p)
{
	collisionH = new SplitSequenceLinearHashing(prime1, prime2); // podesi ove korake
	keys.resize(hashTableSize);
	mark.resize(hashTableSize);
	values.resize(hashTableSize);
	for (int i = 0; i < hashTableSize;i++)
	{
		keys[i] = EMPTY;
		mark[i] = EMPTY;
		values[i] = nullptr;
	}
}

int HashTable::findKeyPos(int key)
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
			adr = collisionH->getAdress(key, adr, att, hashTableSize, *this);
		}

		if (mark[adr] != EMPTY && mark[adr] != DELETED && keys[adr] == key)
			return adr;

		att++;
	} while (mark[adr] != EMPTY && att < hashTableSize);
	return -1;
}

Student* HashTable::findKey(int key)
{
	int pos = findKeyPos(key);
	if (pos == -1) return nullptr;
	return values[pos];
}

bool HashTable::deleteKey(int key)
{
	int pos = findKeyPos(key);
	if (pos == -1) return false;
	mark[pos] = DELETED;
	keys[pos] = DELETED;
	delete values[pos];
	values[pos] = nullptr;
	numberOfKeys--;
	return true;
}

void HashTable::clear()
{
	for (int i = 0; i < hashTableSize;i++)
	{
		mark[i] = EMPTY;
		keys[i] = EMPTY;
		delete values[i];
		values[i] = nullptr;
	}
	numberOfKeys = 0;
}

bool HashTable::insertKey(int key, Student* student)
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
			adr = collisionH->getAdress(key, adr, att, hashTableSize, *this);
		}

		if (mark[adr] == EMPTY || mark[adr] == DELETED)
		{
			keys[adr] = key;
			mark[adr] = FULL;
			values[adr] = student;
			numberOfKeys++;
			return true;
		}
		else if (keys[adr] == key) return false;

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

int main()
{
	string fname = "students_10.csv";

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
	cout << "gotovo" << endl;
	HashTable* hashTable = nullptr;
	int k, p;
	cout << "Uneti parametar p: ";
	cin >> p;
	hashTable = new HashTable(1 << p, p);
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
			cout << "Hes tabela" << endl;
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