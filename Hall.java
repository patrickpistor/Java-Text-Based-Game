/**
 * The Hall Object that contains all
 * the information a hall.
 *
 */
public class Hall {
    
    //The name of the hall
    private String myName;
    //The starting room
    private int myStart;
    //The ending room
    private int myFinish;
    
    /**
     * Constructor that takes a name, a start, and a finish and creates 
     * a Hall Object.
     * @param name
     * @param start
     * @param finish
     */
    public Hall(String name, int start, int finish) {
        myName = name;
        myStart = start;
        myFinish = finish;
    }
    /**
     * @return The starting room
     */
    public int getStart() {
        return myStart;
    }
    /**
     * @return The ending room
     */
    public int getFinish() {
        return myFinish;
    }
    /**
     * The name of the Hall
     */
    public String toString() {
        return myName;
    }
}
