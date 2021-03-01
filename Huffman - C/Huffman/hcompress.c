#include "hcompress.h"

int main(int argc, char* argv[]) {

  if(argc != 3) {
    printf("Error: The correct format is \"hcompress -e filename\"  or \"hcompress -d filename.huf\"\n"); fflush(stdout);
    exit(1);
  }
  
  tnode* leafNodes = createFreqTable("decind.txt");
  tnode* treeRoot = createHuffmanTree(leafNodes);
  
  if(strcmp(argv[1], "-e") == 0) {
    encodeFile(argv[2], leafNodes);

  } else decodeFile(argv[2], treeRoot);

  return 0;
}

tnode* createFreqTable(char* file) {
  
  tnode* freq = (tnode*) malloc(128 * sizeof(tnode));
  char c;
  
  for(int i = 0; i < 128; i++) { //assigning the necessary weights/values
    freq[i].left = NULL;
    freq[i].right = NULL;
    freq[i].parent = NULL;
    
    freq[i].c = i;
    freq[i].weight = 0;
  }

  freq[0].weight = 1; //will be used to determine when the file ends

  FILE* stream = fopen(file, "r"); //read text 
  
  while(fscanf(stream, "%c", &c) != EOF) {
    freq[c].weight += 1;

  }

  fclose(stream);
  return freq;
}

tnode* createHuffmanTree(tnode* leafNodes) {

  LinkedList* tree = llCreate();  
  tnode* root;
  int size = 0;
    
  for(int i = 0; i < 128; i++) {
    if(leafNodes[i].weight >= 1) {
      size++;
      llAddInOrder(&tree, &leafNodes[i]); 
    }     
  }
 
  while(size > 1) { //while size > 1, link nodes in first and second link to root, then combine weights
    root = (tnode*) malloc(sizeof(tnode));
        
    root->left = removeFirst(&tree);
    root->right = removeFirst(&tree);
    root->weight = root->left->weight + root->right->weight;
    
    root->left->parent = root; //set parent to roots
    root->right->parent = root;
    
    llAddInOrder(&tree, root);
    size--; //decrease by 1 node
    
  }
  return root;
}

void encodeFile(char* argv, tnode* leafNodes) {
  
  FILE* readFile = fopen(argv, "r"); //read text
  strcat(argv, ".huf");
  
  FILE* writeFile = fopen(argv, "wb"); //write binary
  
  unsigned char bin;
  unsigned int b;
  int binC, bC, c;
  binC = bC = c = 0;
  bin = bin << 8; // create the bitset //need to redo this when putting new elements in
  tnode* newNode;
  tnode* p;

  do {
    c = fgetc(readFile);
    if (c == EOF) {
      newNode = &leafNodes[0];
    }// generate special end-of-encoding marker (character zero)
    else
      newNode = &leafNodes[c]; // normal character

    // Put bits into temporary buffer, since they'll be backwards (leaf-to-root)
    // Assumes leaves will never be more than 8 levels deep (generate more than 8 encoded bits)
    bC = 0;

    // The node's parent will be NULL if it's the root node.
    while (newNode->parent != NULL) {
      p = newNode->parent;
      if(p->left == newNode) {
	b = (b << 1) | 0; // new bit is a zero
      }
      else if(p->right == newNode) {
	b = (b << 1) | 1; // new bit is a one
      }
      bC += 1;
      // Move up to parent. If this was the root node, "no" will become NULL (the root has no parent) and then the while() will exit.
      newNode = p;
    }
    // Reverse the bits from this traversal so they're root-to-leaf in the "real" output buffer.
    while (bC > 0) {
      bin = bin << 1;
      // Peel bottom-most bit off of b and put it into bin's bottom-most bit.
      bin = bin | (b & 1);
      b = b >> 1;
      bC -= 1;
      binC++;
      if(binC == 8) {
	fputc(bin, writeFile);
	binC = 0;
      }
    }
  } while (c != EOF);

  if(binC != 0) {
    // Write partial bit buffer. Just move the important bits
    // to the top of the byte
    bin = bin << (8 - binC);

    //reverse bits
    fputc(bin, writeFile);
  }
  fclose(readFile);
  fclose(writeFile);

}

void decodeFile(char* argv, tnode* treeRoot) {
  FILE* readFile = fopen(argv, "rb"); //read bi
  char* strcopy = (char*) malloc((strlen(argv) + 5) * sizeof(char)); //5 is to account for ".dec" + end char
  strcpy(strcopy, argv); //copy
  strcat(strcopy, ".dec"); //create
  FILE* writeFile = fopen(strcopy, "w"); //write text
    
  unsigned char bitset, mask; //create vars for bitset
  int bitCount = 0;
  tnode* root;

  do { //do while until char at root == 0
    root = treeRoot;
    while(root->left != NULL || root->right != NULL) { //checking for leafNode
      if(bitCount == 0) {
	fscanf(readFile, "%c", &bitset);
	bitCount = 8; //assign 8 bits 
      }
      mask = bitset >> (bitCount - 1); //set mask with right shift to get value
      if((mask & 1) == 0) {
	root = root->left; //treeRoot at left
      }
      else root = root->right;
      bitCount--;
    }
    if(root->c != 0) fputc(root->c, writeFile); //writeFile with char at root
    
  }while(root->c != 0);
   
  fclose(readFile);
  fclose(writeFile);
}

tnode* findLeafNode(tnode* root, int c){ //finds leafNodes by checking left and right  
  tnode* newNode;
  
  if(root->c == c) return root;
  else if(root->left != NULL) {
    newNode = findLeafNode(root->left, c);
    if(newNode != NULL) return newNode;
  }

  if(root->right != NULL) {
    newNode = findLeafNode(root->right, c);
    if(newNode != NULL) return newNode;
  }
  return NULL;
  
}

unsigned char reverseBitSet(unsigned char a) { //have to reverse to make bitset
  unsigned char mask;

  for(int i = 0; i < 8; i++) {
    mask = mask << 1;
    mask = mask | (a & 1);
    a = a >> 1;

  }

  return mask;
  
}
