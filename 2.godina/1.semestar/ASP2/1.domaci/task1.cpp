#include <iostream>
#include <string>
#include <vector>
#include <stack>
#include <tuple>

using namespace std;

struct list_node
{
	string word;
	struct list_node* next = nullptr;

	list_node(string w)
	{
		word = w;
	}
};

class Node
{
private:
	Node* left = nullptr, * right = nullptr, * parent = nullptr;
	string word;
	vector<string> translations;
	list_node* first = nullptr, * last = nullptr;
	friend class BST;
public:
	void add_to_linked_list(string w)
	{
		list_node* new_node = new list_node(w);
		if (first == nullptr) 
			first = new_node;
		else
			last->next = new_node;
		last = new_node;
	}

	Node(string word, const vector<string>& translations)
	{
		this->word = word;
		for (int i = 0; i < translations.size();i++)
		{
			//this->translations.push_back(translations[i]);
			add_to_linked_list(translations[i]);
		}
	}
	Node(string word, string translated_word)
	{
		this->word = word;
		//translations.push_back(translated_word);
		add_to_linked_list(translated_word);
	}
	int word_comparison(string word)
	{
		if (this->word == word) return 0;
		else if (word < this->word) return -1;
		else return 1;
	}
	void add_translation(string translation)
	{
		//translations.push_back(translation);
		add_to_linked_list(translation);
	}

	void print_info()
	{
		cout << "Original word: " << word << endl;
		cout << "Translations: " << endl;
		//for (int i = 0; i < translations.size();i++)
		//	cout << translations[i] << " ";
		//cout << endl;
		list_node* curr = first;
		while (curr)
		{
			cout << curr->word;
			if (curr != last) cout << ", ";
			curr = curr->next;
		}
		cout << endl;
	}

	//implement destructor
	~Node()
	{
		list_node* old;
		while (first)
		{
			old = first;
			first = first->next;
			delete old;
		}
	}
};

class BST
{
private:
	Node* root = nullptr;
public:
	BST(const vector<string>& words, const vector<string>& translations)
	{
		for (int i = 0; i < translations.size(); i++)
			insert_translated_word(words[i], translations[i]);
	}

	Node* search_for_word(string search_word)
	{
		if (root == nullptr) return nullptr;

		Node* cur = root;
		while (cur)
		{
			int comparison = cur->word_comparison(search_word);
			if (comparison == 0) return cur;
			else if (comparison == -1) cur = cur->left;
			else cur = cur->right;
		}
		return nullptr;
	}

	void insert_translated_word(string word, string translated_word)
	{
		Node* parent = nullptr;
		Node* temp = root;

		while (temp)
		{
			parent = temp;
			if (temp->word == word)
			{
				temp->add_translation(translated_word);
				return;
			}
			else if (word < temp->word)
			{
				temp = temp->left;
			}
			else
			{
				temp = temp->right;
			}
		}

		Node* cur = new Node(word, translated_word);
		if (parent == nullptr)
		{
			root = cur;
		}
		else
		{
			if (word < parent->word) parent->left = cur;
			else parent->right = cur;
		}
	}

	//implementacija iz ASP knjige
	int delete_word(string word)
	{
		if (root == nullptr) return -1;

		Node* temp = root;
		Node* parent = nullptr;

		while (temp && word != temp->word)
		{
			parent = temp;
			if (word < temp->word)
			{
				temp = temp->left;
			}
			else
			{
				temp = temp->right;
			}
		}

		if (temp == nullptr)
		{
			return -1;
		}

		Node* replace_node;
		if (temp->left == nullptr)
		{
			replace_node = temp->right;
		}
		else if (temp->right == nullptr)
		{
			replace_node = temp->left;
		}
		else
		{
			Node* f = temp;
			replace_node = temp->right;
			Node* s = replace_node->left;
			while (s)
			{
				f = replace_node;
				replace_node = s;
				s = replace_node->left;
			}
			if (f != temp)
			{
				f->left = replace_node->right;
				replace_node->right = temp->right;
			}
			replace_node->left = temp->left;
		}

		if (parent == nullptr)
			root = replace_node;
		else if (temp == parent->left)
			parent->left = replace_node;
		else parent->right = replace_node;

		delete temp;
	}

	void print_BST()
	{
		if (root == nullptr) return;

		stack<tuple<string, Node*, bool> > st;
		st.push(make_tuple("", root, false));

		while(!st.empty())
		{
			Node* node;
			string prefix;
			bool is_left;
			tie(prefix, node, is_left) = st.top();
			st.pop();

			if (node == nullptr) continue;

			cout << prefix;

			cout << (is_left ? "|== " : "^== ");

			cout << node->word<< endl;

			st.push(make_tuple(prefix + (is_left ? "|   " : "    "), node->left, true));
			st.push(make_tuple(prefix + (is_left ? "|   " : "    "), node->right, false));
		}
	}

