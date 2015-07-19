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
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import org.gamejolt.GameJoltAPI;

/**
 * For entering in either you game jolt info, the guest info, or no info
 * @author Kevin
 */
public class GameJoltEntryState extends GameState {
    
    private GuiButton enterInfo;
    private GuiButton enterGuest;
    private GuiButton enterNothing;
    
    @Override
    public void update(int i) {
        enterInfo.update(0, 0);
        enterGuest.update(0,0);
        enterNothing.update(0,0);
    }

    @Override
    public void render(Object o) {
        Graphics2D g2 = (Graphics2D) o;
        
        g2.drawImage(getAssetManager().getImage("gameJoltEntryBackground"),0,0,null);
        
        enterInfo.render(o);
        enterGuest.render(o);
        enterNothing.render(o);
    }

    @Override
    public void onEnter() {
        //api = new GameJoltAPI();
        BufferedImage enterInfoImg = getAssetManager().getImage("gameJoltEntryInfoEnter");
        enterInfo = new GuiButton(275, 25, 250, 100, enterInfoImg, enterInfoImg, enterInfoImg, 
            new GuiButtonAction() {
                @Override
                public void onButtonPress() {
                    String userEntered = JOptionPane.showInputDialog("Enter your GameJolt username", "Game Jolt Username");
                    String tokenEntered = JOptionPane.showInputDialog("Enter your GameJolt Game Token", "Game Token");
                    GameJoltDataHolder holder = new GameJoltDataHolder(userEntered, tokenEntered); 
                    boolean valid = holder.validateUser();
                    if (valid) {
                        JOptionPane.showMessageDialog(null, "Your username and token has been saved!", 
                                "Valid username/token", JOptionPane.INFORMATION_MESSAGE);
                        getGameStateRunner().setState(new MainMenuState(
                                holder));
                    } else {
                        JOptionPane.showMessageDialog(null, "That was no a valid username/token", 
                                "Invalid username/token", JOptionPane.ERROR_MESSAGE);
                    }
                }
        });
        BufferedImage enterGuestImg = getAssetManager().getImage("gameJoltEntryGuestEnter");
        enterGuest = new GuiButton(275, 225, 250, 100, enterGuestImg, enterGuestImg, enterGuestImg, 
            new GuiButtonAction() {
                @Override
                public void onButtonPress() {
                    String userEntered = JOptionPane.showInputDialog("Enter your a Guest username", 
                            "Guest" + (100 + (int)(Math.random() * 899)));
                    JOptionPane.showMessageDialog(null, "Your guest name has been saved!", 
                            "Guest name saved", JOptionPane.INFORMATION_MESSAGE);
                    getGameStateRunner().setState(new MainMenuState(
                                new GameJoltDataHolder(userEntered)));
                    
                }
        });
        BufferedImage enterNothingImg = getAssetManager().getImage("gameJoltEntryNoInfo");
        enterNothing = new GuiButton(275, 425, 250, 100, enterNothingImg, enterNothingImg, enterNothingImg, 
            new GuiButtonAction() {
                @Override
                public void onButtonPress() {
                    JOptionPane.showMessageDialog(null, "Your score will not be submitted", 
                            "Nothing entered", JOptionPane.INFORMATION_MESSAGE);
                    getGameStateRunner().setState(new MainMenuState(
                                new GameJoltDataHolder()));
                    
                }
        });
    }

    @Override
    public void onExit() {
    }

    @Override
    public void mouse(int mousebutton, int x, int y, boolean pressed) {
        if (pressed) {
            enterInfo.mouseInput(x, y);
            enterGuest.mouseInput(x, y);
            enterNothing.mouseInput(x, y);
        }
    }

    
}
