{-# OPTIONS_GHC -Wno-incomplete-patterns #-}
module DailyNine where

-- firstFunctorLaw has typeclass Eq to define equality
-- it takes in a Functor and tests to see if it follows the first law, then outputs a Bool

firstFunctorLaw :: (Eq (f a), Functor f) => f a -> Bool
firstFunctorLaw a
    | fmap (id) a == id a = True
    | otherwise = False

-- secondFunctorLaw has typeclass Eq to define equality
-- it takes in two functions and uses the second law to compose them, producing a Bool

secondFunctorLaw :: (Eq (f c), Functor f) => (b -> c) -> (a -> b) -> f a -> Bool
secondFunctorLaw a b c
    | fmap (a . b) c == fmap a (fmap b c) = True
    | otherwise = False

-- helper functions to help with testing secondFunctorLaw under Either String (Maybe Integer)
-- have types Maybe Int to instantiate Ints and produce Bools accordingly

bsHelperOne :: Maybe Integer -> Maybe Integer
bsHelperOne a = Just 2

bsHelperTwo :: Maybe Integer -> Bool
bsHelperTwo a 
    | a == Just 2 = True
    | otherwise = False