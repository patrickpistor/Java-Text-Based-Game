import javax.swing.DefaultListModel;
/**
 * The Player object
 *
 */
public class Player {
    //The health of the player
    private int health;
    //The level of the play
    private int level;
    //The room that the player is in
    private int currentRoom;
    //The current amount of protections the player can carry
    private int inventorySize;
    //The inventory of the player
    private DefaultListModel<Item> inventory = new DefaultListModel<Item>();   
    /**
     * Default Constructor
     */
    public Player() {
        health = 100;
        level = 1;
        currentRoom = 0;
        inventorySize = 0;
    }
    /**
     * Decrement the health by h%
     * Health is never negative.
     * @param h
     * @return The health
     */
    public int decrementHealth(int h) {
        if(health > 0) {
            health = (int)(health * (h * 0.01));
            if(health < 0) {
                health = 0;
            }
            return health;
        }
        else {
            return -1;
        }
    }
    /**
     * Decrease the health of the player by 
     * half or by 10 points, whichever is greater.
     * 
     * Only called by a monster.
     * @return The amounnt of health taken away
     */
    public int decrementHealthMonster() {
        if(health > 0) {
            int per = (int)(health * 0.5);
            int sub = health - 10;
            if(per < sub) {
                health = per;
                return per;
            }
            else {
                health = sub;
                return sub;
            }
        }
        else 
            return -1;
    }
    /**
     * Takes a room number and sets the current room = to it.
     * @param r
     */
    public void moveToRoom(int r) {
        currentRoom = r;
    }
    /**
     * @return The current health of the player
     */
    public int getHealth() {
        return health;
    }
    /**
     * @return The current level of the player
     */
    public int getLevel() {
        return level;
    }
    /**
     * @return True if the game is won, False if it is not won yet
     */
    public boolean wonGame() {
        for(int i = 0; i < inventory.getSize(); i++) {
            if(inventory.get(i).toString().equals("Amulet"))
                return true;
        }
        return false;
    }
    /**
     * @return The current room the player is in
     */
    public int getRoom() {
        return currentRoom;
    }
    /**
     * Method that takes a Monster, and if it is in the same room
     * and if the player has the sword, kill it. If it is in the same
     * room and the player doesn't have the sword. Attack the player.
     * @param m
     */
    public String takeAction(Monster m) {
        if(m != null) {
            if(m.isAlive()) {
                if(m.getRoomNumber() != currentRoom) {
                    m.action();
                }
                else {
                    boolean containsSword = false;
                    for(int i = 0; i < inventory.getSize(); i++) {
                        if(inventory.get(i).toString().equals("Sword")) {
                            containsSword = true;
                        }
                    }
                    if(!containsSword) {
                        return "Player is attacked by " + m.toString() + 
                                " for " + decrementHealthMonster() + 
                                    " damage points \n";
                    }
                    else {
                        incrementLevel();
                        return m.destroyMonster();
                    }
                }
            }
        }
        return "";
    }
    /**
     * Takes an Object and adds it to the inventory. If the Item is
     * not a Sword or Amulet, don't increase the inventory size. 
     * @param item
     */
    public void addToInventory(Item item) {
        if(!item.toString().trim().equals("Sword") && 
                !item.toString().trim().equals("Amulet")){
            inventorySize++;
        }
        inventory.addElement(item);
    }
    /**
     * Takes an Item and removes it from the inventory
     * @param item
     */
    public void removeFromInventory(Item item) {
        int size = inventory.getSize();
        for(int i = 0; i < size; i++) {
            if(inventory.get(i).equals(item)) {
                if(!item.toString().trim().equals("Sword")){
                    inventorySize--;
                }
                inventory.remove(i); 
                break;
            }
        }
    }
    /**
     * @return The players current inventory
     */
    public DefaultListModel<Item> getInventory() {
        return inventory;
    }
    
    /**
     * @return Return the current amount of protections the player has.
     */
    public int getInventorySize() {
        return inventorySize;
    }
    /**
     * @return True if health is <= 0. False if otherwise.
     */
    public boolean isDead() {
        if(health <= 0) 
            return true;
        else 
            return false;
    }
    /**
     * Increment the level of the player
     */
    public void incrementLevel() {
        level++;
    }
}
