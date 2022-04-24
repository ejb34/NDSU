import java.util.LinkedList;

/**
 *
 * @author brade
 */
public class ResStationUnit {
    private static ResStation[] resArray;
    private static RegisterFile registerFile;
    private static RAT rat;
    private static ROB rob;
    private static LinkedList<Instruction> instrQueue;
    private static IntegerAddUnit intAddUnit = new IntegerAddUnit();
    private static IntegerMultiplyUnit intMulUnit = new IntegerMultiplyUnit();
    
    public ResStationUnit(RegisterFile registerFile, RAT rat, ROB rob, ResStation[] resStations, LinkedList<Instruction> queue) {
        this.rat = rat;
        this.rob = rob;
        this.resArray = resStations;
        this.registerFile = registerFile;
        this.instrQueue = queue;
        
        for(int i = 0; i < resArray.length; i++) {
            this.resArray[i] = new ResStation();
        }
    }
    
    public boolean advanceUnit() {
        boolean[] actionTakenOnStation = {false, false, false, false, false};
        intAddUnit.advanceUnit();
        intMulUnit.advanceUnit();
        
        
        
        
        
        boolean terminate = rob.advanceROB(this.rat);
        if(terminate) {
            
            
            
            
            //NEED TO CLEAR INSTR QUEUE
            
            
            
            
            return true;
        }
        
        
        
        
        
        
        
        
        if(!instrQueue.isEmpty()) {
            //issue instructions
            int result = getResStation(instrQueue.peek(), actionTakenOnStation);
            if(result != -1000) {
                
                
                
                
                //get ROB station
                
                
                
                
                
                instrQueue.poll();
                actionTakenOnStation[result] = true;
            }
        }
        
        //receive broadcast
        int[] result = {-1000,-1000};
        if(intMulUnit.isFinished()) {
            result = intMulUnit.getResultBroadcast();
        }
        else if(intAddUnit.isFinished()) {
            result = intAddUnit.getResultBroadcast();
        }
        //apply broadcast to RAT/RS/RF
        if(result[0] != -1000) {
            for(int i = 0; i < resArray.length; i++) {
                if(!actionTakenOnStation[i]) {
                    if(resArray[i].getTag1() == result[1] && resArray[i].getValue1() == -1000) {
                        resArray[i].setValue1(result[0]);
                        actionTakenOnStation[i] = true;
                    }
                    if(resArray[i].getTag2() == result[1] && resArray[i].getValue2() == -1000) {
                        resArray[i].setValue2(result[0]);
                        actionTakenOnStation[i] = true;
                    }
                }
            }
            
            
            
            
            
            //add ROB commits
            
            
            
            
            
            
            
            
            //needs to be run by ROB
            
            
            
            
            
            
            
            for(int i = 0; i < rat.getSize(); i++) {
                if(rat.getEntry(i) == result[1]) {
                    registerFile.setRegister(i, result[0]);
                    rat.setEntry(i, -1000);
                }
            }
            
            //release RS station
            resArray[result[1]].setBusy(false);
            resArray[result[1]].setDispatched(false);
        }
        
        
        
        
        
        
        //free RS right after dispatch
        
        
        
        
        
        //dispatch
        for(int i = 0; i < resArray.length; i++) {
            if(resArray[i].getValue1() != -1000 && resArray[i].getValue2() != -1000 && resArray[i].isBusy() && !actionTakenOnStation[i]) {
                int opcode = resArray[i].getOpcode();
                int v1 = resArray[i].getValue1();
                int v2 = resArray[i].getValue2();
                int dest = resArray[i].getDest();
                
                
                if((opcode == 0 || opcode == 1) && !intAddUnit.isBusy()) {
                    intAddUnit.dispatch(opcode, v1, v2, dest);
                    actionTakenOnStation[i] = true;
                    resArray[i].setDispatched(true);
                    //resArray[i].setBusy(false);
                }
                else if((opcode == 2 || opcode == 3) && !intMulUnit.isBusy()) {
                    intMulUnit.dispatch(opcode, v1, v2, dest);
                    actionTakenOnStation[i] = true;
                    resArray[i].setDispatched(true);
                    //resArray[i].setBusy(false);
                }
            }
        }
        
        return false;
    }
    
    public int getResStation(Instruction instr, boolean[] actionTakenOnStation) {
        int v1, v2, t1, t2;
        if(this.rat.getEntry(instr.getSource1()) == -1000) {
            v1 = registerFile.getValue(instr.getSource1());
            t1 = -1000;
        }
        else {
            v1 = -1000;
            t1 = this.rat.getEntry(instr.getSource1());
        }
        if(this.rat.getEntry(instr.getSource2()) == -1000) {
            v2 = registerFile.getValue(instr.getSource2());
            t2 = -1000;
        }
        else {
            v2 = -1000;
            t2 = this.rat.getEntry(instr.getSource2());
        }
        
        
        
        
        
        //RAT should receive ROB instead of RS station
        
        
        
        
        
        if(instr.getOpcode() == 0 || instr.getOpcode() == 1) {
            for(int i = 0; i <= 2; i++) {
                if(!resArray[i].isBusy() && !actionTakenOnStation[i]) {
                    //assign RAT
                    rat.setEntry(instr.getDest(), i);
                    
                    
                    
                    
                    resArray[i] = new ResStation(instr.getOpcode(), v1, v2, t1, t2, i);
                    
                    
                    
                    
                    
                    return i;
                }
            }
        }
        else {
            for(int i = 3; i <= 4; i++) {
                if(!resArray[i].isBusy() && !actionTakenOnStation[i]) {
                    //assign RAT
                    rat.setEntry(instr.getDest(), i);
                    
                    
                    
                    
                    resArray[i] = new ResStation(instr.getOpcode(), v1, v2, t1, t2, i);
                    
                    
                    
                    
                    
                    
                    return i;
                }
            }
        }
        
        return -1000;
    }
}
