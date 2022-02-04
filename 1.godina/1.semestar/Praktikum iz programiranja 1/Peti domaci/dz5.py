'''
csv fajl podaci razdvojeni ;
svaki film ima
    -id
    -title
    -genre (lista odvojena |)
    -datum izdavanja
    -director
    -revenue
Izuzeci
    - greska rada sa datotekom -> DAT_GRESKA
    - greska pri konverziji podataka -> KONV_GRESKA
    - nekorektan sadrzaj polja -> POLJE_GRESKA
'''

def formatDate(date):
    dateList = date.split(".")
    dateTuple = (int(dateList[0]), int(dateList[1]), int(dateList[2]))
    return dateTuple

def okDate(date):
    mm = [0]*12
    mm[0] = mm[2] = mm[4] = mm[6] = mm[7] = mm[9] = mm[11] = 31
    mm[3] = mm[5] = mm[8] = mm[10] = 30
    if date[2] < 1 : return False
    if date[1] < 1 or date[1] > 12 : return False
    if date[1]==2:
        if date[2]%400==0 or (date[2]%4==0 and date[2]%100!=0): return 1<=date[0]<=29
        else: return 1<=date[0]<=28
    else:
        return 1<=date[0]<=mm[date[1]-1]

def checkDateString(date):
    assert (len(date)==10 and date[2]=="." and date[5]==".")

def checkDate(datei):
    checkDateString(datei)
    dateFormated = formatDate(datei)
    assert okDate(dateFormated)
    return dateFormated

def checkRevenueString(revenue):
    assert (len(revenue) >  1 and revenue[0]=="$")

def beforeDate(date1,date2):
    if date1[2] < date2[2]: return True
    elif date1[2] > date2[2] :  return False
    else:
        if date1[1] < date2[1]:  return True
        elif date1[1] > date2[1]: return False
        else: return date1[0] <= date2[0]

def dateBetween(issued,dateB,dateE):
    #implementiraj
    return beforeDate(dateB,issued) and beforeDate(issued,dateE)

def readMovies(file,dateB,dateE,genreF):
    dirMov = {}
    header = False
    for line in file:
        if not header:
            header = True
            continue
        line = line.split(";")
        id = int(line[0])
        movieTitle = line[1]
        movieGenre = line[2].split("|")
        issued = line[3]; issued = checkDate(issued)
        director = line[4]
        revenue = line[5]
        checkRevenueString(revenue)
        revenue = float(revenue[1:])

        if (not dateBetween(issued,dateB,dateE)) or (genreF not in movieGenre): continue
        key = director
        if key not in dirMov:
            dirMov[key] = [(revenue,movieTitle)]
        else:
            dirMov[key].append((revenue,movieTitle))

    return dirMov

def foo(dic):
    sol = []
    for key, value in dic.items():
        sol.append((key, value[0][1], value[0][0], value[len(value) - 1][1], value[len(value) - 1][0]))
    sol.sort()
    return sol

def printSolution(file, sol):
    file.write("Director;Movie Title Min Revenue;Movie Title Max Revenue\n")
    for x in sol:
        text = "{};{} : {};{} : {}\n".format(x[0],x[1],str(x[2]),x[3],str(x[4]))
        file.write(text)
    return

inputFile = None
outputFile = None
try:
    inputFileName, outputFileName = input().split()
    dateBegin = input(); dateBegin = checkDate(dateBegin)
    dateEnd = input(); dateEnd = checkDate(dateEnd)
    genreFind = input()
    inputFile = open(inputFileName,"r")
    directorsMovies = readMovies(inputFile,dateBegin,dateEnd,genreFind)
    for key in directorsMovies.keys():
        directorsMovies[key].sort()
    solution = foo(directorsMovies)
    outputFile = open(outputFileName,"w")
    printSolution(outputFile, solution)
except IOError:
    print("DAT_GRESKA")
except ValueError:
    print("KONV_GRESKA")
except AssertionError:
    print("POLJE_GRESKA")
finally:
    if inputFile!=None: inputFile.close()
    if outputFile!=None: outputFile.close()

'''
Test primeri 
------
ulaz3.csv izlaz3_1.csv
01.01.2000
18.12.2020
Drama 

-------

pp1_movies_2020.csv izlaz3_2.csv
01.01.2000
18.12.2020
Drama

------

nepostoji.csv izlaz.csv
01.01.2000
18.12.2020
Drama
'''