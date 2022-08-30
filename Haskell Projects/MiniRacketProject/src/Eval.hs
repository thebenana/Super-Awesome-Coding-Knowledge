{-# OPTIONS_GHC -Wno-unrecognised-pragmas #-}
{-# HLINT ignore "Use lambda-case" #-}
module Eval where

import Expr
import Env
import MiniRacketParser
import Error

import Control.Applicative
import Control.Monad
import Parser




-- we begin with an Expr, and return an Either String (a, Expr), here
-- the String is an error message, but Expr is the remaining expression
-- to be evaluated
newtype Evaluator a = E { eval :: (ValueEnv, Expr) -> Either ErrorT (a, (ValueEnv, Expr)) }

-- this is the basic evaluator, it ends when our ast is an EmptyExpr, but otherwise,
-- we get the the environment and expr returned
next :: Evaluator (ValueEnv, Expr)
next = E (\parserState -> case parserState of
            (_, EmptyExpr) -> Left $ EvalError "no more expressions"
            (env, x) -> Right ((env, x), (env, EmptyExpr)))

-- these next evaluators are primarily for error purposes, you can 
-- call them whenever you want to generate an error
evalError :: ErrorT -> Evaluator a
evalError err = E (\_ -> Left err)

evalNotImplemented :: Evaluator a
evalNotImplemented = evalError EvalNotImplemented

-- failEval is an EvalError which is a general error for evaluation
failEval :: String -> Evaluator a
failEval msg = evalError $ EvalError msg

-- NoEval is called when there is nothing to evaluate
noEval :: Evaluator a
noEval = evalError NoEval

-- a TypeError is an error when there's a type mismatch discovered during runtime
typeError :: String -> Evaluator a
typeError msg = evalError $ TypeError msg

-- a NoSymbol error is an error when a symbol is looked up in the environment, but not found
noSymbol :: String -> Evaluator a
noSymbol msg = evalError $ NoSymbol msg


-- an evaluator is a Functor
instance Functor Evaluator where
    fmap f e = E (\expr -> case eval e expr of
        Left msg -> Left msg
        Right (v, out) -> Right (f v, out))

-- an evaluator is also an Applicative
instance Applicative Evaluator where
    -- the evaluator will return a successful evaluation with v as the result
    -- of evaluation and expr as the remaining stuff to evaluate
    pure v = E (\expr -> Right (v, expr))

    efuns <*> px = E (\expr -> case eval efuns expr of
        Left msg -> Left msg
        Right (g, out) -> eval (fmap g px) out)

-- and a Monad...
instance Monad Evaluator where
    e >>= f = E (\expr -> case eval e expr of
        Left msg -> Left msg
        Right (v, out) -> eval (f v) out)

-- and an Alternative ... 
instance Alternative Evaluator where
    empty = failEval "no matching evaluation"

    p <|> q = E (\expr -> case eval p expr of
        Left NoEval -> eval q expr
        Left (EvalError _) -> eval q expr
        -- we will pass forward the Lefts otherwise
        otherEval -> otherEval)

-- in fact we're a MonadPlus too, this helps when we don't have
-- an alternative or when simply we use pattern matching on <-, but
-- that fails, this allows the Monad to pass along the failure 
-- instead of generating a pattern-matching failure 
instance MonadPlus Evaluator
instance MonadFail Evaluator where
  fail _ = mzero


-- here's how we evaluate a literal, fairly straightforward
evalLiteral :: Evaluator Value
evalLiteral = do
    -- retrieve the literal value using next, and return the value
    (_, LiteralExpr v) <- next
    return v

{- DON'T DEFINE THIS YET, IT'S NOT PART OF THE ASSIGNMENT
evalVar :: Evaluator Value
-}

evalVar :: Evaluator Value
evalVar = do
    (eek, VarExpr name) <- next
    case Env.lookup name eek of
        Nothing -> noSymbol $ "symbol " ++ name ++ " not found"
        Just v -> return v

-- this evaluates a list of expressions and returns a list of values
-- by mapping an evaluator function (using <$>) over the list of expressions
evalListOfExprs :: ValueEnv -> [Expr] -> [Either ErrorT Value]
evalListOfExprs env exprs =
    (\expr ->
        case eval evalExpr (env, expr) of
            Right (res, _) -> Right res
            Left msg -> Left msg) <$> exprs


-- evaluates a bool expression, this first evaluates all the 
-- arguments to the bool expression and then uses calcBoolList 
-- to calculate the boolean operation over the arguments. Note that 
-- you must first use evalListOfExprs to evaluate the arguments. Then 
-- you can use calcBoolList with the right op on it
evalBoolExpr :: Evaluator Value
evalBoolExpr = do
    (env, BoolExpr op exprs) <- next
    -- TODO: implement the rest!
    let res = evalListOfExprs env exprs in
        case calcBoolList op res of
            Right v -> return v
            Left erro -> evalError erro

-- performs the boolean operation on Either String Values where this works on the Values
-- only if the kinds are BoolVal, otherwise we return Left
boolOpVal :: (Bool -> Bool -> Bool) -> Either ErrorT Value -> Either ErrorT Value -> Either ErrorT Value
boolOpVal boolop (Right (BoolVal v1)) (Right (BoolVal v2)) = Right $ BoolVal $ v1 `boolop` v2
boolOpVal _ _ _ = Left $ TypeError "boolean expressions require boolean values"

-- fold over the list using the op, assumes the list has one element
boolOpFold :: Foldable t =>
    (Bool -> Bool -> Bool) -> t (Either ErrorT Value) -> Either ErrorT Value
boolOpFold op = foldr1 (boolOpVal op)

-- determine which bool operation to use to fold with by the kind of BoolOp passed in
calcBoolList :: BoolOp -> [Either ErrorT Value] -> Either ErrorT Value
calcBoolList op lst = case op of
    And -> boolOpFold (&&) lst
    Or -> boolOpFold (||) lst


evalMathExpr :: Evaluator Value
evalMathExpr = do
    (env, MathExpr op exprs) <- next
    -- TODO: Implement the rest!
    let res = evalListOfExprs env exprs in
        case calcMathList op res of
            Right v -> return v
            Left erro -> evalError erro

-- evaluates a comparison, specifically equals? and <
evalCompExpr :: Evaluator Value
evalCompExpr = do
    (env, CompExpr op expr1 expr2) <- next
    let result1 = getValue (eval evalExpr (env, expr1))
        result2 = getValue (eval evalExpr (env, expr2)) in 
            case calcCompExpr op result1 result2 of
                Right v -> return v
                Left err -> evalError err

-- takes two Either Values and runs the math op on them internally, producing the same type,
-- but failing if either of them is not an IntVal (which are the only things math ops work on)
mathOpVal :: (Integer -> Integer -> Integer) -> Either ErrorT Value -> Either ErrorT Value
    -> Either ErrorT Value
mathOpVal op (Right (IntVal v1)) (Right (IntVal v2)) = Right $ IntVal $ v1 `op` v2
mathOpVal _ _ _ = Left $ TypeError "math expressions require numeric values"

eqOpVal :: (Integer -> Integer -> Bool) -> Either ErrorT Value -> Either ErrorT Value
    -> Either ErrorT Value
--eqOpVal op (Right (BoolVal v1)) (Right (BoolVal v2)) = Right $ BoolVal $ v1 `op` v2 
eqOpVal op (Right (IntVal v1)) (Right (IntVal v2)) = Right $ BoolVal $ v1 `op` v2
eqOpVal _ _ _ = Left $ TypeError "comparisons must be on the same type"

-- takes a list of Either Values and combines them through the operation
mathOpFold :: Foldable t => (Integer -> Integer -> Integer) -> t (Either ErrorT Value)
    -> Either ErrorT Value
mathOpFold op = foldr1 (mathOpVal op)

addValList :: Foldable t => t (Either ErrorT Value) -> Either ErrorT Value
addValList = mathOpFold (+)

subValList :: [Either ErrorT Value] -> Either ErrorT Value
subValList [Right (IntVal x)] = Right (IntVal (-x))
subValList lst = mathOpFold (-) lst

-- TODO: You'll need to implement these to make the rest of the functions work!
mulValList :: Foldable t => t (Either ErrorT Value) -> Either ErrorT Value
mulValList = mathOpFold (*)

divValList :: Foldable t => t (Either ErrorT Value) -> Either ErrorT Value
divValList = mathOpFold div

modValList :: Foldable t => t (Either ErrorT Value) -> Either ErrorT Value
modValList = mathOpFold mod


calcMathList ::
    MathOp -> [Either ErrorT Value] -> Either ErrorT Value
calcMathList op lst = case op of
    Add -> addValList lst
    Sub -> subValList lst
    Div -> divValList lst
    Mul -> mulValList lst
    Mod -> modValList lst
--    _ -> error "calcMathList not fully Not implemented"


{-
  A somewhat complicated implementation of calcCompExpr mainly because we have 
  so many monadic types that we have to deconstruct for the various cases. Left is
  always a failure, like a type, but Right can then be the different sub-types, like
  Int or Bool, and we can use equal? on int or bools, but not < on bools.  
-}
calcCompExpr :: CompOp -> Either ErrorT Value -> Either ErrorT Value -> Either ErrorT Value
calcCompExpr Eq v@(Right (IntVal _)) v'@(Right (IntVal _)) = Right $ BoolVal $ v == v'
calcCompExpr Eq v@(Right (BoolVal _)) v'@(Right (BoolVal _)) = Right $ BoolVal $ v == v'
calcCompExpr Eq (Right (PairVal (v, v'))) (Right (PairVal (v1, v1'))) =
    case (calcCompExpr Eq (Right v) (Right v1), calcCompExpr Eq (Right v') (Right v1')) of
        (Right (BoolVal True), Right (BoolVal True)) -> Right $ BoolVal True
        (Left _, _) -> Left $ TypeError "equal? can only compare values of the same type"
        (_, Left _) -> Left $ TypeError "equal? can only compare values of the same type"
        (_, _) -> Right $ BoolVal False
calcCompExpr Eq _ _ = Left $ TypeError "equal? can only compare values of the same type"
calcCompExpr Lt (Right (IntVal v)) (Right (IntVal v')) = Right $ BoolVal $ v < v'
calcCompExpr Lt _ _ = Left $ TypeError "< can only compare values of the numbers type"


{- DON'T DEFINE THESE YET, THEY'RE NOT PART OF THE ASSIGNMENT
-- evalLetExpr :: Evaluator Value
-- evalIfExpr :: Evaluator Value
-}

-- evaluate a Not expression, which should flip the boolean result 
-- TODO: if the type isn't a bool value after evaluating it, you
--   should return a type error, which can be done as follows:
-- typeError "not <boolexpr> .. must evaluate to a bool type"

evalNotExpr :: Evaluator Value
evalNotExpr = do
    (env, NotExpr expr) <- next
    case eval evalExpr (env, expr) of
        -- you must resolve the different cases in order to handle Not
        Right (v,_) -> if v == (BoolVal True) then return $ BoolVal False 
            else if v == (BoolVal False) then return $ BoolVal True 
            else typeError "not <boolexpr> .. must evaluate to a bool type"
                
        Left err -> evalError err

{- DON'T DEFINE THESE YET, THEY'RE NOT PART OF THE ASSIGNMENT 
-- callFun :: Value -> Value -> Either ErrorT Value
-- evalLambdaExpr :: Evaluator Value
-- evalApplyExpr :: Evaluator Value
-}
-- evaluates a Pair
evalPairExpr :: Evaluator Value
evalPairExpr = do
    -- extract the current environment and make sure this is a Pair type
    (env, PairExpr e1 e2) <- next
    -- to evaluate a pair, we must evaluate the left and right parts of the pair
    case getValue $ eval evalExpr (env, e1) of
        Right v1 ->
            case getValue $ eval evalExpr (env, e2) of
                Right v2 -> return $ PairVal (v1, v2)
                Left err -> evalError err
        Left err -> evalError err

-- evaluating an expression returns a value, this is the main
-- entry point for all evaluations, so we alternate between the 
-- different options. Note that depending on what you put first might
-- change how you evaluate your expressions.

-- TODO: Add evaluations for Bool, Not, Math, and Comp
evalExpr :: Evaluator Value
evalExpr =
    evalLiteral
    <|>
    evalPairExpr
    <|>
    evalVar
    <|>
    evalNotExpr
    <|>
    evalBoolExpr
    <|>
    evalMathExpr
    <|>
    evalCompExpr


-- parses the string then evaluates it
parseAndEval :: String -> Either ErrorT (Value, (ValueEnv, Expr))
parseAndEval str = do
    (ast, _) <- parse parseExpr str
    -- here, [] represents the empty environment
    eval evalExpr ([], ast)


-- extract the value from the result, which contains extra stuff we don't need to see
getValue :: Either ErrorT (Value, (ValueEnv, Expr)) -> Either ErrorT Value
getValue (Right (val, _ )) = Right val
getValue (Left err) = Left err


-- takes a string and parses it, then it tries to evaluate it
evalStr :: String -> Either ErrorT Value
evalStr = getValue . parseAndEval