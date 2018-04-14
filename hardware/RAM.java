package hardware;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

import virtual_memory.Page;
import virtual_memory.PageFrame;


public class RAM {

	private int numOfPF; // number of page frames in RAM
	private ArrayList<PageFrame> pageFrames;
	private int lastPos; // for FIFO, number to assign newcoming page
	
	
	public RAM(int numOfPageFrames) {
		this.numOfPF = numOfPageFrames;
		pageFrames = new ArrayList<PageFrame>(numOfPageFrames);
		
		
		// creating page frames
		for (int i = 0; i < numOfPageFrames; i++) {
			pageFrames.add(new PageFrame(i));
		}
	}
	
	
	
	public Page getPage(int id, int virtualAddress) {
		Page temp = null;
		
		for (PageFrame p : pageFrames) {
			if (p.getPage() != null && p.getPage().getId() == id && p.getPage().getVirtualAddress() == virtualAddress) temp = p.getPage();
		}
		
		return temp;
	}
	
	
	
	public void replace(int oldId, int oldVirtualAddress, Page newPage) {
		ListIterator<PageFrame> it = pageFrames.listIterator();
		PageFrame temp = null;
		
		while (it.hasNext()) {
			temp = it.next();
			if (temp.getPage() != null && temp.getPage().getId() == oldId && temp.getPage().getVirtualAddress() == oldVirtualAddress) {
				temp.setPage(newPage);
				newPage.setInQue(lastPos++);
			}
		}
	}
	
	
	
	public void replace(Page oldPage, Page newPage) {
		ListIterator<PageFrame> it = pageFrames.listIterator();
		PageFrame temp = null;
		int oldId = oldPage.getId();
		int oldVirtualAddress = oldPage.getVirtualAddress();
		
		while (it.hasNext()) {
			temp = it.next();
			if (temp.getPage() != null && temp.getPage().getId() == oldId && temp.getPage().getVirtualAddress() == oldVirtualAddress) {
				temp.setPage(newPage);
				newPage.setInQue(lastPos++);
			}
		}
	}
	
	
	
	public void add(Page page) throws FullMemoryException {
		for (PageFrame pf : pageFrames) {
			if (pf.getPage() == null) {
				pf.setPage(page);
				page.setInQue(lastPos++);
				return;
			}
		}
		
		throw new FullMemoryException();
	}
	
	
	
	public boolean contains(int id, int virtualAddress) {
		
		for (PageFrame p : pageFrames) {
			if (p.getPage() != null && p.getPage().getId() == id && p.getPage().getVirtualAddress() == virtualAddress) {
				
				return true;
			}
		}
		
		return false;
	}
	
	
	
	// for FIFO
	public Page findOldestPage() {
		Page oldest = null;
		
		if (pageFrames.get(0).getPage() != null) {
			oldest = pageFrames.get(0).getPage();
		}
		
		for (PageFrame pf : pageFrames) {
			if (pf.getPage() != null && pf.getPage().getInQue() < oldest.getInQue()) {
				oldest = pf.getPage();
			}
		}
		
		return oldest;
	}
	
	
	
	public boolean isFull() {
		for (PageFrame pf : pageFrames) {
			if (pf.getPage() == null) return false;
		}
		
		return true;
	}
	
	
	
	// getters && setters //////////////////////////////////////////////////////////////////////////////////////
	public void setNumOfPF(int numOfPF) {
		this.numOfPF = numOfPF;
	}
	
	
	public int getNumOfPF() {
		return numOfPF;
	}
	
	
	public ArrayList<PageFrame> getPageFrames(){
		return pageFrames;
	}
	
	
	
	public ArrayList<Page> getPages() {
		ArrayList<Page> pages = new ArrayList<Page>();
		for (PageFrame pf : pageFrames) {
			if (pf.getPage() != null) {
				pages.add(pf.getPage());
			}
		}
		
		return pages;
	}

	
	
	
	
	// Exception //
	public class FullMemoryException extends RuntimeException{
		public FullMemoryException() {
			super("Memory is full!");
		}
	}
}

