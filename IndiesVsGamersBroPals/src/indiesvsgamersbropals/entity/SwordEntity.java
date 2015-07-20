/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package indiesvsgamersbropals.entity;

import bropals.lib.simplegame.entity.BaseEntity;
import bropals.lib.simplegame.entity.GameWorld;
import bropals.lib.simplegame.math.Vector2D;
import bropals.lib.simplegame.util.Counter;
import bropals.lib.simplegame.util.CounterFunction;

/**
 * The sword entity thing - it's what everything will be.'
 * @author Kevin
 */
public class SwordEntity extends BaseEntity implements CounterFunction {
        
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
     * The bonus gold based on time
     */
    private float goldMod;
    
    private Vector2D knockbackDirection;
    private Counter knockbackTime;
    
    /**
     * Create a new SwordEntity, with a body and carrying some swords.
     * The swords are positioned localed from the orgin of the body block.
     * @param body The body
     * @param swords The swords
     */
    public SwordEntity(SwordEntityComponent body, SwordEntityComponent[] swords) {
        super(null);
        goldAmount = -1;
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
        this.speed = 7;
        this.damaged = false;
        direction = new Vector2D();
        goldMod = 0; // no modifier
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
    
    @Override
    public void update(int i) {
        if (health <= 0) {
            //System.out.println("THIS ENTITY HAS NO HEALTH LEFT");
            setParent(null);
            return;
        }
        
        damaged = false;
        
        if (knockbackTime != null) {
            knockbackTime.update();
            //System.out.println("udpating the knockback");
        }
        
        // move the main component
        parts[0].getVelocity().setValues(direction.getX(), direction.getY());
        parts[0].getVelocity().scaleLocal(speed);
        
        if (knockbackDirection != null) {
            //System.out.println("WE ARE ADDING KNOCKBACK");
            if (knockbackDirection.getX() != 0) {
                parts[0].getVelocity().setX(knockbackDirection.getX());
            }
            if (knockbackDirection.getY() != 0) {
                parts[0].getVelocity().setY(knockbackDirection.getY());
            }
        }
        
        if (parts[0].getVelocity().getX() == 0 && 
                parts[0].getVelocity().getY() == 0) {
            parts[0].getAnimation().setTrack(0);
        } else {
            parts[0].getAnimation().setTrack(1);
        }
        
        // check weapon collisions first
        for (int k=1; k<parts.length; k++) {
            parts[k].checkCollisions();
        }
        
        if (parts[0].getParent() != null) {
            parts[0].update(i);
        }
    }
    
    public void damage(int amount) {
        if (damaged) {
            return;
        }
        System.out.println("before health " + health);
        health = health - amount;
        damaged = true;
        System.out.println("amount: " + amount);
        System.out.println("health " + health);
    }

    @Override
    public void render(Object o) {
        for (int i=0; i<parts.length; i++) {
            parts[i].render(o);
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
    
    public float getX() {
        return parts[0].getX();
    }
    
    public float getY() {
        return parts[0].getY();
    }
    
    public float getWidth() {
        return parts[0].getWidth();
    }
    
    public float getHeight() {
        return parts[0].getHeight();
    }

    public float getCenterX() {
        return parts[0].getCenterX();
    }
    
    public float getCenterY() {
        return parts[0].getCenterY();
    }
    
    public float getSpeed() {
        return speed;
    }

    @Override
    public void setParent(GameWorld parent) {
        for (SwordEntityComponent part : parts) {
            if (parent != null) {
                parent.addEntity(part);
            } else {
                // remove parts if the parent is being set to null
                part.getParent().getEntities().remove(part);
            }
            part.setParent(parent);
        }
        super.setParent(parent);
    }

    public void setGoldAmount(float goldAmount) {
        this.goldAmount = goldAmount;
    }

    public int getGoldAmount(long gameTime) {
        return (int)(goldAmount + (goldMod / (gameTime/3000)));
    }

    public void setGoldMod(float goldMod) {
        this.goldMod = goldMod;
    }
    
    
    
    public void knockback(Vector2D direction, int duration) {
        damaged = true; // knockback counts as being hits
        knockbackDirection = direction;
        knockbackTime = new Counter(duration, this);
        knockbackTime.reset();
    }

    @Override
    public void countFinished() {
        // stop being knocked back
        knockbackDirection = null;
        knockbackTime = null;
    }

    public int getHealth() {
        return health;
    }
    
    public SwordEntityComponent getFirstWeapon() {
        return parts[1];
    }
    
    public SwordEntityComponent getBody() {
        return parts[0];
    }
    
}
