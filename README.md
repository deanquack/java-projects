# java-projects
All the java projects made and developed using data structure fundamentals & design

Another Priority Queue
- This priority queue checks the priority of random values in a linked list. 
- The Driver tests to see if the implementations and functions in each of the respective classes and files are working properly
- OrderedLinkedList checks if the values inserted to the list are in order based on the value itself like "1 has higher priority over 2 even though 2 was entered first"
- UnorderedLinkedList does the same thing but it checks the order of the list based on when the value was inserted like "2 has higher priority than 1 since it was insertred first"
- PQTimer determines the amount of time it takes for the program to be successful after a certain number of times while checking if the performance is linear, quadratic, etc.
- Priority queue is an interface of methods used to implement the size of the list, insert and deletion, to check the very first and last value of the list, clear the list, and check if the list is empty or full, and iterator checks if the iteration is successful 

Binary Heap Priority Queue 
- A Binary Heap is a Binary Tree with following properties. (Found on https://www.geeksforgeeks.org/binary-heap/)
1) It’s a complete tree (All levels are completely filled except possibly the last level and the last level has all keys as left as possible). This property of Binary Heap makes them suitable to be stored in an array.

2) A Binary Heap is either Min Heap or Max Heap. In a Min Binary Heap, the key at root must be minimum among all keys present in Binary Heap. The same property must be recursively true for all nodes in Binary Tree. Max Binary Heap is similar to MinHeap. 

- The Driver tests to see if the implementations and functions in each of the respective classes and files are working properly
- The Binary Heap Priority Queue implements a tree like simulation using arrays. It allows us to create an implementation of how we can recusively make a priority queue list by tricking up and trickling down the array making sure all the values are in the right place.
- PQTimer determines the amount of time it takes for the program to be successful after a certain number of times while checking if the performance is linear, quadratic, etc.
- Priority queue is an interface of methods used to implement the size of the list, insert, delete, and remove, to check the very first and last value of the list, clear the list, and check if the list is empty or full, and iterator checks if the iteration is successful. I also implemented a trickle up and trickle down method that allowed the iteration to go through whenever it's needed to find, insert, delete, remove, or find the size of the array

Priority Queue
This relies on the basics of understanding of how the priority queue in the array.
- OrderedArrayPriorityQueue is used an array that checks if the values inserted to the list are in order based on the value itself like "1 has higher priority over 2 even though 2 was entered first"
- UnorderedArrayPriorityQueue does the same thing but it checks the order of the list based on when the value was inserted like "2 has higher priority than 1 since it was insertred first"
- PQTimer determines the amount of time it takes for the program to be successful after a certain number of times while checking if the performance is linear, quadratic, etc.
- Priority queue is an interface of methods used to implement the size of the list, insert and deletion, to check the very first and last value of the list, clear the list, and check if the list is empty or full, and iterator checks if the iteration is successful 
