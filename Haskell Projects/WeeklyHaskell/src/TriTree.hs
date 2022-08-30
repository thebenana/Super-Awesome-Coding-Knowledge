{-# OPTIONS_GHC -Wno-incomplete-patterns #-}
module TriTree where

data TriTree a = Empty | 
                 NodeOne a (TriTree a) (TriTree a) (TriTree a) | 
                 NodeTwo a a (TriTree a) (TriTree a) (TriTree a) 
                 deriving (Eq, Show)

-- search takes a search value and a TriTree and iterates recursively until if finds said value (false if otherwise)

search :: Ord a => a -> TriTree a -> Bool
search a Empty = False

-- **** both nodes have to be searched according to their children **** --
search a (NodeOne sVal left middle right)
    | a == sVal = True 
    | a < sVal = search a left
    | a > sVal = search a middle
    | otherwise = False
search a (NodeTwo sValA sValB left middle right)
    | a == sValA || a == sValB = True 
    | a < sValA = search a left
    | a > sValB = search a right
    | a < sValB && a > sValA = search a middle
    | otherwise = False

-- insert takes a value and a TriTree and inserts the value into the tree based on if it's less/greater than the current Node value

insert :: (Ord a) => a -> TriTree a -> TriTree a
insert a Empty = NodeOne a Empty Empty Empty
insert newNode (NodeOne currentNode left middle right)
    | newNode == currentNode = NodeTwo newNode currentNode left middle right
    | newNode < currentNode = NodeTwo newNode currentNode left middle right
    | newNode > currentNode = NodeTwo currentNode newNode left middle right
insert newNode (NodeTwo currentLeft currentRight left middle right)
    | newNode == currentLeft || newNode == currentRight = NodeTwo currentLeft currentRight (insert newNode left) middle right
    | newNode < currentLeft = NodeTwo currentLeft currentRight (insert newNode left) middle right
    | newNode > currentRight = NodeTwo currentLeft currentRight left middle (insert newNode right)
    | otherwise = NodeTwo currentLeft currentRight left (insert newNode middle) right

-- insertList takes a list of Ordinals and inserts them into a tree (either Empty or filled with values) based on the insert function parameters

insertList :: (Ord a) => [a] -> TriTree a -> TriTree a
insertList [] a = a
insertList (x:xs) a = foldr insert a (x:xs)

-- identical takes two TriTrees and evaluates whether or not they are exactly the same

identical :: Eq a => TriTree a -> TriTree a -> Bool

-- **** base cases **** --
identical (NodeOne a left middle right) Empty = False                    
identical (NodeTwo a b left middle right) Empty = False
identical Empty (NodeOne a left middle right) = False
identical Empty (NodeTwo a b left middle right) = False

identical Empty Empty = True

identical (NodeOne a leftOne middleOne rightOne) (NodeTwo b c leftTwo middleTwo rightTwo) = False
identical (NodeTwo b c leftTwo middleTwo rightTwo) (NodeOne a leftOne middleOne rightOne) = False 

-- **** iterates through trees comparing values **** --
identical (NodeOne a left middle right) (NodeOne b leftOne middleOne rightOne)
    | a == b = identical left leftOne && identical middle middleOne
    | otherwise = False 
identical (NodeTwo a b left middle right) (NodeTwo c d leftTwo middleTwo rightTwo)
    | a == c && b == d = identical left leftTwo && identical middle middleTwo && identical right rightTwo
    | otherwise = False

-- treeMap takes a function with a parameter and applies it to every Node value in a TriTree

treeMap :: (a -> b) -> TriTree a -> TriTree b
treeMap _ Empty = Empty
treeMap f (NodeOne a left middle right) = NodeOne (f a) (treeMap f left) (treeMap f middle) (treeMap f right)
treeMap f (NodeTwo a b left middle right) = NodeTwo (f a) (f b) (treeMap f left) (treeMap f middle) (treeMap f right)

-- treeFoldPreOrder takes a function with a value after and applies it to every Node value in the TriTree, producing a value

treeFoldPreOrder :: (a -> a -> a) -> a -> TriTree a -> a
treeFoldPreOrder _ x Empty = x
treeFoldPreOrder f x (NodeOne b left middle right) = treeFoldPreOrder f (treeFoldPreOrder f (treeFoldPreOrder f (f x b) left) middle) right
treeFoldPreOrder f x (NodeTwo b c left middle right) = treeFoldPreOrder f (treeFoldPreOrder f (treeFoldPreOrder f (f (f x b) c) left) middle) right

-- treeFoldInOrder is the same as treeFoldPreOrder but iterates starting with the left value and branch, then middle, etc.

treeFoldInOrder :: (a -> a -> a) -> a -> TriTree a -> a
treeFoldInOrder _ x Empty = x
treeFoldInOrder f x (NodeOne b left middle right) = treeFoldInOrder f (treeFoldInOrder f (f b (treeFoldInOrder f x left)) middle) right
treeFoldInOrder f x (NodeTwo b c left middle right) = treeFoldInOrder f (f c (treeFoldInOrder f (f b (treeFoldInOrder f x left)) middle)) right

-- treeFoldPostOrder is the same as treeFoldInOrder but iterates starting with the subtrees, then jumps to the root values (left to right)

treeFoldPostOrder :: (a -> a -> a) -> a -> TriTree a -> a
treeFoldPostOrder _ x Empty = x
treeFoldPostOrder f x (NodeOne a left middle right) = f a (treeFoldPostOrder f (treeFoldPostOrder f (treeFoldPostOrder f x left) middle) right)
treeFoldPostOrder f x (NodeTwo a b left middle right) = f b (f a (treeFoldPostOrder f (treeFoldPostOrder f (treeFoldPostOrder f x left) middle) right))