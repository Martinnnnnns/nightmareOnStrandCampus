
/**
 * Printer class contains any long messages that are to be printed in the game.
 * 
 * Can be easily extended for more messages to be displayed
 * Bernardo Guterres k23092429
 * V1
 */
public class Printer
{
    // instance variables - replace the example below with your own
    private Parser parser;
    /**
     * Constructor for objects of class Printer
     */
    public Printer()
    {
        // initialise instance variables
        parser = new Parser();
    }
    
    public void welcome()
    {
        String[] messages = {
        "You open your eyes to a dimly lit nightmare, your surroundings bathed in an otherworldly gloom.",
        "The oppressive air of Strand Campus clings to your skin as you find yourself in the main entrance.",
        "The flickering lights cast unsettling shadows, revealing a labyrinth of desolation. ",
        "Uncertain of how you arrived, you cautiously peer inside, the silence broken only by the echo of your own footsteps.",
        "As you take a hesitant step forward, the chilling laughter of the Joker resonates through the empty halls,",
        "a sinister symphony that sends shivers down your spine. In the distance,the unmistakable sound of Freddy's claws scrapes",
        "against the walls, a ghastly serenade echoing his relentless pursuit. The rhythmic thud of heavy footsteps",
        "and the chilling sound of metal meeting flesh reveal the presence of Jason Voorhees.",
        "The realization creeps up slowly, but you realize: you are not alone in this place.",
        " ",
        "A glimmer of hope appears as you may encounter iconic anti-villain characters like Laurie Strode and Nancy Thompson.",
        "The choice is yours: bravely take the risk, extend a hand, and offer items to these iconic figures.",
        "In return, you might discover a glint of salvation amid the shadows. Embrace the challenge, for within the darkness",
        "lies the opportunity for a flicker of light to guide you towards an unexpected dawn.",
        " ",
        "You must seek out a key hidden in the shadows. Only by unlocking the Exam Room can you pierce through the",
        "veil of darkness and confront the unsettling reality that binds you. As the echoes of your steps resonate through",
        "the desolate halls, remember that every step brings you closer to unveiling the secrets that will set you free.",
        "Grabbing items will reveal more about the sinister reason that brought you here whispering your untold story.", 
        "Find the key, unlock the door, and step into the unknown,for therein lies the revelation that may lead to the end of this",
        "harrowing nightmare.",
        " ",
        "Good luck, you will need it...(use the command 'help' if you ever feel completely lost)",
        " ",};
        
        for(String message : messages){
            System.out.println(message);
        }
    }
    
    public void win()
    {
        String[] messages = 
        {  " ",
        "As the door creaks open, an ethereal light spills forth, revealing a chamber covered with arcane symbols and flickering shadows. ",
        "You step inside, surrounded by the remnants of your own making—the haunting echoes of your past choices.",
        "In the heart of the Exam Room, a spectral presence materializes, revealing the truth that has eluded you.",
        "The aggro characters were bound by your hand, summoned from the shadows of your desires and fears.",
        "Your pursuit of forbidden knowledge and the thirst for power led to a pact that shaped this nightmarish reality.",
        " ",
        "Alone, you stand in the aftermath, a survivor of your own creation. ",
        "The Nightmare on Strand Campus concludes, leaving behind an indelible imprint on your soul.",
        "The haunting echoes will linger, a reminder of the blurred line between reality and nightmare."
        };
        for(String message : messages){
            System.out.println(message);
        }
    }
    
    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    public void help(){
        System.out.println("In the eerie halls of Strand Campus, you find yourself trapped in a haunting nightmare.");
        System.out.println("As you navigate this surreal university, the air thick with foreboding, you must unravel");
        System.out.println("the mysteries that bind you.");
        System.out.println(" ");
        System.out.println("Your command words are:");
        parser.showCommands();
    }
    
    /**
     * Displays message related to teleportunlocking the Exam Room
     */
    public void teleport(){
        String[] messages = {
            "You have gotten too close...",
            "A cold shiver runs down your spine...",
            "Your misstep has caught the attention of a dark force...",
            "Prepare for the consequences...",
            "In an instant, the fabric of reality unravels, and you find yourself abruptly cast into an unknown realm...",
        };
        for(String message : messages){
            System.out.println(message);
        }
    }
    
    /**
     * Displays message related to unlocking the Exam Room
     */
    public void UnlockedExamRoomText(){
        String [] messages ={
        "With a trembling hand, you insert the key into the rusty lock of the Exam Room door.",
        "The door, burdened with the weight of untold secrets, groans open, revealing a threshold into the unknown.",
        "The Exam Room stands as a nexus of revelation, a realm where the threads of your destiny weave together.",
        "An inexplicable resolve compels you forward.",
        " ",
        "A whisper of destiny calls for your imminent entry.",
        "The final chapter of the Nightmare on Strand Street awaits,",
        "There's no turning back now.",
        "Enter and confront the revelations that await within the heart of this surreal nightmare."
        };
        for(String message : messages){
            System.out.println(message);
        }
    }
}
