#include <bits/stdc++.h>

using namespace std;

int main()
{
    ios_base::sync_with_stdio(false);
    cin.tie(0);
    cout.tie(0);
    cerr.tie(0);

    int t;
    cin >> t;
    stack<pair<int,int> > s;
    while(t--)
    {
        string  op;
        cin >> op;
        if(op == "PUSH")
        {
            int x;
            cin >> x;
            if(s.empty()) s.push(make_pair(x,x));
            else s.push(make_pair(x,min(x,s.top().second)));
        }
        else if(op == "MIN")
        {
            if(s.empty()) cout << "EMPTY\n";
            else cout << s.top().second << "\n";
        }
        else if(op == "POP")
        {
            if(s.empty()) cout << "EMPTY\n";
            else s.pop();
        }
    }

    return 0;
}
