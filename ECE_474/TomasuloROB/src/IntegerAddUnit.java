/**
 *
 * @author brade
 */
public class IntegerAddUnit {
    private static final int ADD_CYCLES = 2;
    private static final int SUB_CYCLES = 2;
    private static int opcode = -1000, value1 = -1000, value2 = -1000, dest = -1000;
    private static int clocksElapsed = 0;
    private static boolean busy = false, finished = false;
    private static int[] result = {-1000, -1000};
    
    public IntegerAddUnit() {}
    
    public void advanceUnit() {
        if(!finished) {
            clocksElapsed++;

            if(opcode == 0 && clocksElapsed == ADD_CYCLES) {
                //broadcast to dest/RAT
                finished = true;
                result[0] = this.value1 + this.value2;
                result[1] = this.dest;
            }
            else if(opcode == 1 && clocksElapsed == SUB_CYCLES) {
                //broadcast to dest/RAT
                finished = true;
                result[0] = this.value1 - this.value2;
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
        this.busy = false;
        this.finished = false;
        return this.result;
    }
    
    public boolean isFinished() {
        return this.finished;
    }
    
    public boolean isBusy() {
        return this.busy;
    }
}
