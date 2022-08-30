module DailyFiveSpec where

import Test.Hspec
import DailyFive

spec :: Spec 
spec = do
    describe "multPairs" $ do
        it "takes a list of doubles and creates a list of its double" $
            multPairs [(1,2),(3,4),(5,6)] `shouldBe` [2,12,30]
        it "takes a list of doubles and creates a list of its double" $
            multPairs [] `shouldBe` []
        it "takes a list of doubles and creates a list of its double" $
            multPairs [(3,4),(5,6)] `shouldBe` [12,30]
    
    describe "squareList" $ do
        it "takes a list and creates a list of the original value and its square" $
            squareList [1,2,3,4,5] `shouldBe` [(1,1),(2,4),(3,9),(4,16),(5,25)]
        it "takes a list and creates a list of the original value and its square" $
            squareList [] `shouldBe` []
        it "takes a list and creates a list of the original value and its square" $
            squareList [-1,-2,-3,-4,-5] `shouldBe` [(-1,1),(-2,4),(-3,9),(-4,16),(-5,25)]

    describe "findLowercase" $ do
        it "takes a list of Strings and creates a list of Bools based on lowerCase" $
            findLowercase ["hello","Crepes","gOODBYE"] `shouldBe` [True,False,True]
        it "takes a list of Strings and creates a list of Bools based on lowerCase" $
            findLowercase [] `shouldBe` []
        it "takes a list of Strings and creates a list of Bools based on lowerCase" $
            findLowercase ["hel lo","Cre pes","gOODBYE"] `shouldBe` [True,False,True]