	void words_with_prefix(string prefix)
	{
		if (root == nullptr) return;

		stack<Node*> st;
		Node* curr;

		st.push(root);

		while (!st.empty())
		{
			curr = st.top();
			st.pop();

			if (prefix.size() > curr->word.size())
			{
				if (prefix > curr->word)
				{
					if (curr->right) st.push(curr->right);
				}
				else
				{
					if (curr->left) st.push(curr->left);
				}
			}
			else
			{
				string sub = curr->word.substr(0, prefix.size());
				if (sub == prefix)
				{
					curr->print_info();
					if (curr->left) st.push(curr->left);
					if (curr->right) st.push(curr->right);
				}
				else if (prefix < sub)
				{
					if (curr->left) st.push(curr->left);
				}
				else
				{
					if (curr->right) st.push(curr->right);
				}
			}
		}
	}

	~BST()
	{
		if (root == nullptr) return;

		stack<Node*> st;
		Node* curr;

		st.push(root);

		while (!st.empty())
		{
			curr = st.top();
			st.pop();
			if (curr->right) st.push(curr->right);
			if (curr->left) st.push(curr->left);

			delete curr;
		}
	}
};

int safe_cin(int low, int high)
{
	int x = low - 1;
	while (x < low || x > high)
	{
		cin >> x;
		if (x < low || x > high) cout << "Unesite ponovo" << endl;
	}
	return x;
}

int check_bst(BST* bst)
{
	if (!bst) { cout << "Morate prvo napraviti recnik(stablo)" << endl; return -1; }
	return 1;
}

void print_menu()
{
	cout << endl;
	cout << "1.Kreirajte recnik(stablo)" << endl;
	cout << "2.Pretrazite odredjenu rec" << endl;
	cout << "3.Ubacite novi prevod" << endl;
	cout << "4.Ispis sadrzaja stabla" << endl;
	cout << "5.Obrisite odredjenu rec" << endl;
	cout << "6.Ispis reci i prevoda sa zadatim prefiksom" << endl;
	cout << "7.Brisanje recnika(stabla)" << endl;
	cout << "0.Kraj programa" << endl;
}

int main()
{	
	//freopen("input.txt","r",stdin);
    //freopen("output.txt","w",stdout);
	BST* bst = nullptr;
	while (true)
	{
		print_menu();
		int choice;
		cout << "Unesite izbor: ";
		cin >> choice;
		getchar();
		if (choice == 1)
		{
			if (bst)
			{
				cout << "Recnik je vec napravljen, morate ga prvo obrisati." << endl;
				continue;
			}
			cout << "Unesti koliko reci zelite da prevedete : ";
			int n;cin >> n;
			getchar();
			vector<string> words, translations;
			for (int i = 0; i < n;i++)
			{
				cout << "Unesite rec koja se prevodi: ";
				string w; 
				getline(cin, w);
				words.push_back(w);
				cout << "Unesite prevod: ";
				string tw;
				getline(cin, tw);
				translations.push_back(tw);
			}
			bst = new BST(words, translations);
		}
		else if (choice == 2)
		{
			if (check_bst(bst) == -1) continue;

			cout << "Unesite rec koju pretrazujete: ";
			string w;
			getline(cin, w);
			Node* cur = bst->search_for_word(w);
			if (cur)
			{
				cur->print_info();
			}
			else
			{
				cout << "Rec nije pronadjena u recniku" << endl;
			}
		}
		else if (choice == 3)
		{
			if (check_bst(bst) == -1) continue;

			cout << "Unesite rec koju zelite da prevedete: ";
			string w;
			getline(cin, w);
			cout << "Unesite prevod: ";
			string tw; 
			getline(cin, tw);
			bst->insert_translated_word(w, tw);
			cout << "Prevod je uspesno ubacen!" << endl;
		}
		else if (choice == 4)
		{
			if (check_bst(bst) == -1) continue;

			cout << "Stampanje recnika(stabla)" << endl;
			//bst->print_bst();
			bst->print_BST();
		}
		else if (choice == 5)
		{
			if (check_bst(bst) == -1) continue;

			cout << "Unesite rec koju zelite da obrisete: ";
			string w; 
			getline(cin, w);
			int deletion = bst->delete_word(w);
			if (deletion == -1) cout << "Rec nije ni postojala" << endl;
			else cout << "Rec je uspesno obrisana" << endl;
		}
		else if (choice == 6)
		{
			if (check_bst(bst) == -1) continue;

			cout << "Izabrali ste ispis reci sa zadatim prefiksom" << endl;
			cout << "Unesite zeljeni prefiks: ";
			string pref;
			getline(cin, pref);
			cout << "Reci sa prefiksom " << pref << endl;
			bst->words_with_prefix(pref);
		}
		else if (choice == 7)
		{
			if (check_bst(bst) == -1) continue;

			cout << "Izabrali ste brisanje recnika(stabla)" << endl;
			delete bst;
			bst = nullptr;
			cout << "Recnik(stablo) uspesno obrisano" << endl;
		}
		else if (choice == 0)
		{
			cout << "Izabrali ste kraj programa, recnik ce biti obrisan." << endl;
			delete bst;
			bst = nullptr;
			break;
		}
		else
		{
			cout << "Pogresan unos!" << endl;
		}
	}

	return 0;
}
