/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package indiesvsgamersbropals.entity.enem;

import bropals.lib.simplegame.util.Counter;
import bropals.lib.simplegame.util.CounterFunction;
import indiesvsgamersbropals.entity.SwordEntity;
import indiesvsgamersbropals.entity.SwordEntityComponent;
import java.awt.Rectangle;

/**
 * The white ghost boss
 * @author Kevin
 */
public class WhiteGhostEntity extends SwordEntity {

    private SwordEntity player;
    
    /**
     * The bounds that the ghost will spawn in.
     */
    private final Rectangle bounds;
    
    private Counter blinkBeforeCounter;
    private Counter blinkCounter; // counter to blink next
    private Counter attackBeforeCounter; // counter to start and finish before attack animation
    private Counter attackCounter; // counter for how long the attack thing will stay
    
    public WhiteGhostEntity(SwordEntityComponent body, 
            SwordEntityComponent[] swords) {
        super(body, swords);
        bounds = new Rectangle(50, 50, 700, 500);
        
        blinkBeforeCounter = new Counter(5000, false, new CounterFunction() {
            @Override
            public void countFinished() {
                // turn off the weapon
                System.out.println("blink before");
                getFirstWeapon().setEnabled(false);
                getBody().getAnimation().setTrack(1);
                blinkCounter.reset();
            }
        });
        
        blinkBeforeCounter.reset();
        
        // blink every 5 seconds
        blinkCounter = new Counter(5000, false, new CounterFunction() {
            @Override
            public void countFinished() {
                System.out.println("blink after");
                blink();
                getBody().getAnimation().setTrack(2);
                attackBeforeCounter.reset();
            }
        });

        attackBeforeCounter = new Counter(1000, false, new CounterFunction() {
            @Override
            public void countFinished() {
                System.out.println("attack before");
                attackCounter.reset();
            }
        });
        
        attackCounter = new Counter(1000, false, new CounterFunction() {
            @Override
            public void countFinished() {
                System.out.println("attacking");
                getBody().getAnimation().setTrack(2);
                getFirstWeapon().setEnabled(true);
                blinkBeforeCounter.reset();
            }
        });
    }
    
    public void givePlayer(SwordEntity player) {
        this.player = player;
    }
    
    private void blink() {
        
    }

    @Override
    public void update(int i) {
        blinkBeforeCounter.update();
        blinkCounter.update();
        attackBeforeCounter.update();
        attackCounter.update();
        super.update(i);
    }
}
