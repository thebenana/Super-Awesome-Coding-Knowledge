#include "linkedList.h"

/*int main(){

}*/

LinkedList* llCreate() {
  return NULL;

}

int isEmpty(LinkedList* ll) {
  return (ll == NULL);

}

void llDisplay(LinkedList* ll) {
  
  printf("[");

  while(ll != NULL) {
    printf("%d ", ll->n->c);
    ll = ll->next;

  }
  printf("]\n");
  
}

void llAdd(LinkedList** ll, tnode* node) {
  LinkedList* newNode = (LinkedList*) malloc(sizeof(LinkedList));
  newNode->n = node;
  newNode->next = NULL;

  LinkedList* llist = (*ll);

  if(llist == NULL) *ll = newNode;
  else {
    while(llist->next != NULL) {
      llist = llist->next;

    }
    llist->next = newNode;
    
  }

}

void llFree(LinkedList* ll) {
  LinkedList* llist = ll;

  while(llist != NULL) {
    LinkedList* oldList = llist;
    llist = llist->next;
    free(oldList);

  }

}

void llAddInOrder(LinkedList** ll, tnode* node) {
  LinkedList* newNode = (LinkedList*) malloc(sizeof(LinkedList));
  newNode->n = node;
  newNode->next = NULL;
  
  LinkedList* llist = *ll; 
  LinkedList* prev = NULL; //setting a previous pointer 
  
  if(llist == NULL) *ll = newNode; //checks if the list is empty then add first node
  else if(newNode->n->weight <= llist->n->weight){
    newNode->next = *ll;
    *ll = newNode;
    
  }
  else {
    while(llist != NULL && newNode->n->weight > llist->n->weight) {
      prev = llist;
      llist = llist->next;

    }
    prev->next = newNode;
    newNode->next = llist;
  }
  
}

tnode* removeFirst(LinkedList** ll) {
  if(*ll == NULL) return NULL;

  LinkedList* first = *ll;
  *ll = (*ll)->next;

  return first->n;

}
