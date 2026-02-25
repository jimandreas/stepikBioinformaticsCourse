---
title: "Parsimony"
date: 2021-10-01
draft: false
---

Notes on solving the Large Parsimony problem with
nearest neighbor interchange. Here are two simple
test examples. The first shows a tree with four leaf nodes and
two internal nodes. The minimal parsimony solution consists
of swapping two leaf nodes to reduce the overall
Hamming distance.

![Large Parsimony NNI](../images/LargeParsimonyNNI.png)

Very simple test example - solution is to swap one C with one G,
resulting in a tree with a minimum parsimony Hamming distance of 1.

![Large Parsimony NNI Test 2](../images/LargeParsimonyNNItest2.png)

Another test example - this time the solution is to swap two internal
nodes, as shown below.

![Large Parsimony NNI Test 2 Solution](../images/LargeParsimonyNNItest2Solution.png)
