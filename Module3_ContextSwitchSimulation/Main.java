package Module3_ContextSwitchSimulation;

import java.util.*;

public class Main {

	public static void main(String[] args) {

		// declare quantum as 5
		final int QUANTUM = 5;

		// create 10 processes and their PCB
		SimProcess googleChrome = new SimProcess("googleChrome", 01, 228);
		ProcessControlBlock googleChromePCB = new ProcessControlBlock(googleChrome);

		SimProcess microsoftWord = new SimProcess("microsoftWord", 02, 200);
		ProcessControlBlock microsoftWordPCB = new ProcessControlBlock(microsoftWord);

		SimProcess microsoftPowerPoint = new SimProcess("microsoftPowerPoint", 03, 110);
		ProcessControlBlock microsoftPowerPointPCB = new ProcessControlBlock(microsoftPowerPoint);

		SimProcess microsoftExcel = new SimProcess("microsoftExcel", 04, 289);
		ProcessControlBlock microsoftExcelPCB = new ProcessControlBlock(microsoftExcel);

		SimProcess zoom = new SimProcess("zoom", 05, 400);
		ProcessControlBlock zoomPCB = new ProcessControlBlock(zoom);

		SimProcess eclipse = new SimProcess("eclipse", 06, 301);
		ProcessControlBlock eclipsePCB = new ProcessControlBlock(eclipse);

		SimProcess calculatorApp = new SimProcess("calculatorApp", 07, 308);
		ProcessControlBlock calculatorAppPCB = new ProcessControlBlock(calculatorApp);

		SimProcess microsoftEdge = new SimProcess("microsoftEdge", 8, 256);
		ProcessControlBlock microsoftEdgePCB = new ProcessControlBlock(microsoftEdge);

		SimProcess skype = new SimProcess("skype", 9, 197);
		ProcessControlBlock skypePCB = new ProcessControlBlock(skype);

		SimProcess photos = new SimProcess("photos", 10, 351);
		ProcessControlBlock photosPCB = new ProcessControlBlock(photos);

		// ArrayLists to hold the processes and their PCBs that are ready or blocked
		ArrayList<SimProcess> ready = new ArrayList<SimProcess>();
		ArrayList<SimProcess> blocked = new ArrayList<SimProcess>();

		ArrayList<ProcessControlBlock> readyPCB = new ArrayList<ProcessControlBlock>();
		ArrayList<ProcessControlBlock> blockedPCB = new ArrayList<ProcessControlBlock>();

		ready.add(googleChrome);
		ready.add(microsoftWord);
		ready.add(microsoftPowerPoint);
		ready.add(microsoftExcel);
		ready.add(zoom);
		ready.add(eclipse);
		ready.add(calculatorApp);
		ready.add(microsoftEdge);
		ready.add(skype);
		ready.add(photos);

		readyPCB.add(googleChromePCB);
		readyPCB.add(microsoftWordPCB);
		readyPCB.add(microsoftPowerPointPCB);
		readyPCB.add(microsoftExcelPCB);
		readyPCB.add(zoomPCB);
		readyPCB.add(eclipsePCB);
		readyPCB.add(calculatorAppPCB);
		readyPCB.add(microsoftEdgePCB);
		readyPCB.add(skypePCB);
		readyPCB.add(photosPCB);

		// creating a processor object and setting the running process and its PCB
		// to the first element in the ArrayLists
		SimProcessor processor = new SimProcessor();
		SimProcess runningProcess = ready.get(0);
		ProcessControlBlock runningPCB = readyPCB.get(0);

		processor.setCurrentProcess(runningProcess);

		// quantum tracker
		int currentQuantum = 0;

		for (int i = 0; i < 3000; i++) {

			System.out.println("Step " + (i + 1));

			currentQuantum++;

			// execute instructions
			ProcessState runningProcessState = processor.executeNextInstructions();

			if (runningProcessState == ProcessState.BLOCKED) {

				System.out.println("***Process Blocked***");

				// update the PCB
				savePCBofCurrentProcess(processor, runningPCB);

				// remove it from the blocked list and add to the ready list
				blocked.add(runningProcess);
				blockedPCB.add(readyPCB.get(0));

				ready.remove(0);
				readyPCB.remove(readyPCB.get(0));

				// can not reset processor until the ready array list has at least one element
				while (ready.size() == 0) {
					System.out.println("Process idling - waiting for blocked processes to return");
					wakeUpBlocked(ready, blocked, readyPCB, blockedPCB);
				}

				// reset running process to first element and put its data on the processor from
				// its PCB
				runningProcess = ready.get(0);
				runningPCB = readyPCB.get(0);

				processor.setCurrentProcess(runningProcess);
				updateProcessor(processor, runningPCB);

				// restart counting the quantum
				currentQuantum = 0;
			}

			else if (runningProcessState == ProcessState.FINISHED) {

				System.out.println("***Process Finished***");

				// remove it from the ready list
				ready.remove(0);
				readyPCB.remove(0);

				// if you remove the element and there are other elements on the blocked list,
				// you must wake at least one up before continuing
				if (blocked.size() != 0) {
					// can not reset processor until the ready array list has at least one element
					while (ready.size() == 0) {
						System.out.println("Process idling - waiting for blocked processes to return");
						wakeUpBlocked(ready, blocked, readyPCB, blockedPCB);
					}
				}

				// if there are no processes on ready or blocked, end the program
				else if (ready.size() == 0) {
					System.out.print("There are no more processes to run");
					System.exit(0);
				}

				// reset running process to first element and put its data on the processor from
				// its PCB
				runningProcess = ready.get(0);
				runningPCB = readyPCB.get(0);

				processor.setCurrentProcess(runningProcess);
				updateProcessor(processor, runningPCB);

				// restart counting the quantum
				currentQuantum = 0;

			}

			else if (currentQuantum == QUANTUM) {

				System.out.println("***Quantum Expired***");

				// update PCB
				savePCBofCurrentProcess(processor, runningPCB);

				// remove it from the front of the line to the back
				ready.add(ready.size() - 1, ready.get(0));
				readyPCB.add(readyPCB.size() - 1, readyPCB.get(0));

				ready.remove(0);
				readyPCB.remove(0);

				// can not reset processor until the ready array list has at least one element
				while (ready.size() == 0) {
					System.out.println("Process idling - waiting for blocked processes to return");
					wakeUpBlocked(ready, blocked, readyPCB, blockedPCB);
				}

				// reset running process to first element and put its data on the processor from
				// its PCB
				runningProcess = ready.get(0);
				runningPCB = readyPCB.get(0);

				processor.setCurrentProcess(runningProcess);
				updateProcessor(processor, runningPCB);

				// restarting counting the quantum
				currentQuantum = 0;
			}

			else if (ready.size() == 0) {
				System.out.println("Process idling - waiting for blocked processes to return");
				wakeUpBlocked(ready, blocked, readyPCB, blockedPCB);

			}
			// wakeUP blocked element method call
			wakeUpBlocked(ready, blocked, readyPCB, blockedPCB);

		}

	}

