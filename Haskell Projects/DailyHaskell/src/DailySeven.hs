{-# OPTIONS_GHC -Wno-incomplete-patterns #-}
module DailySeven where

-- createOneList takes a list of lists of any type and converts them to a single list

createOneList :: [[a]] -> [a]
createOneList [] = []
createOneList [[]] = []
createOneList (x:xs) = foldl (++) x xs

-- findLargest takes a list of positive Ints and finds the largest of them

findLargest :: [Int] -> Int
findLargest [] = 0
findLargest xs = foldr max minBound xs

-- allTrue takes a list of Bools and returns True if there are only Trues in the list

allTrue :: [Bool] -> Bool
allTrue [] = False 
allTrue xs = foldr (&&) True xs
