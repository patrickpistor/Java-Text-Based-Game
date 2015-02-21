import javax.swing.DefaultListModel;
/**
 * A Trap object
 *
 */
public class Trap {
    //The name of the trap
    private String name;
    //The type of trap
    private String type;
    //The parameter of the trap
    private int param;
    //The name of the Item that blocks the trap
    private String itemProtect;
    /**
     * Constructer that takes a String n and assigns it to name,
     * a String t and asigns it to trap, an int p and assigns it to
     * paramater, and a String i and assigns it to itemProtect.
     * @param n
     * @param t
     * @param p
     * @param i
     */
    public Trap(String n, String t, int p, String i) {
        name = n;
        type = t;
        param = p;
        itemProtect = i;
    }
    /**
     * Constructer that takes a String n and assigns it to name,
     * a String t and asigns it to trap, and a String i 
     * and assigns it to itemProtect.
     * @param n
     * @param t
     * @param i
     */
    public Trap(String n, String t, String i) {
        name = n;
        type = t;
        //param is not used
        param = -1;
        itemProtect = i;
    }
    /**
     * Once called, use the trap on the player. 
     * @param p
     * @return
     */
    @SuppressWarnings("rawtypes")
    public String effect(Player p) {
        DefaultListModel pInventory = p.getInventory();
        Item protectedItem = null;
        boolean hasProtection = false;
        for(int i = 0; i < pInventory.getSize(); i++) {
            if(pInventory.get(i).toString().equals(itemProtect)){
                protectedItem = (Item) pInventory.get(i);
                hasProtection = true;
            }
        }
        if(!hasProtection) {
            if(param == -1 && !name.equals("none")) {
                for(int i = 0; i < p.getInventory().getSize(); i++){
                    if(!p.getInventory().get(i).toString().equals("Sword"))
                        p.removeFromInventory((Item) p.getInventory().get(i));
                }
                return "Player inventory is cleared" + ".\n";
            }
            if(param >= 0) {
                if(type.trim().equals("warp")) {
                    p.moveToRoom(param);
                    return "Player was warped to a different room \n";
                }
                else {
                    return "Player health is decreased to " + 
                            p.decrementHealth(param) + ".\n";
                }

            }
        }
        else {
            p.incrementLevel();
            p.removeFromInventory(protectedItem);
            return "Player is protected from " + name + " by " + itemProtect + 
                    ".\n";
        }
        return "";
    }
    /**
     * Returns the name of the trap.
     */
    public String toString() {
        return name;
    }
}
