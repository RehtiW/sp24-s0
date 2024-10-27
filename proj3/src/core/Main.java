package core;

import edu.princeton.cs.algs4.StdDraw;

public class Main {
    private static int WIDTH = 30;
    private static int HEIGHT = 40;
    public static void main(String[] args) {
        // build your own world!

        //starter UI
        UIBoard UI = new UIBoard(WIDTH,HEIGHT);
        UI.drawUI();
        //get user input
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                switch (key){
                    case 'n':
                        UI.seedInputUI();
                        World test = new World(UI.getSeed());
                        test.renderTheWorld();
                }
            }
            StdDraw.show(); // 确保画布更新
        }



        //generate world!
        /*World test = new World(12345);
        test.renderTheWorld();*/

    }
}
