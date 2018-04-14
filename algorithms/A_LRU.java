package algorithms;

import java.util.ArrayList;

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
			ArrayList<Page> toRemove = new ArrayList<Page>(); // holds pages which referenceBit == 0
			
			// finding pages with referenceBit == 0 //
			for (Page p : inRAM) {
				if (p.getReferenceBit() == false) toRemove.add(p);
				else p.setReferenceBit(false);
			}
			
			
			Page temp = null;
			if (! toRemove.isEmpty()) temp = toRemove.get(0);
			
			for (Page p : toRemove) {
				if (p.getInQue() < temp.getInQue()) temp = p;
			}
			
			if (temp != null) {
				oldPage = temp;
			} else {
				for (Page p : inRAM) {
					if (p.getReferenceBit() == false) toRemove.add(p);
				}
				
				temp = toRemove.get(0);
				
				for (Page p : toRemove) {
					if (p.getInQue() < temp.getInQue()) temp = p;
				}
				
				oldPage = temp;
			}
			
			ram.replace(oldPage, newPage);
			
			
		} else {
			ram.add(vm.get(neededId, neededVirtualAddress));
		}
		
		
		
	}
}
