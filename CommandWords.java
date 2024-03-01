import java.util.HashMap;
/**
 * 
 * This class holds an enumeration of all command words known to the game.
 * It is used to recognise commands as they are typed in.
 *
 * Bernardo Guterres k23092429
 * V1
 */

public class CommandWords
{
    //Hashmap that holds all valid commands
    private HashMap<String,CommandWord> validCommands;

    /**
     * Constructor - initialise the command words.
     */
    public CommandWords()
    {
        validCommands = new HashMap<>();
        for(CommandWord command : CommandWord.values()){
            if(command != CommandWord.INVALID) {
                validCommands.put(command.convertToString(), command);
            }
        }
    }
    
    /**
     * Check whether a given String is a valid command word. 
     * @return true if it is, false if it isn't.
     */
    public boolean isCommand(String aString)
    {
        return validCommands.containsKey(aString);
    }
    
    public CommandWord getCommandWord(String commandWord){
        CommandWord command = validCommands.get(commandWord);
        if(command != null){
            return command;
        }
        else{
            return CommandWord.INVALID;
        }
    }
    
    public void showCommandWords() 
    {
        for(String command : validCommands.keySet()) {
            System.out.print(command + "  ");
        }
        System.out.println();
    }
}