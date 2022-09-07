#include <iostream>
#include <string>
#include <queue>

using namespace std;

class Node
{
	int capacity;
	int min_num;
	
	Node* parent = nullptr;
	bool is_leaf = false;
	int parent_pos = -1;
	int* subtree_size;
	friend class BTree;
public:
	Node** subtrees = nullptr;
	int cnt = 0;
	string* words = nullptr;

	Node(int m,int cap, bool islf = false) : capacity(cap), min_num(m), is_leaf(islf)
	{
		words = new string[cap + 1];
		subtrees = new Node * [cap + 2];
		subtree_size = new int[cap + 2];
		for (int i = 0; i <= cap + 1;i++)
		{
			subtrees[i] = nullptr;
			subtree_size[i] = 0;
		}
	}

	void fix()
	{
		for (int i = 0;i <= cnt;i++)
		{
			if (subtrees[i])
			{
				subtrees[i]->parent = this;
				subtrees[i]->parent_pos = i;
			}
		}
	}

	bool over_capacity()
	{
		return cnt > capacity;
	}

	int find_pos(string word)
	{
		for (int i = 0; i < cnt;i++)
			if (words[i] == word)
				return i;
		return -1;
	}

	void move(int index)
	{
		for (int i = cnt;i > index;i--)
		{
			words[i] = words[i - 1];
			subtrees[i+1] = subtrees[i];// proveri ovu liniju 
			if(subtrees[i+1]) subtrees[i + 1]->parent_pos++;
		}
	}

	int first_smaller_or_eq(string word)
	{
		int low = 0;
		int high = cnt - 1;
		int good = -1;
		while (low <= high)
		{
			int mid = (low + high) >> 1;
			if (words[mid] <= word)
			{
				good = mid;
				low = mid + 1;
			}
			else
				high = mid - 1;
		}

		return good;
	}

	string delete_at_position(int pos)
	{
		string w = words[pos];
		for (int i = pos; i < cnt - 1;i++)
		{
			words[i] = words[i + 1];
			subtrees[i] = subtrees[i + 1];
			if (subtrees[i]) subtrees[i]->parent_pos--;
		}
		subtrees[cnt - 1] = subtrees[cnt];
		if (subtrees[cnt - 1]) subtrees[cnt - 1]->parent_pos--;
		cnt--;
		return w;
	}

	void insert_word(string word)
	{
		int good = first_smaller_or_eq(word);
		if (good == -1 || words[good] < word)
		{
			move(good + 1);
			words[good + 1] = word;
			cnt++;
		}
	}

	bool bad()
	{
		return cnt < min_num;
	}

	bool full()
	{
		return cnt == capacity;
	}

	bool ok_for_flow()
	{
		return cnt > min_num;
	}

	string delete_first()
	{
		string w = words[0];
		for (int i = 0; i < cnt - 1;i++)
		{
			words[i] = words[i + 1];
			subtrees[i] = subtrees[i + 1];
			if(subtrees[i]) subtrees[i]->parent_pos--;
		}
		subtrees[cnt - 1] = subtrees[cnt];
		if (subtrees[cnt - 1]) subtrees[cnt - 1]->parent_pos--;
		cnt--;
		return w;
	}

	int find_child(Node* node)
	{
		for (int i = 0;i <= cnt;i++)
			if (subtrees[i] == node)
				return i;
		return -1;
	}

	void print()
	{
		for (int i = 0;i < cnt;i++)
		{
			cout << words[i] << " ";
		}
		cout << endl;
	}

	

	~Node()
	{
		delete[] words;
		delete[] subtrees;
	}
};

class BTree
{
	Node* root = nullptr;
	int m;
public:
	BTree(int m) : m(m) {}


	Node* get_right_brother(Node* node)
	{
		if (node == nullptr) return nullptr;
		if (node->parent == nullptr) return nullptr;
		int parent_pos = node->parent_pos;
		if (parent_pos == m) return nullptr;
		return node->parent->subtrees[parent_pos + 1];
	}

	Node* get_left_brother(Node* node)
	{
		if (node == nullptr) return nullptr;
		if (node->parent == nullptr) return nullptr;
		int parent_pos = node->parent_pos;
		if (parent_pos == 0) return nullptr;
		return node->parent->subtrees[parent_pos - 1];
	}

