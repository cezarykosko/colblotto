package blotto.behaviours;

import java.util.Vector;

import blotto.BlottoAgent;
import blotto.globals.BlottoGlobals;
import jade.lang.acl.ACLMessage;
import jade.proto.ContractNetInitiator;

public class BlottoInitiator extends ContractNetInitiator {
	private static final long serialVersionUID = -1548192369309256437L;
	
	protected BlottoAgent myAgent;

	public BlottoInitiator(BlottoAgent a, ACLMessage cfp) {
		super(a, cfp);
		this.myAgent = a;
	}
	
	@Override
	protected Vector<ACLMessage> prepareCfps(ACLMessage cfp) {
		return this.myAgent.prepareMessages(cfp);
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
