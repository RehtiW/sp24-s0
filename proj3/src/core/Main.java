package core;

import edu.princeton.cs.algs4.StdDraw;
import org.checkerframework.checker.guieffect.qual.UI;
import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;

import java.awt.*;

public class Main {
    private static int WIDTH = 30;
    private static int HEIGHT = 40;
    public static void main(String[] args) {
        // build your own world!

        //starter UI
        UIBoard UI = new UIBoard(WIDTH,HEIGHT);
        UI.drawUI();
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                if (key == 'n') {
                    // 切换到输入种子界面
                    UI.seedInputUI();
                }
            }
            StdDraw.show(); // 确保画布更新
        }



        //generate world!
        /*World test = new World(12345);
        test.fillTheWorld();
        test.drawWorld();*/

    }
}
