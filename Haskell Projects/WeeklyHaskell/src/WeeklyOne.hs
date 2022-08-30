{-# OPTIONS_GHC -Wno-incomplete-patterns #-}
module WeeklyOne where

-- removeChar takes a char/num/etc and a list and removes the input char from the list
-- is type Eq to handle multiple types of input values

removeChar :: Eq a => a -> [a] -> [a]
removeChar _ [] = []
removeChar a (x:xs) 
    | a /= x = x : removeChar a xs
    | otherwise = removeChar a xs

-- removeWhiteSpace takes a list of Chars (or a String) and removes white spaces recursively

removeWhiteSpace :: [Char] -> [Char]
removeWhiteSpace [] = []
removeWhiteSpace (x:xs)
    | x == ' ' || x == '\n' = removeWhiteSpace xs
    | otherwise = x : removeWhiteSpace xs

-- removePunctuation takes a String and iterates through the string in order to find punctuation, removing all matching symbols

removePunctuation :: [Char] -> [Char]
removePunctuation [] = []
removePunctuation (x:xs)
    | x == '(' || x == ')' || x == '.' || x == ',' || x == '[' || x == ']' || x == '{' || x == '}' = removeChar x (removePunctuation xs)
    | otherwise = x : removePunctuation xs

-- charsToAscii takes a String and iterates through the list of chars, converting each one to it's ASCII value

charsToAscii :: Enum a => [a] -> [Int]
charsToAscii [] = []
charsToAscii (x:xs) = fromEnum x : charsToAscii xs

-- asciiToChars takes a list of ints and converts them to their char values
-- results in String because you must declare the type when using toEnum

asciiToChars :: [Int] -> [Char]
asciiToChars [] = []
asciiToChars (x:xs)
    | x < 0 = "no negative ASCII allowed"
    | otherwise = toEnum x : asciiToChars xs

-- shiftInts takes a Number and List of Numbers and shifts them by the input shift bit
-- it type Integral to support integer division through mod 

shiftInts :: Integral a => a -> [a] -> [a]
shiftInts _ [] = []
shiftInts a (x:xs) = ((x + a) `mod` 128) : shiftInts a xs

-- shiftMessage takes a shift value and String and encodes it by shifting the chars over by the given bit

shiftMessage :: Enum a => Int -> [a] -> [Char]
shiftMessage _ [] = []
shiftMessage a (x:xs) = asciiToChars (shiftInts a (charsToAscii (x:xs)))