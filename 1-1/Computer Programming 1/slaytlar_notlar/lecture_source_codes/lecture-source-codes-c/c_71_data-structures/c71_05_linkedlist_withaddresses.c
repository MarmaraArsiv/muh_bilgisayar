// Fig. 12.3: fig12_03.c
// Inserting and deleting nodes in a list
#include <stdio.h>
#include <stdlib.h>

// self-referential structure                       
struct listNode {                                      
   char data; // each listNode contains a character 
   struct listNode *nextPtr; // pointer to next node
}; 

typedef struct listNode ListNode; // synonym for struct listNode
typedef ListNode * ListNodePtr; // synonym for ListNode*

// prototypes
void insert(ListNodePtr *sPtr, char value);
char delete(ListNodePtr *sPtr, char value);
int isEmpty(ListNodePtr sPtr);
void printList(ListNodePtr currentPtr);
void instructions(void);

int main(void)
{ 
   ListNodePtr startPtr = NULL; printf("\nline 24: @startPtr -> %d\n", &startPtr); printf("\nstartPtr -> %d\n", startPtr); //printf("\n@startPtr->data -> %d\n", startPtr->data); printf("\nstartPtr->nextPtr -> %d\n", startPtr->nextPtr); // initially there are no nodes
   char item; printf("\n@item -> %d\n", &item); // char entered by user

   instructions(); // display the menu
   printf("%s", "? ");
   unsigned int choice; printf("\nline 29: @choice -> %d\n", &choice); // user's choice
   scanf("%u", &choice);

   // loop while user does not choose 3
   while (choice != 3) { 

      switch (choice) { 
         case 1:
            printf("%s", "Enter a character: ");
            scanf("\n%c", &item); printf("\nline 38: @startPtr -> %d\n", &startPtr); printf("\nstartPtr -> %d\n", startPtr);
            insert(&startPtr, item); printf("\nline 39: @startPtr -> %d\n", &startPtr); printf("\nstartPtr -> %d\n", startPtr); // insert item in list
            printList(startPtr);
            break;
         case 2: // delete an element
            // if list is not empty
            if (!isEmpty(startPtr)) {  printf("\nline 44: @startPtr -> %d\n", &startPtr); printf("\nstartPtr -> %d\n", startPtr);
               printf("%s", "Enter character to be deleted: ");
               scanf("\n%c", &item);

               // if character is found, remove it
               if (delete(&startPtr, item)) {  printf("\nline 49: @startPtr -> %d\n", &startPtr); printf("\nstartPtr -> %d\n", startPtr);// remove item
                  printf("%c deleted.\n", item); printf("\nline 50: @startPtr -> %d\n", &startPtr); printf("\nstartPtr -> %d\n", startPtr);
                  printList(startPtr);
               } 
               else {
                  printf("%c not found.\n\n", item);
               } 
            } 
            else {
               puts("List is empty.\n");
            } 

            break;
         default:
            puts("Invalid choice.\n");
            instructions();
            break;
      } // end switch

      printf("%s", "? ");
      scanf("%u", &choice);
   } 

   puts("End of run.");
} 

// display program instructions to user
void instructions(void)
{ 
   puts("Enter your choice:\n"
      "   1 to insert an element into the list.\n"
      "   2 to delete an element from the list.\n"
      "   3 to end.");
} 

