package virtual_memory;

public class Page {

	private int virtualAddress;
	private int id; // specified for given process
	
	private int inQue; // for FIFO, the smallest inQue --> first to replace
	private int toNextOcc; // for OPT, how many pages, instructions before this page occurs again
	private int lastUse; // for LRU, holds occurrence
	private boolean referenceBit; // for A_LRU, set true after using this page
	
	
	public Page(int id, int virtualAddress) {
		this.virtualAddress = virtualAddress;
		this.id = id;
		toNextOcc = (int) Double.POSITIVE_INFINITY;
	}
	
	
	
	
	public void setVirtualAddress(int virtualAddress) {
		this.virtualAddress = virtualAddress;
	}
	
	
	public int getVirtualAddress() {
		return virtualAddress;
	}
	
	
	public void setId(int id) {
		this.id = id;
	}
	
	
	public int getId() {
		return id;
	}
	
	
	public void setInQue(int inQue) {
		this.inQue = inQue;
	}
	
	
	public int getInQue() {
		return inQue;
	}
	
	
	public void setToNextOcc(int toNextOcc) {
		this.toNextOcc = toNextOcc;
	}
	
	
	public int getToNextOcc() {
		return toNextOcc;
	}
	
	
	public void setLastUse(int lastUse) {
		this.lastUse = lastUse;
	}
	
	
	public int getLastUse() {
		return lastUse;
	}
	
	
	public void setReferenceBit(boolean referenceBit) {
		this.referenceBit = referenceBit;
	}
	
	
	public boolean getReferenceBit() {
		return referenceBit;
	}
	
	
	
	@Override
	public boolean equals(Object page) {
		Page p = (Page) page;
		if (p.getId() == id && p.getVirtualAddress() == virtualAddress) return true;
		
		return false;
	}
	
	
	
	@Override
	public String toString() {
		return "virtualAddress: " + virtualAddress;
	}
}
