/**
 *
 * @author brade
 */
public class Instruction {
    private int opcode, destination, source1, source2;
    
    public Instruction() {
        opcode = 0;
        destination = 0;
        source1 = 0;
        source2 = 0;
    }
    
    public Instruction(int opcode, int destination,  int source1, int source2) {
        this.opcode = opcode;
        this.destination = destination;
        this.source1 = source1;
        this.source2 = source2;
    }
    
    public int getOpcode() {
        return this.opcode;
    }
    
    public int getDest() {
        return this.destination;
    }
    
    public int getSource1() {
        return this.source1;
    }
    
    public int getSource2() {
        return this.source2;
    }
}
