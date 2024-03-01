import java.util.HashMap;
import java.util.ArrayList;
import java.util.Stack;

/**
 * The Player class represnets the main character in the game.
 * The class contains attributes such as the player's current room, Inventory, attack statistics, and game state.
 * Methods are organized into player information, inventory management, iteraction with items and characters, and movement and game exploration
 * Bernardo k23092429
 * V4
 */
public class Player
{
    private String currentRoom;
    private Stack<String> previousRooms;
    private HashMap<String, Item> playerInventory;
    private final int maxWeight;
    private int attackStat;
    private boolean isDead;
    private boolean hasVisitedTransportRoom;
    private Printer printer;
    /**
     * roomName is the initial room where the player starts
     * Initializes a hashmap for the player inventory and a stack for the previous rooms
     */
    public Player(String roomName)
    {
        currentRoom = roomName;
        maxWeight = 20;
        previousRooms = new Stack<>();
        playerInventory = new HashMap<>();
        printer = new Printer();
        isDead = false;
        hasVisitedTransportRoom = false;
    }
    
    //Player Information
    public int getPlayerAttackStat(){
        return calculateAttackStat();
    }
    
    public String getPlayerRoom(){
        return currentRoom;
    }
    
    /**
     * Sets player's current room and pushes previous room into stack
     */
    public void setPlayerRoom(String roomName){
        previousRooms.push(currentRoom);
        currentRoom = roomName;
    }
    
    /**
     * Sets player's current room and pushes previous room into stack
     */
    public void setPlayerRoomBack(String roomName){
        currentRoom = roomName;
    }
    
    public boolean isDead(){
        return isDead;
    }

    /**
     * Sets player dead, used if layer loses fight against aggro characters.
     */
    public void setDead(){
        isDead = true;
    }
    
    //Inventory Management
    /**
     * Calculates the surplus weight the player can carry in their inventory based on the maximum weight limit.
     * It sums the weights of all items in the player's inventory and subtracts the total from the maximum weight.
     */
    public int getWeightSurplus(){
        int weightCarried = 0;
        //Iterates through each item in the player's inventory to calculate the total weight.
        for (Item item : playerInventory.values()){
            weightCarried += item.getWeight();
        }
        // Calculate the surplus weight by subtracting the total weight carried from the maximum weight limit.
        int weightSurplus = maxWeight - weightCarried;
        return weightSurplus;
    }
    
     /**
     * Calculates the attack stat of the weapons of the player, with a base attack stat of 5 in consideration for the player.
     * It sums the weights of all items in the player's inventory and subtracts the total from the maximum weight.
     */
    public int calculateAttackStat(){
        //Initialize the total attack points with the base attack stat.
        int totalAttackPoints = 5;
        //Iterates through each item in the player's inventory to calculate the attack stat.
        for(Item item : playerInventory.values()){
            if (item instanceof Weapon){
                Weapon weapon = (Weapon) item;
                totalAttackPoints += weapon.getAttackPoints();
            }
        }
        return totalAttackPoints;
    }
    
     /**
     * Returns a string representing the contents of the player's inventory.
     * If command has a second word, it returns a message asking to clarify, as the player input should be 'bag'.
     * If the player's inventory is empty, it informs the player that there are no items in their bag.
     * Otherwise, it lists the names of items in the player's inventory.
     */
    public String getPlayerInventory(Command command) {
        String returnString = "";
        if(command.hasSecondWord()){
            return returnString = "Bag what?";
        }
        else if (playerInventory.isEmpty()){
            return returnString = "There are no items in you bag.";
        }
        else{
            returnString += "The items in you bag are:";
            for (String item : playerInventory.keySet()) {
                returnString += " " + item;
            }
            return returnString;
        }
    } 

    public Item getItemFromInventory(String itemName){
        return playerInventory.get(itemName);
    }
    
    public void addItemToInventory(Item item){
        playerInventory.put(item.getItemName(),item);  
    }
    
    public Item removeItemFromInventory(String itemName){
        return playerInventory.remove(itemName);
    }
    
    public boolean hasItem(String itemName){
        if (playerInventory.get(itemName) == null){
            return false;
        }
        else{
            return true;
        }
    }
    
    //Interaction with Items and Characters
    
    /**
     * Attempts to pick up an item from the current room and add it to the player's inventory.
     * Checks conditions such as if item exists in the room,if item can be grabbed, and weight capacity before performing the action.
     */
    public void grabItem(Room currentRoom,String itemName){
        //Uses method in Room class that returns true if item is in Room; 
        if(currentRoom.isItemInRoom(itemName)){
            Item item = currentRoom.getItemFromRoom(itemName);
            int itemWeight = item.getWeight();
            int remainingWeight = getWeightSurplus();
            //Uses conditions to check if item can be added to inventory, printing an appropriate response depending on the item
            if(!item.canBeGrabbed()){
                System.out.println("You cant pick up the " + itemName + ".");
            }else if(!(itemWeight <= remainingWeight)){
                System.out.println("You are holding onto too many items! Check your 'bag' and see if you could drop some to make room.");
            }else if(item.canBeGrabbed() && (itemWeight <= remainingWeight)){
                currentRoom.removeItemFromRoom(itemName);
                addItemToInventory(item);
                System.out.println("You grabbed: " + itemName);
                //An additional quality of life measure for the player, so they get to know their new attackStat after grabbing a weapon
                if (item instanceof Weapon){
                    System.out.println("Your atttack points are now: " + calculateAttackStat());
                }
                System.out.println(item.getItemDescription());
            }
        }
    }
    
