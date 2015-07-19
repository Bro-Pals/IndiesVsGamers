/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package indiesvsgamersbropals;

import org.gamejolt.GameJoltAPI;

/**
 *
 * @author Kevin
 */
public class GameJoltDataHolder {
    
    public enum VerifyType {FULL, GUEST, NONE;}

    private final int GAME_ID = 80698;
    private final String GAME_SIGNATURE = "f1058d3bb8c74776419554ddfc6d6858";
    
    private final int HIGHSCORE_TABLE_ID = 83711;
    
    private final GameJoltAPI api = new GameJoltAPI(GAME_ID, GAME_SIGNATURE);
    private String username;
    private String token;
    private VerifyType verifyType;
    
    public GameJoltDataHolder(String username, String token) {
        this.username = username;
        this.token = token;
        verifyType = VerifyType.FULL;
    }
    
    public GameJoltDataHolder(String guestName) {
        this.username = guestName;
        verifyType = VerifyType.GUEST;
    }
    
    public GameJoltDataHolder() {
        verifyType = VerifyType.NONE;
    }
    
    public boolean validateUser() {
        if (verifyType == VerifyType.FULL) {
            return api.verifyUser(username, token);
        } else if (verifyType == VerifyType.NONE) {
            return false;
        }
        // other types of entry doesn't need to be validated
        return true;
    }

    /**
     * add the given amount of gold to the scoreboard
     * @param goldAmount 
     */
    public void saveScore(int goldAmount, String gameTime) {
        if (!validateUser()) {
            return;
        }
        
        if (verifyType == VerifyType.FULL) {
            // add using the game jolt account
            api.addHighscore(HIGHSCORE_TABLE_ID, goldAmount + " Gold", goldAmount, 
                    "Time passed: " + gameTime);
        } else if (verifyType == VerifyType.GUEST) {
            // save using the guest name
            api.addHighscore(HIGHSCORE_TABLE_ID, username, goldAmount + " Gold", goldAmount, 
                    "Time passed: " + gameTime);
        }
    }
    
    public VerifyType getVerifyType() {
        return verifyType;
    }
    
}