	void flow_right(Node* left, Node* right)
	{
		string splitting = right->parent->words[right->parent_pos - 1];
		//cout << "flow right" << endl;
		left->parent->words[left->parent_pos] = left->words[left->cnt - 1];
		Node* temp = left->subtrees[left->cnt];
		left->cnt--;

		right->insert_word(splitting);
		right->subtrees[0] = temp;
		if (temp != nullptr) {
			temp->parent = right; temp->parent_pos = 0;
		}
		//right->cnt++;
	}

	void flow_left(Node* left, Node* right)
	{
		//left->print();
		//right->print();
		string splitting = left->parent->words[left->parent_pos];

		Node* temp = right->subtrees[0];
		string w = right->delete_first();

		left->insert_word(splitting);
		left->subtrees[left->cnt] = temp;
		if (temp != nullptr) {
			temp->parent = left;
			temp->parent_pos = left->cnt;
		}
		left->parent->words[left->parent_pos] = w;
	}

	Node* split(Node* parent, Node* left, Node* right)
	{
		int pos = parent->find_child(left);
		vector<string> arr;
		vector<Node*> edges;
		int k = 0;
		int j = 0;
		for (int i = 0; i < left->cnt;i++) arr.push_back(left->words[i]);
		for (int i = 0;i <= left->cnt;i++) edges.push_back(left->subtrees[i]);
		
		arr.push_back(parent->words[pos]);
		
		for (int i = 0; i < right->cnt;i++) arr.push_back(right->words[i]);
		for (int i = 0;i <= right->cnt;i++) edges.push_back(right->subtrees[i]);
		j = arr.size();

		cout << j << endl;

		k = edges.size();
		int first = (2 * m - 2) / 3;
		int second = (2 * m - 1) / 3;
		int third = 2 * m / 3;
		//cout << first << " " << second << " " << third << endl;
		int min_num = (2 * m + 1) / 3 - 1;

		for (int i = 0; i < j;i++)
		{
			cout << arr[i] << endl;
			cout << arr[i].size() << endl;
		}
		cout << endl;

		Node* first_node = new Node(min_num, m-1, left->is_leaf);
		for (int i = 0; i < first;i++) first_node->insert_word(arr[i]);
		for (int i = 0;i <= first;i++) first_node->subtrees[i] = edges[i];

		parent->words[pos] = arr[first];
		cout << arr[first] << endl;

		Node* second_node = new Node(min_num, m-1, left->is_leaf);
		for (int i = first + 1;i < first + second + 1;i++) second_node->insert_word(arr[i]);
		for (int i = first + 1;i <= first + second + 1;i++) second_node->subtrees[i - (first + 1)] = edges[i];

		delete parent->subtrees[pos]; parent->subtrees[pos] = nullptr;
		delete parent->subtrees[pos + 1]; parent->subtrees[pos + 1] = nullptr;

		parent->insert_word(arr[first + second + 1]);

		cout << arr[first + second + 1] << endl;


		Node* third_node = new Node(min_num, m-1 , left->is_leaf);
		for (int i = first + second + 2;i < first + second + third + 2;i++) third_node->insert_word(arr[i]);
		for (int i = first + second + 2;i <= first + second + third + 2;i++)third_node->subtrees[i - (first + second + 2)] = edges[i];
	
		first_node->print();

		first_node->fix();
		second_node->fix();
		third_node->fix();

		parent->subtrees[pos] = first_node; first_node->parent = parent; first_node->parent_pos = pos;
		parent->subtrees[pos + 1] = second_node; second_node->parent = parent; second_node->parent_pos = pos + 1;
		parent->subtrees[pos + 2] = third_node; third_node->parent = parent; third_node->parent_pos = pos + 2;

		return parent;
	}

	void split_root()
	{
		//cout << "split root" << endl;
		int spliting_index = (root->capacity) / 2;
		int x = (2 * m + 1) / 3 - 1;
		Node* left = new Node(x, m-1, root->is_leaf);
		for (int i = 0; i < spliting_index;i++)
		{
			left->insert_word(root->words[i]);
		}
		for (int i = 0; i <= spliting_index;i++)
		{
			left->subtrees[i] = root->subtrees[i];
		}

		Node* right = new Node(x, m-1, root->is_leaf);
		for (int i = spliting_index + 1; i < root->cnt;i++)
		{
			right->insert_word(root->words[i]);
		}
		for (int i = spliting_index + 1;i <= root->cnt;i++)
		{
			right->subtrees[i - (spliting_index + 1)] = root->subtrees[i];
		}

		Node* new_root = new Node(2, 2 * ((2 * m - 2) / 3));
		new_root->insert_word(root->words[spliting_index]);
		new_root->subtrees[0] = left;
		new_root->subtrees[1] = right;

		left->fix();
		right->fix();

		left->parent = new_root; left->parent_pos = 0;
		right->parent = new_root; right->parent_pos = 1;

		delete root;

		root = new_root;
	}