    /**
     * Attempts to drop an item from the player's inventory, and will add it to the player's current room.
     * Checks conditions such as if item exists in the player's inventory,and if its a weapon.
     */
    public void dropItem(Room currentRoom,String itemName){
        //Method that returns true if item is in player's inventory
        if(hasItem(itemName)){
            Item item = removeItemFromInventory(itemName);
            currentRoom.addItemToRoom(item);
            System.out.println("You dropped: " + itemName);
            //An additional quality of life measure for the player, so they get to know their new attackStat after dropping a weapon
            if (item instanceof Weapon){
                System.out.println("Your atttack points are now: " + calculateAttackStat());
            }            
        }else{
            //If player doesn't have item, prints a message saying so.
            System.out.println("You don't have " + itemName + " in your Inventory.");
        }
    }
    
    /**
     * Attempts to give an item from the player's inventory to a character in the current room.
     * Checks if the specified character is present and if the player possesses the item before performing the action.
     * Removes offered item from player's inventory
     * Displays appropriate messages and removes the character from the game if the exchange is successful.
     */    
    public void giveItem(String itemName, Character character,Room currentRoom, Room characterRoom){
        
        //Checks if character is in the room
        if(character.getCharacterRoom().equals(getPlayerRoom())){
            //Checks if player has item offered
            if(hasItem(itemName)){
                //Calls the item that the character is holding and stores it in teh players inventory
                ArrayList<Item> characterItems = new ArrayList<>(character.getCharacterInventory().values());
                Item characterItem = characterItems.get(0);
                addItemToInventory(characterItem);
                Item item = playerInventory.get(itemName);
                character.addItemToCharInv(item);
                removeItemFromInventory(itemName);

                System.out.println("You gave the " + itemName + ".");
                System.out.println("You have received the " + characterItem.getItemName() + " from " + character.getCharacterName());
                System.out.println(character.getCharacterStory());
            }else{
                System.out.println("You don't have "+itemName+" .");
            }
        }else{
            System.out.println("That character is not in this room");
        }
    }
    
    
    //Movement and game exploration
    /**
    * Attempts to move the player to a neighboring room based on the input direction.
    * Checks conditions such as the existence of a locked room, its locked status,but also checks for transportation rooms.
    * Displays appropriate messages and updates the player's current room. 
    */  
    public void movePlayer(String direction,HashMap<String,Room> map){
        Room currentRoom = map.get(getPlayerRoom());
        //Extract nextRoom by checking teh direction word input by user
        Room nextRoom = currentRoom.getExit(direction);
        //If next room is a KeyRoom ,cast it to KeyRoom type.
        if(nextRoom instanceof KeyRoom){
            nextRoom = (KeyRoom)nextRoom;
        }
    
        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        //If next room is the transport room, use method teleport in room class for character to get in next room
        //Player has now visited transport room, so back command gets disabled(accurate to story)
        else if (nextRoom.isTransportRoom()){
            visitedTransportRoom();
            currentRoom = currentRoom.teleport(map);
            setPlayerRoom(currentRoom.getRoomName());
            System.out.println(currentRoom.getLongDescription());
        }
        //meetsConditions() is a method used to check if the keyRoom is unlocked
        //adds previous room to an array, sets new player room, and prints the a detailed description of the room
        else if(nextRoom.meetsConditions()){
            currentRoom = nextRoom;
            setPlayerRoom(currentRoom.getRoomName());
            System.out.println(currentRoom.getLongDescription());
        }//This else is reached only if keyRoom is locked
        else{
            System.out.println(nextRoom.needKey());
        }
    }
    
    /**
    * Attempts to move the player back to the previous room. 
    * Checks if there are previous rooms in the stack.
    * Displays appropriate messages and updates the player's current room.
    * If player has been in transport room, back button gets disabled(accurate to story)
    */
    public void goBack(HashMap<String,Room> map){
        //Check if there are no previous rooms in the array.
        if(previousRooms.empty()){
            System.out.println("You havent moved yet.");
        }else if(hasVisitedTransportRoom){
            System.out.println("You can't go back now, you made a mistake trying to enter Kenny's Office.");
        }else{
            String previousRoomName = previousRooms.pop();
            Room previousRoom = map.get(previousRoomName);
            setPlayerRoomBack(previousRoomName);
            Room currentRoom = previousRoom;
            System.out.println(currentRoom.getLongDescription());
        }
    }

    
    /**
    * Checks if roomName input my player is an instance of a key Room
    * If the player has the required key in their inventory,and is adjacent to room, unlocks the room and displays related messages.
    * Otherwise, informs the player that they don't have the key, or that they are not adjacent to the room.
    */
    public void unlockRoom(Room currentRoom,Room nextRoom){
        if(nextRoom instanceof KeyRoom){
            KeyRoom lockedRoom = (KeyRoom) nextRoom;
            String key = lockedRoom.getKey();
            //Checks if player is next to the room
            if(currentRoom.isAdjacent(lockedRoom)){
                if(hasItem(key)){
                    //Displays a message saying that room ha sbeen unlocked and also a paragraph relevant to the story of the game
                    System.out.println(lockedRoom.unlockRoom());
                    printer.UnlockedExamRoomText();
                }else{
                    System.out.println("You don't have the key to unlock this room.");
                }
            }else{
                System.out.println("You arent right beside this room.");
            }
        }else{
            System.out.println("This room doesnt need unlocking.");
        }
    }
    
    //Methods used for the transport room
    public void visitedTransportRoom(){
        hasVisitedTransportRoom = true;
    }
    
    public boolean hasVisitedTransportRoom(){
        return hasVisitedTransportRoom;
    }
    
}
