module WeeklyThreeSpec where

import Test.Hspec
import WeeklyThree

spec :: Spec
spec = do
    describe "Vec +" $ do
        it "adds vectors" $
            Vec [1.0,2.0,3.0,4.0] + Vec [1.0,2.0,3.0,4.0] `shouldBe` Vec [2.0,4.0,6.0,8.0]
        it "adds vectors" $
            Vec [1.0,2.0,-3.0,4.0] + Vec [8.0,2.0,3.0,4.0] `shouldBe` Vec [9.0,4.0,0.0,8.0]
    
    describe "Vec -" $ do
        it "negative vectors" $
            Vec [1.0,2.0,3.0,4.0] - Vec [1.0,2.0,3.0,4.0] `shouldBe` Vec [0.0,0.0,0.0,0.0]
        it "negative vectors" $
            Vec [1.0,2.0,3.0,4.0] - Vec [1.0,2.0,3.0,4.0] `shouldBe` Vec [0.0,0.0,0.0,0.0]

    describe "Vec *" $ do
        it "mult vectors" $
            Vec [1.0,2.0,3.0,4.0] * Vec [1.0,2.0,3.0,4.0] `shouldBe` Vec [1.0,4.0,9.0,16.0]
        it "mult vectors" $
            Vec [1.0,2.0,3.0,4.0] * Vec [1.0,2.0,3.0,4.0] `shouldBe` Vec [1.0,4.0,9.0,16.0]

    describe "Vec abs" $ do
        it "absolute vectors" $
            abs (Vec [1.0,2.0,3.0,4.0]) `shouldBe` Vec [1.0,2.0,3.0,4.0]
        it "absolute vectors" $
            abs (Vec [1.0,2.0,-3.0,4.0]) `shouldBe` Vec [1.0,2.0,3.0,4.0]

    describe "signum" $ do
        it "signs vector" $
            signum (Vec [1.0,2.0,3.0,4.0]) `shouldBe` Vec [1.0,1.0,1.0,1.0]
        it "signs vector" $
            signum (Vec [1.0,2.0,-3.0,4.0]) `shouldBe` Vec [1.0,1.0,-1.0,1.0]

    describe "fromInteger" $ do
        it "doubles integer" $
            fromInteger 5 `shouldBe` 5
        it "doubles integer" $
            fromInteger 6 `shouldBe` 6

    describe "equal" $ do
        it "sees if equal" $
            Vec ([1.0,2.0,3.0,4.0]) == Vec ([1.0,2.0,3.0,4.0]) `shouldBe` True
        it "sees if equal" $
            Vec ([1.0,2.0,3.0,4.0]) == Vec ([1.0,2.0,4.0,4.0]) `shouldBe` False

    describe "not equal" $ do
        it "sees if equal" $
            Vec ([1.0,2.0,3.0,4.0]) /= Vec ([1.0,2.0,3.0,4.0]) `shouldBe` False
        it "sees if equal" $
            Vec ([1.0,2.0,3.0,4.0]) /= Vec ([1.0,2.0,4.0,4.0]) `shouldBe` True

    describe "compare" $ do
        it "compares vectors" $
            compare (Vec ([1.0,2.0,3.0,4.0])) (Vec ([1.0,2.0,3.0,4.0])) `shouldBe` EQ
        it "compares vectors" $
            compare (Vec ([1.0,2.0,2.0,4.0])) (Vec ([1.0,2.0,3.0,4.0])) `shouldBe` LT

    describe "less than" $ do
        it "compares vectors" $
            (Vec ([1.0,2.0,3.0,4.0])) <= (Vec ([1.0,2.0,3.0,4.0])) `shouldBe` True
        it "compares vectors" $
            (Vec ([1.0,2.0,3.0,4.0])) <= (Vec ([5.0,2.0,3.0,4.0])) `shouldBe` True

    describe "magnitude" $ do
        it "compares vectors" $
            magnitude (Vec ([1.0,2.0,3.0,4.0])) `shouldBe` 5.477225575051661
        it "compares vectors" $
            magnitude (Vec ([5.0,2.0,3.0,4.0])) `shouldBe` 7.3484692283495345

    describe "semigroup" $ do
        it "semigroups" $
            (<>) (Vec [1.0,2.0,3.0]) (Vec [2.0,3.0,4.0]) `shouldBe` Vec [1.0,2.0,3.0,2.0,3.0,4.0]
        it "semigroups" $
            (<>) (Vec [1.0,2.0,3.0]) (Vec [2.0,-3.0,4.0]) `shouldBe` Vec [1.0,2.0,3.0,2.0,-3.0,4.0]

    describe "monoid" $ do
        it "monoid" $
            mempty `shouldBe` ()

    