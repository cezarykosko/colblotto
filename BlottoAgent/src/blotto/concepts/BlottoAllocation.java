package blotto.concepts;

import java.util.ArrayList;

import jade.content.Concept;

public class BlottoAllocation implements Concept{
	
	private static final long serialVersionUID = -1046961840112579207L;

	private ArrayList<?> assignment;
	
	public BlottoAllocation() {
		this.assignment = new ArrayList<Object>();
	}
	
	public ArrayList<?> getAssignment() {
		return this.assignment;
	}
	
	public void setAssignment(ArrayList<?> assignment) {
		this.assignment = assignment;
	}
}
