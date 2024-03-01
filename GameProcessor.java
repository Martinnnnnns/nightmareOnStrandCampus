import java.util.HashMap;
import java.util.Scanner;
/**
 * The GameProcessor class is responsible for processing the foundation of the game
 * by reading input from a Scanner and initializing the game elements, including rooms,
 * items, exits, and character
 *
 * The processGameFoundation method reads input
 * from a Scanner, interprets commands, and constructs the game world accordingly.
 *
 * Bernardo Guterres k23092429
 * V3
 */
public class GameProcessor
{
    // instance variables - replace the example below with your own
    /**
     * Constructor for objects of class GameProcessor
     */
    public GameProcessor()
    {
        //No specific initialization required in the constructor
    }
    
    /**
     * Processes the foundation of the game by reading input from a Scanner and initializing
     * the game elements, including rooms, items, exits, and characters.
     *
     * *This method iterates through each line in the provided Scanner file and interprets
     * the commands specified in the input file to construct the game world. It uses various
     * helper methods to process specific commands based on the command type.
     */
    public void processGameFoundation(Scanner file,HashMap<String, Room> map, HashMap<String,Character> npcs){
        int lineNum = 0;
        while(file.hasNext()){
            lineNum++;
            String line = file.next();
            Scanner scanLine = new Scanner(line);
            String nextCase = "";
            if (scanLine.hasNext()){
                nextCase = scanLine.next();
            }
            
            switch (nextCase){
                case "//":
                    //Does nothing, simply used to show the formats in the .txt file
                    break;
                case "room":
                    processRoomCommand(scanLine, lineNum,map);
                    break;
                case "addIn":
                    processItemCommand(scanLine, lineNum,map);
                    break;
                case "exitsOf":
                    processExitCommand(scanLine, lineNum, map);
                    break;
                case "character":
                    processCharacterCommand(scanLine, lineNum, npcs);
                    break;
                case "giveTo":
                    processCharacterItemCommand(scanLine, lineNum, npcs);
                case "":
                    //Handles empty lines, does nothing
                    break;
            }
        }
        
        
    }
    
    // Room Processing
    /**
     * Processes the "room" command, creating and adding a new room to the game map.
     * It extracts relevant information such as room type, name, transport status, and description
     * from the provided scanLine and constructs a Room object using the CreateRoom method. The created room is
     * then added to the game map.
     */
    private void processRoomCommand(Scanner scanLine,int lineNum,HashMap<String, Room> map){
        //initiliazing variables with default values
        String roomType = " ";
        boolean isTransportRoom = false;
        String name = " ";
        String description = " ";
        String keyForLock = " ";
        //Extracting information from one line, will keep repeating for every line that starts with "room"
        if(scanLine.hasNext()){
            roomType = scanLine.next();
        }
        if(scanLine.hasNext()){
            name = scanLine.next();
        }
        if(scanLine.hasNextBoolean()){
            isTransportRoom = scanLine.nextBoolean();
         }
        if(scanLine.hasNext() && (roomType.equals("lock"))){
            keyForLock = scanLine.next();
        }
        while (scanLine.hasNext()){
            description += scanLine.next() + " ";
        }
        //Creates the room object with information collected in parameters and places it in the map
        Room room = createRoom(roomType,isTransportRoom, name, keyForLock, description);
        map.put(room.getRoomName(), room);
    }
    
    private Room createRoom(String roomType, boolean isTransportRoom, String name,  String keyForLock, String description){
        Room room = null;
        //If roomType equals lock room will be handled accordingly as oject of class KeyRoom
        
        if (roomType.equals("lock")){
            room = new KeyRoom(name, description,isTransportRoom);
        } else {
            room = new Room(name, description, isTransportRoom);
        }
        
        if (room instanceof KeyRoom){
            KeyRoom lockRoom = (KeyRoom)room;
            lockRoom.setKey(keyForLock);
            return lockRoom;
        } else {
            return room;
        }
    }
    
    //Item processing
    /**
     * Processes the "addIn" command, creating and adding a new item to a specific room in the game map.
     * It extracts relevant information such as storeRoom, itemType, name, grab status,attack stat(if weapon), and description
     * from the provided scanLine and constructs a Item object using the CreateIte  method. The created item is
     * then added to the appropriate room.
     */
    private void processItemCommand(Scanner scanLine, int lineNum,HashMap<String, Room> map){
        //Initializing variables with default values
        String storeRoom = " ";
        String itemType = " ";
        String itemName = " ";
        int weight = 0;
        boolean canBeGrabbed = false;
        int attackStat = 0;
        String itemDesc = " ";
        //Extracting information from one line, will keep repeating for every line that starts with "addIn"
        if(scanLine.hasNext()){
            storeRoom = scanLine.next();
        }
        if(scanLine.hasNext()){
            itemType = scanLine.next();
        }
        if(scanLine.hasNext()){
            itemName = scanLine.next();
        }
        if(scanLine.hasNextInt()){
            weight = scanLine.nextInt();
        }
        if(scanLine.hasNextBoolean()){
            canBeGrabbed = scanLine.nextBoolean();
        }
        if(scanLine.hasNextInt() && itemType.equals("weapon")){
            attackStat = scanLine.nextInt();
        }
        while (scanLine.hasNext()){
            itemDesc += scanLine.next() + " ";
        }
        //Creates item with informated collected in parameters, and in the room provided in the line
        Item item = createItem(itemType, itemName, weight, canBeGrabbed, attackStat, itemDesc);
        map.get(storeRoom).addItemToRoom(item);       
    }
    
