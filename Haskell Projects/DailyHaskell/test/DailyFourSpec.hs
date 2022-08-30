module DailyFourSpec where

import Test.Hspec
import DailyFour

spec :: Spec
spec = do
    describe "zip3Lists" $ do
        it "takes 1 list of triples and creates 3 triples" $
            zip3Lists [1, 2, 3] [4, 5, 6] [7, 8, 9] `shouldBe` [(1,4,7),(2,5,8),(3,6,9)]
        it "takes 1 list of triples and creates 3 triples" $
            zip3Lists [1, 2, 3] ['a', 'b', 'c'] [4, 5, 6] `shouldBe` [(1,'a',4),(2,'b',5),(3,'c',6)]
        it "takes 1 list of triples and creates 3 triples" $
            zip3Lists [13, 2, 37] [45, 59, 6] [90, 90, 90] `shouldBe` [(13,45,90),(2,59,90),(37,6,90)]
    
    describe "unzipTriples" $ do
        it "takes 1 list of triples and outputs 3 tuples" $
            unzipTriples [ (1,2,3), (4, 5, 6), (7, 8, 9) ] `shouldBe` ( [1,4,7], [2, 5, 8], [3, 6, 9] )
        it "takes 1 list of triples and outputs 3 tuples" $
            unzipTriples [ (1, 1, 1), (4, 4, 4), (7, 7, 7) ] `shouldBe` ([1,4,7],[1,4,7],[1,4,7])
        it "takes 1 list of triples and outputs 3 tuples" $
            unzipTriples [(1,'a',4),(2,'b',5),(3,'c',6)] `shouldBe` ([1,2,3],"abc",[4,5,6])

    describe "mergeSorted3" $ do
        it "takes 3 lists in order and merges them, sorting along the way" $
            mergeSorted3 [2, 3, 5] [1, 8] [-1, 0, 4, 10] `shouldBe` [-1, 0, 1, 2, 3, 4, 5, 8, 10]
        it "takes 3 lists in order and merges them, sorting along the way" $
            mergeSorted3 [] [1, 8] [-1, 0, 4, 10] `shouldBe` [-1,0,1,4,8,10]
        it "takes 3 lists in order and merges them, sorting along the way" $
            mergeSorted3 [] [] [-1, 0, 4, 10] `shouldBe` [-1,0,4,10]
        
    