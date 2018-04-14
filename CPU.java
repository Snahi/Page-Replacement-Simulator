package hardware;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import algorithms.Algorithm;
import algorithms.OPT;
import virtual_memory.Page;
import virtual_memory.VirtualMemory;


public class CPU {

	private Algorithm alg;
	private ArrayList<Process> processes;
	private MMU mmu;
	private VirtualMemory vm; // maybe not the best place for this variable, but it makes things simplier
	private RAM ram;
	
	
	public CPU(Algorithm algorithm, RAM ram, VirtualMemory vm) {
		this.alg = algorithm;
		processes = new ArrayList<Process>();
		mmu = new MMU(ram, algorithm);
		this.vm = vm;
		this.ram = ram;
	}
	
	
	
	public void run() {
		createProcesses();
		
		Page ref = null;
		int index = 0; // holds number of executed instructions
		Page currentPage = null;
		
		for (Process p : processes) {
			while ((ref = p.getReference()) != null) {
				currentPage = mmu.receivePage(ref.getId(), ref.getVirtualAddress(), ref);
				currentPage.setLastUse(index);
				currentPage.setReferenceBit(true);
				
				if (alg instanceof OPT) {
					((OPT) alg).sendInstructionList(getInstruction(), index);
					((OPT) alg).refresh();	
				}
				
				index++;
			}
		}
	}
	
	
	
	private void createProcesses() {// creates processes and adds pages to Virtual Memory
		try (BufferedReader reader = new BufferedReader(new FileReader("data/data.txt"))){
			
			Process temp = null;
			ArrayList<Page> references;
			Page pageVM = null;
			
			String line = null;
			while ((line = reader.readLine()) != null) {
				if (line.charAt(0) == 'p') {
					references = new ArrayList<Page>();
					temp = new Process("Process" + line.charAt(7));
					
					while ((line = reader.readLine()) != null && !line.equals("")) {
						pageVM = new Page(Integer.parseInt(Character.toString(temp.getId().charAt(7))), Integer.parseInt(line));
						references.add(pageVM);
						vm.add(pageVM);
					}
					
					temp.setReferences(references);
					processes.add(temp);
					setNextOcc(references); // sets distance to the next occ, for OPT algorithm
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void setNextOcc(ArrayList<Page> references) {
		Page temp = null;
		for (int i = 0; i < references.size(); i++) {
			temp = references.get(i);
			for (int j = i + 1; j < references.size(); j++) {
				if (temp.equals(references.get(j))) {
					temp.setToNextOcc(j - i);
					break;
				}
			}
		}
	}
	
	
	
	public ArrayList<Process> getProcesses(){
		return processes;
	}
	
	
	
	public ArrayList<Page> getInstruction(){
		ArrayList<Page> ins = new ArrayList<Page>();
		
		for (Process p : processes) {
			ins.addAll(p.getReferences());
		}
		
		return ins;
	}
	
	
	
	public MMU getMmu() {
		return mmu;
	}
	
	
	
	public class Process {
		
		private String id;
		private ArrayList<Page> references; // contains succeeding pages in order
		private int lastReference; // holds position in ArrayList<Integer> references of page that was used as last; -1 while none was used
		
		
		public Process(String id) {
			references = new ArrayList<Page>();
			lastReference = -1;
			this.id = id;
		}
		
		
		
		public Page getReference() {// returns null while there are no more references
			if (lastReference != references.size() - 1) {
				lastReference++;
				return references.get(lastReference);
			}
			
			return null;
		}
		
		
		
		public void setReferences(ArrayList<Page> references) {
			this.references = references;
		}
		
		
		
		public void setId(String id) {
			this.id = id;
		}
		
		
		public String getId() {
			return id;
		}
		
		
		public ArrayList<Page> getReferences(){
			return references;
		}
	}
}
