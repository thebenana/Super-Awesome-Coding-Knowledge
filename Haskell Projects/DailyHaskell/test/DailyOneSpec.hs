module DailyOneSpec where

import Test.Hspec
import DailyOne

spec :: Spec
spec = do
    describe "quadratic" $ do
        it "produces the quadratic 0" $
            quadratic 0 0 0 0 `shouldBe` 0
        it "produces the quadratic 57" $
            quadratic 1 2 3 4 `shouldBe` 57
        it "produces the quadratic 3" $
            quadratic 1 1 1 1 `shouldBe` 3
    
    describe "scaleVector" $ do
        it "produces the vector (15,20)" $
            scaleVector 5 (3,4) `shouldBe` (15,20)
        it "produces the vector 6,8" $
            scaleVector 2 (3,4) `shouldBe` (6,8)
        it "produces the vector 3,4" $
            scaleVector 1 (3,4) `shouldBe` (3,4)

    describe "tripleDistance" $ do
        it "produces the distance 5.196152422706632" $
            tripleDistance (1, 2, 3) (4, 5, 6) `shouldBe` 5.196152422706632
        it "produces the distance 0" $
            tripleDistance (2, 2, 2) (2, 2, 2) `shouldBe` 0
        it "produces the distance 6.6332498" $
            tripleDistance (2, 2, 2) (4, 8, 4) `shouldBe` 6.6332498