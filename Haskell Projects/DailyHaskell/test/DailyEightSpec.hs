module DailyEightSpec where

import Test.Hspec
import DailyEight

spec :: Spec
spec = do 

    let eventA = Event {name = "eventA", day = 1, month = "Jan", year = 2020, xlocation = 1.0, ylocation = 4.0}
    let eventB = Event {name = "eventB", day = 2, month = "Feb", year = 2021, xlocation = 2.0, ylocation = 8.0}
    let eventC = Event {name = "eventC", day = 4, month = "Mar", year = 2022, xlocation = 4.0, ylocation = 16.0}
    let eventD = Event {name = "eventD", day = 8, month = "Apr", year = 2023, xlocation = 8.0, ylocation = 2.0}

    describe "inYear" $ do
        it "checks if Event is in Year" $
            inYear 2021 [] `shouldBe` []
        it "checks if Event is in Year" $
            inYear 2021 [eventA, eventB, eventC, eventD] `shouldBe` [eventB]
        it "checks if Event is in Year" $
            inYear 2018 [eventA, eventB, eventC, eventD] `shouldBe` []

    describe "inDayRange" $ do
        it "checks if Event is in day range" $
            inDayRange 1 4 [] `shouldBe` []
        it "checks if Event is in day range" $
            inDayRange 1 4 [eventA, eventB, eventC, eventD] `shouldBe` ["eventA", "eventB", "eventC"]
        it "checks if Event is in day range" $
            inDayRange 30 31 [eventA, eventB, eventC, eventD] `shouldBe` []

    describe "inArea" $ do
        it "checks if Event is in Area" $
            inArea "eventA" 1.0 2.0 3.0 4.0 [] `shouldBe` []
        it "checks if Event is in Area" $
            inArea "eventA" 1.0 2.0 3.0 4.0 [eventA, eventB, eventC, eventD] `shouldBe` [eventA]
        it "checks if Event is in Area" $
            inArea "random" 1.0 2.0 3.0 4.0 [eventA, eventB, eventC, eventD] `shouldBe` []

    