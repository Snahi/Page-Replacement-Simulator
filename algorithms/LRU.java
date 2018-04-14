package algorithms;

import hardware.RAM;
import virtual_memory.Page;
import virtual_memory.VirtualMemory;

public class LRU implements Algorithm {

	private RAM ram;
	private VirtualMemory vm;
	
	
	public LRU(RAM ram, VirtualMemory vm) {
		this.ram = ram;
		this.vm = vm;
	}
	
	
	
	@Override
	public void replacePage(int neededId, int neededVirtualAddress) {
		
		Page needed = vm.get(neededId, neededVirtualAddress);
		if (ram.isFull()) {
			
			// finding least recently used //
			Page lru = ram.getPages().get(0);
			
			for (Page p : ram.getPages()) {
				if (p.getLastUse() < lru.getLastUse()) {
					lru = p;
				}
			}
			
			ram.replace(lru, needed);
			
		} else {
			ram.add(needed);
		}
	}
}

















