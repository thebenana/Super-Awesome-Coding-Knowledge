{-# OPTIONS_GHC -Wno-incomplete-patterns #-}
module DailyTwo where

-- every4th takes the 5th element using pattern matching given a non-empty list
-- Type: [Integer] -> [Integer]

every4th :: [Integer] -> [Integer]
every4th [] = []
every4th [_, _, _] = []
every4th [_, _] = []
every4th [_] = []
every4th ( _ : _ : _ : f : es) = f : every4th es

-- tupleDotQuotient takes two given lists and multiplies x1 by y1..xn by yn, then add them to get the dot Quotient
-- Type: [Integer] -> [Integer] -> Integer

tupleDotQuotient :: [Integer] -> [Integer] -> Integer
tupleDotQuotient [] [] = 0
tupleDotQuotient (x:xs) (y:ys) = x `div` y + tupleDotQuotient xs ys

-- appendToEach takes a given expression and list of words/Strings and adds the expression to the end of each index in that list
-- Type: String -> [String] -> [String]

appendToEach :: String -> [String] -> [String]
appendToEach x [] = []
appendToEach y (x:xs) = (x ++ y) : appendToEach y xs

-- toSetList takes a given list and removes the duplicates values
-- Type: Eq a => [a] -> [a] or [Integer] -> [Integer]

toSetList :: [Integer] -> [Integer]
toSetList [] = []
toSetList (x:xs) 
    | x `elem` xs = toSetList xs 
    | otherwise = x : toSetList xs