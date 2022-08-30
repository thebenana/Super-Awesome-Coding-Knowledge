{-# OPTIONS_GHC -Wno-incomplete-patterns #-}
module DailyFive where
import Data.Char (isLower)

-- multPairs takes a list of Doubles and multiplies x and y to make a new list of Ints
-- type Num to handle all cases but could be changed to Int easily

multPairs :: Num a => [(a, a)] -> [a]
multPairs [] = []
multPairs (x:xs) = map (\(x, y) -> x * y) (x:xs)

-- squareList takes a list of Ints and produces a list of Pairs, including the original Int and its square
-- is type Num to deal with not just ints, but could be changed to fit just Ints

squareList :: Num b => [b] -> [(b, b)]
squareList [] = []
squareList (x:xs) = map (\x -> (x, x * x)) (x:xs)

-- findLowercase takes a list of Strings and creates a list of Bools based on if the String is lowercase
-- type [[Char]] to handle list of list of Chars

findLowercase :: [[Char]] -> [Bool]
findLowercase [] = []
findLowercase (x:xs) = map (\(y:ys) -> isLower y) (x:xs)