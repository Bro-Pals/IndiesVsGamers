/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package indiesvsgamersbropals.entity;

import bropals.lib.simplegame.entity.block.BlockEntity;
import bropals.lib.simplegame.entity.block.TexturedBlock;
import bropals.lib.simplegame.math.Vector2D;

/**
 * A piece of the SwordEntity, either the body or the sword
 * @author Kevin
 */
public class SwordEntityComponent extends TexturedBlock {

    private boolean isSword;
    private int damage;
    private SwordEntity parentEntity;
    private float localX, localY;
    
    /**
     * Create a new entity componenent, a part of a sword entity
     * @param x x pos AND the offset x
     * @param y y pos AND the offset y
     * @param width width
     * @param height height
     */
    public SwordEntityComponent(float x, float y, 
            float width, float height) {
        super(null, x, y, width, height);
        setCollidable(true);
        setAnchored(false);
        this.isSword = false;
        localX = x;
        localY = y;
    }
    
    public void makeASword(int dmg) {
        isSword = true;
        damage = dmg;
    }

    @Override
    public void collideWith(BlockEntity other) {
        if (other instanceof SwordEntityComponent) {
            SwordEntityComponent otherComp = (SwordEntityComponent)other;
            if (otherComp.getParentEntity().getParent() == null ||
                    otherComp.getParentEntity().getParent() != getParent() ||
                    getParent() == null) {
                // can't be hurt by something in a different world or with no parent
                return;
            }
            //System.out.println("hit another");
            if (otherComp.isIsSword()) {
                if (this.isSword) {
                    // make knockback
                    System.out.println("KNOCKBACK");
                    float diffX = otherComp.getCenterX() - getCenterX();
//                    float diffY = otherComp.getCenterY() - getCenterY();
                    /// always get pushed back
                    float direction = (diffX / Math.abs(diffX));
                    Vector2D knockbackVector = new Vector2D(direction * 18 * -1, 0);
                    int knockbackDuration = 80;
                    //getParentEntity().knockback(knockbackVector, knockbackDuration);
                    //knockbackVector.scaleLocal(-1); // opposite direction
                    otherComp.getParentEntity().knockback(knockbackVector, knockbackDuration);
                } else {
                    //System.out.println("DAMAGE");
                    parentEntity.damage(otherComp.getDamage()); // swords do 1 damage to body
                }
            }
        } else if (other instanceof Projectile) {
            Projectile proj = (Projectile)other;
            parentEntity.damage(proj.getDamage());
            proj.setParent(null);
        } else if (other instanceof HazardBlock) {
            parentEntity.damage(((HazardBlock)other).getDamage());
        }
    }

    @Override
    public boolean handleCollide(BlockEntity other) {
        if (other instanceof SwordEntityComponent) {
            if (((SwordEntityComponent)other).getParentEntity() == getParentEntity()) {
                return false;
            }
        }
        return super.handleCollide(other); 
    }

    /**
     * The sword entity that this component is a piece of.
     * @return See desc.
     */
    public SwordEntity getParentEntity() {
        return parentEntity;
    }

    public void setParentEntity(SwordEntity parentEntity) {
        this.parentEntity = parentEntity;
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
    
    @Override
    public float getX() {
        // GLOBAL coordinates
        if (!isSword) {
            return super.getX();
        }
        return parentEntity.getX() + localX;
    }
    
    public float getLocalX() {
        //local coordinates
        return localX;
    }
    
    @Override
    public float getY() {
        // GLOBAL coordinates
        if (!isSword) {
            return super.getY();
        }
        return parentEntity.getY() + localY;
    }
    
    public float getLocalY() {
        //local coordinates
        return localY;
    }

}
