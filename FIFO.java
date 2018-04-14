package algorithms;

import hardware.RAM;
import virtual_memory.Page;
import virtual_memory.VirtualMemory;

public class FIFO implements Algorithm {

	
	private RAM ram;
	private VirtualMemory vm;
	
	
	public FIFO(RAM ram, VirtualMemory virtualMemory) {
		this.ram = ram;
		this.vm = virtualMemory;
	}
	
	
	@Override
	public void replacePage(int neededId, int neededVirtualAddress) {
		
		Page oldest = ram.findOldestPage();
		Page needed = vm.get(neededId, neededVirtualAddress);
		
		if (ram.isFull()) {
			//vm.add(oldest);
			ram.replace(oldest, needed);
		} else {
			ram.add(needed);
		}
	}
}
