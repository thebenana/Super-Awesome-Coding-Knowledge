{-# OPTIONS_GHC -Wno-incomplete-patterns #-}
module DailyFour where

-- zip3Lists takes three triples and adds them to a new list of triples 
-- takes type containing three triples and outputs one List

zip3Lists :: [a] -> [b] -> [c] -> [(a, b, c)]
zip3Lists [] [] [] = []
zip3Lists (x:xs) (y:ys) (z:zs) = (x, y, z) : zip3Lists xs ys zs

------------------------------------------------------------------------------------------------------------

-- unzipTriples takes a list containing triples and exports a list of three tuples
-- takes type List containing three triples

unzipTriples :: [(x, y, z)] -> ([x], [y], [z])
unzipTriples [] = ([], [], [])

-- takes list and disects it, by extracting first x in each triple then moving on to first y, etc.
-- defines "remaining" in order to move onto next triple
unzipTriples ((x, y, z):xs) = (x : first remaining, y : second remaining, z : third remaining)
    where remaining = unzipTriples xs

-- handles taking first element
first :: (x, y, z) -> x
first (x, y, z) = x

-- handles taking second element
second :: (x, y, z) -> y
second (x, y, z) = y

-- handles taking third element
third :: (x, y, z) -> z
third (x, y, z) = z

------------------------------------------------------------------------------------------------------------

-- mergeSorted3 takes three lists and merges them together in a sorted order
-- has type Ord a => [a] -> [a] -> [a] -> [a] to take in sorted lists

mergeSorted3 :: Ord a => [a] -> [a] -> [a] -> [a]
mergeSorted3 [] [] [] = []

mergeSorted3 [] (x:xs) (y:ys) = if x >= y                           -- in case first list is blank, then add smaller value to new list
    then y : mergeSorted3 [] (x:xs) ys
    else x : mergeSorted3 [] xs (y:ys)
mergeSorted3 (x:xs) [] (y:ys) = if x >= y                           -- in case second list is blank, then add smaller value to new list
    then y : mergeSorted3 (x:xs) [] ys
    else x : mergeSorted3 xs [] (y:ys)
mergeSorted3 (x:xs) (y:ys) [] = if x >= y                           -- in case third list is blank, then add smaller value to new list
    then y : mergeSorted3 (x:xs) ys []
    else x : mergeSorted3 xs (y:ys) []

-- handles cases where there are two empty lists input (already sorted so sorting doesn't matter)
mergeSorted3 [] [] (x:xs) = x : mergeSorted3 [] [] xs               
mergeSorted3 [] (x:xs) [] = x : mergeSorted3 [] xs []
mergeSorted3 (x:xs) [] [] = x : mergeSorted3 xs [] []

-- handles remaining scenario where all ordered lists are full (merges and sorts based on smallest values of three lists)
mergeSorted3 (x:xs) (y:ys) (z:zs)
    | x <= y && x <= z = x : mergeSorted3 xs (y:ys) (z:zs)
    | y <= x && y <= z = y : mergeSorted3 (x:xs) ys (z:zs)
    | z <= y && z <= x = z : mergeSorted3 (x:xs) (y:ys) zs
