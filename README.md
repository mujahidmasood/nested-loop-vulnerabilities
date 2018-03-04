# Identifying Algorithmic Complexity Vulnerabilities Caused by Input-Dependent Nested Loops

The single-threaded event model of JavaScript makes it vulnerable to a specific class of denial of
service attack called algorithmic complexity attacks. These attacks consist of exploiting the worst
case performance of algorithms to trigger slow computations that block the event loop for a large
period of time. In this project we study the prevalence of a particular class of algorithmic complexity
vulnerabilities called input-dependent nested loops.


This project intends to find vulnerabilities in JavaScript code which uses
- Function Parameters in loops


if an attacker can control the input to function that is he or she can control the value of argument the attacker may introduce large slowdowns in 
the event loop of Node.js a 1.5 seconds delay can be introduced. Considering that the Node.js security experts consider any slowdown larger than one second as security relevant

Running the code

Clone the directory using
    git clone https://github.com/mujahidmasood/nested-loop-vulnerabilities.git

Adding dependencies
    mvn clean compile install 

Running the code
To run the code simple run Main.java