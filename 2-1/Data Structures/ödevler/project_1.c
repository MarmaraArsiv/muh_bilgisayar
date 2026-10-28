/*
 * CSE2025 Fall 2020
 * Project #1
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

#define MAXCHAR 128 // Maximum char count for a string and line

/*
 * Node for linked list
 */
typedef struct Element {
    char word[MAXCHAR]; // Field for word
    int frequency; // Field for store frequency
    unsigned long long length; // Char count of word
    struct Element *next; // Address of next element
} Element;

/*
 * Node for binary tree
 */
typedef struct Node {
    char word[MAXCHAR];
    int frequency;
    int depth;
    unsigned long long length;
    struct Node *left;
    struct Node *right;
} Node;

// Creates an element (node) for linked list
Element *createElement(char word[MAXCHAR], int frequency);

// Finds maximum frequency word in linked list
Element *findMax(Element *element);

// Creates a node for binary tree
Node *createNode(char word[MAXCHAR], int frequency, int depth);

// Inserts an element (node) to end of list
void insertElement(Element **list, Element **tail, char *word, int frequency);

// Inserts a node to binary search tree
void insertNode(Node **root, Element *element, int depth);

void insertNode2(Node **root, Element *element, int depth);

// Prints a linked list to screen
void printList(Element *element);

// Creates a binary search tree by using elements (nodes) of a linked list
void listToTree(Element *e, Node **root);

void sortedListToTree(Element *e, Node **root);

// Prints a tree in pre-order traversal (NLR)
void printNLR(Node *root, int *totalAccessTime);

int main(void) {
    // Head and tail of the word linked list
    Element *head = NULL;
    Element *tail = NULL;
    // Root node of the tree
    Node *root = NULL;
    // Define a line
    char line[MAXCHAR];
    // Open input file
    FILE *fp = fopen("input.txt", "r");
    while (fgets(line, MAXCHAR, fp) != NULL) {
        char *token = strtok(line, ",");
        int i = 0;
        char *word;
        int frequency;
        while (token != NULL) {
            if (i == 1) {
                word = token;
                int j = 0;
                while (j < strlen(word)) {
                    // Make letters lowercase
                    word[j] = tolower(word[j]);
                    j++;
                }
            } else if (i == 2) {
                // Convert number char to integer
                frequency = atoi(token);
            }
            token = strtok(NULL, ",");
            i++;
        }
        // Add word to the linked list
        insertElement(&head, &tail, word, frequency);
    }
    // Close file
    fclose(fp);
    //printList(head);
    // Part A Building binary search tree with key "word"
    printf("/* Part a) BST with key word pre-order traversal (NLR) */\n");
    listToTree(head, &root);
    int totalAccessTime = 0;
    printNLR(root, &totalAccessTime);
    // Part B Total access time of the binary search tree
    printf("\nTotal access time: %d\n", totalAccessTime);
    // Create a new linked list for store words by frequency order
    Element *max = findMax(head);
    Element *head2 = NULL;
    Element *tail2 = NULL;
    while (max->frequency > 0) {
        insertElement(&head2, &tail2, max->word, max->frequency);
        // Remove frequency of word to prevent add it into list many times
        max->frequency = -1;
        // Find new max
        max = findMax(head);
    }
    //printList(head2);
    // Part C Create an AVL tree by using sorted word list
    printf("/* Part c) BST with key word length by using sorted list pre-order traversal (NLR) */\n");
    // Root node of the new tree
    Node *root2 = NULL;
    sortedListToTree(head2, &root2);
    int totalAccessTime2 = 0;
    printNLR(root2, &totalAccessTime2);
    printf("\nTotal access time: %d\n", totalAccessTime2);
    // Part D Comparison of part B and part D results
    printf("Gain: %f%c\n", (totalAccessTime - totalAccessTime2) * 100.0 / totalAccessTime, '%');
    return 0;
}

Element *createElement(char word[MAXCHAR], int frequency) {
    Element *element = NULL;
    element = (Element *) malloc(sizeof(Element));
    strcpy(element->word, word);
    element->frequency = frequency;
    element->length = strlen(word);
    element->next = NULL;
    return element;
}

Node *createNode(char word[MAXCHAR], int frequency, int depth) {
    Node *node = NULL;
    node = (Node *) malloc(sizeof(Node));
    strcpy(node->word, word);
    node->frequency = frequency;
    node->depth = depth;
    node->length = strlen(word);
    node->left = NULL;
    node->right = NULL;
    return node;
}

void insertElement(Element **list, Element **tail, char *word, int frequency) {
    Element *temp = createElement(word, frequency);
    if (*list == NULL) {
        *tail = *list = temp;
    } else {
        (*tail)->next = temp;
        *tail = temp;
    }
}

Element *findMax(Element *element) {
    int currentMax = 0;
    Element *currentElement;
    while (element != NULL) {
        if (element->frequency > currentMax) {
            currentMax = element->frequency;
            currentElement = element;
        }
        element = element->next;
    }
    return currentElement;
}

void insertNode(Node **root, Element *element, int depth) {
    if (!(*root)) {
        Node *node = createNode(element->word, element->frequency, depth);
        *root = node;
        return;
    }
    if (strcmp(element->word, (*root)->word) >= 0) {
        insertNode(&(*root)->right, element, (*root)->depth + 1);
    } else {
        insertNode(&(*root)->left, element, (*root)->depth + 1);
    }
}

void insertNode2(Node **root, Element *element, int depth) {
    if (!(*root)) {
        Node *node = createNode(element->word, element->frequency, depth);
        *root = node;
        return;
    }
    if (element->length >= (*root)->length) {
        insertNode(&(*root)->right, element, (*root)->depth + 1);
    } else {
        insertNode(&(*root)->left, element, (*root)->depth + 1);
    }
}

void printList(Element *element) {
    while (element != NULL) {
        printf("Word: %s Frequency: %d \n", element->word, element->frequency);
        element = element->next;
    }
}

void listToTree(Element *e, Node **root) {
    while (e != NULL) {
        insertNode(root, e, 0);
        e = e->next;
    }
}

void sortedListToTree(Element *e, Node **root) {
    while (e != NULL) {
        insertNode2(root, e, 0);
        e = e->next;
    }
}

void printNLR(Node *root, int *totalAccessTime) {
    if (root) {
        int accessTime = root->frequency * (root->depth + 1);
        printf("%s ", root->word);
        *totalAccessTime += accessTime;
        /*printf("Word: %s Frequency: %d Depth: %d Access Time: %d \n", root->word, root->frequency, root->depth,
               (root->depth+1)*root->frequency);*/
        printNLR(root->left, totalAccessTime);
        printNLR(root->right, totalAccessTime);
    }
}