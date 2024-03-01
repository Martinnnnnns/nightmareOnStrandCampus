import java.util.Random;
import java.util.HashMap;
import java.io.*;
import java.util.Scanner;

/**
 * The Game class is the main class of the program.
 *
 * To play this game, create an instance of this class and call the "play" method.
 *
 * This main class creates and initializes all the necessary components:
 * rooms, parser, player, NPCs, battle, and printer. It also evaluates and
 * executes the commands that the parser returns.
 *
 * @author Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Player player;
    private HashMap<String, Room> map;
    private HashMap<String, Character> npcs;
    private Battle battle;
    private Printer printer;
    /**
     * Create the game and initialise its internal map, player, battle, an dprinter.
     */
    public Game() 
    {
        parser = new Parser();
        map = new HashMap<> ();
        npcs = new HashMap<> ();
        player = new Player("MainEntrance");
        battle = new Battle();
        printer = new Printer();
        //Loads game foundation from a file
        try{
            //Buffered reader allows for more efficient reading of a file as a strean if character
            //Scanner will read the specified efficiently
            //Delimiter is set to "\n" which represents a newlien character, meaning scanner reads file line by line//
            Scanner scanner = new Scanner(new BufferedReader(new FileReader("GameFoundation.txt")));
            scanner.useDelimiter("\\n");
            GameProcessor gameProcessor = new GameProcessor();
            gameProcessor.processGameFoundation(scanner, map, npcs);
            currentRoom = map.get(player.getPlayerRoom());
        }catch (IOException e){
            System.out.println("Error:" + e); //had to search up how to catch errors in the exception of file not found, this was the solution.
        }
    }
    
    /**
     *  Main play routine.  Loops until end of play.
     
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
        //Game will end wiht quit command, if player is dead, or if player enters the exam room
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
            if(player.getPlayerRoom().equals("ExamRoom")){
                printWin();
                finished = true;
            }else if(player.isDead()){
                finished = true;
            }
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        printer.welcome();
        System.out.println(currentRoom.getLongDescription());
    }
    
    /**
     * Print out the ending message for the player.
     */
    private void printWin(){
        printer.win();
    }
    
    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;
        
        CommandWord commandWord = command.getCommandWord();

        //Switch statement to handle different command words        
        switch (commandWord){
            case INVALID:
                System.out.println("I don't know what you mean...");
                break;
            case HELP:
                printHelp();
                break;                
            case GO:
                goRoom(command);
                //Move characters in the game
                moveCharacter();
                //Check if the player and a character are in the same room
                checkCharacterPlayerInSameRoom();
                break;                
            case QUIT:
                wantToQuit = quit(command);
                break;                
            case BACK:
                goBack(command);
                moveCharacter();
                checkCharacterPlayerInSameRoom();
                break;                
            case GRAB:
                grabItem(command);
                break;                
            case DROP:
                dropItem(command);
                break;
            case BAG:
                showBag(command);
                break;
            case UNLOCK:
                unlockRoom(command);
                break;
            case GIVE:
                giveItem(command);
                break;

        }
        return wantToQuit;
    }

    // implementations of user commands:
    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        printer.help();
    }

    /** 
     * Try to in to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(command.noSecondWordOrHasThird()) {
            // if there is no second word, we don't know where to go...
            System.out.println("'go' command requires an existing cardinal direction after, eg. 'go north'");
            return;
        }
        String direction = command.getSecondWord();
        player.movePlayer(direction, map);
    }
    
    /**
     * Attempts to move the player back to the previous room.
     */
    private void goBack(Command command) {
        if(command.hasSecondWord()) {
            System.out.println("Back where? It's a single word!");
            return;
        }
        player.goBack(map);
    }
    
    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what? It's a single word!");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
    
    /**
     * Attempts to grab an item from the current room and add it to the player's inventory.
     */
    private void grabItem(Command command){
        if(command.noSecondWordOrHasThird()){
            System.out.println("'grab' command requires a item name, eg. 'grab knife'");
            return;
        }
        //Get the players current room and extract itemName from the command's second word
        Room currentRoom = map.get(player.getPlayerRoom());
        String itemName = command.getSecondWord();
        player.grabItem(currentRoom, itemName);
        
    }
    
    /**
     * Attempts to drop an item from the player's inventory and add it to the current room player is in.
     */
    private void dropItem(Command command){
        if(command.noSecondWordOrHasThird()){
            System.out.println("'drop' command requires a item name, eg. 'drop knife'");
            return;
        }
        Room currentRoom = map.get(player.getPlayerRoom());
        String itemName = command.getSecondWord();
        player.dropItem(currentRoom, itemName);
    }

    /**
     * Attempts to give an item from the player's inventory to a character in the current room.
     * Checks if the specified character is present and if the player possesses the item before performing the action.
     */
    private void giveItem(Command command){
        if(!command.hasThirdWord()){
            System.out.println("'give' command requires a item name and then a character name, eg. 'give LaurieStrode knife'");
        }
        //Extract item and character name from command
        String itemName = command.getSecondWord();
        String characterName = command.getThirdWord();
        //Checks if character exists at all, if true, will extract all needed information for the giveItem method
        if(npcs.containsKey(characterName)){
            Character character = npcs.get(characterName);
            Room currrentRoom = map.get(player.getPlayerRoom());
            Room characterRoom = map.get(character.getCharacterRoom());
            player.giveItem(itemName,character,currentRoom, characterRoom);
        }else{
            System.out.println("That character doesn't exist.");
        }
    }
    
    /**
     * Displays information about the items in the player's inventory, remaining weight capacity,
     * and current attack points.
     * Eg.
     * The items in your bag are: knife invitation
     * Your remaining available weight is:3
     * Your attack points are now:7
     */
    private void showBag(Command command){
        if(command.hasSecondWord()) {
            System.out.println("Bag what? It's a single word!");
        }else{
            System.out.println(player.getPlayerInventory(command));
            System.out.println("Your remaining available weight is:" + player.getWeightSurplus());
            System.out.println("Your attack points are now:"+ player.calculateAttackStat());
        }
    }
    
    /**
     * Unlocks a specified room if player has specified key
     * Prints an error message if the command is not formatted correctly.
     */
    private void unlockRoom(Command command){
        if (command.noSecondWordOrHasThird()){
            System.out.println("'unlock' command requires the name of a room after, eg. 'unlock ExamRoom'");
            return;
        } 
        String roomName = command.getSecondWord();
        Room nextRoom = map.get(roomName);
        Room currentRoom = map.get(player.getPlayerRoom());
        player.unlockRoom(currentRoom,nextRoom);
    }
    
    /**
     * Iterates through all characters in the game and moves them to a new room.
     * The new room is determined by checking exits of the character, and if character can move
     * When player moves, character moves
     */
    private void moveCharacter(){
        for(Character character : npcs.values()){
            //Get characterRoom
            Room currentCharacterRoom = map.get(character.getCharacterRoom());
            character.moveCharacter(currentCharacterRoom);
        }
    }
    /**
     * Checks if any character is present in the same room as the player.
     * If an character is present and hasn't interacted before, it interprets the character's behavior:
     * - If aggressive, initiates a fight between the player and the character.
     * - If non-aggressive, prompts a message indicating the character's presence and encourages the player to interact.
     * After interaction, the character is marked as having interacted, preventing repeated encounters.
     * This method is typically called after the player has moved to a new room.
     */
    private void checkCharacterPlayerInSameRoom(){
        for (Character character : npcs.values()){
            //If character has interacted then it game will interpret as if those characters have "dissapeared" or been "killed" by player
            if(character.getCharacterRoom().equals(player.getPlayerRoom()) && !character.hasInteracted()){
                character.setInteracted();
                //Gives priority in the interactions to the aggressive characters, as that makes more logical sense(first fight, then talk to other friendly characters)
                if(character.isAggressive()){
                    fight(player,character);
                }else{
                    System.out.println(character.getCharacterName()+ " is in the room, but don't worry, she wont hurt you.");
                    System.out.println("Give her something and you will be rewarded.\n");
                }
            }   
        }
    }
    
    /**
     * Initiates a battle between the player and an aggressive character.
     * Determines the outcome of the battle using the Battle class.
     * If the player wins, displays the character's story; otherwise, marks the player as defeated, and ends the game.
     */
    private void fight(Player player, Character character){
        System.out.println(character.getCharacterName()+" enters the room where you are and attacks you!");
        if(battle.hasPlayerWon(player,character)){
            System.out.println(character.getCharacterStory());
        }else{
            System.out.println("Defeated by the relentless "+character.getCharacterName()+", your vision fades into the abyss, a victim to the shadows that now claim you.");
            System.out.println("As darkness envelopes your consciousness, the secrets of Strand Campus remain forever elusive, buried in the labyrinth of your unfinished journey.");
            player.setDead();
        }
    }
}
