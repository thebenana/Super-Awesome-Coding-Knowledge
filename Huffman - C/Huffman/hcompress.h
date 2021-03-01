#include "linkedList.h"

tnode* createFreqTable(char* txt);
tnode* createHuffmanTree(tnode* leafNodes);
void encodeFile(char* argv, tnode* leafNodes);
void decodeFile(char* argv, tnode* treeRoot);
tnode* findLeafNode(tnode* troot, int c);
unsigned char reverseBitSet(unsigned char a);
