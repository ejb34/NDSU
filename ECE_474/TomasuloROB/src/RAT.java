/**
 *
 * @author brade
 */
public class RAT {
    private static int[] rat = new int[8];
    
    public RAT() {
        for(int i = 0; i < rat.length; i++) {
            rat[i] = -1000;
        }
    }
    
    public int getEntry(int entry) {
        return this.rat[entry];
    }
    
    public void setEntry(int entry, int value) {
        this.rat[entry] = value;
    }
    
    public int getSize() {
        return this.rat.length;
    }
}