	bool insert(string word)
	{
		if (root == nullptr)
		{
			root = new Node(2, 2 * ((2 * m - 2) / 3), true);
			root->insert_word(word);
			return true;
		}

		Node* prev = nullptr;
		Node* curr = root;
		int pos = -1;
		while (curr)
		{
			pos = curr->first_smaller_or_eq(word);

			if (pos == -1 || curr->words[pos] < word)
			{
				prev = curr;
				curr = curr->subtrees[pos + 1];
			}
			else
				return false;
		}

		curr = prev;
		curr->insert_word(word);
		while (1)
		{
			if (curr->over_capacity())
			{

				if (curr == root)
				{
					split_root();
					break;
				}

				Node* rbrother = get_right_brother(curr);
				if (rbrother != nullptr)
				{
					if (rbrother->cnt < rbrother->capacity)
					{
						flow_right(curr, rbrother);
					}
					else
					{
						Node* lbrother = get_left_brother(curr);
						if (lbrother != nullptr && lbrother->cnt < lbrother->capacity)
						{
							
							flow_left(lbrother, curr);
						}
						else
						{
							curr = split(curr->parent, curr, rbrother);
						}
					}
				}
				else
				{
					Node* lbrother = get_left_brother(curr);
					if (lbrother != nullptr && lbrother->cnt < lbrother->capacity)
					{
						
						flow_left(lbrother, curr);
					}
					else
					{
						//cout << "splitting" << endl;
						curr = split(curr->parent, lbrother, curr);
					}
				}

				//curr = curr->parent;
			}
			else
			{
				return true;
			}
		}


		return true;
	}

	Node* get_successor(Node* node, int pos)
	{
		Node* curr = node->subtrees[pos+1];
		if (curr == nullptr) return node;
		while (1)
		{
			if (curr->subtrees[0] == nullptr) return curr;
			curr = curr->subtrees[0];
		}

		return nullptr;
	}

	Node* merge(Node* left, Node* mid, Node* right)
	{
		if (left == nullptr || right == nullptr)
		{
			if (mid->parent == root)
			{
				Node* new_root = new Node(2, 2 * ((2 * m - 2) / 3));
				vector<string> arr;
				vector<Node*> edges;

				new_root->insert_word(mid->parent->words[0]);

				int k = 0;

				if(left!=nullptr) for (int i = 0; i < left->cnt;i++) new_root->insert_word(left->words[i]);
				if (left != nullptr) for (int i = 0; i <= left->cnt;i++) new_root->subtrees[k++] = left->subtrees[i];

				for (int i = 0;i < mid->cnt;i++) new_root->insert_word(mid->words[i]);
				for (int i = 0; i <= mid->cnt;i++) new_root->subtrees[k++] = mid->subtrees[i];

				if (right != nullptr) for (int i = 0; i < right->cnt;i++) new_root->insert_word(right->words[i]);
				if (right != nullptr) for (int i = 0; i <= right->cnt;i++) new_root->subtrees[k++] = right->subtrees[i];
				
				new_root->fix();
				
				delete left;
				delete right;

				root = new_root;
				return new_root;
			}
			else
			{
				// m = 3

			}
		}
		else
		{
			vector<string> arr;
			vector<Node*> edges;
			string split1 = left->parent->words[left->parent_pos];
			string split2 = mid->parent->words[mid->parent_pos];

			for (int i = 0; i < left->cnt;i++) arr.push_back(left->words[i]);
			for (int i = 0; i <= left->cnt;i++) edges.push_back(left->subtrees[i]);

			arr.push_back(split1);

			for (int i = 0;i < mid->cnt;i++) arr.push_back(mid->words[i]);
			for (int i = 0; i <= mid->cnt;i++) edges.push_back(mid->subtrees[i]);

			arr.push_back(split2);

			for (int i = 0;i < right->cnt;i++) arr.push_back(right->words[i]);
			for (int i = 0; i <= right->cnt;i++) edges.push_back(right->subtrees[i]);

			Node* new_left = new Node((2 * m + 1) / 3 - 1, m-1);
			Node* new_right = new Node((2 * m + 1) / 3 - 1, m - 1);

			int mid_index = (arr.size()) / 2;

			for (int i = 0; i < mid_index;i++) new_left->insert_word(arr[i]);
			for (int i = 0; i <= mid_index;i++) new_left->subtrees[i] = edges[i];
			for (int i = mid_index + 1;i < arr.size();i++) new_right->insert_word(arr[i]);
			for (int i = mid_index + 1;i < arr.size();i++) new_right->subtrees[i - (mid_index + 1)] = edges[i];

			mid->parent->words[mid->parent_pos] = arr[mid_index];

			mid->parent->subtrees[mid->parent_pos] = new_left;
			new_left->fix();

			mid->parent->subtrees[right->parent_pos] = new_right;
			new_right->fix();

			mid->parent->delete_at_position(left->parent_pos);
			
			Node* parent = mid->parent;
			parent->fix();
			cout << "mid parent ";parent->print();

			delete left;
			delete mid;
			delete right;

			return parent;
		}

		return nullptr;
	}

