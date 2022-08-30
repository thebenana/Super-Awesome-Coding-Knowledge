module DailyThreeSpec where

import Test.Hspec
import DailyThree

spec :: Spec
spec = do
    describe "removeAllExcept" $ do
        it "produces the new set " $
            removeAllExcept 'a' ['b', 'a', 'c', 'a'] `shouldBe` "aa"
        it "produces the new set " $
            removeAllExcept 1 [2, 3, 4, 1] `shouldBe` [1]
        it "produces the new set " $
            removeAllExcept 'b' ['b', 'a', 'c', 'a'] `shouldBe` "b"
    
    describe "countOccurrences" $ do
        it "produces the number of occurences" $
            countOccurrences 'a' ['a', 'b', 'a', 'c'] `shouldBe` 2
        it "produces the number of occurences" $
            countOccurrences 1 [2, 4, 5, 2] `shouldBe` 0
        it "produces the number of occurences" $
            countOccurrences 'b' ['a', 'b', 'a', 'c'] `shouldBe` 1

    describe "substitute" $ do
        it "substitutes first for second" $
            substitute 3 4 [1, 2, 3, 4] `shouldBe` [1, 2, 4, 4]
        it "substitutes first for second" $
            substitute 2 7 [1, 2, 3, 4] `shouldBe` [1, 7, 3, 4]
        it "substitutes first for second" $
            substitute 3 5 [1, 2, 3, 3, 3, 4] `shouldBe` [1, 2, 5, 5, 5, 4]