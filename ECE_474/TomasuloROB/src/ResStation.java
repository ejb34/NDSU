/**
 *
 * @author brade
 */
public class ResStation {
    private int opcode;
    private int value1, value2;
    private int tag1, tag2;
    private int dest;
    private boolean busy;
    
    public ResStation(int opcode, int value1, int value2, int tag1, int tag2, int dest) {
        this.opcode = opcode;
        this.value1 = value1;
        this.value2 = value2;
        this.tag1 = tag1;
        this.tag2 = tag2;
        this.dest = dest;
        this.busy = true;
    }
    
    public ResStation() {
        this.opcode = -1000;
        this.value1 = -1000;
        this.value2 = -1000;
        this.tag1 = -1000;
        this.tag2 = -1000;
        this.dest = -1000;
        this.busy = false;
    }
    
    public boolean isBusy() {
        return this.busy;
    }
    
    public int getOpcode() {
        return this.opcode;
    }
    
    public int getDest() {
        return this.dest;
    }
    
    public int getValue1() {
        return this.value1;
    }
    
    public int getValue2() {
        return this.value2;
    }
    
    public int getTag1() {
        return this.tag1;
    }
    
    public int getTag2() {
        return this.tag2;
    }
    
    public void setBusy(boolean busy) {
        this.busy = busy;
    }
    
    public void setValue1(int value) {
        this.value1 = value;
    }
    
    public void setValue2(int value) {
        this.value2 = value;
    }
}
