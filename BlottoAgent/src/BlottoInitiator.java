


import java.util.Vector;

import jade.lang.acl.ACLMessage;
import jade.proto.ContractNetInitiator;

public class BlottoInitiator extends ContractNetInitiator {
	private static final long serialVersionUID = -1548192369309256437L;
	
	protected MetaBlottoAgent myAgent;

	public BlottoInitiator(MetaBlottoAgent myAgent2, ACLMessage cfp) {
		super(myAgent2, cfp);
		this.myAgent = myAgent2;
	}
	
	@Override
	protected Vector<ACLMessage> prepareCfps(ACLMessage cfp) {
		Vector<ACLMessage> result = this.myAgent.prepareMessages(cfp);
		
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	protected void handleAllResponses(Vector responses, Vector acceptances) {
		myAgent.handleResponses(responses, acceptances);
	}
	
	@Override
	protected void handleInform(ACLMessage inform) {
		myAgent.handleResult(inform);
	}
	
	@Override
	public int onEnd() {
		myAgent.addBehaviour(new BlottoSleeper(myAgent, BlottoGlobals.SLEEP));
		return 0;
	}

}
