import java.util.HashMap;
import java.util.ArrayList;
import java.util.Random;
import java.util.Map;
/**
 * The character class represents  a character in the game.
 * Characters have attributes such as their name, story, current room, inventory, if they're aggro and if they move.
 *
 * Bernardo Guterres k23092429
 * V3
 */
public class Character
{
    private boolean isAggressive;
    private boolean moves;
    private boolean hasInteracted;
    private String currentCharRoom;
    private String characterName;
    private String charStory;
    private Random random = new Random();
    private HashMap<String, Item> characterInventory;
    /**
     * Constructor for objects of class Character
     */
    public Character(String characterName, String spawnRoom, boolean isAggressive,boolean moves,String charStory){
        currentCharRoom = spawnRoom;
        this.characterName = characterName;
        this.charStory = charStory;
        this.isAggressive = isAggressive;
        this.moves = moves;
        this.hasInteracted = false; //used for giving item and for fight logic of game
        characterInventory = new HashMap<>();
    }
    
    //Accessor methods
    public boolean isAggressive(){
         return isAggressive;
    }
    
    public boolean canMove(){
        return moves;
    }
    
    public boolean hasInteracted(){
        return hasInteracted;
    }
    
    public void setInteracted(){
        hasInteracted = true;
    }
    public String getCharacterName(){
        return characterName;
    }
    
    public String getCharacterRoom(){
        return currentCharRoom;
    }
    
    public String getCharacterStory(){
        return charStory;
    }
    
    public Map<String,Item> getCharacterInventory(){
        return characterInventory;
    }
    
    //Room and Inventory manipulation methods
    public void setCharacterRoom(String roomName){
        currentCharRoom = roomName;
    }
    
    public void addItemToCharInv(Item item){
        characterInventory.put(item.getItemName(),item);  
    }
    
    //Character Movement method
    /**
     * Moves character to a randomly chosen adjacent room if the room is not a transport or a locked room.
     */
    public void moveCharacter(Room currentCharacterRoom){
        //At the moment all characters can move, but implemented this if condition for possible extensions of code
        if(canMove()){
            //Get list of valid exits from that room
            ArrayList<String> exits = currentCharacterRoom.getExitList();
            //Randomly select one
            int num = random.nextInt(exits.size());
            String anExit = exits.get(num);
            //Gets room based on exit selected
            Room nextRoom = currentCharacterRoom.getExit(anExit);
            //If room is a lockedRoom or a transportRoom, character will not move
            if((nextRoom instanceof KeyRoom) || nextRoom.isTransportRoom()){
                //Nothing should happen, character simply doesn't move
            }else if(nextRoom != null && !nextRoom.isTransportRoom()){
                //Moves character to next room
                setCharacterRoom(nextRoom.getRoomName());
            }else{
                System.out.println(" This should not have printed."); //Debugging line to ensure it works
            }
        }
    }
}