// insert a new value into the list in sorted order
void insert(ListNodePtr *sPtr, char value)
{  printf("\nline 86: @sPtr -> %d\n", &sPtr); printf("\nsPtr -> %d\n", sPtr); //printf("\n@sPtr->data -> %d\n", sPtr->data); printf("\nsPtr->nextPtr -> %d\n", sPtr->nextPtr); printf("\n*sPtr -> %d\n", *sPtr);
   ListNodePtr newPtr = malloc(sizeof(ListNode)); printf("\nline 87: @newPtr -> %d\n", &newPtr); printf("\nnewPtr -> %d\n", newPtr); //printf("\n@newPtr->data -> %d\n", newPtr->data); printf("\nnewPtr->nextPtr -> %d\n", newPtr->nextPtr); // create node

   if (newPtr != NULL) { // is space available
      newPtr->data = value; // place value in node
      newPtr->nextPtr = NULL; printf("\nline 91: @newPtr -> %d\n", &newPtr); printf("\nnewPtr -> %d\n", newPtr); //printf("\n@newPtr->data -> %d\n", newPtr->data); printf("\nnewPtr->nextPtr -> %d\n", newPtr->nextPtr); // node does not link to another node

      ListNodePtr previousPtr = NULL; printf("\nline 93: @previousPtr -> %d\n", &previousPtr); printf("\npreviousPtr -> %d\n", previousPtr); //printf("\n@previousPtr->data -> %d\n", previousPtr->data); printf("\npreviousPtr->nextPtr -> %d\n", previousPtr->nextPtr);
      ListNodePtr currentPtr = *sPtr; printf("\nline 94: @currentPtr -> %d\n", &currentPtr); printf("\ncurrentPtr -> %d\n", currentPtr); //printf("\n@currentPtr->data -> %d\n", currentPtr->data); printf("\ncurrentPtr->nextPtr -> %d\n", currentPtr->nextPtr);

      // loop to find the correct location in the list       
      while (currentPtr != NULL && value > currentPtr->data) {
         previousPtr = currentPtr; printf("\nline 98: @previousPtr -> %d\n", &previousPtr); printf("\npreviousPtr -> %d\n", previousPtr); //printf("\n@previousPtr->data -> %d\n", previousPtr->data); printf("\npreviousPtr->nextPtr -> %d\n", previousPtr->nextPtr); // walk to ...               
         currentPtr = currentPtr->nextPtr; printf("\nline 99: @currentPtr -> %d\n", &currentPtr); printf("\ncurrentPtr -> %d\n", currentPtr); //printf("\n@currentPtr->data -> %d\n", currentPtr->data); printf("\ncurrentPtr->nextPtr -> %d\n", currentPtr->nextPtr); // ... next node 
      }                                          

      // insert new node at beginning of list
      if (previousPtr == NULL) {  printf("\nline 103: @sPtr -> %d\n", &sPtr); printf("\nsPtr -> %d\n", sPtr);
         newPtr->nextPtr = *sPtr; printf("\nline 104: @newPtr -> %d\n", &newPtr); printf("\nnewPtr -> %d\n", newPtr); //printf("\n@newPtr->data -> %d\n", newPtr->data); printf("\nnewPtr->nextPtr -> %d\n", newPtr->nextPtr);
         *sPtr = newPtr; printf("\nline 105: @sPtr -> %d\n", &sPtr); printf("\nsPtr -> %d\n", sPtr); //printf("\n@sPtr->data -> %d\n", sPtr->data); printf("\nsPtr->nextPtr -> %d\n", sPtr->nextPtr); printf("\n*sPtr -> %d\n", *sPtr);
      } 
      else { // insert new node between previousPtr and currentPtr
         previousPtr->nextPtr = newPtr; printf("\nline 108: @previousPtr -> %d\n", &previousPtr); printf("\npreviousPtr -> %d\n", previousPtr); //printf("\n@previousPtr->data -> %d\n", previousPtr->data); printf("\npreviousPtr->nextPtr -> %d\n", previousPtr->nextPtr);
         newPtr->nextPtr = currentPtr; printf("\nline 109: @newPtr -> %d\n", &newPtr); printf("\nnewPtr -> %d\n", newPtr); //printf("\n@newPtr->data -> %d\n", newPtr->data); printf("\nnewPtr->nextPtr -> %d\n", newPtr->nextPtr);
      } 
   } 
   else {
      printf("%c not inserted. No memory available.\n", value);
   } 
} 

