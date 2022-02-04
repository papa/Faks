#include <bits/stdc++.h>

using namespace std;

struct clan
{
    long long vr;
    long long ap;
    int ind;
    bool operator <(clan c) const
    {
        return ap > c.ap;
    }
};

clan c[5005];
int n;
int k;
long long proizvod;
bool zauzeto1[5005];
bool zauzeto2[5005];
bool zauzeto[5005];
long long pomocni[5005];
int indp;
int indn;
int izbacipoz;
int izbacineg;

int nadjineg()
{
    for(int i=k;i<n;i++)
      if(c[i].vr<0)
      {
          indn=c[i].ind;
          return i;
      }
    return -1;
}

int nadjipoz()
{
    for(int i=k;i<n;i++)
      if(c[i].vr>=0)
      {
          indp=c[i].ind;
          return i;
      }

    return -1;
}

int main()
{
    ios_base::sync_with_stdio(false);
    cin.tie(0);
    cout.tie(0);

    cin >> n >> k;

    for(int i=0;i<n;i++)
      {
          cin >> c[i].vr;
          c[i].ap=abs(c[i].vr);
          c[i].ind=i;
          pomocni[i]=c[i].vr;
      }

      sort(c,c+n);

      proizvod=1;

      int zadnjipoz=-1;
      int zadnjineg=-1;
      int brojac=0;

      for(int i=0;i<n;i++)
       zauzeto[i]=zauzeto1[i]=zauzeto2[i]=false;

      for(int i=0;i<k;i++)
        {
            proizvod=proizvod*c[i].vr;
            if(c[i].vr>=0)
            {
                zadnjipoz=i;
                izbacipoz=c[i].ind;
            }
            else
            {
                zadnjineg=i;
                brojac++;
                izbacineg=c[i].ind;
            }
            zauzeto[c[i].ind]=true;
            zauzeto1[c[i].ind]=true;
            zauzeto2[c[i].ind]=true;
        }

    long long ans;

    if(brojac%2==0)
    {
        ans=proizvod;
        for(int i=0;i<n;i++)
            if(zauzeto[i])
                printf("%lld ",pomocni[i]);
    }
    else
    {
        long long prvineg=nadjineg();
        long long prvipoz=nadjipoz();
        long long rez1=proizvod;
        long long rez2=proizvod;

        if(prvipoz>0 && zadnjineg>0)
        {
            rez1=rez1/(c[zadnjineg].vr)*c[prvipoz].vr;
            zauzeto1[indp]=true;
            zauzeto1[izbacineg]=false;
        }


        if(prvineg>0 && zadnjipoz>0)
        {
            rez2=rez2/(c[zadnjipoz].vr)*c[prvineg].vr;
            zauzeto2[indn]=true;
            zauzeto2[izbacipoz]=false;
        }

        ans=max(max(proizvod,rez1),rez2);

        if(ans==proizvod)
        {
            for(int i=0;i<n;i++)
              if(zauzeto[i])
                cout << pomocni[i] << " ";
        }
        else if(ans==rez1)
        {
            for(int i=0;i<n;i++)
                if(zauzeto1[i])
                    cout << pomocni[i] << " ";
        }
        else
        {
            for(int i=0;i<n;i++)
                if(zauzeto2[i])
                    cout << pomocni[i] << " ";
        }
    }

    return 0;
}
