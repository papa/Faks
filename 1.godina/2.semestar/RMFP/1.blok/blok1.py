# -*- coding: utf-8 -*-
"""
Created on Tue Mar 16 15:17:02 2021

@author: papa
"""

import konstante

a = konstante.q * konstante.h
print(a)

#u konzoli _*2 oznacava 
#poslednju vrednost do koje je konzola
#stigla (ispisala) i mnozi je sa 2 

#modul numpy
#najbitniji element je ndarray
#najbitnije funkcije nad nizom su 
#ndim - broj dimenzija
#shape - dimenzija niza
#size - ukupan broj elemenata
#dtype - tip elemenata
import numpy as np
from numpy import pi

a = np.array([2,3,4])
print(a)
print(a.dtype)

c = np.array([[1,2],[3,4]], dtype = complex)
print(c)

zeros = np.zeros((3,5))
print(zeros)

#pravimo niz pomocu arange
a = np.arange(10,30,5)
print(a) #ekvidisantni niz 

#pomocu linspace zadajemo ukupan broj elemenata
a = np.linspace(0,2*pi,100)
x = np.sin(a)
print(a)
print(x)

A = np.array([[1,1],[0,1]])
B = np.array([[2,0],[3,4]])
print(A*B) #ne mnoze se ovako matrice
print(A.dot(B)) #ovako se mnoze matrice

a = np.exp(a) #vraca niz tako da je a[i] = e^(a[i])

#matplotlib
#modul za crtanje grafika 
import matplotlib.pyplot as plt

#plt.plot([1,2,3,4],[1,4,9,16],'ro') # crta grafik
#plt.axis([0,6,0,20]) # x osa od 0 do 6, y osa od 0 do 20
#plt.ylabel('oznaka ose') # labelira osu

#ekvidisantni vremenski niz
t = np.arange(0.,5.,0.2)
#po 3 karaktera za svaki grafik
#r-- = crvena isprekidana linija
#bs = blue square
#g^ = zeleni trougao
plt.plot(t,t,'r--',t,t**2,'bs',t,t**3,'g^')
plt.xlabel('oznaka x ose')
plt.ylabel('oznaka y ose')

#ako zelimo sustinski jedan grafik ali sa 2 prozora
def f(t):
    return np.exp(-t)*np.cos(2*pi*t)

#k - crno
t1 = np.arange(0.,5.0,0.1)
t2 = np.arange(0.,5.0,0.02)
plt.figure(1) #unapred se rezervise memorijski prostor
plt.subplot(211)#2  reda, 1  kolona, 1 - prvo polje
plt.plot(t1, f(t1), 'bo', t2, f(t2), 'k')
plt.subplot(212)#2  reda, 1 kolona, 2 - drugo polje
plt.plot(t2, np.cos(2*pi*t2),'r--')