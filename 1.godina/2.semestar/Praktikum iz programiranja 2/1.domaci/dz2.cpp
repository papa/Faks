#include <bits/stdc++.h>

using namespace std;

bool ok(int row,int col,int prep,vector<vector<int> > mapa, vector<vector<bool> >& vis,vector<int> por,vector<int> pok)
{
    if(mapa[row][col] == 3) return true;
    int n = mapa.size();
    int m = mapa[0].size();
    vis[row][col] = true;
    for(int i = 0; i < 4;i++)
    {
        int nr= row + por[i];
        int nk= col + pok[i];
        if(nr >= 0 && nr < n && nk>=0 && nk < m && mapa[nr][nk] > 0 && !vis[nr][nk])
        {
            if(mapa[nr][nk] == 2)
            {
                bool b = false;
                if(prep > 0) b = ok(nr,nk,prep-1,mapa,vis,por,pok);
                if(b) return b;
            }
            else
            {
                bool b = ok(nr,nk,prep,mapa,vis,por,pok);
                if(b) return b;
            }

        }
    }
    vis[row][col] = false;
    return false;
}

int main()
{
    ios_base::sync_with_stdio(false);
    cin.tie(0);
    cout.tie(0);
    cerr.tie(0);

    int n,m,prep;
    cin >> n >> m >> prep;
    vector<vector<int> > mapa;
    vector<vector<bool> > vis;
    mapa.resize(n);
    vis.resize(n);
    for(int i = 0;i < n;i++)
    {
        mapa[i].resize(m);
        vis[i].resize(m);
    }
    vector<pair<int,int> > ss;
    for(int i = 0;i < n;i++)
    {
        string str;
        cin >> str;
        for(int j = 0;j < m;j++)
        {
            vis[i][j] = false;
            if(str[j] == '@' || str[j] == '.')
            {
                mapa[i][j] = 1;
                if(str[j] == '@')
                {
                    ss.push_back(make_pair(i,j));
                }
            }
            else if(str[j] == '#')
            {
                mapa[i][j] = 0;
            }
            else if(str[j] == 's')
            {
                mapa[i][j] = 2;
            }
            else
            {
                mapa[i][j] = 3;
            }
        }
    }
    vector<int> por(4),pok(4);
    por[0] = por[1] = 0; por[2] = 1; por[3] = -1;
    pok[0] = 1;pok[1] = -1; pok[2] = pok[3] = 0;

    bool b = false;
    for(int i = 0;i < ss.size() && !b;i++)
    {
        b = ok(ss[i].first,ss[i].second,prep/2,mapa,vis,por,pok);
    }

    if(b) cout << "SUCCESS";
    else cout << "IMPOSSIBLE";

    return 0;
}