// delete a list element
char delete(ListNodePtr *sPtr, char value)
{  printf("\nline 119: @sPtr -> %d\n", &sPtr); printf("\nsPtr -> %d\n", sPtr); //printf("\n@sPtr->data -> %d\n", sPtr->data); printf("\nsPtr->nextPtr -> %d\n", sPtr->nextPtr); printf("\n*sPtr -> %d\n", *sPtr);
   // delete first node if a match is found
   if (value == (*sPtr)->data) {  printf("\nline 121: @sPtr -> %d\n", &sPtr); printf("\nsPtr -> %d\n", sPtr);
      ListNodePtr tempPtr = *sPtr; printf("\nline 122: @tempPtr -> %d\n", &tempPtr); printf("\ntempPtr -> %d\n", tempPtr); //printf("\n@tempPtr->data -> %d\n", tempPtr->data); printf("\ntempPtr->nextPtr -> %d\n", tempPtr->nextPtr); // hold onto node being removed
      *sPtr = (*sPtr)->nextPtr; printf("\nline 123: @sPtr -> %d\n", &sPtr); printf("\nsPtr -> %d\n", sPtr); //printf("\n@sPtr->data -> %d\n", sPtr->data); printf("\nsPtr->nextPtr -> %d\n", sPtr->nextPtr); printf("\n*sPtr -> %d\n", *sPtr); // de-thread the node
      free(tempPtr); // free the de-threaded node
      return value;
   } 
   else { 
      ListNodePtr previousPtr = *sPtr; printf("\nline 128: @previousPtr -> %d\n", &previousPtr); printf("\npreviousPtr -> %d\n", previousPtr); //printf("\n@previousPtr->data -> %d\n", previousPtr->data); printf("\npreviousPtr->nextPtr -> %d\n", previousPtr->nextPtr);
      ListNodePtr currentPtr = (*sPtr)->nextPtr; printf("\nline 129: @currentPtr -> %d\n", &currentPtr); printf("\ncurrentPtr -> %d\n", currentPtr); //printf("\n@currentPtr->data -> %d\n", currentPtr->data); printf("\ncurrentPtr->nextPtr -> %d\n", currentPtr->nextPtr);

      // loop to find the correct location in the list
      while (currentPtr != NULL && currentPtr->data != value) { 
         previousPtr = currentPtr; printf("\nline 133: @previousPtr -> %d\n", &previousPtr); printf("\npreviousPtr -> %d\n", previousPtr); //printf("\n@previousPtr->data -> %d\n", previousPtr->data); printf("\npreviousPtr->nextPtr -> %d\n", previousPtr->nextPtr); // walk to ...  
         currentPtr = currentPtr->nextPtr; printf("\nline 134: @currentPtr -> %d\n", &currentPtr); printf("\ncurrentPtr -> %d\n", currentPtr); //printf("\n@currentPtr->data -> %d\n", currentPtr->data); printf("\ncurrentPtr->nextPtr -> %d\n", currentPtr->nextPtr); // ... next node  
      } 

      // delete node at currentPtr
      if (currentPtr != NULL) { 
         ListNodePtr tempPtr = currentPtr; printf("\nline 139: @tempPtr -> %d\n", &tempPtr); printf("\ntempPtr -> %d\n", tempPtr); //printf("\n@tempPtr->data -> %d\n", tempPtr->data); printf("\ntempPtr->nextPtr -> %d\n", tempPtr->nextPtr);
         previousPtr->nextPtr = currentPtr->nextPtr; printf("\nline 140: @previousPtr -> %d\n", &previousPtr); printf("\npreviousPtr -> %d\n", previousPtr); //printf("\n@previousPtr->data -> %d\n", previousPtr->data); printf("\npreviousPtr->nextPtr -> %d\n", previousPtr->nextPtr);
         free(tempPtr);
         return value;
      } 
   } 

   return '\0';
} 

// return 1 if the list is empty, 0 otherwise
int isEmpty(ListNodePtr sPtr)
{  printf("\nline 151: @sPtr -> %d\n", &sPtr); printf("\nsPtr -> %d\n", sPtr); //printf("\n@sPtr->data -> %d\n", sPtr->data); printf("\nsPtr->nextPtr -> %d\n", sPtr->nextPtr); printf("\n*sPtr -> %d\n", *sPtr);
   return sPtr == NULL;
} 

// print the list
void printList(ListNodePtr currentPtr)
{  printf("\nline 157: @currentPtr -> %d\n", &currentPtr); printf("\ncurrentPtr -> %d\n", currentPtr);
   // if list is empty
   if (isEmpty(currentPtr)) {
      puts("List is empty.\n");
   } 
   else { 
      puts("The list is:");

      // while not the end of the list
      while (currentPtr != NULL) {  printf("\nline 166: @currentPtr -> %d\n", &currentPtr); printf("\ncurrentPtr -> %d\n", currentPtr); //printf("\n@currentPtr->data -> %d\n", currentPtr->data); printf("\ncurrentPtr->nextPtr -> %d\n", currentPtr->nextPtr);
         printf("%c --> ", currentPtr->data);
         currentPtr = currentPtr->nextPtr;   
      } 

      puts("NULL\n");
   } 
} 




/**************************************************************************
 * (C) Copyright 1992-2015 by Deitel & Associates, Inc. and               *
 * Pearson Education, Inc. All Rights Reserved.                           *
 *                                                                        *
 * DISCLAIMER: The authors and publisher of this book have used their     *
 * best efforts in preparing the book. These efforts include the          *
 * development, research, and testing of the theories and programs        *
 * to determine their effectiveness. The authors and publisher make       *
 * no warranty of any kind, expressed or implied, with regard to these    *
 * programs or to the documentation contained in these books. The authors *
 * and publisher shall not be liable in any event for incidental or       *
 * consequential damages in connection with, or arising out of, the       *
 * furnishing, performance, or use of these programs.                     *
 *************************************************************************/
