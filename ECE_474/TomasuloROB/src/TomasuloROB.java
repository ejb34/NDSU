import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/*
 * Tomasulo Project
 * ECE474
 * Created by Braden Rempel, Ethan Brown, and Luke Freeland
 */

/**
 *
 * @author brade
 */
public class TomasuloROB {
    
    public static void main(String[] args) {
        LinkedList<Instruction> instrQueue = new LinkedList<Instruction>();
        RegisterFile registerFile = new RegisterFile();
        RAT rat = new RAT();
        Buffer[] robArray = new Buffer[6];
        int issuePointer = 0, commitPointer = 0;
        ROB rob = new ROB(robArray, issuePointer, commitPointer);
        ResStation[] resStations = new ResStation[5];
        ResStationUnit resUnit = new ResStationUnit(registerFile, rat, rob, resStations, instrQueue);
        
        int numberOfInstructions = 0, numberofClockCycles = 0;
        
        
        //read in text file to initialize queue, rf, and constants
        File file = new File("input.txt");
        try {
            Scanner scan = new Scanner(file);
            
            int lineCounter = 0;
            int rfCounter = 0;
            while(scan.hasNextLine()) {
                String line = scan.nextLine();
                String[] contents = line.split(" ");
                if(lineCounter == 0) {
                    numberOfInstructions = Integer.parseInt(contents[0]);
                }
                else if(lineCounter == 1) {
                    numberofClockCycles = Integer.parseInt(contents[0]);
                }
                else {
                    if(contents.length > 1) {
                        if(instrQueue.size() < numberOfInstructions && instrQueue.size() < 10) {
                            Instruction instrToBeEnqueued = new Instruction(Integer.parseInt(contents[0]), Integer.parseInt(contents[1]), Integer.parseInt(contents[2]), Integer.parseInt(contents[3]));
                            instrQueue.offer(instrToBeEnqueued);
                        }
                    }
                    else {
                        registerFile.setRegister(rfCounter, Integer.parseInt(contents[0]));
                        rfCounter++;
                    }
                }
                
                lineCounter++;
            }
        } catch(FileNotFoundException fnfe) {
            System.out.println("It done broke");
        }
        
        
        //Print starting instruction queue
        System.out.println("Starting Instruction Queue: ");
        for(Instruction instr : instrQueue) {
            String opcode;
            switch(instr.getOpcode()) {
                case(0) : opcode = "Add"; break;
                case(1) : opcode = "Sub"; break;
                case(2) : opcode = "Mul"; break;
                case(3) : opcode = "Div"; break;
                default : opcode = "Invalid";
            }
            System.out.println(opcode + " R" + instr.getDest() + ", R" + instr.getSource1() + ", R" + instr.getSource2());
        } System.out.println();
        
        
        System.out.println("Number of Instructions: " + numberOfInstructions);
        System.out.println("Clock Cycles: " + numberofClockCycles + "\n");
        for(int cycle = 0; cycle < numberofClockCycles; cycle++) {      
            
            //terminate if expection is present
            boolean terminate = resUnit.advanceUnit();
            if(terminate) {
                break;
            }
            
            System.out.println("Clock Cycle " + (cycle+1));
            //print RSUnit
            System.out.println("\tBusy\tOp\tV1\tV2\tT1\tT2\tROB");
            for(int i = 0; i < resStations.length; i++) {
                ResStation station = resStations[i];
                System.out.print("RS" + (i+1) + " :\t");
                System.out.print(station.isBusy() + "\t");
                System.out.print(station.getOpcode() + "\t");
                System.out.print(station.getValue1() + "\t");
                System.out.print(station.getValue2() + "\t");
                if(station.getTag1() != -1000) {
                    System.out.print(station.getTag1()+1 + "\t");
                }
                else {
                    System.out.print(station.getTag1() + "\t");
                }
                if(station.getTag2() != -1000) {
                    System.out.print(station.getTag2()+1 + "\t");
                }
                else {
                    System.out.print(station.getTag2() + "\t");
                }
                if(station.getDest() != -1000) {
                    System.out.print(station.getDest()+1 + "\t\n");
                }
                else {
                    System.out.print(station.getDest()+ "\t\n");
                }
            }
            System.out.println();
        }
        System.out.println("\t|\n\t|\n\t|After Execution\n\t|\n\tV\n");
                
        
        //print ending instruction queue
        System.out.println("Ending Instruction Queue:");
        if(instrQueue.size() == 0) {
            System.out.println("All instructions were issued\n");
        }
        else {
            for(Instruction instr : instrQueue) {
                String opcode;
                switch(instr.getOpcode()) {
                    case(0) : opcode = "Add"; break;
                    case(1) : opcode = "Sub"; break;
                    case(2) : opcode = "Mul"; break;
                    case(3) : opcode = "Div"; break;
                    default : opcode = "Invalid";
                }
                System.out.println(opcode + " R" + instr.getDest() + ", R" + instr.getSource1() + ", R" + instr.getSource2());
            } System.out.println();
        }
        
        
        //print RF and RAT
        int[] rf = registerFile.getRegisterArray();
        System.out.println("\tRF\tRAT");
        for(int i = 0; i < rf.length; i++) {
            if(rat.getEntry(i) != -1000) {
                System.out.println(i + ":\t" + rf[i] + "\t" + (rat.getEntry(i)+1));
            }
            else {
                System.out.println(i + ":\t" + rf[i] + "\t" + rat.getEntry(i));
            }
        } System.out.println();
        
        
        
        
        
        //print ROB and pointers
        
        
        
        
                
        
        //print RSUnit
        System.out.println("\tBusy\tOp\tV1\tV2\tT1\tT2\tROB");
        for(int i = 0; i < resStations.length; i++) {
            ResStation station = resStations[i];
            System.out.print("RS" + (i+1) + " :\t");
            System.out.print(station.isBusy() + "\t");
            System.out.print(station.getOpcode() + "\t");
            System.out.print(station.getValue1() + "\t");
            System.out.print(station.getValue2() + "\t");
            if(station.getTag1() != -1000) {
                System.out.print(station.getTag1()+1 + "\t");
            }
            else {
                System.out.print(station.getTag1() + "\t");
            }
            if(station.getTag2() != -1000) {
                System.out.print(station.getTag2()+1 + "\t");
            }
            else {
                System.out.print(station.getTag2() + "\t");
            }
            if(station.getDest() != -1000) {
                System.out.print(station.getDest()+1 + "\t\n");
            }
            else {
                System.out.print(station.getDest()+ "\t\n");
            }
        }
    }
}
