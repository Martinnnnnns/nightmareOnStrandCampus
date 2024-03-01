/**
 * KeyRoom represents a special type of room that requires unlocking with a key.
 * It extends the functionality of the base Room class and introduces mechanisms for
 * unlocking and setting a key for the room.
 *
 * KeyRooms provide an added layer of complexity by requiring
 * the player to first unlock the room before being able to enter.
 *
 * Bernardo Guterres k23092429
 * V2
 */
public class KeyRoom extends Room
{
    // instance variables - replace the example below with your own
    private boolean isLocked;
    private String keyForLock = "";
    /**
     * Constructor for objects of class KeyRoom
     */
    public KeyRoom(String roomName,String roomDescription,boolean isTransportRoom)
    {
        super(roomName, roomDescription, false);
        this.isLocked = true;// initialise instance variables
        
    }

    public boolean meetsConditions(){
        return !isLocked;
    }
    
    public String needKey(){
        String returnString = "You need to unlock the "+getRoomName()+". You will need a key for that. If you have the key, then use the 'unlock "+getRoomName()+"' command.";
        return returnString;
    }
    
    /**
     * Unlocks the room,allowing player to enter with the "go x" command
     * that has been linked to this room.
     */
    public String unlockRoom(){
        isLocked = false;
        return "Room has been unlocked..."; // add system print "you can now enter xxxx"
    }
    
    /**
     * Sets the name of the key needed to open the room
     */
    public String setKey(String key){
        this.keyForLock = key;
        return key;
    }
    
    public String getKey(){
        return keyForLock;
    }
    
}
