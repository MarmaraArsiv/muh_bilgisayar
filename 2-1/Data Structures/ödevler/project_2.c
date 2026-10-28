/*
 * CSE2025 Fall 2020 Project II
 */

#include <stdio.h>
#include <dirent.h>
#include <stdlib.h>
#include <ctype.h>

#define MAXCHAR 128 // A word can contain maximum 128 characters
#define MAXLINE 512 // A line can contain maximum 512 characters

int LIMIT = 5; // By default 5 relevant docs will be displayed
char inputdir[] = "./files";
char filedir[] = "./files/";

typedef struct Word {
    char word[MAXCHAR];
    int frequency;
    struct Word *next;
} Word;

typedef struct File {
    char filename[MAXCHAR];
    struct Word *words;
    struct Word *tail;
    struct File *next;
} File;

typedef struct Node {
    File file;
    int degree;
    int frequency;
    struct Node *parent;
    struct Node *child;
    struct Node *sibling;
} Node;

typedef struct Heap {
    Node *head;
} Heap;

void readFile(File *file);

void insertFile(File **list, File **tail, char *filename);

void insertWord(Word **head, Word **tail, char word[MAXCHAR]);

void insert(Heap *heap, Node *node);

void delete(Heap *heap, Node *node);

void extractMin(Heap *heap);

void printHeap(Heap *heap);

void printFile(File file);

int count(File *file, char word[MAXCHAR]);

Word *findWord(Word **head, char word[MAXCHAR]);

Node *createNode(File *file);

Node *findMin(Heap *heap);

Heap *createHeap();

int main(void) {
    struct dirent *de;
    char keyword[MAXCHAR];
    DIR *directory = opendir(inputdir);
    if (directory == NULL) {
        printf("Could not open current directory");
        return 0;
    }
    File *head = NULL;
    File *tail = NULL;
    while ((de = readdir(directory)) != NULL) {
        if (de->d_name[0] != '.') {
            insertFile(&head, &tail, de->d_name);
            readFile(tail);
        }
    }
    closedir(directory);
    printf("Enter a keyword:\n");
    scanf("%s", keyword);
    printf("Enter a limit for the results:\n");
    int input;
    scanf("%d", &input);
    if (input > 0) {
        LIMIT = input;
    }
    Heap *heap = createHeap();
    File *currentFile = head;
    int numberOfNodes = 0;
    while (currentFile != NULL) {
        Node *node = createNode(currentFile);
        node->frequency = count(currentFile, keyword);
        if (numberOfNodes < LIMIT) {
            insert(heap, node);
            numberOfNodes++;
        } else {
            insert(heap, node);
            extractMin(heap);
        }
        currentFile = currentFile->next;
    }
    printHeap(heap);
    return 0;
}

void printHeap(Heap *heap) {
    Node *docs[LIMIT];
    Heap *temp = heap;
    int i = 1;
    printf("The relevance order is: ");
    // Sort binomial heap nodes in descending order by keyword frequency
    while (temp->head != NULL && i <= LIMIT) {
        docs[LIMIT - i] = findMin(temp);
        delete(temp, docs[LIMIT - i]);
        i++;
    }
    for (i = 0; i < LIMIT; i++) {
        printf("%s(%d) ", docs[i]->file.filename, docs[i]->frequency);
    }
    printf("\n");
    for (int j = 0; j < LIMIT; j++) {
        printf("\n%s(%d): ", docs[j]->file.filename, docs[j]->frequency);
        printFile(docs[j]->file);
    }
}

void printFile(File file) {
    char contents;
    char tempdir[] = "";
    strcpy(tempdir, filedir);
    strcat(tempdir, file.filename);
    FILE *fp = fopen(tempdir, "r");
    if (fp == NULL) {
        printf("Couldn't open the file!\n");
    }
    contents = fgetc(fp);
    while (contents != EOF) {
        printf("%c", contents);
        contents = fgetc(fp);
    }
    fclose(fp);
}

void insertFile(File **list, File **tail, char *filename) {
    File *temp = NULL;
    temp = (File *) malloc(sizeof(File));
    strcpy(temp->filename, filename);
    temp->next = NULL;
    if (*list == NULL) {
        *tail = *list = temp;
    } else {
        (*tail)->next = temp;
        *tail = temp;
    }
}

void readFile(File *file) {
    file->words = NULL;
    file->tail = NULL;
    char delimiters[] = " ,.!:?\"()";
    // Define a line
    char line[MAXLINE];
    char tempdir[] = "";
    strcpy(tempdir, filedir);
    strcat(tempdir, file->filename);
    FILE *fp = fopen(tempdir, "r");
    while (fscanf(fp, "%s", line) != EOF) {
        char *token = strtok(line, delimiters);
        char *word;
        while (token != NULL) {
            word = token;
            int j = 0;
            while (j < strlen(word)) {
                word[j] = tolower(word[j]);
                j++;
            }
            Word *search = findWord(&file->words, word);
            if (search == NULL) {
                insertWord(&file->words, &file->tail, word);
            } else {
                search->frequency += 1;
            }
            token = strtok(NULL, delimiters);
        }
    }
    fclose(fp);
}

