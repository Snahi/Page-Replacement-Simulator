package algorithms;

import java.util.ArrayList;
import java.util.Comparator;

import hardware.RAM;
import virtual_memory.Page;
import virtual_memory.VirtualMemory;

public class A_LRU implements Algorithm {

	private RAM ram;
	private VirtualMemory vm;
	
	
	public A_LRU(RAM ram, VirtualMemory vm) {
		this.ram = ram;
		this.vm = vm;
	}
	
	
	
	@Override
	public void replacePage(int neededId, int neededVirtualAddress) {
		
		if (ram.isFull()) {
			Page newPage = vm.get(neededId, neededVirtualAddress);
			Page oldPage = ram.findOldestPage();
			
			ArrayList<Page> inRAM = new ArrayList<Page>(ram.getPages());
			inRAM.sort(new Comparator<Page>() {// sorting (from the oldest to newest)
				
				@Override
				public int compare(Page p1, Page p2) {
					if (p1.getInQue() < p2.getInQue()) {// if is longer in RAM move it forward
						return -1;
					} else if (p1.getInQue() > p2.getInQue()) {
						return 1;
					} else {
						return 0;
					}
				}
			});
			
			
			// finding pages with referenceBit == 0 //
			for (Page p : inRAM) {
				if (p.getReferenceBit() == false) {
					oldPage = p;
					break;
				} else {
					p.setReferenceBit(false);
				}
			}
			
			
			ram.replace(oldPage, newPage);
			
			
		} else {
			ram.add(vm.get(neededId, neededVirtualAddress));
		}
		
		
		
	}
}
