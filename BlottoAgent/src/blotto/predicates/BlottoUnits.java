package blotto.predicates;

import jade.content.Predicate;

public class BlottoUnits implements Predicate {
	private static final long serialVersionUID = -504074982876420890L;
	
	private int count;
	
	public BlottoUnits() {
		this.count = 0;
	}
	
	public BlottoUnits(int count) {
		this.count = count;
	}
	
	public int getCount() {
		return this.count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	
}
