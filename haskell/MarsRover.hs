module MarsRover where

  data Direction = N | W | S | E deriving (Show, Eq, Read)

  vector :: Direction -> (Int,Int)
  vector direction = case direction of
    N -> (0,1)
    S -> (0,-1)
    E -> (1,0)
    W -> (-1,0)

  left :: Direction -> Direction
  left direction = case direction of
    N -> W
    W -> S
    S -> E
    E -> N

  right :: Direction -> Direction
  right direction = case direction of
    N -> E
    W -> N
    S -> W
    E -> S


  data Rover = Rover { position :: (Int, Int),
                       direction :: Direction} deriving (Show, Eq)

  data ProgrammedRover = ProgrammedRover
                          { roverState :: Rover,
                            commands :: String } deriving (Show)

  apply :: ProgrammedRover -> Rover
  apply rover =
    let r = roverState rover
        c = commands rover
    in foldl applySingle r c

  applySingle :: Rover -> Char -> Rover
  applySingle r c
      | c == 'f' = Rover (add (vector $ direction r) (position r)) $ direction r
      | c == 'b' = Rover (add (negative $ vector $ direction r) (position r)) $ direction r
      | c == 'l' = Rover (position r) (left $ direction r)
      | c == 'r' = Rover (position r) (right $ direction r)

  add :: (Int, Int) -> (Int, Int) -> (Int, Int)
  add x y = (fst x + fst y, snd x + snd y)

  negative :: (Int, Int) -> (Int, Int)
  negative x = (-fst x, -snd x)

  main :: IO()
  main = do
    putStrLn "Enter x location of rover:"
    x <- getLine
    putStrLn "Enter y location of rover:"
    y <- getLine
    putStrLn "Enter direction of rover:"
    direction <- getLine
    putStrLn "Enter commands:"
    commands <- getLine
    let rS = Rover ((read x)::Int, (read y)::Int) ((read direction)::Direction)
    let r = ProgrammedRover rS commands
    putStrLn (show(apply r))
