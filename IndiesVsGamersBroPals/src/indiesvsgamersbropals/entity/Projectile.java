/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package indiesvsgamersbropals.entity;

import bropals.lib.simplegame.entity.GameWorld;
import bropals.lib.simplegame.entity.block.TexturedBlock;
import bropals.lib.simplegame.math.Vector2D;

/**
 * A projectile that moves and damages
 * @author Kevin
 */
public class Projectile extends TexturedBlock {

    private int damage;
    private int lifetime;
    
    public Projectile(GameWorld parent, float x, float y, 
            float width, float height, Vector2D direction) {
        super(parent, x, y, width, height);
        damage = 100;
        lifetime = 10000;
        getVelocity().setValues(direction.getX(), direction.getY());
    }

    @Override
    public void update(int i) {
        super.update(i);
        lifetime -= i;
        if (lifetime <= 0) {
            setParent(null);
        }
    }
    
    /**
     * Set the lifetime in milliseconds
     * @param time lifetimei n milliseconds
     */
    public void setLifetime(int time) {
        lifetime = time;
    }
    
    public int getDamage() {
        return damage;
    }

    
    
}
