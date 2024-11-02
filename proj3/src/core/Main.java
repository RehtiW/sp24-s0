package core;

import edu.princeton.cs.algs4.StdDraw;

public class Main {
    public static void main(String[] args) {
        // build your own world!
        //starter UI
        UIBoard UI = new UIBoard();
        UI.drawUI();
        boolean allOver = false;
        //get user input
        while (!allOver) {
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                switch (key) {
                    case 'n':
                        UI.seedInputUI();
                        World test = new World(UI.getSeed());
                        Game game = new Game(test, UI);
                        game.runGame();
                        break;
                    case 'q':
                        allOver = true;
                        break;
                }
            }
        }
        System.exit(0);



    }
}
