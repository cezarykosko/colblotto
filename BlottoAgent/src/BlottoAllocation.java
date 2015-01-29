

import jade.util.leap.ArrayList;
import jade.util.leap.List; 

import jade.content.Concept;

public class BlottoAllocation implements Concept{
	
	private static final long serialVersionUID = -1046961840112579207L;

	private List assignment;
	
	public BlottoAllocation() {
		this.assignment = new ArrayList();
	}
	
	public List getAssignment() {
		return this.assignment;
	}
	
	public void setAssignment(List assignment) {
		this.assignment = assignment;
	}
}

