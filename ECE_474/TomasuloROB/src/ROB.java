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
    
 /**
  * commits to RAT
  * @param rat
  * @return if thee ROB was dumped due to an exception
  */
    public boolean advanceROB(RAT rat) {

        
        /* 
        dump Rob same way you intiitalized it above
        -Ethan
        */
        if(rob[commitPointer].getExceptionStatus()) {
            System.out.println("Exception Detected");
            for(Buffer entry : rob){
               entry = null;
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
        if(rob[commitPointer].isDone()) {
            //after committed, reset entry to null
            rat.setEntry(rob[commitPointer].getDestReg(), rob[commitPointer].getValue());
            rob[commitPointer] = null;

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
}
