#ifndef list
#define list
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct tnode {
  double weight;
  int c;
  struct tnode* left;
  struct tnode* right;
  struct tnode* parent;

} tnode;

typedef struct LinkedList {
  tnode* n;
  struct LinkedList* next;

} LinkedList;

LinkedList*  llCreate();
int isEmpty(LinkedList* ll);
void llDisplay(LinkedList* ll);
void llAdd(LinkedList** ll, tnode* node);
void llFree(LinkedList* ll);
void llAddInOrder(LinkedList** ll, tnode* node);
tnode* removeFirst(LinkedList** ll);

#endif
