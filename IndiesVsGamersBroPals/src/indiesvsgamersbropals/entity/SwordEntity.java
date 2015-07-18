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
        
        for (SwordEntityComponent part : parts) {
            part.setParentEntity(this);
        }
        this.health = 1;
        this.speed = 10;
        this.damaged = false;
        direction = new Vector2D();
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
        }
        
        if (parts[0].getParent() != null)
            parts[0].update(i);
            
        for (int k=1; k<parts.length; k++) {
//            if (parts[k].getParent() == null)
//                continue;
            
            // save local coordinates
            float localX = parts[k].getX();
            float localY = parts[k].getY();
            
            // move to world coordinates
            parts[k].setX(parts[k].getX() + parts[0].getX());
            parts[k].setY(parts[k].getY() + parts[0].getY());
            
            parts[k].checkCollisions();
            
            // move back to local coordinates
            parts[k].setX(localX);
            parts[k].setY(localY);
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
        for (int i=0; i<parts.length; i++) {
            if (i > 0) {
                parts[i].setX(parts[i].getX() + parts[0].getX());
                parts[i].setY(parts[i].getY() + parts[0].getY());
            }
            
            parts[i].render(o);
            
            if (i > 0) {
                parts[i].setX(parts[i].getX() - parts[0].getX());
                parts[i].setY(parts[i].getY() - parts[0].getY());
            }
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

    public float getSpeed() {
        return speed;
    }

    @Override
    public void setParent(GameWorld parent) {
        for (SwordEntityComponent part : parts) {
            part.setParent(parent);
        }
        super.setParent(parent);
    }
    
    
    
}
