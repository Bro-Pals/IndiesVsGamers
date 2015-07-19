/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package indiesvsgamersbropals.screens;

import bropals.lib.simplegame.gui.GuiButton;
import bropals.lib.simplegame.gui.GuiButtonAction;
import bropals.lib.simplegame.state.GameState;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * The first screen you see
 * @author Kevin
 */
public class CoverScreenState extends GameState implements GuiButtonAction {

    GuiButton button;
    
    @Override
    public void update(int i) {
        button.update(0, 0);
    }

    @Override
    public void render(Object o) {
        Graphics2D g2 = (Graphics2D) o;
        
        g2.drawImage(getAssetManager().getImage("coverScreenBackground"), 0, 0, null);
        button.render(o);
    }

    @Override
    public void onEnter() {
        BufferedImage img = getAssetManager().getImage("coverScreenEnterButton");
        button = new GuiButton(350, 500, 100, 50, img, img, img, this);
    }

    @Override
    public void onExit() {
    }

    @Override
    public void onButtonPress() {
        // to the next state
        getGameStateRunner().setState(new GameJoltEntryState());
    }

    @Override
    public void mouse(int mousebutton, int x, int y, boolean pressed) {
        button.mouseInput(x, y);
    }

    
}
