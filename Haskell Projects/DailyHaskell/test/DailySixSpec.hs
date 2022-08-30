module DailySixSpec where

import Test.Hspec
import DailySix

spec :: Spec 
spec = do
    describe "shorterThan" $ do
        it "finds words shorter than or equal to the value" $
            shorterThan 3 ["aaa","aa","aaaaa","b","bbb","bbbb"] `shouldBe` ["aaa","aa","b","bbb"]
        it "finds words shorter than or equal to the value" $
            shorterThan 0 ["aaa","aa","aaaaa","b","bbb","bbbb"] `shouldBe` []
        it "finds words shorter than or equal to the value" $
            shorterThan 3 [] `shouldBe` []
    
    describe "removeMultiples" $ do
        it "removes multiples of given value" $
            removeMultiples 5 [3,5,10,9,15] `shouldBe` [3,9]
        it "removes multiples of given value" $
            removeMultiples 10 [3,5,10,9,15,100] `shouldBe` [3,5,9,15]
        it "removes multiples of given value" $
            removeMultiples 5 [] `shouldBe` []

    describe "onlyJust" $ do
        it "removes Nothing from the list" $
            onlyJust [Nothing, Just 5, Nothing, Just 10] `shouldBe` [Just 5, Just 10]
        it "removes Nothing from the list" $
            onlyJust [Nothing, Just 5] `shouldBe` [Just 5]
        it "removes Nothing from the list" $
            onlyJust [Just 5, Just 5, Nothing] `shouldBe` [Just 5, Just 5]