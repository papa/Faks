#include <bits/stdc++.h>

using namespace std;

void set_use()
{
    set<int> s;
    for(int i = 0; i < 10;i++) s.insert(i);
    set<int>::iterator it;
    for(it = s.begin();it!=s.end();it++)
        cout << *it << " ";
    cout << "\n";
    for(int x : s)
        cout << x << " ";
    cout << "\n";
    it = s.find(5);
    if(it!=s.end()) cout << "element je u skupu\n";
    else cout << "element nije u skupu\n";

    s.erase(s.find(5));
    s.erase(5);

    it = s.lower_bound(4);
    if(it != s.end()) cout << *it << "\n";

    it = s.upper_bound(4);
    if(it!=s.end()) cout << *it << "\n";
}

int main()
{
    ios_base::sync_with_stdio(false);
    cin.tie();
    cout.tie();
    cerr.tie(0);

    

    return 0;
}
