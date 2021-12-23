#include <iostream>
#include <vector>
#include <fstream>
#include <sstream>
#include <queue>

using namespace std;

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

class Bucket
{
	int bucketSize;
	vector<pair<int, Student*> > data;


	void removePos(int pos)
	{
		data.erase(data.begin() + pos);
	}

public:
	Bucket(int bs) : bucketSize(bs) {}

	Student* findStudent(int key)
	{
		for (int i = 0; i < data.size();i++)
		{
			if (data[i].first == key)
				return data[i].second;
		}
		return nullptr;
	}

	void addStudent(int key, Student* student)
	{
		data.push_back(make_pair(key,student));
	}

	bool findKey(int key)
	{
		for (int i = 0; i < data.size();i++)
			if (data[i].first == key)
				return true;
		return false;
	}

	bool removeStudent(int key)
	{
		for (int i = 0; i < data.size();i++)
		{
			if (data[i].first == key)
			{
				removePos(i);
				return true;
			}
		}
		return false;
	}

	bool full()
	{
		return data.size() == bucketSize;
	} 

	bool empty()
	{
		return data.size() == 0;
	}

	void empty_into(vector<pair<int, Student*> >& vec)
	{
		for (int i = 0; i < data.size();i++)
			vec.push_back(make_pair(data[i].first, data[i].second));

		data.clear();
	}

	int getSize()
	{
		return data.size();
	}

	void print()
	{
		cout << "Bucket print" << endl;
		cout << "size " << data.size() << endl;
		for (int i = 0; i < data.size();i++)
			cout << *data[i].second << endl;
	}

	~Bucket()
	{
		for (int i = 0; i < data.size();i++)
			delete data[i].second;

		data.clear();
	}
};

class HashTable
{
	int hashTableSize;
	int numOfKeysInBucket;
	int d;
	int p;
	vector<Bucket*> buckets;
	//vector<int> bucketDepth;
public:
	HashTable(int k, int p) : numOfKeysInBucket(k), p(p)
	{
		this->d = 1;
		hashTableSize = 1 << this->d;
		buckets.resize(hashTableSize);
		//bucketDepth.resize(d + 1, 0);
		for (int i = 0; i < hashTableSize;i++)
			buckets[i] = nullptr;
	}

	int getDBits(int num)
	{
		return ((1 << d) - 1) & (num >> (p - d));
	}

	bool keyFound(int key)
	{
		int index = indexOfBucket(key);
		if (buckets[index] == nullptr)
			return false;

		return buckets[index]->findKey(key);
	}

	Student* findKey(int key)
	{
		int index = indexOfBucket(key);
		if (buckets[index] == nullptr)
			return nullptr;

		return buckets[index]->findStudent(key);
	}

	int originalHash(int key)
	{
		return key % (1 << p);
	}

	int indexOfBucket(int key)
	{
		return getDBits(originalHash(key));
	}

	void insertPair(pair<int, Student*> pa)
	{
		int index = indexOfBucket(pa.first);
		buckets[index]->addStudent(pa.first, pa.second);
	}

	void insertFromVector(const vector<pair<int, Student*> >& vec)
	{
		for (int i = 0; i < vec.size();i++)
			insertPair(vec[i]);
	}

	void expandTable()
	{
		d++;
		vector<Bucket*> newBuckets(1 << d);
		for (int i = 0;i < buckets.size();i++)
		{
			newBuckets[2 * i] = newBuckets[2 * i + 1] = buckets[i];
		}
		hashTableSize = newBuckets.size();
		buckets = newBuckets;
	}

	int inTheSameHalf(int ind1, int ind2, int low, int high)
	{
		int mid = (low + high) >> 1;
		bool left1 = (ind1 <= mid);
		bool left2 = (ind2 <= mid);
		return left1 == left2;
	}

	bool allHashesSame(vector<pair<int, Student*> >& vec, int low, int high)
	{
		for (int i = 1; i < vec.size();i++)
			if (!inTheSameHalf(indexOfBucket(vec[i - 1].first), indexOfBucket(vec[i].first), low, high))
				return false;
		
		return true;
	}

	void addSplitted(int low, int high, int key, Student* student)
	{
		vector<pair<int, Student*> > dataHash;
		buckets[low]->empty_into(dataHash);
		dataHash.push_back(make_pair(key, student));
		cout << dataHash.size() << endl;
		while (allHashesSame(dataHash, low, high))
		{
			expandTable();
			low = low << 1;
			high = (high << 1) + 1;
		}
		int mid = (low + high) >> 1;
		Bucket* newBucket = new Bucket(numOfKeysInBucket);
		for (int i = mid + 1;i <= high;i++)
			buckets[i] = newBucket;
		insertFromVector(dataHash);
	}

