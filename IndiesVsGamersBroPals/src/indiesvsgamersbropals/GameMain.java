/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package indiesvsgamersbropals;

import bropals.lib.simplegame.AWTGameWindow;
import bropals.lib.simplegame.GameStateRunner;
import bropals.lib.simplegame.io.AssetManager;
import java.io.File;

/**
 *
 * @author Pants
 */
public class GameMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        AssetManager manager = new AssetManager(new File("assets"), true);
        GameStateRunner runner = new GameStateRunner(new AWTGameWindow("Indies vs Gamers", 800, 600, false), 
                manager);
        runner.setState(new PlayState());
        runner.loop();
    }
    
}
