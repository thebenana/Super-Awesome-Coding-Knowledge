module EvalSpec where


import Test.Hspec
import Parser
import Expr
import MiniRacketParser

import Eval
import Error

type ParseResult = Either ErrorT (Expr, String)

spec :: Spec
spec = do
    describe "eval expressions" $ do
        it "evaluates number: 1235" $ 
            evalStr "1235" `shouldBe` Right (IntVal 1235)
        it "evaluates negative numbers: -12235" $
            evalStr "-12235" `shouldBe` Right (IntVal (-12235))
        it "evaluates true" $
            evalStr "true" `shouldBe` Right (BoolVal True)
        it "evaluates false" $
            evalStr "false" `shouldBe` Right (BoolVal False)

        it "evaluates and" $
            evalStr "(and true (and false true) true)" `shouldBe` Right (BoolVal False)
        it "evaluates or" $
            evalStr "(or true (or false true) true)" `shouldBe` Right (BoolVal True)

        it "evaluates not" $
            evalStr "(not false)" `shouldBe` Right (BoolVal True)

        it "evaluates Eq" $
            evalStr "(equal? 4 4)" `shouldBe` Right (BoolVal True)
        it "evaluates Lt" $
            evalStr "(< 5 4)" `shouldBe` Right (BoolVal False)

        it "evaluates MATH" $
            evalStr "(+ 5 4)" `shouldBe` Right (IntVal 9)
        it "evaluates MATH" $
            evalStr "(- 5 4)" `shouldBe` Right (IntVal 1)
        it "evaluates MATH" $
            evalStr "(`div` 8 4)" `shouldBe` Right (IntVal 2)
        it "evaluates MATH" $
            evalStr "(`mod` 1 1)" `shouldBe` Right (IntVal 0)
        it "evaluates MATH" $
            evalStr "(* 5 4)" `shouldBe` Right (IntVal 20)
        