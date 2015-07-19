/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package indiesvsgamersbropals.screens;

import bropals.lib.simplegame.gui.GuiButton;
import bropals.lib.simplegame.gui.GuiButtonAction;
import bropals.lib.simplegame.state.GameState;
import indiesvsgamersbropals.GameJoltDataHolder;
import indiesvsgamersbropals.PlayState;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * The main menu
 * @author Kevin
 */
public class MainMenuState extends GameState {
    
    private GameJoltDataHolder gameJoltDataHolder;
    private GuiButton playButton;
    
    public MainMenuState(GameJoltDataHolder holder) {
        gameJoltDataHolder = holder;
    }
    
    @Override
    public void update(int i) {
        playButton.update(0, 0);
    }

    @Override
    public void render(Object o) {
        Graphics2D g2 = (Graphics2D) o;
        
        g2.drawImage(getAssetManager().getImage("mainMenuBackground"), 0, 0, null);
        playButton.render(o);
    }

    @Override
    public void onEnter() {
        BufferedImage playImage = getAssetManager().getImage("playMenuButton");
        playButton = new GuiButton(350, 300, 100, 50, playImage, playImage, playImage, 
                new GuiButtonAction() {

            @Override
            public void onButtonPress() {
                getGameStateRunner().setState(new PlayState(gameJoltDataHolder));
            }
        });
    }

    @Override
    public void onExit() {
    }

    @Override
    public void mouse(int mousebutton, int x, int y, boolean pressed) {
        if (pressed) {
            playButton.mouseInput(x, y);
        }
    }

    
}
