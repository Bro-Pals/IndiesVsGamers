/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package indiesvsgamersbropals.entity;

import bropals.lib.simplegame.animation.Animation;
import bropals.lib.simplegame.animation.Track;
import bropals.lib.simplegame.entity.GameWorld;
import bropals.lib.simplegame.io.AssetManager;
import indiesvsgamersbropals.entity.enem.GuardEntity;
import indiesvsgamersbropals.entity.enem.KnightDownEntity;
import indiesvsgamersbropals.entity.enem.KnightUpEntity;
import indiesvsgamersbropals.entity.enem.RogueEntity;
import indiesvsgamersbropals.entity.enem.WhiteGhostEntity;
import java.awt.image.BufferedImage;

/**
 * A factory to build sword entities.
 * @author Kevin
 */
public class SwordEntityFactory {

    AssetManager manager;
    
    public SwordEntityFactory(AssetManager manag) {
        manager = manag;
    }
    
    /**
     * make a player at origin
     * @return The player entity at origin
     */
    public SwordEntity makePlayer() {
        SwordEntityComponent body = new SwordEntityComponent(0, 0, 42, 50);
        Animation anim = new Animation();
        BufferedImage[] frames = (new Track(manager.getImage("player"), 42, 50)).getImages();
        anim.addTrack(new Track(new BufferedImage[]{frames[0]}, 1000)); // idle animation
        anim.addTrack(new Track(
                new BufferedImage[]{frames[0], frames[1], frames[2], frames[1]}, 110));
        anim.setTrack(0);
        body.setAnimation(anim);
        SwordEntityComponent[] sword = {new SwordEntityComponent(42, 12, 30, 20)};
        sword[0].makeASword(1);
        sword[0].setImage(manager.getImage("playerWeaponWide"));
        return new SwordEntity(body, sword);
    }
    
    public SwordEntity makeGuardEnemy(float x, float y) {
        SwordEntityComponent body = new SwordEntityComponent(x, y, 42, 50);
        Animation anim = new Animation();
        BufferedImage[] frames = (new Track(manager.getImage("guard"), 42, 50)).getImages();
        anim.addTrack(new Track(new BufferedImage[]{frames[0]}, 1000)); // idle animation
        anim.addTrack(new Track(
                new BufferedImage[]{frames[0], frames[1], frames[2], frames[1]}, 110));
        anim.setTrack(0);
        body.setAnimation(anim);
        SwordEntityComponent[] sword = {new SwordEntityComponent(-45, 18, 45, 15)};
        sword[0].makeASword(1);
        sword[0].setImage(manager.getImage("guardWeapon"));
        GuardEntity enemy = new GuardEntity(body, sword);
        enemy.setGoldMod(15);
        enemy.setSpeed(4);
        return enemy;
    }
    
    public SwordEntity makeKnightDownEnemy(float x, float y) {
        SwordEntityComponent body = new SwordEntityComponent(x, y, 42, 50);
        Animation anim = new Animation();
        BufferedImage[] frames = (new Track(manager.getImage("knightDown"), 42, 50)).getImages();
        anim.addTrack(new Track(new BufferedImage[]{frames[0]}, 1000)); // idle animation
        anim.addTrack(new Track(
                new BufferedImage[]{frames[0], frames[1], frames[2], frames[1]}));
        anim.setTrack(0);
        anim.getTrackOn().setMillisBetweenImages(110);
        body.setAnimation(anim);
        SwordEntityComponent[] sword = {new SwordEntityComponent(-1, 50, 15, 30)};
        sword[0].makeASword(1);
        sword[0].setImage(manager.getImage("knightDownWeapon"));
        KnightDownEntity enemy = new KnightDownEntity(body, sword);
        enemy.setGoldMod(15);
        return enemy;
    }
    
    public SwordEntity makeKnightUpEnemy(float x, float y) {
        SwordEntityComponent body = new SwordEntityComponent(x, y, 42, 50);
        Animation anim = new Animation();
        BufferedImage[] frames = (new Track(manager.getImage("knightUp"), 42, 50)).getImages();
        anim.addTrack(new Track(new BufferedImage[]{frames[0]}, 1000)); // idle animation
        anim.addTrack(new Track(
                new BufferedImage[]{frames[0], frames[1], frames[2], frames[1]}));
        anim.setTrack(0);
        anim.getTrackOn().setMillisBetweenImages(110);
        body.setAnimation(anim);
        SwordEntityComponent[] sword = {new SwordEntityComponent(-2, -30, 15, 30)};
        sword[0].makeASword(1);
        sword[0].setImage(manager.getImage("knightUpWeapon"));
        KnightUpEntity enemy = new KnightUpEntity(body, sword);
        enemy.setGoldMod(15);
        return enemy;
    }
    
    public SwordEntity makeRogueEnemy(float x, float y) {
        SwordEntityComponent body = new SwordEntityComponent(x, y, 42, 40);
        Animation anim = new Animation();
        BufferedImage[] frames = (new Track(manager.getImage("rouge"), 42, 40)).getImages();
        anim.addTrack(new Track(new BufferedImage[]{frames[0]}, 1000)); // idle animation
        anim.addTrack(new Track(
                new BufferedImage[]{frames[0], frames[1], frames[2], frames[1]}));
        anim.setTrack(0);
        anim.getTrackOn().setMillisBetweenImages(110);
        body.setAnimation(anim);
        SwordEntityComponent[] sword = {new SwordEntityComponent(42, 2, 30, 15)};
        sword[0].makeASword(1);
        sword[0].setImage(manager.getImage("rougeWeapon"));
        RogueEntity enemy = new RogueEntity(body, sword);
        enemy.setGoldMod(15);
        return enemy;
    }
    
    public SwordEntity makeWhiteGhostBoss(float x, float y) {
        SwordEntityComponent body = new SwordEntityComponent(x, y, 60, 60);
        Animation anim = new Animation();
        anim.addTrack(new Track(manager.getImage("whiteGhostIdle"), 60, 60, 300)); // 0
        anim.addTrack(new Track(manager.getImage("whiteGhostPopOut"), 60, 60, 300)); // 1
        anim.addTrack(new Track(manager.getImage("whiteGhostAttackBefore"), 60, 60, 300)); // 2
        anim.addTrack(new Track(manager.getImage("whiteGhostAttackAfter"), 60, 60, 300)); // 3
        
        anim.setTrack(0); // idle
        body.setAnimation(anim);
        
        // area attack
        SwordEntityComponent[] sword = {new SwordEntityComponent(-10, -10, 80, 80)};
        Animation swordAnimation = new Animation();
        swordAnimation.addTrack(new Track(manager.getImage("whiteGhostWeaponAnim"), 80, 80, 500));
        sword[0].setAnimation(swordAnimation);
        sword[0].makeASword(1);
        WhiteGhostEntity entity = new WhiteGhostEntity(body, sword);
        return entity;
    }
}
