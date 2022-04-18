/**
 *
 * @author brade
 */
public class Buffer {
    private int destReg, value;
    private boolean done, exception;
    
    public Buffer(int reg) {
        this.destReg = reg;
        this.value = -1000;
        this.done = false;
        this.exception = false;
    }
    
    public void setValue(int value, boolean exception) {
        this.value = value;
        this.exception = exception;
    }

    public int getValue() {
        return value;
    }

    public int getDestReg() {
        return destReg;
    }
    

    /**
     * @return  status fo exception for the data
     * -Ethan
     */
    public boolean getExceptionStatus() {
        return exception;
    }
    

}