	void split(int index, int key, Student* student)
	{
		pair<int, int> bounds = findBounds(index);
		int low = bounds.first;
		int high = bounds.second;
		if (low == high)
		{
			expandTable();
			cout << *this << endl;
			index = indexOfBucket(key);
			bounds = findBounds(index);
			addSplitted(bounds.first, bounds.second, key, student);
		}
		else
		{
			addSplitted(low, high, key, student);
		}
	}

	bool insertKey(int key, Student* student)
	{
		if (findKey(key))
			return false;

		int index = indexOfBucket(key);
		if (buckets[index] == nullptr)
		{
			Bucket* newBucket = new Bucket(numOfKeysInBucket);
			pair<int, int> bounds = findBounds(index);
			for (int i = bounds.first;i <= bounds.second;i++)
				buckets[i] = newBucket;

			newBucket->addStudent(key, student);
		}
		else if(buckets[index]->full())
		{
			split(index, key, student);
		}
		else
		{
			buckets[index]->addStudent(key, student);
		}
		return true;
	}

	pair<int, int> findBounds(int index)
	{
		int div = index / (1 << (d-1));
		int lo = div * (1 << (d-1));
		int hi = lo + (1 << (d-1)) - 1;
		pair<int, int> pa = make_pair(-1, -1);
		for (int i = lo; i <= hi;i++)
		{
			if (buckets[i] == buckets[index])
			{
				if (pa.first == -1)
					pa.first = i;
				pa.second = i;
			}
		}
		return pa;
	}

	void shrinkTable()
	{
		d--;
		vector<Bucket*> newBuckets(1 << d);
		for (int i = 0; i < newBuckets.size();i++)
			newBuckets[i] = buckets[2 * i];
		buckets = newBuckets;
	}

	void mergeBuckets(int index)
	{
		pair<int, int> bounds = findBounds(index);
		int sz = bounds.second - bounds.first + 1;
		if (sz == 1)
		{
			if (index % 2 == 1)
				buckets[index] = buckets[index - 1];
			else
				buckets[index] = buckets[index + 1];
		}
		else
		{
			bool left = (bounds.first / (1 << (d - 1))) == ((bounds.first - 1) / (1 << (d - 1)));
			bool right = (bounds.second / (1 << (d - 1))) == ((bounds.second + 1) / (1 << (d - 1)));
			if (left)
			{
				int lo = bounds.first - sz;
				int hi = bounds.second;
			}
			else if (right)
			{
				int lo = bounds.first;
				int hi = bounds.second + sz;
			}
		}
	}

	bool deleteKey(int key)
	{
		int index = indexOfBucket(key);
		if (buckets[index] == nullptr)
			return false;

		bool ret = buckets[index]->removeStudent(key);
		if (!ret)
			return false;

		if (buckets[index]->empty())
		{
			mergeBuckets(index);
		}

		return true;
	}

	void clear()
	{
		for (int i = 0; i < buckets.size();i++)
		{
			buckets[i] = nullptr;
			delete buckets[i];
		}
	}

	int keyCount()
	{
		return 0;
	}

	int tableSize()
	{
		return hashTableSize;
	}

	friend ostream& operator << (ostream& os, const HashTable& hashTable)
	{
		os << "HashTable print" << endl;
		cout << "size " << hashTable.hashTableSize << endl;
		for (int i = 0; i < hashTable.hashTableSize;i++)
		{
			cout << "pos " << i << endl;
			if (hashTable.buckets[i] == nullptr) cout << "nullptr" << endl;
			else hashTable.buckets[i]->print();

		}
		return os;
	}

	double fillRatio()
	{
		return 0.;
	}

	~HashTable()
	{
		clear();
	}

};

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
	string fname = "students_5.csv";

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

	queue<Student*> q;

	for (int i = 1; i < content.size();i++)
	{
		cout << content[i][0] << endl;
		int indeks = stoi(content[i][0]);
		string ime = content[i][1];
		vector<string> vec;
		if (content[i].size() > 2) vec.push_back(content[i][2]);
		Student* stud = new Student(indeks, ime, vec);
		q.push(stud);
	}

	int k;
	cout << "Unesite broj kljuceva u jednom baketu: "; cin >> k;
	cout << "Unesite stepen p : "; int p; cin >> p;
	//cout << "Unesite pocetnu dubinu tabele: "; int d;cin >> d;
	HashTable* hashTable = new HashTable(k, p);

	while (1)
	{
		printMenu();
		int izb; cin >> izb;
		if (izb == 1)
		{
			cout << "Izabrali ste ubacivanje studenta" << endl;
			if (q.empty())
			{
				cout << "Nema vise studenata" << endl;
			}
			else
			{
				Student* stud = q.front();
				q.pop();
				hashTable->insertKey(stud->getIndeks(), stud);
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