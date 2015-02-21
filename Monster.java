import javax.swing.DefaultListModel;
/**
 * The Monster Object. 
 *
 */
public class Monster {
    //The name of the Monster
    private String name;
    //The current room that the monster is in
    private int roomNumber;
    //The current cellar where the monster resides
    private Model model;
    //The list of the hallways in the cellar
    private DefaultListModel<Hall> hallList;
    //boolean that stores whether the monster is alive
    private boolean alive;
    /**
     * Constructor that takes a String and asigns it to the name
     * and a integer and assigns it to the roomNumber.
     * Also sets alive = true.
     * @param n
     * @param rn
     */
    public Monster(String n, int rn) {
        name = n;
        roomNumber = rn;
        alive = true;
    }
    /**
     * Returns the name of the Monster
     */
    public String toString() { 
        return name;
    }
    /**
     * Takes an integer and sets it equal to the current room
     * @param r
     */
    public void changeRoom(int r) {
        roomNumber = r;
    }
    /**
     * Takes a cellar object and sets it = to the model
     * Also gets the list of halls connected to the roomNumber
     * @param m
     */
    public void setGameModel(Model m) {
        model = m;
        hallList = model.getHallList(roomNumber);
    }
    /**
     * @return The current Room Number
     */
    public int getRoomNumber() {
        return roomNumber;
    }
    /**
     * Sets alive = to false
     * @return A message to print out
     */
    public String destroyMonster() {
        alive = false;
        return name + " is killed with the sword. \n";
    }
    
    /**
     * @return True if alive, False if dead.
     */
    public boolean isAlive() {
        return alive;
    }
    /**
     * Moves down a random Hallway. Called everytime a player takes an action.
     */
    public void action() {
        if(hallList.getSize() > 0) {
            hallList = model.getHallList(roomNumber);
            int randomHall = (int)(Math.random() * hallList.getSize());
            if(roomNumber == ((Hall) hallList.get(randomHall)).getFinish()) {
                roomNumber = ((Hall) hallList.get(randomHall)).getStart();
                hallList = model.getHallList(roomNumber);
            }
            else {
                roomNumber = ((Hall) hallList.get(randomHall)).getFinish();
                hallList = model.getHallList(roomNumber);
            }
        }
    }
}
