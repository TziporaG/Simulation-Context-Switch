package Module3_ContextSwitchSimulation;
import java.util.*;
public class SimProcessor {
	
	private SimProcess currentProcess;
	private int [] registers = new int[4];
	private int currInstructions;
	
	public SimProcessor() {
		
	}
	
	public SimProcess getCurrentProcess() {
		
		return this.currentProcess;
	}
	
	public void setCurrentProcess(SimProcess newProcess) {
		
		this.currentProcess = new SimProcess(newProcess);
	}
	
	//gets all the values in an array
	public int [] getRegisterValues () {
		
		int [] registerValues = new int[registers.length];
				
		for(int i = 0; i < registers.length; i++) {
			
			registerValues[i] = registers[i];
		}
		
		return registerValues;
	}
	
	//set one register based on parameter registerNum
	public void setRegister(int registerNum, int data) {
		
		if(registerNum != 0 && registerNum != 1 && registerNum != 2 && registerNum != 3) {
			
			throw new IllegalArgumentException("Please choose a valid register address (0-3)");
		}
		
		switch (registerNum) {
		
			case 0:
				registers[0] = data;
				break;
				
			case 1:
				registers[1] = data;
				break;
				
			case 2:
				registers[2] = data;
				break;
			
			case 3:
				registers[3] = data;
				break;
		}
	}
	
	public int getCurrInstructions() {
		
		return this.currInstructions;
	}
	
	public void setCurrInstructions(int currInstructions) {
		
		this.currInstructions = currInstructions;
	}
	
	//processor executes instructions by calling the execute method on the running process
	//returns the process state of the process after execution
	public ProcessState executeNextInstructions() {
		
		ProcessState currState = this.currentProcess.execute(this.currInstructions);
		this.currInstructions++;
		
		Random dataValueFromExecution = new Random ();
		
		for(int i = 0; i < registers.length; i ++) {
			
			this.setRegister(i, dataValueFromExecution.nextInt());
		}
		
		return currState;
	}
}
