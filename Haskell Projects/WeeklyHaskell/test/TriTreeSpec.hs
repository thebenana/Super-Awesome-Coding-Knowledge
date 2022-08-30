module TriTreeSpec where

import Test.Hspec
import TriTree

spec :: Spec
spec = do
    describe "search" $ do
        it "takes a value and a TriTree and searches for that value" $
            search 7 (NodeTwo 4 5 (NodeTwo 2 3 (NodeOne 1 Empty Empty Empty) Empty Empty) Empty Empty) `shouldBe` False
        it "takes a value and a TriTree and searches for that value" $
            search 3 (NodeTwo 4 5 (NodeTwo 2 3 (NodeOne 1 Empty Empty Empty) Empty Empty) Empty Empty) `shouldBe` True
        it "takes a value and a TriTree and searches for that value" $
            search 7 Empty `shouldBe` False

    describe "insert" $ do
        it "takes a value and inserts in based on the order of the TriTree" $
            insert 7 (NodeTwo 4 5 (NodeTwo 2 3 (NodeOne 1 Empty Empty Empty) Empty Empty) Empty Empty) `shouldBe` NodeTwo 4 5 (NodeTwo 2 3 (NodeOne 1 Empty Empty Empty) Empty Empty) Empty (NodeOne 7 Empty Empty Empty)
        it "takes a value and inserts in based on the order of the TriTree" $
            insert (-7) (NodeTwo 4 5 (NodeTwo 2 3 (NodeOne 1 Empty Empty Empty) Empty Empty) Empty Empty) `shouldBe` NodeTwo 4 5 (NodeTwo 2 3 (NodeTwo (-7) 1 Empty Empty Empty) Empty Empty) Empty Empty
        it "takes a value and inserts in based on the order of the TriTree" $
            insert 7 Empty `shouldBe` NodeOne 7 Empty Empty Empty

    describe "insertList" $ do
        it "takes a list and inserts in into a TriTree in order" $
            insertList [1..3] (NodeTwo 4 5 (NodeTwo 2 3 (NodeOne 1 Empty Empty Empty) Empty Empty) Empty Empty) `shouldBe` NodeTwo 4 5 (NodeTwo 2 3 (NodeTwo 1 3 (NodeOne 1 Empty Empty Empty) (NodeOne 2 Empty Empty Empty) Empty) Empty Empty) Empty Empty
        it "takes a list and inserts in into a TriTree in order" $
            insertList [] (NodeTwo 4 5 (NodeTwo 2 3 (NodeOne 1 Empty Empty Empty) Empty Empty) Empty Empty) `shouldBe` NodeTwo 4 5 (NodeTwo 2 3 (NodeOne 1 Empty Empty Empty) Empty Empty) Empty Empty
        it "takes a list and inserts in into a TriTree in order" $
            insertList [1..3] Empty `shouldBe` NodeTwo 2 3 (NodeOne 1 Empty Empty Empty) Empty Empty

    describe "identical" $ do
        it "takes two TriTrees and returns true if they are exactly the same" $
            identical (NodeTwo 4 5 (NodeTwo 2 3 (NodeOne 1 Empty Empty Empty) Empty Empty) Empty Empty) (NodeOne 4 Empty Empty Empty) `shouldBe` False
        it "takes two TriTrees and returns true if they are exactly the same" $
            identical (NodeTwo 4 5 (NodeTwo 2 3 (NodeOne 1 Empty Empty Empty) Empty Empty) Empty Empty) (NodeTwo 4 5 (NodeTwo 2 3 (NodeOne 1 Empty Empty Empty) Empty Empty) Empty Empty) `shouldBe` True
        it "takes two TriTrees and returns true if they are exactly the same" $
            identical Empty (NodeOne 4 Empty Empty Empty) `shouldBe` False

    describe "treeMap" $ do
        it "takes a function and a value and applies it to every Node" $
            treeMap (*2) (NodeTwo 4 5 (NodeTwo 2 3 (NodeOne 1 Empty Empty Empty) Empty Empty) Empty Empty) `shouldBe` NodeTwo 8 10 (NodeTwo 4 6 (NodeOne 2 Empty Empty Empty) Empty Empty) Empty Empty
        it "takes a function and a value and applies it to every Node" $
            treeMap (*2) Empty `shouldBe` Empty
        it "takes a function and a value and applies it to every Node" $
            treeMap (+(-9)) (NodeTwo 4 5 (NodeTwo 2 3 (NodeOne 1 Empty Empty Empty) Empty Empty) Empty Empty) `shouldBe` NodeTwo (-5) (-4) (NodeTwo (-7) (-6) (NodeOne (-8) Empty Empty Empty) Empty Empty) Empty Empty

    describe "treeFoldPreOrder" $ do
        it "takes a function and a value and applies it to every Node together producing a value" $
            treeFoldPreOrder (*) 3 (NodeTwo 4 5 (NodeTwo 3 5 (NodeOne 1 Empty Empty Empty) Empty Empty) Empty (NodeTwo 11 30 (NodeTwo 8 8 Empty Empty Empty) Empty Empty)) `shouldBe` 19008000
        it "takes a function and a value and applies it to every Node together producing a value" $
            treeFoldPreOrder (-) 30 (NodeTwo 4 5 (NodeTwo 3 5 (NodeOne 1 Empty Empty Empty) Empty Empty) Empty (NodeTwo 11 30 (NodeTwo 8 8 Empty Empty Empty) Empty Empty)) `shouldBe` (-45)
        it "takes a function and a value and applies it to every Node together producing a value" $
            treeFoldPreOrder (*) 3 Empty `shouldBe` 3

    describe "treeFoldInOrder" $ do
        it "takes a function and a value and applies it to every Node together producing a value" $
            treeFoldInOrder (*) 3 (NodeTwo 4 5 (NodeTwo 3 5 (NodeOne 1 Empty Empty Empty) Empty Empty) Empty (NodeTwo 11 30 (NodeTwo 8 8 Empty Empty Empty) Empty Empty)) `shouldBe` 19008000
        it "takes a function and a value and applies it to every Node together producing a value" $
            treeFoldInOrder (-) 30 (NodeTwo 4 5 (NodeTwo 3 5 (NodeOne 1 Empty Empty Empty) Empty Empty) Empty (NodeTwo 11 30 (NodeTwo 8 8 Empty Empty Empty) Empty Empty)) `shouldBe` (-7)
        it "takes a function and a value and applies it to every Node together producing a value" $
            treeFoldInOrder (*) 3 Empty `shouldBe` 3

    describe "treeFoldPostOrder" $ do
        it "takes a function and a value and applies it to every Node together producing a value" $
            treeFoldPostOrder (*) 3 (NodeTwo 4 5 (NodeTwo 3 5 (NodeOne 1 Empty Empty Empty) Empty Empty) Empty (NodeTwo 11 30 (NodeTwo 8 8 Empty Empty Empty) Empty Empty)) `shouldBe` 19008000
        it "takes a function and a value and applies it to every Node together producing a value" $
            treeFoldPostOrder (-) 30 (NodeTwo 4 5 (NodeTwo 3 5 (NodeOne 1 Empty Empty Empty) Empty Empty) Empty (NodeTwo 11 30 (NodeTwo 8 8 Empty Empty Empty) Empty Empty)) `shouldBe` (-7)
        it "takes a function and a value and applies it to every Node together producing a value" $
            treeFoldPostOrder (*) 3 Empty `shouldBe` 3