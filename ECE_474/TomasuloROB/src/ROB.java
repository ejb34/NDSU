/**
 *
 * @author brade
 */
public class ROB {
    private static Buffer[] rob;
    private static int issuePointer, commitPointer;
    
    public ROB(Buffer[] rob, int issue, int commit) {
        this.rob = rob;
        for(Buffer entry : rob) {
            entry = null;
        }
        this.issuePointer = issue;
        this.commitPointer = commit;
    }
    
    //does commits
    public boolean advanceROB() {
        
        
        
        
        
        
        //after committed, reset entry to null
        
        
        //if exception, see BB steps
        if() {
            return true;    
        }
        
        

        
        
        if(commitPointer == 6) {
            commitPointer = 0;
        }
    }
    
    //adds values and exceptions from broadcasts
    public void updateROB(int robIndex, int calculatedValue, boolean exceptionPresent) {
        rob[robIndex].setValue(calculatedValue, exceptionPresent);
    }
    
    public boolean assignROBEntry(int register) {
        if(rob[issuePointer] == null) {
            rob[issuePointer] = new Buffer(register);
            
            issuePointer++;
            if(issuePointer == 6) {
                issuePointer = 0;
            }
            
            return true;
        }
        
        return false;
    }
}
