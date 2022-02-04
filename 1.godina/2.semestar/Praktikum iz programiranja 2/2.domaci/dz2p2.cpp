#include <bits/stdc++.h>

using namespace std;

typedef long long ll;

#define MAX 1000000

struct selo
{
    int x,y,w;
};

bool cmp(selo s1,selo s2)
{
    return s1.w > s2.w;
}

int main()
{
    ios_base::sync_with_stdio(false);
    cin.tie(0);
    cout.tie(0);
    cerr.tie(0);

    int n,c;
    cin >> n >> c;
    selo s[n];

    for(int i = 0; i < n;i++)
    {
        cin >> s[i].x >> s[i].y >> s[i].w;
    }
    sort(s,s+n,cmp);
    int maksx = -1,maksy = -1,tempx = -1,tempy = -1;
    int res = s[0].w;
    tempx = abs(s[0].x);
    tempy = abs(s[0].y);
    maksx = -1;
    maksy = -1;
    for(int i = 1;i < n;i++)
    {
        if(s[i].w == s[i-1].w)
        {
            tempx = max(abs(s[i].x), tempx);
            tempy = max(abs(s[i].y), tempy);
        }
        else
        {
            maksx = max(maksx,tempx);
            maksy = max(maksy,tempy);
            tempx = abs(s[i].x);
            tempy = abs(s[i].y);
        }
        int zid;
        if(maksx==-1) zid = 0;
        else zid = 4*c*(maksx + maksy);
        zid = zid + s[i].w;
        res = min(res,zid);
    }
    maksx = max(maksx,tempx);
    maksy = max(maksy,tempy);
    int zid;
    if(maksx==-1) zid = 0;
    else zid = 4*c*(maksx + maksy);
    res = min(res,zid);

    cout << res;

    return 0;
}
