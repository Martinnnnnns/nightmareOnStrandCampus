
/**
 * Subclass of Item class, representing a weapon item in the game
 * Includes the weapons'attack stat.
 *
 * Bernardo Guterres k23092429
 * V1
 */
public class Weapon extends Item
{
    private int weaponAttackStat;
    /**
     * Constructor for objects of class Weapon
     */
    public Weapon(String weaponName,int weaponWeight,boolean canBeGrabbed, int weaponAttackStat, String itemDesc)
    {
        super(weaponName, weaponWeight, canBeGrabbed, itemDesc);
        this.weaponAttackStat = weaponAttackStat;
    }
    
    //Accessor methods
    public int getAttackPoints() {
        return weaponAttackStat;
    }
}
