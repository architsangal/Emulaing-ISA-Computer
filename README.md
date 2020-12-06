# Emulating-IAS-ISA-Computer
Implementing the IAS computer (fetch the instruction, decode and execute) using java. Here, idea is to emulate the ISA computer.

It’ll give you a list of values stored in different registers after each 40 bit instruction(LHS+RHS) is executed. This will be done for each of the 2 programs implemented in the code through the IAS instructions.

1 st program is a set of random instructions encompassing all the 21 instructions available in an IAS ISA.


2 nd program is a valid higher-level program which returns the |(a-b)| of as written below:-

a = 10, b = 15;

if(a >=b)

+ c = a-b;
else

+  c = b-a;