    private Item createItem(String itemType, String itemName, int weight, boolean canBeGrabbed, int attackStat, String itemDesc){
        Item item = null;
        //If itemType equals weapon item will be handled accordingly as oject of class Weapon
        
        if(itemType.equals("weapon")){
            item = new Weapon(itemName,weight,canBeGrabbed, attackStat, itemDesc);
        }else{
            item = new Item(itemName,weight,canBeGrabbed, itemDesc);
        }
        
        if(item instanceof Weapon){
            Weapon weapon = (Weapon)item;
            return weapon;
        }else{
            return item;
        }
    }
    
    //Exit processing
    /**
     * Processes the "exitsOf" command,setting the exits for the rooms in the game
     * It reads the room name and iterates through the provided directions and corresponding exit room names
     * in the input file. It then retrieves the specified room from the game map, sets the exits according to the
     * given directions, and links them to the corresponding rooms. If the specified room does not exist in the
     * game map, an error message is printed to the console.
     *
     */
    private void processExitCommand(Scanner scanLine, int lineNum,HashMap<String, Room> map){
        String roomName = "roomName";
        if(scanLine.hasNext()){
            roomName = scanLine.next();
        }
        
        Room room = map.get(roomName);
        if (room == null){
            System.out.println("you messed up with " + roomName + "at " + lineNum);
        }
        while (scanLine.hasNext()){
            String direction = scanLine.next();
            roomName = "Exit";
            if(scanLine.hasNext()){
                roomName = scanLine.next();
                Room exit = map.get(roomName);
                room.setExit(direction, exit);
            }
        }
    }
    
    
    //Character processing
    /**
     * Processes the "character" command from the input file, creating and adding a new character to the game.
     * It reads the character type, name, spawn room, if aggro, attack level (if aggressive),
     * and character story from the input file. It then creates a new Character object based on the provided
     * information and adds it to the HashMap of non-playable characters (npcs) in the game.
     *
     */
    private void processCharacterCommand(Scanner scanLine,int lineNum,HashMap<String, Character> npcs){
        //Initializing variables with default values
        String charType = " ";
        String charName = " ";
        String spawnRoom = " ";
        boolean isAggressive = false;
        boolean moves = false;
        int attackLevel = 0;
        String charStory = " ";
        //Extracting information from one line, will keep repeating for every line that starts with "character"
        if(scanLine.hasNext()){
            charType = scanLine.next();
        }
        if(scanLine.hasNext()){
            charName = scanLine.next();
        }
        if(scanLine.hasNext()){
            spawnRoom = scanLine.next();
        }
        if(scanLine.hasNextBoolean()){
            isAggressive = scanLine.nextBoolean();
        }
        if(scanLine.hasNextBoolean()){
            moves = scanLine.nextBoolean();
        }
        if(scanLine.hasNextInt() && isAggressive == true){
            attackLevel = scanLine.nextInt();
        }
        while (scanLine.hasNext()){
            charStory += scanLine.next() + " ";
        }
        // Creating a new Character object based on the provided information, and adds it to the npcs hashmap
        Character character = createCharacter(charType,charName,spawnRoom,isAggressive,moves,attackLevel,charStory);
        npcs.put(character.getCharacterName(), character);
    }
    
    private Character createCharacter(String charType,String charName,String spawnRoom,boolean isAggressive,boolean moves,int attackLevel,String charStory)
    {
        Character character = null;
        //If character type equals monsters character will be handled accordingly as oject of class Monster

        if(charType.equals("monster")){
            character = new Monster(charName, spawnRoom, isAggressive,moves,attackLevel, charStory);
        }else{
            character = new Character(charName, spawnRoom, isAggressive,moves,charStory);
        }
        
        if(character instanceof Monster){
            Monster monster = (Monster)character;
            return monster;
        }else{
            return character;
        }
    }
    
    //Character Item Processing
    /**
     * Processes the "giveTo" command from the input file, adding an item to the inventory of a specific character in the game.
     * It reads the character name, item type, item name, item weight, if can be grabbed,
     * attack stat (if the item is a weapon), and item description from the input file.
     * It then creates a new Item object based on the provided information and adds it to the inventory
     * of the specified character in the HashMap of npcs.
     *
     */
    private void processCharacterItemCommand(Scanner scanLine,int lineNum,HashMap<String, Character> npcs){
        String charName = " ";
        String itemType= " ";
        String itemName = " ";
        int itemWeight = 0;
        boolean canBeGrabbed = true;
        int attackStat = 0;
        String itemDesc = " ";
        if(scanLine.hasNext()){
            charName = scanLine.next();
        }
        if(scanLine.hasNext()){
            itemType = scanLine.next();
        }
        if(scanLine.hasNext()){
            itemName = scanLine.next();
        }
        if(scanLine.hasNextInt()){
            itemWeight = scanLine.nextInt();
        }
        if(scanLine.hasNextBoolean()){
            canBeGrabbed = scanLine.nextBoolean();
        }
        if(scanLine.hasNextInt() && itemType.equals("weapon")){
            attackStat = scanLine.nextInt();
        }
        while (scanLine.hasNext()){
            itemDesc += scanLine.next() + " ";
        }
        //Creates the item with the information provided, and adds it to the inventory of the appropriate character read in the line
        Item item = createItem(itemType,itemName,itemWeight,canBeGrabbed,attackStat,itemDesc);
        npcs.get(charName).addItemToCharInv(item);
    }
}
