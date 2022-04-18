/**
 *
 * @author brade
 */
public class IntegerMultiplyUnit {
    private static final int MUL_CYCLES = 10;
    private static final int DIV_CYCLES = 40;
    private static int opcode = -1000, value1 = -1000, value2 = -1000, dest = -1000;
    private static int clocksElapsed = 0;
    private static boolean busy = false, finished = false;
    private static int[] result = {-1000, -1000};
    
    public IntegerMultiplyUnit() {}
    
    public void advanceUnit() {
        if(!finished) {
            clocksElapsed++;

            if(opcode == 2 && clocksElapsed == MUL_CYCLES) {
                //broadcast to dest/RAT
                finished = true;
                result[0] = this.value1 * this.value2;
                result[1] = this.dest;
            }
            else if(opcode == 3 && clocksElapsed == 38 && this.value2 == 0) {
                //broadcast to dest/RAT
                finished = true;
                
                
                
                
                
                
                
                //pass exception = true to ROB
                result[0] = 0;
                
                
                
                
                
                
                
                
                result[1] = this.dest;
                
                
                
                
                
                
                
            }
            else if(opcode == 3 && clocksElapsed == DIV_CYCLES) {
                //broadcast to dest/RAT
                finished = true;
                result[0] = this.value1 / this.value2;
                result[1] = this.dest;
            }
        }
    }
    
    public void dispatch(int opcode, int v1, int v2, int dest) {
        this.busy = true;
        this.opcode = opcode;
        this.value1 = v1;
        this.value2 = v2;
        this.dest = dest;
        clocksElapsed = 0;
    }
    
    public int[] getResultBroadcast() {
        busy = false;
        finished = false;
        return this.result;
    }
    
    public boolean isFinished() {
        return this.finished;
    }
    
    public boolean isBusy() {
        return this.busy;
    }
}
