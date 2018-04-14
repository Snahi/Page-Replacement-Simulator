package virtual_memory;

public class PageFrame {

	private int address;
	private Page page; // this page frame contains this page
	
	
	public PageFrame(int address) {
		this.address = address;
	}
	
	
	public PageFrame(int address, Page page) {
		this.address = address;
		this.page = page;
	}
	
	
	
	// getters && setters ///////////////////////////////////////////////////////
	public void setPage(Page page) {
		this.page = page;
	}
	
	
	public Page getPage() {
		return page;
	}
	
	
	public void setAddress(int address) {
		this.address = address;
	}
	
	
	public int getAddress() {
		return address;
	}
}
