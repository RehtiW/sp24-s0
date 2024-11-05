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
                        World test1 = new World(UI.getSeed());
                        test1.generateAndRenderTheWorld();
                        Game game1 = new Game(test1, UI);
                        game1.runGame();
                        break;
                    case 'l':
                        World test2 = new World("E:\\cs61b\\sp24-s0\\proj3\\src\\save.txt");
                        test2.RenderTheWorld();
                        Game game2 = new Game(test2, UI);
                        game2.runGame();
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
