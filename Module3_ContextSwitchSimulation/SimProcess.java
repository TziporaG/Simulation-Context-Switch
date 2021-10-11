package Module3_ContextSwitchSimulation;
import java.util.*;
public class SimProcess {
	
	private String procName;
	private int pID;
	private int totalInstructions;
	
	public SimProcess(String procName, int pID, int totalInstructions) {
		
		this.procName = procName;
		this.pID = pID;
		this.totalInstructions = totalInstructions;
	}
	
	public SimProcess(SimProcess copy) {
		
		this(copy.getProcName(), copy.getPID(), copy.getTotalInstructions());
	}
	
	//execute method simulates an instruction execution and returns a process state
	public ProcessState execute(int instructionNum) {
		
		System.out.println("Executing Process Details: ");
		System.out.println("--------------------------");
		System.out.println("Process Name: " + this.procName);
		System.out.println("Process ID: " + this.pID);
		System.out.println("Current Instruction Num: " + instructionNum +"\n");
		
		if(instructionNum >= this.totalInstructions) {
			return ProcessState.FINISHED;
		}
		
		//Generate a random number out of 100 to determine 15%  
		Random percent = new Random();

		//15% returns blocked, 85% returns ready
		if(percent.nextInt(100)+1 <= 15 ) {
			return ProcessState.BLOCKED;
		}
		
		else {
			return ProcessState.READY;
		}
		
		
	}
	
	public String getProcName() {
		
		return this.procName;
	}
	
	public int getPID() {
		
		return this.pID;
	}
	
	public int getTotalInstructions() {
		
		return this.totalInstructions;
	}

}
