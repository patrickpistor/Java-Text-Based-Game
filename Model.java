import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
/**
 * 
 * The model class which reads a text file and creates a cellar out of it.
 * 
 * @author Patrick (pjp5228)
 *
 */
public class Model {
    //Variable that stores the file address
    private File file;
    //Variables that hold the information taken from the game-file
    private int amountOfTraps = 0;
    private int amountOfRooms = 0;
    private int amountOfHallways = 0;
    private int roomWithAmulet = 0;
    private int amountOfMonsters = 0;
    private ArrayList<Trap> trapList = new ArrayList<Trap>();
    private ArrayList<Room> roomList = new ArrayList<Room>();
    private ArrayList<Hall> hallList = new ArrayList<Hall>();
    private Monster theMonster = null;
    
    /**
     * Default Contructer that takes a file and proceeds to create
     * a cellar out of it.
     * 
     * @param f
     * @throws IOException
     */
    public Model(File f) throws IOException {
        file = f;
        start();
    }
    
    /**
     * Method that uses the file and assigns values to each of the 
     * instance variables, and creates the cellar.
     * @throws IOException
     */
    public void start() throws IOException {
        //Current line in the file that is being read
        int lineNum = 0;
        BufferedReader br = null;
        //Checks if the file exists
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //Variable that stores the current line
        String next = null;
        //Variables used for creating the room.
        int roomPart = 0;
        int roomNumber = 0;
        /**
         * Loop that cycles though each line of the file, and
         * stops when it reaches the end.
         */
        while ((next = br.readLine()) != null) {
            //Removes any spaces from the line of text
            next = next.trim();
            if(lineNum == 0) {
                //The first line always contains the amount of traps
                amountOfTraps = Integer.parseInt(next);
            }
            else if(lineNum > 0 && lineNum <= amountOfTraps) {
                /**
                 * Creates a trap object and adds it to the trapList.
                 * 
                 * Traps objects contain:
                 * 1. The name of the trap.
                 * 2. The type of trap. (weaken, warp, vanish)
                 * 3. A parameter. (Vanish does not have one)
                 * 4. The name of the item that stops it. (the protection)
                 */
                String name = next.substring(0, next.indexOf(" ")).trim();
                next = next.substring(next.indexOf(" "), next.length()).trim();

                String type = next.substring(0, next.indexOf(" ")).trim();
                next = next.substring(next.indexOf(" "), next.length()).trim();
                //If the type is vanish, create a separate object without a 
                //parameter
                if(type.equals("vanish")) {
                    String prot = next.trim();
                    trapList.add(new Trap(name, type, prot));
                }
                else {
                    int param = Integer.parseInt(next.substring(0, 
                            next.indexOf(" ")).trim());
                    next = next.substring(next.indexOf(" "), 
                            next.length()).trim();
                    String prot = next.trim();
                    trapList.add(new Trap(name, type, param, prot));
                }
            }
            else if(lineNum == amountOfTraps + 1) {
                /**
                 * The line after the traps always contains the amount of rooms
                 * in the cellar, and the room with the amulet
                 */
                String temp = next.trim();
                amountOfRooms = Integer.parseInt(temp.substring(0, 
                        temp.indexOf(" ")));
                roomWithAmulet = Integer.parseInt(temp.substring(temp.indexOf(
                        " "), temp.length()).trim());
            }
            else if(lineNum > amountOfTraps + 1 && 
                    lineNum < ((amountOfRooms * 2) + 1) + (amountOfTraps + 1)) {
                /**
                 * Creates a room object and adds it to the list. 
                 * Room objects take up two lines in the text file and contain:
                 * 
                 * 1. On line one, the trap (if any) in the room.
                 * 2. The items in the room (if any).
                 * 
                 */
                if(roomPart == 0) {
                    boolean a = false;
                    if(roomWithAmulet == roomNumber) {
                        a = true;
                    }
                    roomList.add(new Room(next.trim(), trapList, a, 
                            roomNumber));
                    roomPart = 1;
                    roomNumber++;
                }
                else {
                    next = next + " ";
                    while(!next.equals(" ")){
                        int sub = next.indexOf(" ");
                        Item temp = new Item(next.substring(0, sub).trim());
                        roomList.get(roomList.size() - 1).addItem(temp);
                        next = next.substring(sub, next.length()).trim();
                        next = next + " ";
                    }
                    roomPart = 0;
                }
            }
            else if(lineNum == ((amountOfRooms * 2) + 1) + (amountOfTraps + 1)) {
                //The line after room always contains the amount of hallways.
                amountOfHallways = Integer.parseInt(next.trim());
            }
            else if(lineNum > ((amountOfRooms * 2) + 1) +(amountOfTraps + 1) && 
                    lineNum < ((amountOfRooms * 2) + 1) + 
                        (amountOfTraps + 1) + (amountOfHallways + 1))  {
                /**
                 * Creates a hall object and adds it to the list.
                 * A hall object contains:
                 * 
                 * 1. The name of the hall
                 * 2. The starting room
                 * 3. The ending room
                 * 
                 */
                next = next + " ";
                String tempName = next.substring(0, next.indexOf(" ")).trim();
                next = next.substring(next.indexOf(" "), next.length()).trim();
                next = next + " ";
                int tempStart = Integer.parseInt(next.substring(0, 
                        next.indexOf(" ")).trim());
                next = next.substring(next.indexOf(" "), next.length()).trim();
                next = next + " ";
                int tempEnd = Integer.parseInt(next.substring(0, 
                        next.indexOf(" ")).trim());
                hallList.add(new Hall(tempName, tempStart, tempEnd));
            }
            else if(lineNum == ((amountOfRooms * 2) + 1) + 
                    (amountOfTraps + 1) + (amountOfHallways + 1)){
                /**
                 * The line after the hallways always state whether the 
                 * cellar contains a monster or not.
                 */
                amountOfMonsters = Integer.parseInt(next.trim());
            }
            else {
                /**
                 * If the game contains a monster, create a monster object.
                 * A monster object contains:
                 * 
                 * 1. The name of the monster.
                 * 2. The starting room.
                 */
                if(amountOfMonsters > 0) {
                    next = next + " ";
                    String tempName = next.substring(0, 
                            next.indexOf(" ")).trim();
                    next = next.substring(next.indexOf(" "), 
                            next.length()).trim();
                    next = next + " ";
                    int tempRoom = Integer.parseInt(next.substring(0, 
                            next.indexOf(" ")).trim());
                    theMonster = new Monster(tempName, tempRoom);
                }
            }
            lineNum++;
        }
        br.close();
    }
    /**
     * Gets the ArrayList with all of the rooms.
     * @return A list of all of the rooms in the cellar.
     */
    public ArrayList<Room> getRoomList() {
        return roomList;
    }
    
    /**
     * Gets the monster object.
     * 
     * @return The Monster Object.
     */
    public Monster getTheMonster() {
        return theMonster;
    }
    /**
     * Gets the list of the halls that are connected to a particular room.
     * @param r (The room that we wish to get the hallways from). 
     * @return The hallways connected to room "r".
     */
    public DefaultListModel<Hall> getHallList(int r) {
        DefaultListModel<Hall> m = new DefaultListModel<Hall>();
        for(Hall x : hallList) {
            if(x.getStart() == r || x.getFinish() == r) {
                m.addElement(x);
            }
        }
        return m;
    }
}
