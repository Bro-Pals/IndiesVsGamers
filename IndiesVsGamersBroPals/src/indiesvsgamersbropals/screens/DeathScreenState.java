/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package indiesvsgamersbropals.screens;

import bropals.lib.simplegame.state.GameState;
import indiesvsgamersbropals.GameJoltDataHolder;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

/**
 * The screen that shows when you die
 * @author Kevin
 */
public class DeathScreenState extends GameState {
    
    private GameJoltDataHolder gameJoltDataHolder;
    private String gameTimeString;
    private long gameTime;
    private int gold;
    
    public DeathScreenState(String time, long gameTime, int gold, GameJoltDataHolder holder) {
        gameJoltDataHolder = holder;
        gameTimeString = time;
        this.gameTime = gameTime;
        this.gold = gold;
    }
    
    @Override
    public void update(int i) {
    }

    @Override
    public void render(Object o) {
        Graphics2D g2 = (Graphics2D) o;
        
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, 800, 600);
        
        g2.setFont(new Font("Arial", Font.PLAIN, 32));
        g2.setColor(Color.BLACK);
        
        g2.drawString("Time: " + gameTimeString, 200, 50);
        g2.drawString("Gold: " + gold, 200, 110);
    }

    @Override
    public void onEnter() {
    }

    @Override
    public void onExit() {
    }

}
