/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package indiesvsgamersbropals.entity.enem;

import indiesvsgamersbropals.entity.SwordEntity;
import indiesvsgamersbropals.entity.SwordEntityComponent;

/**
 *
 * @author Kevin
 */
public class GuardEntity extends SwordEntity {

    private SwordEntity player;
    
    public GuardEntity(SwordEntityComponent body, 
            SwordEntityComponent[] swords) {
        super(body, swords);
    }
    
    public void givePlayer(SwordEntity player) {
        this.player = player;
    }
    
    @Override
    public void update(int i) {
        if (player != null) {
            boolean isInFront = getX() > player.getX() + player.getWidth() + 60; // padding
            // different of the left side of the guard compared to the right side of the player
            float diffX = (getX() - player.getX() - player.getWidth());
            boolean close = diffX > 50 && diffX < 70;
            float yDiff = player.getCenterY() - getCenterY();
            float yDirection = (int)(yDiff / Math.abs(yDiff)) ;

            if (isInFront) {
                getDirection().setX(-1); // charge to the player
                if (Math.abs(yDiff) > (getSpeed() * 2) + 1) {
                    // move towards the player slower than x axis
                    getDirection().setY(yDirection * 0.7);
                } else {
                    getDirection().setY(0);
                }
            } else {
                    getDirection().setX(1); // move to the front
                    if (close) {
                        getDirection().setX(0);
                    }
                // if the guard isn't too close to the player in the y direction
                if (Math.abs(yDiff) > 50) {
                    // move towards the player
                    getDirection().setY(yDirection);
                    
                } else {
                    getDirection().setY(yDirection * 0.15); // don't move in the y direction as much
                }
            }
        } else {
            System.err.println("player is null");
        }
        
        super.update(i);
    }

}