	bool delete_word(string word)
	{
		if(root == nullptr) return false;

		Node* prev = nullptr;
		Node* curr = root;
		int pos = -1;
		while (curr)
		{
			pos = curr->first_smaller_or_eq(word);
			if (pos== -1 || curr->words[pos] < word)
			{
				prev = curr;
				curr = curr->subtrees[pos + 1];
			}
			else
			{
				break;
			}
		}

		if (curr == nullptr)
			return false;

		Node* succ = get_successor(curr, pos);

		if (curr != succ)
		{
			string temp = curr->words[pos];
			curr->words[pos] = succ->words[0];
			succ->words[0] = temp;
			curr = succ;
			curr->delete_first();
		}
		else
		{
			curr->delete_at_position(pos);
		}
		while (1) 
		{
			curr->print();
			if (curr->bad())
			{
				Node* rbrother = get_right_brother(curr);
				if (rbrother != nullptr && rbrother->ok_for_flow())
				{
					flow_left(curr, rbrother);
					break;
				}
				
				Node* lbrother = get_left_brother(curr);
				if(lbrother != nullptr && lbrother->ok_for_flow())
				{
					flow_right(lbrother, curr);
					break;
				}

				Node* rbrother2 = get_right_brother(get_right_brother(curr));
				if (rbrother2 != nullptr && rbrother2->ok_for_flow())
				{
					// da li je bitan redosled
					flow_left(curr, rbrother);
					flow_left(rbrother, rbrother2);
					break;
				}

				Node* lbrother2 = get_left_brother(get_left_brother(curr));
				if (lbrother2 != nullptr && lbrother2->ok_for_flow())
				{
					// da li je bitan redosled
					flow_right(lbrother, curr);
					flow_right(lbrother2, lbrother);
					break;
				}

				if (rbrother) cout << "rbrother" << endl;
				if (rbrother2) cout << "rbrother2" << endl;
				if (lbrother) cout << "lbrother" << endl;
				if (lbrother2) cout << "lbrother2" << endl;



				if (rbrother)
				{
					if (lbrother)
					{
						curr = merge(lbrother, curr, rbrother);
					}
					else
					{
						cout << "ovo kurac" << endl;
						curr = merge(curr, rbrother, rbrother2);
					}
				}
				else if (lbrother)
				{
					curr = merge(lbrother2, lbrother, curr);
				}
				else
				{
					//da li ovo uopste moze da se desi 
					return true;
				}


			}
			else
			{
				break;
			}
		}

		return true;
	}

	bool search(string word)
	{
		Node* curr = root;
		while (curr)
		{
			int pos = curr->first_smaller_or_eq(word);

			if (pos == -1 || curr->words[pos] < word)
				curr = curr->subtrees[pos + 1];
			else
				return true;
		}

		return false;
	}

	bool check_tree()
	{
		Node* curr = root;
		queue<Node*> q;
		q.push(curr);
		while (!q.empty())
		{
			curr = q.front(); q.pop();
			if (curr == nullptr) continue;
			for (int i = 0;i <= curr->cnt;i++)
			{
				if (curr->subtrees[i] != nullptr && curr != curr->subtrees[i]->parent)
				{
					return false;
				}
				q.push(curr->subtrees[i]);
			}
		}

		return true;
	}

