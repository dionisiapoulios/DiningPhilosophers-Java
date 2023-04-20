This code is our solution to assignment #3 in the course COMP346: Operating Systems.

The goal is to implement a solution to the classical problem of synchronization – the Dining Philosophers problem, with a slight extension. 
We are asked to solve it using the Monitor synchronization construct built on top of Java’s synchronization primitives. The extension refers to the fact that sometimes philosophers would like to talk, but only one (any) philosopher can be talking at a time while they are not eating.
