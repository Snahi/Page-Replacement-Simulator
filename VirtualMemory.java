package virtual_memory;

import java.util.ArrayList;

public class VirtualMemory {
	// virtual memory just stores the pages

	private int size; // number of pages
	private ArrayList<Page> pages;
	
	
	public VirtualMemory(int size) {
		this.size = size;
		pages = new ArrayList<Page>(size);
	}
	
	
	
	public void add(Page page) {
		if (!pages.contains(page)) {
			pages.add(page);
		}
	}
	
	
	
	public void remove(Page page) {
		pages.remove(page);
	}
	
	
	
	public void remove(int index) {
		pages.remove(index);
	}
	
	
	
	public Page get(int id, int virtualAddress) {
		for (Page p : pages) {
			if (p.getId() == id && p.getVirtualAddress() == virtualAddress) return p;
		}
		
		return null;
	}
	
	
	
	public ArrayList<Page> getPages() {
		return pages;
	}
	
}
