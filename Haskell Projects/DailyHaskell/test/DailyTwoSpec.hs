module DailyTwoSpec where

import Test.Hspec
import DailyTwo

spec :: Spec
spec = do
    describe "every4th" $ do
        it "produces the 5th element of [1..10]" $
            every4th [1..10] `shouldBe` [4,8]
        it "produces the 5th element of [7..15]" $
            every4th [7..15] `shouldBe` [10,14]
        it "produces the 5th element of [5..20]" $
            every4th [5..20] `shouldBe` [8, 12, 16, 20]
    
    describe "tupleDotQuotient" $ do
        it "produces the dot Quotient of [1,2,3,4] [1,2,3,4]" $
            tupleDotQuotient [1,2,3,4] [1,2,3,4] `shouldBe` 4
        it "produces the dot Quotient of (2,6,2) (1,2,3)" $
            tupleDotQuotient [2,6,2] [1,2,3] `shouldBe` 5
        it "produces the dot Quotient of (1,1,1) (6,7,8)" $
            tupleDotQuotient [1,1,1] [6,7,8] `shouldBe` 0

    describe "appendToEach" $ do
        it "produces the append '!!!' [ 'Hello', 'Goodbye' ]" $
            appendToEach "!!!" [ "Hello", "Goodbye" ] `shouldBe` ["Hello!!!","Goodbye!!!"]
        it "produces the append '$$&&' [ 'Cash', 'Money', 'Beaver' ]" $
            appendToEach "$$&&" [ "Cash", "Money", "Beaver" ] `shouldBe` ["Cash$$&&","Money$$&&","Beaver$$&&"]
    -- Don't think this needs extensive testing
        
    describe "toSetList" $ do
        it "produces set from [5, 1, 2, 3, 3, 4, 5, 5]" $
            toSetList [5, 1, 2, 3, 3, 4, 5, 5] `shouldBe` [1,2,3,4,5]
        it "produces set from [1..10]" $
            toSetList [1..10] `shouldBe` [1,2,3,4,5,6,7,8,9,10]
        it "produces set from [7,13,4,9,10,3,1,14,5,8,1,1,1]" $
            toSetList [7,13,4,9,10,3,1,14,5,8,1,1,1] `shouldBe` [7,13,4,9,10,3,14,5,8,1]