module DailyNineSpec where

import Test.Hspec
import DailyNine
import Data.Char

spec :: Spec
spec = do
    describe "firstFunctorLaw" $ do
        it "tests to see if abides first functor law" $
            firstFunctorLaw (Just ('c', 35)) `shouldBe` True
        it "tests to see if abides first functor law" $
            firstFunctorLaw [2, 3, 5, 7, 11] `shouldBe` True

    describe "secondFunctorLaw" $ do
        it "tests to see if abides second functor law" $
            secondFunctorLaw chr (+96) [2,3,5,7,11] `shouldBe` True
        it "tests to see if abides second functor law" $
            secondFunctorLaw isAlpha fst (Just ('c', 35)) `shouldBe` True

    describe "tests for question 3" $ do
        it "tests Either String (Maybe Integer) to see if it follows functor laws" $
            firstFunctorLaw (Right Nothing :: Either String (Maybe Integer)) `shouldBe` True
        it "tests Either String (Maybe Integer) to see if it follows functor laws" $
            firstFunctorLaw (Right (Just 4) :: Either String (Maybe Integer)) `shouldBe` True
        it "tests Either String (Maybe Integer) to see if it follows functor laws" $
            firstFunctorLaw (Left "test" :: Either String (Maybe Integer)) `shouldBe` True

    describe "tests for question 3" $ do
        it "tests Either String (Maybe Integer) to see if it follows functor laws" $
            secondFunctorLaw bsHelperTwo bsHelperOne (Right Nothing :: Either String (Maybe Integer)) `shouldBe` True
        it "tests Either String (Maybe Integer) to see if it follows functor laws" $
            secondFunctorLaw bsHelperTwo bsHelperOne (Right (Just 4) :: Either String (Maybe Integer)) `shouldBe` True
        it "tests Either String (Maybe Integer) to see if it follows functor laws" $
            secondFunctorLaw bsHelperTwo bsHelperOne (Left "test" :: Either String (Maybe Integer)) `shouldBe` True