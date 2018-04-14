package algorithms;

import hardware.RAM;
import virtual_memory.Page;
import virtual_memory.VirtualMemory;

public class RAND implements Algorithm {

	private RAM ram;
	private VirtualMemory vm;
	
	
	public RAND(RAM ram, VirtualMemory vm) {
		this.ram = ram;
		this.vm = vm;
	}
	
	
	
	@Override
	public void replacePage(int neededId, int neededVirtualAddress) {
		
		Page newPage = vm.get(neededId, neededVirtualAddress);
		
		
		if (ram.isFull()) {
			int ramSize = ram.getNumOfPF();
			int random = (int) (Math.random() * (ramSize));
			
			ram.replace(ram.getPages().get(random), newPage);
			
		} else {
			ram.add(newPage);
		}

	}

}
