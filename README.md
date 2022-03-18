# ToyLangInterpreter
A custom "toy" language interpreter built on Java

A project I worked on for the 3rd semester, this language features the following:

**Instructions:**

- variable declaration & assignment
- if
- while
- fork (multi-threading)
- file opening, closing, reading, writing
- execution of pre-defined procedures
- printing

**Functionalities:**

- Storing local variables in symbol tables
- Storing instructions in execution stacks
- Program states: each state has an ID, a stack of symbol tables and an execution stack
- Upon forking, a new program state with a unique ID is being created
- Storing BufferedReader objects into a file table for file operations
- Storing pre-defined procedures into a procedure table as <name -> <params_list, statement>>
- Upon procedure call, a clone of the initial symbol table with the added function parameters is being pushed to the symbol table stack
- Shared heap across all the states created by a program - allocation, reading, writing, garbage collector
- Storing printing output in an output table
- Automatic storing of execution steps history in log files.

![image](https://user-images.githubusercontent.com/39191865/159029038-6e55f4e9-d65e-43dc-b9a0-bb30eb348a2b.png)
