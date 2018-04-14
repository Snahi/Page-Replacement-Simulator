package algorithms;

import virtual_memory.Page;
import virtual_memory.VirtualMemory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import hardware.RAM;

public class OPT implements Algorithm {

	private ArrayList<Page> pages; // holds all instructions in order
	private int pos;
	private RAM ram;
	private VirtualMemory vm;
	
	public OPT(RAM ram, VirtualMemory vm) {
		this.ram = ram;
		this.vm = vm;
	}
	
	
	
	@Override
	public void replacePage(int neededId, int neededVirtualAddress) {
		
		ArrayList<Page> ltb = new ArrayList<Page>();
		double ltbVal = Double.NEGATIVE_INFINITY;
		
		if (ram.isFull()) {
			
			// finding last process to be used //
			for (Page p : ram.getPages()) {
				
				if (p.getToNextOcc() >= ltbVal) {
					ltbVal = p.getToNextOcc();
				}
			}
			
			for (Page p : ram.getPages()) {
				if (p.getToNextOcc() == ltbVal) {
					ltb.add(p);
				}
			}
			
			// choosing randomly from ltb (last to be) //
			int ran = (int) (Math.random() * ltb.size());
			
			Page toReplace = ltb.get(ran);
			Page newPage = vm.get(neededId, neededVirtualAddress);
			ram.replace(toReplace, newPage);
		} else {
			Page needed = vm.get(neededId, neededVirtualAddress);
			ram.add(needed);
		}
		
	}	
	
	
	
	public void sendInstructionList(ArrayList<Page> instructions, int pos) {
		pages = instructions;
		this.pos = pos;
	}
	
	
	
	public void refresh() {
		for (Page p : ram.getPages()) {
			for (int i = pos + 1; i < pages.size(); i++) {
				if (pages.get(i).equals(p)) {
					p.setToNextOcc(i - pos);
					break;
				}
				p.setToNextOcc((int) Double.POSITIVE_INFINITY); 
			}
		}
	}
	
	
	
	public ArrayList<Page> getPages(){
		return pages;
	}
}
