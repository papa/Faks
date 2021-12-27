#include <iostream>
#include <vector>
#include <fstream>
#include <sstream>
#include <queue>
#include <algorithm>
#include <ctime>

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
		{
			vec.push_back(make_pair(data[i].first, data[i].second));
			data[i].second = nullptr;
		}
		data.clear();
	}

	int getSize()
	{
		return data.size();
	}

	void print()
	{
		cout << endl;
		cout << "Bucket print" << endl;
		cout << "size " << data.size() << endl;
		cout << endl;
		for (int i = 0; i < data.size();i++)
			cout << *data[i].second << endl;
		cout << endl;
	}

	void moveTo(Bucket* b)
	{
		cout << data.size() << endl;
		int x = data.size();
		//system("pause");
		for (int i = 0; i < x;i++)
		{
			cout << i << " " << x << endl;
			b->addStudent(data[i].first, data[i].second);
			data[i].second = nullptr;
		}
		data.clear();
	}

	void clear()
	{
		cout << "brisem bucket" << endl;
		for (int i = 0; i < data.size();i++)
		{
			delete data[i].second;
			data[i].second = nullptr;
		}
		data.clear();
		cout << "obrisao" << endl;
	}

	~Bucket()
	{
		clear();
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
			buckets[i] = new Bucket(numOfKeysInBucket);
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

	bool allHashesInRange(int low, int high, vector<int>& vec)
	{
		for (int i = 0;i < vec.size();i++)
			if (!(vec[i] >= low && vec[i] <= high))
				return false;
		return true;
	}

	bool allHashesSame(vector<pair<int, Student*> >& vec, int low, int high)
	{
		vector<int> hashes;

		int mini = 1e9;
		int maks = -1;
		for (int i = 0; i < vec.size();i++)
		{
			//cout << "data student " << endl;
			//cout << vec[i].first << endl;
			//cout << indexOfBucket(vec[i].first) << endl;
			hashes.push_back(indexOfBucket(vec[i].first));
			maks = max(indexOfBucket(vec[i].first), maks);
			mini = min(indexOfBucket(vec[i].first), mini);
		}

		bool flag = false;
		for (int i = 1; i < hashes.size() && !flag;i++)
		{
			if (hashes[i] != hashes[i - 1])
				flag = true;
		}

		if (!flag)
			return true;

		/*while (high - low > 0 && allHashesInRange(low, high, hashes))
		{
			int mid = (low + high) >> 1;
			if (allHashesInRange(low, mid, hashes))
			{
				Bucket* newBucket = new Bucket(numOfKeysInBucket);
				for (int i = mid + 1; i <= high;i++)
					buckets[i] = newBucket;
				high = mid;
			}
			else if (allHashesInRange(mid + 1, high, hashes))
			{
				Bucket* newBucket = new Bucket(numOfKeysInBucket);
				for (int i = low; i <= mid;i++)
					buckets[i] = newBucket;
				low = mid + 1;
			}
			else
			{
				Bucket* newBucket = new Bucket(numOfKeysInBucket);
				for (int i = low; i <= mid;i++)
					buckets[i] = newBucket;
				return false;
			}
		}*/

		while (high - low > 0 && mini>=low && maks <= high)
		{
			int mid = (low + high) >> 1;
			if (mini >= low && maks <= mid)
			{
				Bucket* newBucket = new Bucket(numOfKeysInBucket);
				for (int i = mid + 1; i <= high;i++)
					buckets[i] = newBucket;
				high = mid;
			}
			else if(mini >= mid + 1 && maks <= high)
			{
				Bucket* newBucket = new Bucket(numOfKeysInBucket);
				for (int i = low; i <= mid;i++)
					buckets[i] = newBucket;
				low = mid + 1;
			}
			else
			{
				Bucket* newBucket = new Bucket(numOfKeysInBucket);
				for (int i = low; i <= mid;i++)
					buckets[i] = newBucket;
				return false;
			}
		}
		
		return false;
	}

	void addSplitted(int low, int high, int key, Student* student)
	{
		vector<pair<int, Student*> > dataHash;
		buckets[low]->empty_into(dataHash);
		dataHash.push_back(make_pair(key, student));
		//cout <<  "size " << dataHash.size() << endl;
		while (allHashesSame(dataHash, low, high))
		{
			//cout << "expanindg all hashes same " << endl;
			expandTable();
			low = low << 1;
			high = (high << 1) + 1;
		}
		insertFromVector(dataHash);
		//int mid = (low + high) >> 1;
		//Bucket* newBucket = new Bucket(numOfKeysInBucket);
		//for (int i = mid + 1;i <= high;i++)
		//	buckets[i] = newBucket;
		//insertFromVector(dataHash);
	}

	void split(int index, int key, Student* student)
	{
		pair<int, int> bounds = findBounds(index);
		int low = bounds.first;
		int high = bounds.second;
		if (low == high)
		{
			expandTable();
			//cout << *this << endl;
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
		//cout << "index " << index << endl;
		if(buckets[index]->full())
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
		//cout << "shrinking" << endl;
		d--;
		vector<Bucket*> newBuckets(1 << d);
		for (int i = 0; i < newBuckets.size();i++)
			newBuckets[i] = buckets[2 * i];
		buckets = newBuckets;
		hashTableSize = 1 << d;
	}

	bool shouldShrink()
	{
		for (int i = 1;i < buckets.size();i += 2)
			if (buckets[i] != buckets[i - 1])
				return false;
		return true;
	}

	bool canMergeLeft(int index)
	{
		//cout << "left" << endl;
		//cout << "ovde sam" << endl;
		//cout << index << endl;
		if (index == 0) return false;
		pair<int, int> bounds = findBounds(index);
		if (bounds.first == 0) return false;
		pair<int, int> boundsLeft = findBounds(bounds.first - 1);
		int sz1 = bounds.second - bounds.first + 1;
		int sz2 = boundsLeft.second - boundsLeft.first + 1;

		//cout << "bounds" << endl;
		//cout << bounds.first << " " << bounds.second << endl;
		//cout << boundsLeft.first << " " << boundsLeft.second << endl;

		if (sz1 != sz2)
			return false;

		if(sz1 == (1 << (d-1)))
			return false;

		if (sz1 == 1 && index % 2 == 0)
			return false;
		int mod = 2*sz1;
		if (bounds.first / mod != boundsLeft.first / mod)
			return false;
		
		if (buckets[bounds.first]->getSize() + buckets[boundsLeft.first]->getSize() > numOfKeysInBucket)
			return false;

		buckets[boundsLeft.first]->moveTo(buckets[bounds.first]);

		delete buckets[boundsLeft.first];

		for (int i = boundsLeft.first;i <= boundsLeft.second;i++)
			buckets[i] = buckets[bounds.first];

		//cout << "izaso" << endl;

		return true;
	}

	bool canMergeRight(int index)
	{
		//cout << "right" << endl;
		if (index == (1 << d) - 1) return false;
		pair<int, int> bounds = findBounds(index);
		//cout << "bounds1" << endl;
		if (bounds.second == (1 << d) - 1) return false;
		pair<int, int> boundsRight = findBounds(bounds.second + 1);
		//cout << "bounds2" << endl;
		int sz1 = bounds.second - bounds.first + 1;
		int sz2 = boundsRight.second - boundsRight.first + 1;

		if (sz1 != sz2)
			return false;

		if (sz1 == (1 << (d - 1)))
			return false;

		if (sz1 == 1 && index % 2 ==1)
			return false;
		//cout << "ajde mod" << endl;
		int mod = 2*sz1;
		cout << mod << endl;
		cout << bounds.first << " " << boundsRight.first << endl;
		if (bounds.first / mod != boundsRight.first / mod)
			return false;
		//cout << "modovao" << endl;
		if (buckets[bounds.first]->getSize() + buckets[boundsRight.first]->getSize() > numOfKeysInBucket)
			return false;
		//cout << "prebacujem..." << endl;
		vector<pair<int, Student*> > vec;
		buckets[boundsRight.first]->empty_into(vec);
		//cout << "ispraznio" << endl;
		for (int i = 0; i < vec.size();i++)
			buckets[bounds.first]->addStudent(vec[i].first, vec[i].second);
		//buckets[boundsRight.first]->moveTo(buckets[bounds.first]);
		//cout << "prebacio" << endl;
		delete buckets[boundsRight.first];

		//cout << "iteriram" << endl;
		for (int i = boundsRight.first;i <= boundsRight.second;i++)
			buckets[i] = buckets[bounds.first];

		return true;
	}

	bool merge()
	{
		//cout << "d " << d << endl;
		bool merged = false;
		for (int i = 0; i < buckets.size();i++)
		{
			if (i > 0 && buckets[i] == buckets[i - 1]) 
				continue;
			if (canMergeLeft(i))
				merged = true;
			else if (canMergeRight(i))
				merged = true;
		}
		cout << merged << endl;
		return merged;
	}

	void tryMerging()
	{
		while (1)
		{
			if (d == 1) return;
			while (merge())
			{
				//cout << "merging" << endl;
				//cout << *this << endl;
			}
			//cout << "zavrsio" << endl;

			if (shouldShrink())
			{
				//cout << "old d " << d << endl;
				shrinkTable();
				//cout << "new d " << d << endl;
			}
			else
				break;
		}
	}

	bool deleteKey(int key)
	{
		int index = indexOfBucket(key);
		//cout << index << endl;
		bool ret = buckets[index]->removeStudent(key);
		if (!ret)
		{
			//cout << "nema ga " << key << endl;
			//buckets[index]->print();
			return false;
		}
		//cout << "ovde" << endl;
		//cout << hashTableSize << endl;
		//if (buckets[index]->empty())
		//{
			//cout << "trying" << endl;
			tryMerging();
		//}
		//cout << *this << endl;
		return true;
	}

	void clear()
	{
		cout << "brisem" << endl;
		for (int i = 0; i < buckets.size();i++)
		{
			cout << i << endl;
			if (i == 0) continue;
			if (buckets[i] != buckets[i - 1])
			{
				delete buckets[i - 1];
				buckets[i - 1] = nullptr;
			}
		}
		delete buckets[buckets.size() - 1];
		buckets[buckets.size() - 1] = nullptr;
		buckets.clear();
		cout << "ovde " << endl;
		d = 1;
		buckets.resize(1 << d);
		hashTableSize = 1 << d;
		for (int i = 0; i < buckets.size();i++)
			buckets[i] = new Bucket(numOfKeysInBucket);
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
		cout << endl;
		for (int i = 0; i < hashTable.hashTableSize;i++)
		{
			cout << "POZICIJA " << i << endl;
			if (i == hashTable.hashTableSize - 1 || hashTable.buckets[i] != hashTable.buckets[i + 1])
			{
				hashTable.buckets[i]->print();
			}
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

void static_test(int k, int p, deque<Student*> &dq)
{
	int start = clock();
	cout << "gotovo" << endl;
	int duration = (std::clock() - start) / (double)CLOCKS_PER_SEC;
	cout << duration << endl;

	HashTable* hashTable = new HashTable(k, p);
	vector<int> keys;
	int cntx = 0;
	while (!dq.empty())
	{
		cntx++;
		//cout << cntx << endl;
		Student* stud = dq.front();
		dq.pop_front();
		hashTable->insertKey(stud->getIndeks(), stud);
		keys.push_back(stud->getIndeks());
	}
	duration = (std::clock() - start) / (double)CLOCKS_PER_SEC;
	cout << duration << endl;
	cout << hashTable->keyCount() << endl;

	int cnt = 0;
	for (int i = 0; i < keys.size();i++)
	{
		//cout << i << endl;
		Student* ok = hashTable->findKey(keys[i]);
		if (ok != nullptr) cnt++;
	}
	//cout << *hashTable << endl;
	int cnt2 = 0;
	for (int i = 0; i < keys.size();i++)
	{
		cout << i << endl;
		bool ok = hashTable->deleteKey(keys[i]);
		//cout << *hashTable << endl;
		if (ok) cnt2++;
		//else { cout << "ne brise"; exit(-1); }
	}
	cout << cnt2 << endl;
	if (cnt2 == 50)
		cout << "ok delete" << endl;
	if (cnt == 50)
		cout << "OK" << endl;
	cout << cnt << endl;
	duration = (std::clock() - start) / (double)CLOCKS_PER_SEC;
	cout << duration << endl;
	cout << *hashTable << endl;
	exit(0);
}

int main()
{
	string fname = "students_50.csv";
	
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
		//cout << content[i][0] << endl;
		int indeks = stoi(content[i][0]);
		string ime = content[i][1];
		vector<string> vec;
		if (content[i].size() > 2) vec.push_back(content[i][2]);
		Student* stud = new Student(indeks, ime, vec);
		dq.push_back(stud);
	}
	
	//static_test(4, 25, dq);

	int k,p;
	cout << "Unesite broj kljuceva u jednom baketu: "; cin >> k;
	cout << "Unesite stepen p : "; cin >> p;
	//cout << "Unesite pocetnu dubinu tabele: "; cin >> d;
	HashTable* hashTable = new HashTable(k, p);
	while (1)
	{
		printMenu();
		int izb; cin >> izb;
		if (izb == 1)
		{
			cout << "Izabrali ste ubacivanje studenta" << endl;
			if (dq.empty())
			{
				cout << "Nema vise studenata" << endl;
			}
			else
			{
				Student* stud = dq.front();
				dq.pop_front();
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

/*

2
20
1
1
1
1
2
20200245
2
19980735
*/