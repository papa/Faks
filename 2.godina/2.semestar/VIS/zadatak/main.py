from random import randrange

def uzmiKuglicu():
    global brKuglica
    global izvucena
    kug = randrange(brKuglica) + 1
    if izvucena[kug - 1]:
        return 0
    izvucena[kug - 1] = True
    return kug

def odradiJednoIzlvacenje():
    global brIzvlacenja
    global trazenaKuglica
    global brIzvucenihKuglica
    i = 0
    while i < brIzvucenihKuglica:
        kuglica = uzmiKuglicu()
        if kuglica != 0:
            if kuglica == trazenaKuglica:
                return 1
            i+=1

    return 0

def restart():
    global izvucena
    for i in range(brKuglica):
        izvucena[i] = False

def odradiIzvlacenja():
    global brojUspeha
    brojUspeha = 0
    for i in range(brIzvlacenja):
        restart()
        brojUspeha+=odradiJednoIzlvacenje()

    print("Broj izvlacenja " + str(brIzvlacenja))
    print("Broj uspeha " + str(brojUspeha))
    print("Relativna frekvencija dogadjaja " + str(float(brojUspeha) / brIzvlacenja))

brSimulacija = 20
brKuglica = 20
izvucena = brKuglica * [False]
brojUspeha = 0
brIzvlacenja = 1000000
trazenaKuglica = 7
brIzvucenihKuglica = 3
for i in range(brSimulacija):
    print("Unesite broj izvlacenja ")
    brIzvlacenja = randrange(10) + 1
    odradiIzvlacenja()
    print("-----------------------------")

