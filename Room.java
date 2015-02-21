import java.util.ArrayList;

import javax.swing.DefaultListModel;
/**
 * A Room Object
 *
 */
public class Room {
    //The trap in the room. All values of trap are none if there isn't one.
    private Trap trapInTheRoom;
    //List of the items in the room
    private DefaultListModel<Item> itemsInTheRoom = new DefaultListModel<Item>();
    //The number that is assigned to this room
    private int myRoomNumber;

    /** 
     * Constructor that takes a String the name of the trap, and gets it out of
     * the lists of traps, A boolean if the amulet is in the room, and the
     * number that is assigned to the room.
     * 
     * @param trap
     * @param trapList
     * @param a
     * @param roomNumber
     */
    public Room(String trap, ArrayList<Trap> trapList, boolean a, 
            int roomNumber) {
        if(!trap.trim().equals("none")){
            for(int i = 0; i < trapList.size(); i++) {
                if(trapList.get(i).toString().equals(trap.trim())){
                    trapInTheRoom = trapList.get(i);
                    break;
                }
            }

        }
        else {
            trapInTheRoom = new Trap("none", "none", "none");
        }
        if(a == true) {
            itemsInTheRoom.addElement(new Item("Amulet"));
        }
        myRoomNumber = roomNumber;
    }
    
    /**
     * Called when the player enters the room. Executes the trap(If any).
     * @param p
     * @return
     */
    public String enteredRoom(Player p) {
        if(trapInTheRoom != null) {
            return trapInTheRoom.effect(p);
        }
        return "";
    }
    /**
     * Adds an item to the room.
     * @param i
     */
    public void addItem(Item i) {
        if(!i.toString().trim().equals("none")) {
            itemsInTheRoom.addElement(i);
        }
    }
    /**
     * Removes an item from the room.
     * @param item
     */
    public void removeItem(Item item) {
        int size = itemsInTheRoom.getSize();
        for(int i = 0; i < size; i++) {
            if(itemsInTheRoom.get(i).equals(item)) {
                itemsInTheRoom.remove(i); 
                break;
            }
        }
    }
    /**
     * @return The list of all of the Items in the room.
     */
    public DefaultListModel<Item> getItemList() {
        return itemsInTheRoom;
    }
    /**
     * @return The number assigned to the room.
     */
    public int getRoomNumber() {
        return myRoomNumber;
    }
}
