def lower(char):
    return ord("a") <= ord(char) <= ord("z")

def upper(char):
    return ord("A") <=ord(char) <= ord("Z")

def digit(char):
    return ord("0") <= ord(char) <= ord("9")

def correctCharacter(char):
    return upper(char) or lower(char) or digit(char) or (char == "-")

def prepare(text):
    return text.split("-")

def correctInput(text):
    for char in text:
        if correctCharacter(char) == False:
            return False
    return True

def tryNewLongest(ls,ts):
    if len(ts) >= len(ls):
        return ts
    return ls

def findLongest(word):
    temp_str = ""
    longest_str = ""
    for char in word:
        if lower(char):
            temp_str+=char
        else:
            longest_str = tryNewLongest(longest_str,temp_str)
            temp_str = ""

    longest_str = tryNewLongest(longest_str,temp_str)
    return longest_str

def foo(wordlist):
    sq = []
    for word in wordlist:
        sq.append(findLongest(word))
    return sq

def joinSequences(seqs):
    return "".join(seqs)

def solve(text):
    flag = correctInput(text)
    sol = ""
    if flag:
        wordlist = prepare(text)
        seq = foo(wordlist)
        sol = joinSequences(seq)

    return sol,flag

inputString = input().strip()
solution, ok =  solve(inputString)
if ok:
    print(solution,end="")