	int number_of_smaller_words(string word)
	{
		Node* curr = root;
		int count = 0;
		while (curr)
		{
			int pos = curr->first_smaller_or_eq(word);
			if (pos == -1 || curr->words[pos] < word)
			{
				count = (pos + 1) + curr->subtree_size[pos + 1];
				curr = curr->subtrees[pos + 1];
			}
			else
				break;    
		}
		return count;
	}

	friend ostream& operator << (ostream& os, const BTree& b)
	{
		Node* curr = b.root;
		queue<Node*> q;
		q.push(curr);
		while (!q.empty())
		{
			curr = q.front(); q.pop();
			if (curr == nullptr) continue;
			curr->print();
			for (int i = 0; i <= curr->cnt;i++)
			{
				if(curr->subtrees[i])
					q.push(curr->subtrees[i]);
			}
		}
		return os;
	}

	~BTree()
	{

	}
};

void print_menu()
{
	cout << endl;
	cout << "1.Kreirajte stablo"<< endl;
	cout << "2.Pretrazite odredjenu rec" << endl;
	cout << "3.Ubacite novu rec" << endl;
	cout << "4.Ispis stabla" << endl;
	cout << "5.Obrisite odredjenu rec" << endl;
	cout << "6.Broj stringova veci od zadatog" << endl;
	cout << "7.Brisanje recnika(stabla)" << endl;
	cout << "0.Kraj programa" << endl;
}

int check_btree(BTree* btree)
{
	if (btree == nullptr)
	{
		cout << "Morate prvo napraviti stablo." << endl;
		return -1;
	}
}

int main()
{
	BTree* btree = nullptr;
	string w;
	while (true)
	{
		print_menu();
		int choice;
		cout << "Unesite izbor: ";
		cin >> choice;
		getchar();
		if (choice == 1)
		{
			if (btree != nullptr)
			{
				cout << "Stablo je vec napravljeno, morate ga prvo obrisati" << endl;
				continue;
			}
			int m;
			cout << "Unesite red stabla: ";
			cin >> m;
			btree = new BTree(m);
			cout << "Unesti koliko reci zelite da unesete: ";
			int n;
			cin >> n;
			getchar();
			for (int i = 0; i < n;i++)
			{
				cout << "Unesite rec: ";
				string w;
				getline(cin, w);
				btree->insert(w);
				if (btree->check_tree()) cout << "DOBAR" << endl;
				else cout << "LOS" << endl;
			}
		}
		else if (choice == 2)
		{
			if (check_btree(btree) == -1) continue;

			cout << "Unesite rec koju pretrazujete: ";
			getline(cin, w);
			bool found = btree->search(w);
			if (found)
			{
				cout << "Rec se nalazi u stablu." << endl;
			}
			else
			{
				cout << "Rec se ne nalazi u stablu." << endl;
			}
		}
		else if (choice == 3)
		{
			if (check_btree(btree) == -1) continue;

			cout << "Unesite rec koju zelite da ubacite: ";
			getline(cin, w);
			bool insertion = btree->insert(w);
			if (insertion) cout << "Rec je uspesno ubacena!" << endl;
			else cout << "Rec vec postoji u stablu." << endl;
		}
		else if (choice == 4)
		{
			if (check_btree(btree) == -1) continue;

			cout << "Stampanje stabla" << endl;
			cout << *btree << endl;
		}
		else if (choice == 5)
		{
			if (check_btree(btree) == -1) continue;

			cout << "Unesite rec koju zelite da obrisete: ";
			getline(cin, w);
			bool deletion = btree->delete_word(w);
			if (!deletion) cout << "Rec nije ni postojala" << endl;
			else cout << "Rec je uspesno obrisana" << endl;
		}
		else if (choice == 6)
		{
			if (check_btree(btree) == -1) continue;

			cout << "Unesite rec: ";
			getline(cin, w);
			int smaller_words = btree->number_of_smaller_words(w);
			cout << "Broj reci manjih od " << w << " je " << smaller_words << endl;
		}
		else if (choice == 7)
		{
			if (check_btree(btree) == -1) continue;

			cout << "Izabrali ste brisanje stabla" << endl;
			delete btree;
			btree = nullptr;
			cout << "Stablo uspesno obrisano" << endl;
		}
		else if (choice == 0)
		{
			cout << "Izabrali ste kraj programa, stablo ce biti obrisano." << endl;
			delete btree;
			btree = nullptr;
			break;
		}
		else
		{
			cout << "Pogresan unos!" << endl;
		}
	}

	return 0;
}