
/**
 * The CommandWord enum contains all valid command words that player uses in the game.
 * 
 * Bernardo Guterres k23092429
 * V1
 */
public enum CommandWord
{
    
    //Enum constants representing command words
    GO("go"), QUIT("quit"), HELP("help"), INVALID("?"), GRAB("grab"), DROP("drop"), BAG("bag"), GIVE("give"), UNLOCK("unlock"), BACK("back");
    private String commandString;
    
    /**
     * Constructor for objects of class CommandWord
     */
    CommandWord(String commandString)
    {
        this.commandString = commandString;
    }

    /**
     * Converts command word to its string representation
     */
    public String convertToString(){
        return commandString;
    }
}
