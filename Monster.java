/**
 * The Monster class is a subclass of the character class.
 * Monsters are a type of character with an additional attack stat.
 * Has a get method for the monster attckstat.
 * 
 * Bernardo Guterres k23092429
 * V3
 */
public class Monster extends Character
{
    private int attackStat;
    /**
     * Constructor for objects of class Monster
     */
    public Monster(String monsterName, String roomName, boolean isAggressive,boolean moves, int attackStat, String fightStory)
    {
        super(monsterName,roomName,isAggressive,moves, fightStory);// initialise instance variables
        this.attackStat = attackStat;
    }

    //Accessor methods
    public int getMonsterAttackStat()
    {
        return attackStat;
    }
}