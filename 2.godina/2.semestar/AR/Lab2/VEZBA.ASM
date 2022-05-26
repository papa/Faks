! inicijalizacija simulacije
dc x.1, x.9996
dc x.0, x.9998
onkp false, x.1, x.1
onkp false, x.1, x.1
onkp true, x.1, x.1
ondma x.1, x.1
ondma x.1, x.1
ondma x.1, x.1
ondma x.1, x.1
kpreg 1.1, r0, x.1
kpreg 1.1, r1, x.2
kpreg 1.1, r2, x.3
kpreg 1.1, r3, x.4
kpreg 1.1, r4, x.5
kpreg 1.1, r5, x.6
kpreg 1.1, r6, x.7
kpreg 1.1, r7, x.8
kpreg 2.1, r0, x.9
kpreg 2.1, r1, x.a
kpreg 2.1, r2, x.b
kpreg 2.1, r3, x.c
kpreg 2.1, r4, x.d
kpreg 2.1, r5, x.e
kpreg 2.1, r6, x.f
kpreg 2.1, r7, x.10
reg pc, x.4000
reg ivtp, x.4000
reg sp, x.9000
! kraj inicijalizacije
org x.4000

ldimm x.0100, r1
mvrir r1, ivtp

!ulaz 0
ldimm x.3000, r1
stmem x.0100, r1
!ulaz 1
ldimm x.2500, r1
stmem x.0101, r1
!ulaz 2
ldimm x.1500, r1
stmem x.0102, r1
!ulaz 3
ldimm x.2000, r1
stmem x.0103, r1
!ulaz 4
ldimm x.1000, r1
stmem x.0104, r1
!ulaz 5
ldimm x.0500, r1
stmem x.0105, r1

!---------------------------

!podaci za niz A
ldimm x.08, r2 !broj elemenata
ldimm x.5000, r3 !pocetna adresa
ldimm x.00, r4 !semafor
!startovanje periferije KP1.1
ldimm x.05, r0
stmem x.F102, r0
ldimm x.0F,r0
stmem x.F100, r0
!podaci za niz B
ldimm x.08, r5 !broj elemenata
ldimm x.6000, r6 !pocetna adresa
!startovanje periferije KP2.1
ldimm x.02, r0
stmem x.F202, r0
ldimm x.05, r0
stmem x.F200, r0


!------------------------------

loopKP21:
ldmem x.F201,r0
ldimm x.01,r1
tst r0,r1
beql loopKP21
ldmem x.F203, r0
stri [r6],r0
inc r6
dec r5
bgrt loopKP21
!zaustavljanje KP2.1
ldimm x.00, r0
stmem x.F200, r0

loopKP11:
ldimm x.01, r0
tst r0, r4
beql loopKP11
!zaustavljanje KP1.1
ldimm x.00, r0
stmem x.F100, r0

!---------------------------------------
!poziv funkcije xorAll
ldimm x.08, r0
push r0
ldimm x.6000, r0
push r0
ldimm x.5000, r0
push r0
jsr xorAll
pop r0
pop r0
pop r0


ldmem x.5000, r0
stmem x.9999, r0
!----------------------------------------
!DMA14 kopiranje nultog elementa od A
!5100h
ldimm x.00, r2 !semafor
ldimm x.0, r0
stmem x.F0C2, r0
ldimm x.08,r0
stmem x.F0C4, r0
ldimm x.9999, r0
stmem x.F0C5, r0
ldimm x.5100, r0
stmem x.F0C6, r0
ldimm x.09E ,r0
stmem x.F0C0, r0

loopDMA14:
ldimm x.01, r0
tst r0, r2
beql loopDMA14

!Gasenje DMA14
ldimm x.00, r0
stmem x.F0C0, r0

!-------------------------------------
!podaci za niz
ldimm x.08, r2 !broj elemenata
ldimm x.5000, r3 !pocetna adresa
!slanje periferiji KP1.2
ldimm x.04, r0
stmem x.F142, r0
ldimm x.04,r0
stmem x.F140, r0

loopKP12:
ldmem x.F141,r0
ldimm x.01,r1
tst r0,r1
beql loopKP12
ldrid [r3]x.00, r0
stmem x.F143, r0
inc r3
dec r2
bgrt loopKP12
!zaustavljanje KP2.1
ldimm x.00, r0
stmem x.F140, r0

!--------------------------------------------

!DMA12 poslednja tacka
ldimm x.0, r2 ! semafor
ldimm x.01, r0
stmem x.F044, r0
ldimm x.01,r0
stmem x.F042, r0
ldimm x.08E, r0
stmem x.F040, r0
!-------------------------------

loopDMA12:
ldimm x.01, r0
tst r2,r0
beql loopDMA12
!kraj rada DMA1.2

!kraj rada procesora
halt

xorAll:
push r0
push r1
push r2
push r3
push r4
push r5
mvrpl r0, sp
ldrid [r0]x.09, r1 
ldrid [r0]x.08, r2
ldrid [r0]x.07, r3
loopXor:
ldrid [r3]x.00, r4
ldrid [r2]x.00, r5
xor r4,r4,r5
stri [r3], r4
inc r2
inc r3
dec r1
bgrt loopXor
pop r5
pop r4
pop r3
pop r2
pop r1
pop r0
rts

!-------------------------------------

org x.0500

push r0
push r1

ldimm x.00, r0
cmp r2, r0
bgrt getElement
ldimm x.01, r4
ldimm x.00, r0
stmem x.F100, r0
jmp backKP11
getElement:
ldmem x.F103, r0
stri [r3],r0
inc r3
dec r2
backKP11:
pop r1
pop r0
rti


!--------------------------------------
!prekidna rutina za DMA12
org x.2500
push r0
push r1
ldimm x.01, r2
ldimm x.00, r0
stmem x.F040, r0
pop r1
pop r0
rti

!--------------------------------------
!prekidna rutina za DMA14
org x.3000
push r0
push r1
ldimm x.01, r2
ldimm x.00, r0
stmem x.F0C0, r0
pop r1
pop r0
rti

