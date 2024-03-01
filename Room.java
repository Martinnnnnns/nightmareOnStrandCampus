import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Random;
/**
 * Represents a room in the game.
 * Stores information of a room such as its name, its description, its exits and items, and if its a transport room.
 * Each room is connected to other rooms via exits and may contain items.
 * Some rooms are marked as transport rooms, allowing players to be teleported to random non-KeyRooms.
 * 
 * Bernardo Guterres k23092429
 * V3
 */

public class Room 
{
    private String roomName;
    private String roomDescription;
    private HashMap<String, Room> exits;        // stores exits of this room.
    private HashMap<String, Item> itemsInRoom;
    private boolean isTransportRoom;
    private Random random;
    private Printer printer;
    /**
     * Creates a room with the specified name, description, and transport status.
     * Initializes exits, and itemsInRoom hashmap.
     */
    public Room(String roomName, String roomDescription, boolean isTransportRoom) 
    {
        this.roomName = roomName;
        this.roomDescription = roomDescription;
        this.isTransportRoom = isTransportRoom;
        exits = new HashMap<>();
        itemsInRoom = new HashMap<>();
        random = new Random();
        printer = new Printer();
    }

    //Room information
    public String getRoomName(){
        return roomName;
    }
    
    public String getShortDescription()
    {
        return roomDescription;
    }
    
    public boolean isTransportRoom(){
        return isTransportRoom;
    }
    
    public boolean isAdjacent(Room room){
        return exits.containsValue(room);
    }
    
    /**
     * Return a description of the room in the form:
     * You are in the kitchen.
     * Exits: north west
     * Items in the room: knife table
     */
    public String getLongDescription()
    {
        return "You are in the " + roomName + ".\n" + roomDescription + "\n" + getExitString() + "\n" + getItemString() + "\n";
    }
    
    //Exits
    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        if(keys.isEmpty()){
            returnString += " None.";
            return returnString;            
        }
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }
    
    /**
     * Returns an array containg all the keys in the exits hashMap
     */
    public ArrayList<String> getExitList(){
        ArrayList<String> allExits = new ArrayList<>(exits.keySet());
        return allExits;
    }
    
    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null..
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }
    
    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }
        
    //Items manipulation
    public void addItemToRoom(Item item){
        if (itemsInRoom.get(item.getItemName()) == null){
            itemsInRoom.put(item.getItemName(), item);
        } else {
            System.out.println("There is already a " + item.getItemName() + " in " + roomName); // Debugging line to ensure no duplicate items are created
        }
    }
    
    public Item removeItemFromRoom(String itemName){
        return itemsInRoom.remove(itemName);
    }
    
    public Item getItemFromRoom(String itemName){
        return itemsInRoom.get(itemName);
    }
    
    /**
     * Returns boolean if itemName is included in the itemsInRoom hashmap
     */
    public boolean isItemInRoom(String itemName){
        if(itemsInRoom.containsKey(itemName)){
            return true;
        }else{
            System.out.println("That doesnt exist here.");
            return false;
        }
    }
    
    /**
     * Return a string describing the room's itmes, for example
     * "Items in the room: knife table
     */
    private String getItemString()
    {
        String returnString = "Items in the room:";
        Set<String> keys = itemsInRoom.keySet();
        if(keys.isEmpty()){
            returnString += " None.";
            return returnString;
        }
        for(String item : keys) {
            returnString += " " + item;
        }
        return returnString;
    }
        
    //Methods for Teleportation
    
    /**
     * Teleports the player to a random non-KeyRoom.
     */
    public Room teleport(HashMap<String,Room> map) {
        //prints teleport message
        printer.teleport();
        // Create a list of valid rooms for teleportation (excluding KeyRooms and transport rooms)
        ArrayList<Room> validRooms = new ArrayList<>();
        for (Room room : map.values()){
            if(!(room instanceof KeyRoom) && !(room.isTransportRoom)){
                validRooms.add(room);
            }
        }
        //Selects a random froom from the valid rooms
        int number = random.nextInt(validRooms.size());
        Room room = validRooms.get(number); 
        //Informs the player about the teleeportation and returns the roob object representing the next destination of the plaer
        System.out.println("You open your eyes, and realize you're now in the " +room.getRoomName() +".");
        return room;
    }
    
    //Methods mostly to cover for locked room, if called for object of room class, will return true
    public boolean meetsConditions(){
        return true;
    }
    //Used for debugging, 
    //this string would print on the screen in the case that the code would need a key for a normal room, which shouldnt occur
    public String needKey(){
        String returnString = "Key is only needed for KeyRoom class, this printing means there is an error";
        return returnString;
    }
    
}

