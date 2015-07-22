/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package indiesvsgamersbropals.entity.enem;

import bropals.lib.simplegame.math.Vector2D;
import indiesvsgamersbropals.entity.SwordEntity;
import indiesvsgamersbropals.entity.SwordEntityComponent;

/**
 *
 * @author Kevin
 */
public class KnightUpEntity extends SwordEntity {
    
    private boolean waiting, charging;
    private int waitingTimer, chargingTimer;
    
    public KnightUpEntity(SwordEntityComponent body, 
            SwordEntityComponent[] swords) {
        super(body, swords);
        charging = false;
        waiting = false;
    }
    
    @Override
    public void knockbackCreate(float amount, int duration) {
        knockback(new Vector2D(0, amount), duration);
    }
    
    @Override
    public void update(int i) {
        // same as down knight but backwards checks and directions
        SwordEntity player = getPlayer();
        // Try to move above the player and charge down
        float diffY = player.getCenterY() - getCenterY();
        float diffX = player.getCenterX() - getCenterX();
        
        boolean belowPlayer = diffY < 100;
        
        
        //System.out.println("Above: " + abovePlayer);
            // shimmy until you're above the player
        
        // charging takes precedence over everything else the knight does
        if (charging) {
            getDirection().setY(-1.3f); // go REALLY fast!
            chargingTimer = chargingTimer - i;
            if (chargingTimer < 0) {
                System.out.println("DONE CHARGING");
                // reset
                charging = false;
                waiting = false;
            }
        } else {
            // do normal behavior when not charging
            if (belowPlayer) {
                if (diffY > 120) {
                    getDirection().setY(1);
                } else {
                    getDirection().setY(-0.2 * Math.abs(diffY) / diffY);
                }
                if (Math.abs(diffX) > 15) {
                    // walk towards above the player
                    getDirection().setX(Math.abs(diffX) / diffX);
                } else {
                    // stop moving and wait
                    getDirection().setX(0);
                    // backing up slowly for effect
                    getDirection().setY(0.1f);
                    // if the knight is right above the player
                    if (!waiting) {
                        waiting = true;
                        charging = false;
                        chargingTimer = (int)(Math.abs(diffY) * 4); // charge longer when farther
                        waitingTimer = 500; // wait for half a second
                    } else {
                        waitingTimer = waitingTimer - i;
                        if (waitingTimer < 0) {
                            // done waiting, now it's charging time!
                            charging = true;
                            System.out.println("CHARGING TIME");
                        }
                    }
                }
            } else {
                // try to get over the player
                if (Math.abs(diffX) < 60) {
                    // don't walk straight into the player
                    getDirection().setX(-Math.abs(diffX) / diffX);
                }

                getDirection().setY(1);
            }
        }
        super.update(i);
    }
    

}
