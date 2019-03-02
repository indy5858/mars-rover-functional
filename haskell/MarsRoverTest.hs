module MarsRoverTest where

    import MarsRover
    import Test.Hspec

    test :: IO ()
    test = hspec $ do
        describe "Rover" $ do
            it "has position and direction" $ do
                let rover = Rover (0,0) N
                position rover `shouldBe` (0,0)
                direction rover `shouldBe` N
            it "moves forward one step north" $ do
              let rover = ProgrammedRover (Rover (0,0) N) "f"
              let movedRover = apply rover
              movedRover `shouldBe` Rover (0,1) N
            it "moves forward two steps north" $ do
              let rover = ProgrammedRover (Rover (0,0) N) "ff"
              let movedRover = apply rover
              movedRover `shouldBe` Rover (0,2) N
            it "moves forward one step south" $ do
              let rover = ProgrammedRover (Rover (0,1) S) "f"
              apply rover `shouldBe` Rover (0,0) S
            it "move backward one step when facing east" $ do
              let rover = ProgrammedRover (Rover (1,0) E) "b"
              apply rover `shouldBe` Rover (0,0) E
            it "turns left when facing north" $ do
              let rover = ProgrammedRover (Rover (0,0) N) "l"
              apply rover `shouldBe` Rover (0,0) W
            it "turns right when facing north" $ do
              let rover = ProgrammedRover (Rover (0,0) N) "r"
              apply rover `shouldBe` Rover (0,0) E
