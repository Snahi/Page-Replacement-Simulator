package hardware;

import algorithms.Algorithm;
import virtual_memory.Page;

public class MMU {

	private RAM ram;
	private Algorithm alg;
	private int pageFaults;
	
	
	public MMU(RAM ram, Algorithm algorithm) {
		this.ram = ram;
		this.alg = algorithm;
	}
	
	
	
	public Page receivePage(int id, int virtualAddress, Page page) { // page was added later, for compatibility I've kept id and virtualAddress
		if (ram.contains(id, virtualAddress)) {
			Page temp = ram.getPage(id, virtualAddress);
			temp.setToNextOcc(page.getToNextOcc());
			return temp;
		} else {
			alg.replacePage(id, virtualAddress);
			pageFaults++;
			return ram.getPage(id, virtualAddress);
		}
	}
	
	
	
	public int getPageFaults() {
		return pageFaults;
	}
}
