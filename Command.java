/**
 *
 * This class holds information about a command that was issued by the user.
 * A command can consist of up to three strings: a command word, a second
 * word, and a third(this is only used in for the give command, which goes give (item) (character).
 * 
 * The way this is used is: Commands are already checked for being valid
 * command words. If the user entered an invalid command (a word that is not
 * known) then the command word is set to INVALID(enum), and nothing occurs.
 *
 * If the command had only one word, then the second word and the third word are null.
 * 
 * Bernardo Guterres k23092429
 * V2
 */

public class Command
{
    private CommandWord commandWord;
    private String secondWord;
    private String thirdWord;
    /**
     * Create a command object. First, second and third word must be supplied, but
     * either one (or all) can be null.
     */
    public Command(CommandWord commandWord, String secondWord, String thirdWord)
    {
        this.commandWord = commandWord;
        this.secondWord = secondWord;
        this.thirdWord = thirdWord;
    }

    /**
     * Return the command word (the first word) of this command. If the
     * command was not understood, the result is null.
     */
    public CommandWord getCommandWord()
    {
        return commandWord;
    }
    
    /**
     * Returns the second word of this command. Returns null if there was no
     * second word.
     */
    public String getSecondWord()
    {
        return secondWord;
    }
    
    
    /**
    * Returns the third word of this command. Returns null if there was no 
    * second word.
    */
    public String getThirdWord(){
        return thirdWord;
    }

    /**
     * Return true if this command was not understood.
     */
    public boolean isInvalid()
    {
        return (commandWord == CommandWord.INVALID);
    }

    /**
     * Return true if the command has a second word.
     */
    public boolean hasSecondWord()
    {
        return (secondWord != null);
    }
    
    /**
     * Return true if the command has a third word.
     */    
    public boolean hasThirdWord(){
        return (thirdWord != null);
    }
    
    /**
     * Return true if there is no second word or if there is a third. Used in the game class to avoid code duplication
     * as there are many commands that can only take two words, such as grab x, drop x, unlock x, etc...
     */     
    public boolean noSecondWordOrHasThird(){
        if(!hasSecondWord() || hasThirdWord()){
            return true;
        }else{
            return false;
        }
    }

    
    
}

