import java.util.Random;
/**
 * The Battle class represents the battle between the player and a monster character in the game.
 * It contains methods to determine whether the player has won the battle based on attack statistics.
 * The class utilizes a random number generator to introduce an element of chance in the battle outcome.
 *
 * Bernardo Guterres k23092429
 * V1
 */
public class Battle
{
    // instance variables - replace the example below with your own
    private Player player;
    private Random random;
    
    public Battle(){
        //No initialization required
    }

     /**
     * Determines whether the player has won the battle through comparing attack stats.
     * playerAttack > monsterAttack 5% chance death
     * playerAttack == monsterAttack 10 % chance death
     * playerAttack < monsterAttack 15 % chance death
     * If player has the item guardian totem in inventory death chances are reduced by 3%(2,7,12)
     * Returns true if playerWins
     */
    public boolean hasPlayerWon(Player player, Character character){
        Monster monster = (Monster) character;
        int playerAttack = player.getPlayerAttackStat();
        int monsterAttack = monster.getMonsterAttackStat();
        double[] deathChance = {0.05,0.1,0.15};
        
        if(player.hasItem("guardiantotem")){
            for (int i = 0; i < deathChance.length; i++){
                deathChance[i] -= 0.03;
            }
        }
        //Calls method below to determine which double in deathChance to use
        int probability = comparePlayerCharacterAttackLevel(playerAttack, monsterAttack);
        
        Random random = new Random();
        //Generates random value from 0.00 to 1.00
        double randomValue = random.nextDouble();
        
        return randomValue > deathChance[probability];
    }

    /**
     * Compares the attack levels of the player and a monster to determine the battle outcome.
     * Returns an integer representing the probability of the player winning the battle.
     */
    private int comparePlayerCharacterAttackLevel(int playerAttack,int monsterAttack){
        if(playerAttack > monsterAttack){
            return 0;
        }else if(playerAttack == monsterAttack){
            return 1;     
        }else{
            return 2;
        }
    }
}
