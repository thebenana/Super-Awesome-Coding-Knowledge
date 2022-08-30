module MiniRacketParser where

import Parser
import Expr
import Control.Applicative
import Error ( ErrorT ) 

parseBool :: Parser Bool
parseBool = do
        parseKeyword "true"
        return True
        <|> do
        parseKeyword "false"
        return False

-- implement parsing bool operations, these are 'and' and 'or'
parseBoolOp :: Parser BoolOp
parseBoolOp = do
    parseKeyword "and"
    return And
    <|> do
    parseKeyword "or"
    return Or
    

-- parse math operations and return the MathOp
-- TODO: Add the other math operations: *, div, mod
parseMathOp :: Parser MathOp
parseMathOp =
    do symbol "+" >> return Add
    <|> do symbol "-" >> return Sub
    <|> do symbol "*" >> return Mul
    <|> do symbol "`div`" >> return Div
    <|> do symbol "`mod`" >> return Mod
    

-- parse the comp operations and return the CompOp
-- TODO: add the comparison operators: equals?, < 
parseCompOp :: Parser CompOp
parseCompOp = 
    do symbol "<" >> return Lt 
    <|> do symbol "equal?" >> return Eq

    
-- a literal in MiniRacket is true, false, or a number
-- TODO: parse literals which can be natural numbers or bools (true, false)
literal :: Parser Value
literal = do 
    IntVal <$> natural
    <|>
    BoolVal <$> parseBool


-- parse a literal expression, which at this point, is just a literal
literalExpr :: Parser Expr
literalExpr = do
    LiteralExpr <$> literal


keywordList :: [String]
keywordList = ["false", "true", "not", "and", "or", "equal?"]

-- try to parse a keyword, otherwise it's a variable, this can be
-- used to check if the identifier we see (i.e., variable name) is
-- actually a keyword, which isn't legal
parseKeyword :: String -> Parser String
parseKeyword keyword = do
    -- all keywords follow the identifier rules, so we'll use that
    name <- identifier
    if name `elem` keywordList && keyword == name
    then return name
    else failParse $ "saw " ++ name ++ ", expected " ++ keyword


-- TODO: parse not expressions, note that "not" is a keyword, so
-- as a big hint, you should use parseKeyword
notExpr :: Parser Expr
notExpr = do 
    parseKeyword "not"
    NotExpr <$> parseExpr

varExpr :: Parser Expr
varExpr = do
    name <- identifier
    if name `elem` keywordList
        then failParse (name ++ " var is a keyword name") 
    else return $ VarExpr name

-- IMPLEMENT STILL
-- negateExpr :: Parser Expr

-- a bool expression is the operator followed by one or more expressions that we have to parse
-- TODO: add bool expressions 
boolExpr :: Parser Expr
boolExpr = BoolExpr <$> parseBoolOp <*> kplus parseExpr

-- a math expression is the operator followed by one or more expressions that we have to parse
-- TODO: add math expressions
mathExpr :: Parser Expr
mathExpr = MathExpr <$> parseMathOp <*> kplus parseExpr
    

-- a comp expression is the comp operator and the parsing of two expressions
compExpr :: Parser Expr
compExpr = CompExpr <$> parseCompOp <*> parseExpr <*> parseExpr

{- DON'T DEFINE THESE YET, THEY'RE NOT PART OF THE ASSIGNMENT 
-- ifExpr :: Parser Expr
-- applyExpr :: Parser Expr
-- letExpr :: Parser Expr
-}

pairExpr :: Parser Expr
pairExpr = do
    expr1 <- parseExpr
    symbol "."
    PairExpr expr1 <$> parseExpr

-- note that this is syntactic sugar, cons is just replaced by the PairExpr ast
consExpr :: Parser Expr 
consExpr = do 
    symbol "cons"
    expr1 <- parseExpr 
    PairExpr expr1 <$> parseExpr 



parseParens :: Parser Expr -> Parser Expr
parseParens p = do
    symbol "("
    e <- p
    symbol ")"
    return e

{- DON'T DEFINE THESE YET, THEY'RE NOT PART OF THE ASSIGNMENT 
-- negateAtom :: Parser Expr
-- lambdaExpr :: Parser Expr
-}

-- an atom is a literalExpr, which can be an actual literal or some other things
parseAtom :: Parser Expr
parseAtom = do
    literalExpr
    <|>
    varExpr

-- the main parsing function which alternates between all the options you have
parseExpr :: Parser Expr
parseExpr = do
    parseAtom
    <|> parseParens notExpr
    <|> parseParens boolExpr
    <|> parseParens mathExpr
    <|> parseParens compExpr
    <|> parseParens pairExpr
    <|> parseParens consExpr
    <|> parseParens parseExpr 

-- a helper function that you can use to test your parsing:
-- syntax is simply 'parseStr "5"' which will call parseExpr for you
parseStr :: String -> Either ErrorT (Expr, String) 
parseStr str = do 
    parse parseExpr str