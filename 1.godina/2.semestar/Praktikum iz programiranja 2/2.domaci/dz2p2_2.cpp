#include <bits/stdc++.h>

using namespace std;

typedef long long ll;

int main()
{
    ios_base::sync_with_stdio(false);
    cin.tie(0);
    cout.tie(0);
    cerr.tie(0);

    int n;
    cin >> n;
    vector<int> a(n);
    for(int i = 0;i<n;i++)
        cin >> a[i];

    stack<int> s;
    vector<int> levo(n);
    vector<int> desno(n);

    levo[0] = -1;
    s.push(0);
    for(int i = 1; i < n;i++)
    {
        while(!s.empty() && a[s.top()] <= a[i]) s.pop();
        if(s.empty()) levo[i] = -1;
        else levo[i] = s.top();
        s.push(i);
    }

    while(!s.empty()) s.pop();

    desno[n-1] = n;
    s.push(n-1);
    for(int i = n-2;i >=0;i--)
    {
        while(!s.empty() && a[s.top()] < a[i]) s.pop();
        if(s.empty()) desno[i] = n;
        else desno[i] = s.top();
        s.push(i);
    }

    ll mod = (ll)(1e9) + 7LL;
    map<int,ll> mp;
    for(int i = 0;i < n;i++)
    {
        int lg = levo[i] + 1;
        int dg = desno[i] - 1;
        ll xx = (ll)(i - lg + 1)*(dg - i + 1)%mod;
        mp[a[i]] = (mp[a[i]] + xx)%mod;
    }

    int q;
    cin >> q;
    while(q--)
    {
        ll xx;
        cin >> xx;
        cout << mp[xx] << "\n";
    }

    return 0;
}
