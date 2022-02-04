#include <stdio.h>

using namespace std;

void foo(bool x,char c)
{
    //printf("%d %c",x,c);
    if(!x) printf("%c ",c);
}

void test(int x)
{
    int x00,x11,x22,x33;
    bool x0,x1,x2,x3;
    x0 = x1 = x2 = x3 = false;
    x00 = x & 1;
    x11 = (x >> 1) & 1;
    x22 = (x >> 2) & 1;
    x33 = (x >> 3) & 1;
    printf("%d %d %d %d\n",x33,x22,x11,x00);
    if(x00) x0 = true;
    if(x11) x1 = true;
    if(x22) x2 = true;
    if(x33) x3 = true;
    bool a,b,c,d,e,f,g;
    //a = (!x1)&(!x3)&(!x0 | !x2)&(x0 | x2);
    a = (!x0 & !x1 & x2 &!x3) | (x0 & !x1 & !x2 & !x3);
    //b = (x2 | x1)&(x2 | x3)&(!x0 | !x1 | x3)&(x0 | x1 | x3);
    b = (x2 & x3) | (x1 & x3) | (!x0 & x1 & x2) | (x0 & !x1 & x2);
    //c = (x3 | !x2)&(x3 | !x0)&(x1 | x2);
    c = (x2 & x3) | (x1 & x3) | (!x0 & x1 & !x2);
    //d = (x0 | x2)&(x0 | !x1)&(!x1 | x2)&(!x3)&(!x0 | x1 | !x2);
    d = (x0 & x1 & x2 & !x3) | (x0 & !x1 & !x2 & !x3) | (!x0 & !x1 & x2 & !x3);
    //e = (x0 | !x1 | x3)&(x0 | x1 | x2);
    e = (x0) | (x1 & x3) | (!x1 & x2);
    //f = (x1 | x2 | !x3)&(x1 | !x2 | x3)&(x0 | x1 | x3)&(x0 | !x2 | x3);
    f = (x0 & !x2 & !x3) | (x2 & x3) | (x0 & x1) | (x1 & !x2);
    //g = (!x3)&(x1 | !x2)&(!x1 | x2)&(x0 | !x1);
    g = (x0 & x1 & x2 & !x3) | (!x1 & !x2 & !x3);
    foo(a,'a');
    foo(b,'b');
    foo(c,'c');
    foo(d,'d');
    foo(e,'e');
    foo(f,'f');
    foo(g,'g');
    printf("\n");
}

int main()
{
    for(int i =0;i<16;i++)
    {
        test(i);
    }

    return 0;
}
