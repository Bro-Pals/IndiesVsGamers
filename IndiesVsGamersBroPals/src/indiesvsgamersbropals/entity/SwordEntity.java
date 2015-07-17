/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package indiesvsgamersbropals.entity;

import bropals.lib.simplegame.entity.BaseEntity;
import bropals.lib.simplegame.entity.GameWorld;
import bropals.lib.simplegame.entity.block.BlockEntity;
import bropals.lib.simplegame.math.Vector2D;

/**
 * The sword entity thing - it's what everything will be.'
 * @author Kevin
 */
public class SwordEntity extends BaseEntity {
        
    /** 
        parents of the body
    */
    private SwordEntityComponent[] parts;

    private float speed;
    private Vector2D direction;
    private int health;
    // a lock so you can't be damaged too much in one update cycle
    private boolean damaged;
    /**
     * The base amount of gold you get for killing an enemy
     */
    private float goldAmount;
    
    /**
     * Create a new SwordEntity, with a body and carrying some swords.
     * The swords are positioned localed from the orgin of the body block.
     * @param body The body
     * @param swords The swords
     */
    public SwordEntity(SwordEntityComponent body, SwordEntityComponent[] swords) {
        super(null);
        parts = new SwordEntityComponent[swords.length + 1];
        parts[0] = body;
        // turn the swords into world space from local space
        for (int i=0; i<swords.length; i++) {
            parts[i+1] = swords[i];
        }
        this.health = 1;
        this.speed = 10;
        this.damaged = false;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
    
    @Override
    public void update(int i) {
        
        // move all the components with the sword entity
        for (SwordEntityComponent comp : parts) {
            comp.getVelocity().setValues(direction.getX(), direction.getY());
            comp.getVelocity().scaleLocal(speed);
            comp.update(i);
        }
        
        // check for collisions and fix (or kill) if needed
        for (int k=0; k<parts.length; k++) {
            if (k > 0) { // if not the main body part
                // move to world coordinates
                parts[k].setX(parts[k].getX() + parts[0].getX());
                parts[k].setY(parts[k].getY() + parts[0].getY());
            }
            
            // save the position before checking collisions
            int x = (int)parts[k].getX();
            int y = (int)parts[k].getY();
            
            parts[k].checkCollisions();
            
            if (k > 0) { // if not the main body part
                // move back to local coordinates
                parts[k].setX(parts[k].getX() - parts[0].getX());
                parts[k].setY(parts[k].getY() - parts[0].getY());
            }
        }
    }
    
    public void damage(int amount) {
        health -= amount;
        if (health <= 0) {
            setParent(null);
        }
    }

    @Override
    public void render(Object o) {
        for (SwordEntityComponent comp : parts) {
            comp.render(o);
        }
    }

    public Vector2D getDirection() {
        return direction;
    }
    
    public void setX(float x) {
        parts[0].setX(x);
    }
    
    public void setY(float y) {
        parts[0].setY(y);
    }
}
