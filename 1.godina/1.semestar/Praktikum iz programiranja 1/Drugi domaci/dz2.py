a = input()
prazan_string = ""
if a!=prazan_string:
    a = a.split()
    for i in range(len(a)): a[i] = int(a[i])
    l1 = []
    l2 = []
    l3 = []
    for x in a:
        if x%3==0:
            l1.append(x)
        elif x%2==0:
            l2.append(x)
        else:
            l3.append(x)
    res = l1 + l2 + l3
    print(res)
    print("{} {} {}".format(len(l1),len(l2),len(l3)))
else:
    print([])
    print(0,0,0)