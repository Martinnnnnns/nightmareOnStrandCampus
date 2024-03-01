/**
 * The Item class represents an item that can be interacted with in the game.
 * Characters have attributes such as their name, weight, grab status, and description.
 *
 * Bernardo Guterres k23092429
 * V1
 */
public class Item
{
    private String itemName;
    private int itemWeight;
    private boolean canBeGrabbed;
    private String itemDescription;
    /**
     * Constructor for objects of class Item
     */
    public Item(String itemName, int itemWeight, boolean canBeGrabbed, String itemDescription)
    {
        this.itemName = itemName;
        this.itemWeight = itemWeight;
        this.canBeGrabbed = canBeGrabbed;
        this.itemDescription = itemDescription;
    }

    //Accessor methods
    public String getItemName(){
        return itemName;
    }

    public int getWeight(){
        return itemWeight;
    }
    
    public boolean canBeGrabbed(){
        return canBeGrabbed;
    }
    
    public String getItemDescription(){
        return itemDescription;
    }

}
