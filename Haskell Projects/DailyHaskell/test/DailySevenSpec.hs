module DailySevenSpec where

import Test.Hspec
import DailySeven

spec :: Spec 
spec = do
    describe "createOneList" $ do
        it "takes a list of lists and creates one list" $
            createOneList [ [1,2], [3], [ ], [4, 5] ] `shouldBe` [1,2,3,4,5]
        it "takes a list of lists and creates one list" $
            createOneList [ ["bye","hello"], ["cool"], [ ], ["great"] ] `shouldBe` ["bye","hello","cool","great"]
        it "takes a list of lists and creates one list" $
            createOneList [[1]] `shouldBe` [1]

    describe "findLargest" $ do
        it "takes a list of Ints and finds the largest" $
            findLargest [3, 5,10, 9, 15] `shouldBe` 15
        it "takes a list of Ints and finds the largest" $
            findLargest [-3, 5,10, 9, 15] `shouldBe` 15
        it "takes a list of Ints and finds the largest" $
            findLargest [] `shouldBe` 0

    describe "allTrue" $ do
        it "returns True if all bools in the list are True" $
            allTrue [True,False] `shouldBe` False
        it "returns True if all bools in the list are True" $
            allTrue [True,True] `shouldBe` True
        it "returns True if all bools in the list are True" $
            allTrue [] `shouldBe` False

    