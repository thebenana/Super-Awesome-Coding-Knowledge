{-# OPTIONS_GHC -Wno-incomplete-patterns #-}
module WeeklyThree where

data Vec = Vec [Double] deriving (Show)

-- uses functions to combine every element of the vector

instance Num Vec where
    (Vec a) + (Vec b) = Vec (zipWith (+) a b)
    (Vec a) * (Vec b) = Vec (zipWith (*) a b)
    (Vec a) - (Vec b) = Vec (zipWith (-) a b)
    abs (Vec a) = Vec (map (abs) a)
    signum (Vec a) = Vec (map (signum) a)
    fromInteger a = Vec [fromInteger a]

instance Eq Vec where
    (Vec a) == (Vec b) = and (zipWith (==) a b)
    (Vec a) /= (Vec b) = or (zipWith (/=) a b)

instance Ord Vec where
    compare (Vec a) (Vec b) = compare a b
    (Vec a) <= (Vec b) = a <= b

-- creates the magnitude typeclass

class VecT a where
    magnitude :: a -> Double

-- uses the magnitude equation to instantiate the function

instance VecT Vec where
    magnitude (Vec a) = sqrt (foldr (+) 0 (map (**2) a))

-- smigroup default append

instance Semigroup Vec where
    (<>) (Vec a) (Vec b) = Vec (a ++ b)

-- monoid empty case

instance Monoid Vec where 
    mempty = Vec (repeat 0.0)
