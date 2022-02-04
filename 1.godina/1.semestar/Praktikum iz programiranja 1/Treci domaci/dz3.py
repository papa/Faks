def inputMatrix():
    (n, m) = [int(x) for x in input().split()]
    mat = []
    if n<=0 or m<=0: return mat
    for i in range(0,n):
        mat.append([int(x) for x in input().split()])
    return mat

def printMatrix(mat):
    n = getRows(mat)
    m = getColumns(mat)
    for i in range(n):
        for j in range(m-1):
            print(mat[i][j],end = " ")
        str_end = "\n" if i < n-1 else  ""
        print(mat[i][m-1],end = str_end)

    return

def getRows(matrix):
    return len(matrix)

def getColumns(matrix):
    if(getRows(matrix) > 0):
        return len(matrix[0])

    return -1

def validDim(x,d):
    return x>=0 and x<d

def validInd(n,m,r,c):
    return validDim(r,n) and validDim(c,m)

def check(mat,n,m,r1,c1,r2,c2):
    if validInd(n,m,r2,c2) and mat[r1][c1]>=mat[r2][c2]:
        return True
    else:
        return False

def foo(mat):
    n = getRows(mat)
    m = getColumns(mat)
    res = [[0 for i in range(m)] for i in range(n)]
    dr = [1,1,1,-1,-1,-1,0,0]
    dc = [1,0,-1,1,0,-1,1,-1]
    for i in range(n):
        for j in range(m):
            for k in range(len(dr)):
                if(check(mat,n,m,i,j,i + dr[k],j+dc[k])):
                    res[i][j]+=1
    return res

matrix = inputMatrix()
if getRows(matrix) > 0 and getColumns(matrix) > 0:
    sol = foo(matrix)
    printMatrix(sol)


