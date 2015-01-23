package blotto.behaviours;

import java.util.Date;

import jade.core.behaviours.WakerBehaviour;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import blotto.BlottoAgent;
import blotto.globals.BlottoGlobals;
import blotto.ontologies.BlottoOntology;

public class BlottoSleeper extends WakerBehaviour{
	private static final long serialVersionUID = -5599578953286687763L;

	protected BlottoAgent myAgent;

	public BlottoSleeper(BlottoAgent a, long timeout) {
		super(a, timeout);
		this.myAgent = a;
	}
	
	@Override
	public void onWake() {
		if (this.myAgent.getUnits() == 0) {
			this.myAgent.doDelete();
			return;
		}
		
		ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
		cfp.setProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET);
		cfp.setLanguage(BlottoGlobals.getCodec().getName());
		cfp.setOntology(BlottoOntology.getInstance().getName());
		
		Date date = new Date();
		date.setTime(date.getTime() + BlottoGlobals.CFP_WAIT);
		cfp.setReplyByDate(date);
		
		myAgent.addBehaviour(new BlottoInitiator(myAgent, cfp));
	}
}
