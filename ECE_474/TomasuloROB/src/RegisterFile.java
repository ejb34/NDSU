/**
 *
 * @author brade
 */
public class RegisterFile {
    private static int[] registers = new int[8];
    
    public RegisterFile() {
        for(int i = 0; i < registers.length; i++) {
            registers[i] = -1000;
        }
    }
    
    public int getValue(int register) {
        return this.registers[register];
    }
    
    public void setRegister(int register, int value) {
        this.registers[register] = value;
    }
    
    public int[] getRegisterArray() {
        return this.registers;
    }
}