void insertWord(Word **head, Word **tail, char word[MAXCHAR]) {
    Word *temp = NULL;
    temp = (Word *) malloc(sizeof(Word));
    temp->frequency = 1;
    temp->next = NULL;
    strcpy(temp->word, word);
    if (*head == NULL) {
        *tail = *head = temp;
    } else {
        (*tail)->next = temp;
        *tail = temp;
    }
}

Word *findWord(Word **head, char word[MAXCHAR]) {
    Word *current = *head;
    while (current != NULL) {
        if (strcmp(current->word, word) == 0) {
            return current;
        }
        current = current->next;
    }
    return NULL;
}

int count(File *file, char word[MAXCHAR]) {
    Word *current = file->words;
    while (current != NULL) {
        if (strcmp(current->word, word) == 0) {
            return current->frequency;
        }
        current = current->next;
    }
    return 0;
}

Node *createNode(File *file) {
    Node *node;
    node = (Node *) malloc(sizeof(Node));
    node->file = *file;
    node->parent = NULL;
    node->child = NULL;
    node->sibling = NULL;
    node->degree = 0;
    node->frequency = 0;
    return node;
}

Heap *createHeap() {
    Heap *heap = malloc(sizeof(Heap));
    heap->head = NULL;
    return heap;
}

Node *merge(Heap *a, Heap *b) {
    if (a->head == NULL) {
        return b->head;
    } else if (b->head == NULL) {
        return a->head;
    } else {
        Node *head;
        Node *aNext = a->head;
        Node *bNext = b->head;
        if (a->head->degree <= b->head->degree) {
            head = a->head;
            aNext = aNext->sibling;
        } else {
            head = b->head;
            bNext = bNext->sibling;
        }
        Node *tail = head;
        while (aNext != NULL && bNext != NULL) {
            if (aNext->degree <= bNext->degree) {
                tail->sibling = aNext;
                aNext = aNext->sibling;
            } else {
                tail->sibling = bNext;
                bNext = bNext->sibling;
            }
            tail = tail->sibling;
        }
        if (aNext != NULL) {
            tail->sibling = aNext;
        } else {
            tail->sibling = bNext;
        }
        return head;
    }
}

void link(Node *a, Node *b) {
    b->parent = a;
    b->sibling = a->child;
    a->child = b;
    a->degree++;
}

Node *unionHeap(Heap *a, Heap *b) {
    Node *newHead = merge(a, b);
    a->head = NULL;
    b->head = NULL;
    if (newHead == NULL) {
        return NULL;
    }
    Node *current = newHead;
    Node *next = newHead->sibling;
    Node *prev = NULL;
    while (next != NULL) {
        if (current->degree != next->degree || (next->sibling != NULL && next->sibling->degree == current->degree)) {
            prev = current;
            current = next;
        } else {
            if (current->frequency < next->frequency) {
                current->sibling = next->sibling;
                link(current, next);
            } else {
                if (prev == NULL) {
                    newHead = next;
                } else {
                    prev->sibling = next;
                }
                link(next, current);
                current = next;
            }
        }
        next = current->sibling;
    }
    return newHead;
}

void insert(Heap *heap, Node *node) {
    Heap *temp = createHeap();
    temp->head = node;
    heap->head = unionHeap(heap, temp);
}

Node *findMin(Heap *heap) {
    if (heap->head == NULL) {
        return NULL;
    } else {
        Node *min = heap->head;
        Node *next = min->sibling;
        while (next != NULL) {
            if (next->frequency < min->frequency) {
                min = next;
            }
            next = next->sibling;
        }
        return min;
    }
}

Node *bubbleUp(Node *node, int toRoot) {
    Node *parent = node->parent;
    while (parent != NULL && (toRoot || node->frequency < parent->frequency)) {
        int temp = node->frequency;
        node->frequency = parent->frequency;
        parent->frequency = temp;
        node = parent;
        parent = parent->parent;
    }
    return node;
}

void removeTreeRoot(Heap *heap, Node *root, Node *prev) {
    if (root == heap->head) {
        heap->head = root->sibling;
    } else {
        prev->sibling = root->sibling;
    }
    Node *child = root->child;
    Node *newHead = NULL;
    while (child != NULL) {
        Node *next = child->sibling;
        child->sibling = newHead;
        child->parent = NULL;
        newHead = child;
        child = next;
    }
    Heap *temp = createHeap();
    temp->head = newHead;
    heap->head = unionHeap(heap, temp);
}

void delete(Heap *heap, Node *node) {
    node = bubbleUp(node, 1);
    if (heap->head == node) {
        removeTreeRoot(heap, node, NULL);
    } else {
        Node *prev = heap->head;
        while (prev->sibling->frequency != node->frequency) {
            prev = prev->sibling;
        }
        removeTreeRoot(heap, node, prev);
    }
}

void extractMin(Heap *heap) {
    if (heap->head == NULL) {
        return;
    }
    Node *min = heap->head;
    Node *next = min->sibling;
    Node *nextPrev = min;
    Node *minPrev = NULL;
    while (next != NULL) {
        if (next->frequency < min->frequency) {
            min = next;
            minPrev = nextPrev;
        }
        nextPrev = next;
        next = next->sibling;
    }
    removeTreeRoot(heap, min, minPrev);
}