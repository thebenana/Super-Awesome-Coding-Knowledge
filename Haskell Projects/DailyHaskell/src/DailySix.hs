{-# OPTIONS_GHC -Wno-incomplete-patterns #-}
module DailySix where

-- shorterThan consumes a value and a list of words and filters out words with a length less than or equal to the value

shorterThan :: Int -> [String] -> [String]
shorterThan a [] = []
shorterThan a (x:xs) = filter (\x -> length x <= a) (x:xs)

-- removeMultiples consumes a value and a list of Ints and filters out Ints that are multiples (or mod /= 0) of the value

removeMultiples :: Int -> [Int] -> [Int]
removeMultiples a [] = []
removeMultiples a (x:xs) = filter (\x -> x `mod` a /= 0) (x:xs)

-- onlyJust takes a list of Maybe and removes all Nothings from the list

onlyJust :: Eq a => [Maybe a] -> [Maybe a]
onlyJust [] = []
onlyJust (x:xs) = filter (/= Nothing) (x:xs)