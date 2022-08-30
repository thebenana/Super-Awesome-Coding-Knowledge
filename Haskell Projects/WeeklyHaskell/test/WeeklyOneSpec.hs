module WeeklyOneSpec where

import Test.Hspec
import WeeklyOne

spec :: Spec
spec = do
    describe "removeChar" $ do
        it "takes an input variable r and removes that instance from a String" $
            removeChar 'r' "Rats Reap Rewards" `shouldBe` "Rats Reap Rewads"
        it "takes an input variable R and removes that instance from an empty String" $
            removeChar 'R' "" `shouldBe` ""
        it "takes an input variable ' ' and removes that instance from a String" $
            removeChar ' ' "Rats Reap Rewards" `shouldBe` "RatsReapRewards"

    describe "removeWhiteSpace" $ do
        it "takes a string and removes white space" $
            removeWhiteSpace "hello there" `shouldBe` "hellothere"
        it "takes a string and removes white space" $
            removeWhiteSpace "" `shouldBe` ""
        it "takes a string and removes white space" $
            removeWhiteSpace "hello \t          there            \n" `shouldBe` "hello\tthere"

    describe "removePunctuation" $ do
        it "takes a String and removes symbols" $
            removePunctuation "hello. there, and[ good)bye( to{ you}!" `shouldBe` "hello there and goodbye to you!"
        it "takes a String and removes symbols" $
            removePunctuation "" `shouldBe` ""
        it "takes a String and removes symbols" $
            removePunctuation "hello. there, and[      good)bye( {}{}{}{}[[[][][[to{ you}!" `shouldBe` "hello there and      goodbye to you!"

    describe "charsToAscii" $ do
        it "takes a String and converts the chars to their ASCII representation" $
            charsToAscii "hello" `shouldBe` [104,101,108,108,111]
        it "takes a String and converts the chars to their ASCII representation" $
            charsToAscii "" `shouldBe` []
        it "takes a String and converts the chars to their ASCII representation" $
            charsToAscii "hello. and, goodbye!" `shouldBe` [104,101,108,108,111,46,32,97,110,100,44,32,103,111,111,100,98,121,101,33]
    
    describe "asciiToChars" $ do
        it "takes a list of ASCII Ints and converts them to their char representation" $
            asciiToChars [104,101,108,108,111] `shouldBe` "hello"
        it "takes a list of ASCII Ints and converts them to their char representation" $
            asciiToChars [] `shouldBe` ""
        it "takes a list of ASCII Ints and converts them to their char representation (test negative)" $
            asciiToChars [-1,101,108,108,111] `shouldBe` "no negative ASCII allowed"

    describe "shiftInts" $ do
        it "takes a shift value (Int) and a List of Ints and shifts the Ints by the given shift bit" $
            shiftInts 4 [1,2,3,4,5] `shouldBe` [5,6,7,8,9]
        it "takes a shift value (Int) and a List of Ints and shifts the Ints by the given shift bit" $
            shiftInts 130 [1,2,3,4,5] `shouldBe` [3,4,5,6,7]
        it "takes a shift value (Int) and a List of Ints and shifts the Ints by the given shift bit" $
            shiftInts 2 [] `shouldBe` []

    describe "shiftMessage" $ do
        it "takes a String and encodes it by a shift value (uses negative shift to decode)" $
            shiftMessage 4 "hello" `shouldBe` "lipps"
        it "takes a String and encodes it by a shift value (uses negative shift to decode)" $
            shiftMessage 4 "" `shouldBe` ""
        it "takes a String and encodes it by a shift value (uses negative shift to decode)" $
            shiftMessage (-4) "lipps" `shouldBe` "hello"
        
        