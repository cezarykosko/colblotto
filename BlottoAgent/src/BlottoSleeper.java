


import java.util.Date;

import jade.core.behaviours.WakerBehaviour;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;

public class BlottoSleeper extends WakerBehaviour{
	private static final long serialVersionUID = -5599578953286687763L;

	protected MetaBlottoAgent myAgent;

	public BlottoSleeper(MetaBlottoAgent metaBlottoAgent, long timeout) {
		super(metaBlottoAgent, timeout);
		this.myAgent = metaBlottoAgent;
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
