package blotto.behaviours;

import java.util.ArrayList;
import java.util.Vector;

import blotto.BlottoAgent;
import blotto.actions.BlottoResultAction;
import blotto.globals.BlottoGlobals;
import blotto.ontologies.BlottoOntology;
import blotto.predicates.BlottoUnits;
import jade.content.ContentElement;
import jade.content.ContentElementList;
import jade.content.onto.basic.Action;
import jade.domain.FIPAException;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.proto.AchieveREInitiator;
import jade.proto.ContractNetResponder;

public class BlottoSidekick extends AchieveREInitiator{
	private static final long serialVersionUID = -9122739606071521600L;

	protected BlottoAgent myAgent;
	private ACLMessage accMsg;

	public BlottoSidekick(BlottoAgent a, ACLMessage msg) {
		super(a, msg);
		this.myAgent = a;
	}
	
	private void putReply(ACLMessage reply) {
		getDataStore().put(((ContractNetResponder)parent).REPLY_KEY, reply);
	}
	
	private int unitsCount() {
		int units = 0;
		try {
			ContentElementList cel = myAgent.getContents(this.accMsg);
			for (ContentElement ce : cel.toArray()) {
				if (ce instanceof BlottoUnits)
					units += ((BlottoUnits)ce).getCount();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return units;
	}

	private ArrayList<Integer> assignment(int units) {
		ArrayList<Integer> result = new ArrayList<>();
		
		for (int i = 0; i < 4; i++) {
			result.add(units/5);
		}
		
		result.add(units - 4*(units/5));
		
		return result;
	}
	
	@Override
	protected void handleInform(ACLMessage inform) {
		try {
			ACLMessage result = myAgent.handleSidekickResult(inform, accMsg);
			
			putReply(result);
		} catch (FIPAException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected Vector prepareRequests(ACLMessage request) {
		accMsg = (ACLMessage)getDataStore().get(((ContractNetResponder)parent).ACCEPT_PROPOSAL_KEY);
		
		request = new ACLMessage(ACLMessage.REQUEST);
		request.setOntology(BlottoOntology.getInstance().getName());
		request.setLanguage(BlottoGlobals.LANGUAGE);
		request.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
		request.addReceiver(myAgent.queryDF_Judge());
		
		BlottoResultAction act = new BlottoResultAction();
		act.getAllocation().setAssignment(assignment(unitsCount()));
		Action el = new Action(myAgent.getAID(), act);
		
		try {
			myAgent.getContentManager().fillContent(request, el);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		Vector result = new Vector();
		result.add(request);
		return result;
	}
	
	@Override
	protected void handleRefuse(ACLMessage refuse) {
		ACLMessage reply = accMsg.createReply();
		reply.setPerformative(ACLMessage.FAILURE);
		reply.setContent(refuse.getContent());
		putReply(reply);
	}
}
