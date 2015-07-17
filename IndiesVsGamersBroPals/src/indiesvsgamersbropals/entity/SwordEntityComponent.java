/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package indiesvsgamersbropals.entity;

import bropals.lib.simplegame.entity.block.BlockEntity;
import bropals.lib.simplegame.entity.block.TexturedBlock;

/**
 * A piece of the SwordEntity, either the body or the sword
 * @author Kevin
 */
public class SwordEntityComponent extends TexturedBlock {

    private boolean isSword;
    private int damage;
    private SwordEntity parentEntity;
    
    /**
     * Create a new entity componenent, a part of a sword entity
     * @param x x pos
     * @param y y pos
     * @param width width
     * @param height height
     * @param isSword Whether or not this is a sword part. If not it's a body
     */
    public SwordEntityComponent(float x, float y, 
            float width, float height, boolean isSword) {
        super(null, x, y, width, height);
        setCollidable(true);
        this.isSword = isSword;
        if (isSword) {
            damage = 1;
        }
    }

    @Override
    public void collideWith(BlockEntity other) {
        if (other instanceof SwordEntityComponent) {
            SwordEntityComponent otherComp = (SwordEntityComponent)other;
            if (otherComp.isIsSword()) {
                parentEntity.damage(1); // swords do 1 damage to parent
            }
        } else if (other instanceof Projectile) {
            Projectile proj = (Projectile)other;
            parentEntity.damage(proj.getDamage());
            proj.setParent(null);
        }
    }

    public boolean isIsSword() {
        return isSword;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

}
