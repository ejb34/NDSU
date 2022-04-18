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
            System.out.println("done screwed it");
            for(Buffer entry : rob){
               entry = null;
            }
            //I assume this true bool indicates an exception was detected? -Ethan
            return true;    
        }
        
        /*
        Check to make sure the below is the proper way of finding the right DST_REG and REG_VAL for the rest of 
        the implementation. I easily may misunderstand the way you have the pointers behaving.
        -Ethan
        */
         rat.setEntry(rob[commitPointer].getDestReg(), rob[commitPointer].getValue());
        

        //after committed, reset entry to null
        
        //This should be good enough right? lol -Ethan
        rob[commitPointer] = null;

        
        if(commitPointer == 6) {
            commitPointer = 0;
        }
        
        //I assume false bool continues advancement in main -Ethan
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
            if(issuePointer == 6) {
                issuePointer = 0;
            }
            
            return true;
        }
        
        return false;
    }
}
