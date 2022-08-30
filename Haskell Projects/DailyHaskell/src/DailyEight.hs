{-# OPTIONS_GHC -Wno-incomplete-patterns #-}
module DailyEight where

data Event = Event {name :: String, day :: Int, month :: String,
year :: Int, xlocation :: Float, ylocation :: Float} deriving (Eq, Show)

-- checks if Event happened in year
-- type Int to list of Event
-- produces Event

inYear :: Int -> [Event] -> [Event]
inYear _ [] = []
inYear yr lst = filter (\x -> (year x == yr)) lst

-- checks if Event happened in number of days
-- type Int Int to accept day range and finds Event
-- produces Event in String form

inDayRange :: Int -> Int -> [Event] -> [String]
inDayRange _ _ [] = []
inDayRange start end lst = map (getName) (filter (\x -> (day x >= start && day x <= end)) lst)

-- helped for inDayRange to handle name

getName :: Event -> String
getName ev = name ev

-- checks if Event happened at location
-- type String to accept name and Float to accept location then returns Event
-- produces Event

inArea :: String -> Float -> Float -> Float -> Float -> [Event] -> [Event]
inArea _ _ _ _ _ [] = []
inArea str lx ux ly uy lst = filter (\x -> (str == name x) && 
    (lx <= xlocation x && ux >= xlocation x) && (ly <= ylocation x)) lst