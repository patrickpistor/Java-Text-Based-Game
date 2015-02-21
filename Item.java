/**
 * The Item Object
 *
 */
public class Item {
    //The name of the item
    private String name;
    /**
     * A Constructor that takes a String and
     * assigns it to the name
     * @param n
     */
    public Item(String n) {
        name = n;
    }
    
    /**
     * Returns the name of the Item
     */
    public String toString() {
        return name;
    }
}
