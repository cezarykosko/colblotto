package blotto.actions;

import blotto.concepts.BlottoAllocation;
import jade.content.AgentAction;

public class BlottoResultAction implements AgentAction{
	private static final long serialVersionUID = -1318718429494538242L;
	
	private BlottoAllocation allocation;
	
	public BlottoResultAction() {
		this.allocation = new BlottoAllocation();
	}
	
	public BlottoAllocation getAllocation() {
		return this.allocation;
	}
	
	public void setAllocation(BlottoAllocation allocation) {
		this.allocation = allocation;
	}
}
