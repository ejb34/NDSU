/**
 *
 * @author brade
 */
public class ROB {
    private static Buffer[] rob;
    private static int issuePointer, commitPointer;
    private static int totalCommits = 0;
    
    public ROB(Buffer[] rob, int issue, int commit) {
        this.rob = rob;
        for(Buffer entry : rob) {
            entry = null;
        }
        this.issuePointer = issue;
        this.commitPointer = commit;
    }
    
 /**
  * commits to RAT
  * @param rat
  * @return if thee ROB was dumped due to an exception
  */
    public boolean advanceROB(RAT rat, RegisterFile rf) {

        
        /* 
        dump Rob same way you intiitalized it above
        -Ethan
        */
        if(rob[commitPointer] != null && rob[commitPointer].getExceptionStatus()) {
            System.out.println("------------------------------------------------------------------");
            System.out.println("EXCEPTION DETECTED\tEXCEPTION DETECTED\tEXCEPTION DETECTED");
            System.out.println("------------------------------------------------------------------\n");
            for(int i = 0; i < rob.length; i++){
               rob[i] = null;
            }
            
            //reset pointers
            issuePointer = 0;
            commitPointer = 0;
            
            //point all RAT entries to RF
            for(int i = 0; i < rat.getSize(); i++) {
                rat.setEntry(i, -1000);
            }
            
            //Exception deteced, pass up the chain to RSUnit to clear Instr Queue
            return true;    
        }
        
        //check current ROB entry, set values, release ROB entry
        if(rob[commitPointer] != null && rob[commitPointer].isDone()) {
            //update rf
            rf.setRegister(rob[commitPointer].getDestReg(), rob[commitPointer].getValue());
            
            //update RAT
            if(rat.getEntry(rob[commitPointer].getDestReg()) == commitPointer) {
                rat.setEntry(rob[commitPointer].getDestReg(), -1000);
            }
            
            //reset to null after commit
            rob[commitPointer] = null;

            totalCommits++;
            commitPointer++;
            //cycle to valid index
            if(commitPointer == 6) {
                commitPointer = 0;
            }
        }
        
        //No exceptions detected, run like normal
        return false;
    }
    
    //adds values and exceptions from broadcasts
    public void updateROB(int robIndex, int calculatedValue, boolean exceptionPresent) {
        rob[robIndex].setValue(calculatedValue, exceptionPresent);
    }
    
    public boolean assignROBEntry(int register) {
        if(rob[issuePointer] == null) {
            rob[issuePointer] = new Buffer(register);
            
            issuePointer++;
            //cycle to valid index
            if(issuePointer == 6) {
                issuePointer = 0;
            }
            
            return true;
        }
        
        return false;
    }
    
    public boolean isROBFull() {
        return issuePointer == commitPointer && !isEmpty();
    }
    
    public int getIssuePointer() {
        return ROB.issuePointer;
    }
    
    public int getCommitPointer() {
        return ROB.commitPointer;
    }
    
    public int getTotalCommits() {
        return ROB.totalCommits;
    }
    
    public boolean isEmpty() {
        for(Buffer buffer : rob) {
            if(buffer != null) {
                return false;
            }
        }
        return true;
    }
    /**
     * 
     * @param entryNum
     * @return value of passed ROB entry
     */
    public int getEntryVal(int entryNum){
        if(this.rob[entryNum] == null){
            return -1000;
        } 
        
        return this.rob[entryNum].getValue();
    }
    
    public boolean getEntryDone(int entryNum) {
        if(this.rob[entryNum] == null){
            return false;
        } 
        
        return this.rob[entryNum].isDone();
    }
    
    public int getException(int entryNum) {
        if(this.rob[entryNum] == null){
            return 0;
        } 
        
        return this.rob[entryNum].getExceptionStatus() ? 1 : 0;
    }
}
