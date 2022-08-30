module DailyOne where

someFunc :: IO ()
someFunc = putStrLn "someFunc"


-- Using float to handle decimals if input

-- quadratic (takes four parameters of type Float and outputs the quadratic of type Float) 
quadratic :: Float -> Float -> Float -> Float -> Float
quadratic a b c x = a + (b * x) + (c * (x * x))

-- scaleVector (takes two parameters of type Float (one should be 2-tuple representing a two-dimensional vector) and outputs the scaled version of type Float) 
scaleVector :: Float -> (Float, Float) -> (Float, Float)
scaleVector x (y, z) = (y * x, z * x)

-- tripleDistance (takes two sets of coordinates (x1, y1, z1) (x2, y2, z2) of type Float and outputs the distance between them of type Float) 
tripleDistance :: (Float, Float, Float) -> (Float, Float, Float) -> Float
tripleDistance (x1, y1, z1) (x2, y2, z2) = sqrt ((x2 - x1)**2 + (y2 - y1)**2 + (z2 - z1)**2)