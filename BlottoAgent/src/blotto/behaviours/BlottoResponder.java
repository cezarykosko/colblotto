package blotto.behaviours;

import jade.content.ContentElement;
import jade.content.ContentElementList;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.ContractNetResponder;
import blotto.BlottoAgent;
import blotto.predicates.BlottoUnits;

public class BlottoResponder extends ContractNetResponder {
	
	private static final long serialVersionUID = -5332855623045493650L;
	
	protected BlottoAgent myAgent;
	int units;

	public BlottoResponder(BlottoAgent a, MessageTemplate mt) {
		super(a, mt);
		this.myAgent = a;
		this.units = 0;
		this.registerHandleAcceptProposal(new BlottoSidekick(myAgent, null));
	}
	
	@Override
	protected ACLMessage handleCfp(ACLMessage cfp) {
		myAgent.setupResponder();
		
		ACLMessage reply = cfp.createReply();
		
		try {
			ContentElementList cel = myAgent.getContents(cfp);
			int minUnits = 0;
			for (ContentElement ce : cel.toArray()) {
				if (ce instanceof BlottoUnits) {
					minUnits = ((BlottoUnits)ce).getCount();
				}
			}
			
			ContentElementList replyList = new ContentElementList();
			int unitsToSpend = myAgent.getUnits();
			if (unitsToSpend < minUnits) {
				reply.setPerformative(ACLMessage.REFUSE);
				reply.setContent(cfp.getContent());
				return reply;
			}
			
			reply.setPerformative(ACLMessage.PROPOSE);
			BlottoUnits blUnits = new BlottoUnits(minUnits);
			replyList.add(blUnits);
			myAgent.getContentManager().fillContent(reply, replyList);
			myAgent.alterUnits(-minUnits);
			this.units = minUnits;
		} catch (Exception e) {
			reply.setPerformative(ACLMessage.NOT_UNDERSTOOD);
			reply.setContent(e.getMessage());
		}
		
		return reply;
	}
	
	@Override
	protected void handleRejectProposal(ACLMessage cfp, ACLMessage propose, ACLMessage reject) {
		myAgent.alterUnits(units);
		this.units = 0;
	}
}
