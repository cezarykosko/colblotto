

import jade.content.Predicate;

public class BlottoResult implements Predicate {
	private static final long serialVersionUID = 2894345877794692101L;

	private int result;
	
	public BlottoResult() {
		this.result = 0;
	}
	
	public BlottoResult(int count) {
		this.result = count;
	}
	
	public int getResult() {
		return this.result;
	}
	
	public void setResult(int count) {
		this.result = count;
	}
	
}
