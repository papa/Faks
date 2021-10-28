#include <iostream>
#include <string>
#include <vector>
#include <algorithm>

using namespace std;

void print_menu()
{
	cout << endl;
	cout << "1.Pocnite igru" << endl;
	cout << "0.Zavrsite igru" << endl;
}

int query(double number)
{
	cout << "Broj " << number << "?" << endl;
	cout << "Unesite da li je trazeni broj manji, veci ili u opsegu (0 - manji, 1 - veci, 2 - u opsegu) : ";
	int answer; cin >> answer;
	return answer;
}

//implementacija pretrage iz ASP knjige
int fibbonaci_search(double low, double high, double decimal)
{
	int n;
	double offset;
	if (decimal != 0)
	{
		double segment = 2 * decimal;
		double num_of_segs = (high - low) / segment;
		int num_of_segs_int = int(num_of_segs);

		if (num_of_segs == num_of_segs_int) n = num_of_segs_int;
		else n = num_of_segs_int + 1;

		offset = segment;
	}
	else
	{
		n = (high - low) * 1000 + 1; 
		//decimal = 0.0005;
		offset = 0.001;
	}
	int fbk2 = 0;
	int fbk1 = 1;
	int fbk = fbk2 + fbk1;

	int moves = 0;

	while (fbk < n)
	{
		fbk2 = fbk1;
		fbk1 = fbk;
		fbk = fbk1 + fbk2;
	}

	fbk = fbk1;
	fbk1 = fbk2;
	fbk2 = fbk - fbk1;

	//cout << "fbk " << fbk << endl;
	//cout << "fbk1 " << fbk1 << endl;
	//cout << "fbk2 " << fbk2 << endl;
 	//cout << "n " << n << endl;
	cout << "Upiti :" << endl;
	int gone_left = -1;
	int ans;
	while (true)
	{
		moves++;
		int i = fbk - 1;
		//cout << "i " << i << endl;
		double num_check = low + decimal + i * offset;
		ans = query(num_check);
		if (ans == 0)
		{
			if (gone_left == -1)
				gone_left = 1;

			if (fbk2 == 0)
				break;
			
			fbk = fbk - fbk2;
			int temp = fbk1;
			fbk1 = fbk2;
			fbk2 = temp - fbk2;
		}
		else if (ans == 1)
		{
			if (fbk1 == 1)
				break;

			fbk = fbk + fbk2;
			fbk1 = fbk1 - fbk2;
			fbk2 = fbk2 - fbk1;
		}
		else return moves;
	}

	moves++;
	if (gone_left == -1) 
	{
		ans = query(low + decimal + (n-1) * offset);
		if (ans == 2) return moves;
	}
	else
	{
		ans = query(low + decimal);
		if (ans == 2) return moves;
	}

	return -1;
}

int main()
{
	while (true)
	{
		print_menu();
		int choice;
		cout << "Unesite izbor: ";
		cin >> choice;
		if (choice == 1)
		{
			double low, high;
			cout << "Unesite donju granicu : "; cin >> low;
			cout << "Unesti gornju granicu : ";cin >> high;
			double decimal;
			cout << "Unesite tacnost za decimalu: "; cin >> decimal;
			cout << "Zamisljeni broj: ";
			double target_number; cin >> target_number;
			int search_moves = fibbonaci_search(low, high, decimal);
			if (search_moves != -1) cout << "Potrebno je bilo " << search_moves << " poteza!" << endl;
		}
		else if (choice == 0)
		{
			cout << "Igra se prekida" << endl;
			break;
		}
		else
		{
			cout << "Pogresan unos!" << endl;
		}
	}

	return 0;
}
