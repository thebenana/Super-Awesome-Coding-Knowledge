{-# OPTIONS_GHC -Wno-incomplete-patterns #-}
module DailyThree where

-- removesAllExcept removes all instances of NOT the input variable (or not equal to the given element)
-- accepts multiple types

removeAllExcept :: Eq a => a -> [a] -> [a]
removeAllExcept a [] = []
removeAllExcept a (x:xs)
    | a /= x = removeAllExcept a xs             -- checks for all elements that are NOT the input 
    | otherwise = x : removeAllExcept a xs      -- makes new list with updated values

-- countOccurrences counts how many times a given element occurrs in the set using otherwise logic
-- accepts multiple types equating to Int

countOccurrences :: Eq a => a -> [a] -> Int                
countOccurrences a [] = 0
countOccurrences a (x:xs)
    | a /= x = countOccurrences a xs
    | otherwise = 1 + countOccurrences a xs

-- substitute replaces all instances of the first element with the second element in a given set
-- accepts multiple types

substitute :: Eq a => a -> a -> [a] -> [a]
substitute _ _ [] = []
substitute a b (x:xs)
    | a == x = b : substitute a b xs
    | otherwise = x : substitute a b xs