	// reset PCB to processors current values
	public static void savePCBofCurrentProcess(SimProcessor processor, ProcessControlBlock processPCB) {

		processPCB.setlastExecutedInstructions(processor.getCurrInstructions());

		int[] currRegisterValues = processor.getRegisterValues();
		processPCB.setRegister(0, currRegisterValues[0]);
		processPCB.setRegister(1, currRegisterValues[1]);
		processPCB.setRegister(2, currRegisterValues[2]);
		processPCB.setRegister(3, currRegisterValues[3]);

		System.out.println("\nContext Switch: Saving Process " + processor.getCurrentProcess().getPID());

		System.out.print("Register Values: ");
		int[] registers = processor.getRegisterValues();
		for (int i = 0; i < registers.length; i++) {
			System.out.print(" Register [" + i + "]: " + registers[i]);
		}

		System.out.println("\nInstruction #: " + processor.getCurrInstructions() + "\n");

	}

	// reset processor from current running proceess's PCB
	public static void updateProcessor(SimProcessor processor, ProcessControlBlock processPCB) {

		processor.setCurrInstructions(processPCB.getlastExecutedInstructions());

		int[] currRegisterValues = processPCB.getRegisterValues();
		processor.setRegister(0, currRegisterValues[0]);
		processor.setRegister(1, currRegisterValues[1]);
		processor.setRegister(2, currRegisterValues[2]);
		processor.setRegister(3, currRegisterValues[3]);

		System.out.println("Context Switch: Restoring Process " + processor.getCurrentProcess().getPID());

		System.out.print("Register Values: ");
		int[] registers = processor.getRegisterValues();
		for (int l = 0; l < registers.length; l++) {
			System.out.print(" Register [" + l + "]: " + registers[l]);
		}

		System.out.println("\nCurrent instructions: " + processor.getCurrInstructions() + "\n");

	}

	public static void wakeUpBlocked(ArrayList<SimProcess> ready, ArrayList<SimProcess> blocked,
			ArrayList<ProcessControlBlock> readyPCB, ArrayList<ProcessControlBlock> blockedPCB) {
		Random rand = new Random();

		for (int onBlockedList = 0; onBlockedList < blocked.size(); onBlockedList++) {

			if (rand.nextInt(100) + 1 < 30) {

				SimProcess temp = blocked.get(onBlockedList);
				ProcessControlBlock tempPCB = blockedPCB.get(onBlockedList);

				blocked.remove(onBlockedList);
				blockedPCB.remove(onBlockedList);

				ready.add(temp);
				readyPCB.add(tempPCB);

			}
		}
	}
}
