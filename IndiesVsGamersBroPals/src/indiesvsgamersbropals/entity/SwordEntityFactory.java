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
        anim.addTrack(new Track(
                new BufferedImage[]{frames[0], frames[1], frames[2], frames[1]}));
        anim.setTrack(0);
        anim.getTrackOn().setMillisBetweenImages(80);
        body.setAnimation(anim);
        SwordEntityComponent[] sword = {new SwordEntityComponent(42, 14, 30, 15)};
        sword[0].makeASword(1);
        sword[0].setImage(manager.getImage("playerWeapon"));
        return new SwordEntity(body, sword);
    }
    
    public SwordEntity makeGuardEnemy() {
        SwordEntityComponent body = new SwordEntityComponent(0, 0, 42, 50);
        Animation anim = new Animation();
        BufferedImage[] frames = (new Track(manager.getImage("guard"), 42, 50)).getImages();
        anim.addTrack(new Track(
                new BufferedImage[]{frames[0], frames[1], frames[2], frames[1]}));
        anim.setTrack(0);
        anim.getTrackOn().setMillisBetweenImages(80);
        body.setAnimation(anim);
        SwordEntityComponent[] sword = {new SwordEntityComponent(-45, 18, 45, 15)};
        sword[0].makeASword(1);
        sword[0].setImage(manager.getImage("guardWeapon"));
        GuardEntity enemy = new GuardEntity(body, sword);
        enemy.setGoldMod(15);
        return enemy;
    }
    
    public SwordEntity makeKnightDownEnemy() {
        SwordEntityComponent body = new SwordEntityComponent(0, 0, 42, 50);
        Animation anim = new Animation();
        BufferedImage[] frames = (new Track(manager.getImage("knightDown"), 42, 50)).getImages();
        anim.addTrack(new Track(
                new BufferedImage[]{frames[0], frames[1], frames[2], frames[1]}));
        anim.setTrack(0);
        anim.getTrackOn().setMillisBetweenImages(80);
        body.setAnimation(anim);
        SwordEntityComponent[] sword = {new SwordEntityComponent(8, 50, 15, 30)};
        sword[0].makeASword(1);
        sword[0].setImage(manager.getImage("knightDownWeapon"));
        KnightDownEntity enemy = new KnightDownEntity(body, sword);
        enemy.setGoldMod(15);
        return enemy;
    }
    
    public SwordEntity makeKnightUpEnemy() {
        SwordEntityComponent body = new SwordEntityComponent(0, 0, 42, 50);
        Animation anim = new Animation();
        BufferedImage[] frames = (new Track(manager.getImage("knightUp"), 42, 50)).getImages();
        anim.addTrack(new Track(
                new BufferedImage[]{frames[0], frames[1], frames[2], frames[1]}));
        anim.setTrack(0);
        anim.getTrackOn().setMillisBetweenImages(80);
        body.setAnimation(anim);
        SwordEntityComponent[] sword = {new SwordEntityComponent(8, -30, 15, 30)};
        sword[0].makeASword(1);
        sword[0].setImage(manager.getImage("knightUpWeapon"));
        KnightUpEntity enemy = new KnightUpEntity(body, sword);
        enemy.setGoldMod(15);
        return enemy;
    }
    
    public SwordEntity makeRogueEnemy() {
        SwordEntityComponent body = new SwordEntityComponent(0, 0, 42, 50);
        Animation anim = new Animation();
        BufferedImage[] frames = (new Track(manager.getImage("rogue"), 42, 50)).getImages();
        anim.addTrack(new Track(
                new BufferedImage[]{frames[0], frames[1], frames[2], frames[1]}));
        anim.setTrack(0);
        anim.getTrackOn().setMillisBetweenImages(80);
        body.setAnimation(anim);
        SwordEntityComponent[] sword = {new SwordEntityComponent(42, 15, 30, 15)};
        sword[0].makeASword(1);
        sword[0].setImage(manager.getImage("rogueWeapon"));
        RogueEntity enemy = new RogueEntity(body, sword);
        enemy.setGoldMod(15);
        return enemy;
    }
}
