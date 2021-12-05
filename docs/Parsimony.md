# Parsimony

Notes on solving the Large Parsimony problem with 
nearest neighbor interchange.  Here are two simple
test examples.  The first shows a tree with four leaf nodes and 
two internal nodes.  The minimal parsimony solution consists 
of swapping two leaf nodes to reduce the overall 
hamming distance.
<br>

![Placeholder](assets/LargeParsimonyNNI.png){ align=left }

<br>
Very simple test example - solution is to swap one C with one G, 
resulting in a tree with a minimum parsimony hamming distance of 1.
<br>

![Placeholder](assets/LargeParsimonyNNItest2.png){ align=left }

<br>
Another test example - this time the solution is to swap two internal
nodes, as shown below.
<br>

![Placeholder](assets/LargeParsimonyNNItest2solution.png){ align=left }