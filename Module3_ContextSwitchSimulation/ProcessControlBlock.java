package Module3_ContextSwitchSimulation;

public class ProcessControlBlock {
	
	private SimProcess process;
	private int lastExecutedInstructions;
	private int [] registers = new int[4];
	
	public ProcessControlBlock(SimProcess process) {
		
		this.process = new SimProcess(process);
	}
	
	public int getlastExecutedInstructions() {
		
		return this.lastExecutedInstructions;
	}
	
	public void setlastExecutedInstructions(int lastExecutedInstructions) {
		
		this.lastExecutedInstructions = lastExecutedInstructions;
	}
	
	//retun register array
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
	
	public SimProcess getProcess() {
		
		return this.process;
	}
	
}
