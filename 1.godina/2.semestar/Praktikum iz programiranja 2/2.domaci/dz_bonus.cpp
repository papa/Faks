#include <bits/stdc++.h>

using namespace std;

typedef long long ll;

ll sol[1000010];

const ll mod = (ll)1e9 + 7;

void f(ll& x)
{
	if (x >= mod)
		x -= mod;
	if (x < 0)
		x += mod;
}

ll g(ll x)
{
	if (x >= mod)
		x -= mod;
	if (x < 0)
		x += mod;
	return x;
}

ll calc(ll n)
{

	if (n < 1) return 0;

	ll x = ((n + 1) * n) / 2;
	if (x >= mod)
		x %= mod;
	return x;
}

ll t[1000010];

struct stk
{
	ll x;
	ll p;
};

int main()
{
	ios_base::sync_with_stdio(false);
	cin.tie(0);
	cout.tie(0);
	cerr.tie(0);

	for (auto& i : t)
		i = INT32_MAX;

	int n;
	cin >> n;

	vector<ll> a(n);
	vector<pair<ll,ll> > b;

	int cnt = 0;
	for (auto& i : a)
    {
		cin >> i;
		b.push_back({i, cnt++});
	}

	sort(b.begin(), b.end());

	cnt = 0;
	vector<ll> tmp;
	ll last = 0;
	for (auto& i : b)
    {
		if (tmp.empty() || last != i.first)
        {
			tmp.push_back(cnt + 1);
			a[i.second] = ++cnt;
			t[cnt] = i.first;
		}
        else
        {
			a[i.second] = cnt;
		}
		last = i.first;
	}

	a.push_back(INT32_MAX);
	t[0] = 0;

	stack<stk> st;
	st.push({INT32_MAX, 0});
	for (int i = 0; i <= n; i++)
    {
		auto c = st.top();
		while (a[i] > c.x)
        {
			st.pop();
			auto tmp = st.top();
			sol[c.x] += calc(i - tmp.p);
			sol[c.x] -= calc(i - c.p);
			f(sol[c.x]);
			sol[c.x] -= calc(c.p - 1 - tmp.p);
			f(sol[c.x]);
			c = tmp;
		}

		st.push({a[i], i + 1});
	}

	int q;
	cin >> q;

	for (int i = 0; i < q; i++)
    {
		ll x;
		cin >> x;

		int index = lower_bound(t, t + n + 1, x) - t;
		if (t[index] != x)
			cout << "0\n";
		else
			cout << sol[index] << '\n';
	}
}
