


import java.util.List;
import java.util.Vector;

import jade.content.ContentElementList;
import jade.content.onto.basic.Action;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.ContractNetResponder;

@SuppressWarnings("serial")
public abstract class MetaBlottoAgent extends Agent{
	protected int units;
	protected BlottoGameList gameList;
	
	protected void launchArgs() {
		units = 0;
		Object[] args = getArguments();
		if (args != null && args.length > 0) {
			units = Integer.parseInt((String)args[0]);
		}
	}
	
	public int getUnits() {
		return this.units;
	}
	
	public void alterUnits(int diff) {
		this.units += diff;
	}
	
	protected abstract void setupCM();
	
	protected abstract void setupDF();
	
	public abstract void setupResponder();
	
	protected abstract List<AID> queryDF_Players();
	
	public abstract AID queryDF_Judge();
	
	@Override
	public void setup() {
		launchArgs();
		gameList = new BlottoGameList();
		
		setupCM();
		setupDF();
		

		setupResponder();
		
		addBehaviour(new BlottoSleeper(this, BlottoGlobals.SLEEP));

		/*addBehaviour(new CyclicBehaviour() {

			public void action() {
				DFAgentDescription templ = new DFAgentDescription();
				ServiceDescription sd = new ServiceDescription();
				sd.setType("Blotto");
				templ.addServices(sd);

				try {
					DFAgentDescription[] result = DFService.search(myAgent,
							templ);
					System.out.println(result.length);
				} catch (FIPAException e) {

				}
			}
		});*/
	}
	
	public abstract ContentElementList getContents(ACLMessage message) throws FIPAException;
	
	public Vector<ACLMessage> prepareMessages(ACLMessage cfp) {
		System.out.println("gotowanie wiadomosci");
		Vector<ACLMessage> result = new Vector<>();
		for (AID player : queryDF_Players()) {
			if (player.equals(getAID()))
				continue;
			
			ACLMessage msg = (ACLMessage) cfp.clone();
			
			msg.addReceiver(player);
			ContentElementList cel = new ContentElementList();
			cel.add(new Action(player, new BlottoAgentAction()));
			cel.add(new BlottoUnits());
			
			try {
				getContentManager().fillContent(msg, cel);
				result.add(msg);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		
		return result;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void handleResponses(Vector responses, Vector acceptances) {
		System.out.println("jest wejscie");
		int index = 0;
		System.out.println("wybrany nty proposal" + index);
		for (int i = 0; i < responses.size(); i++) {
			ACLMessage response = (ACLMessage)responses.get(i);
			if (response.getPerformative() == ACLMessage.PROPOSE) {
				ACLMessage msg = response.createReply();
				try {
					if (i == index) {
						msg.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
						int units = this.assignUnits();
						System.out.println("tyle unituw" + units);
						ContentElementList cel = this.getContents(response);
						System.out.println("ding");
						cel.add(new BlottoUnits(units));
						System.out.println("ding");
						getContentManager().fillContent(msg, cel);
						this.units -= units;
						System.out.println("ding");
					} else {
						msg.setPerformative(ACLMessage.REJECT_PROPOSAL);
						msg.setContent(response.getContent());
						System.out.println("dung");
					}
					acceptances.add(msg);
					System.out.println("taki rozmiar" + acceptances.size());
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		}
	}
	
	public void handleResult(ACLMessage resultMsg) {
		int result = 0;
		try {
			ContentElementList cel = this.getContents(resultMsg);
			if (cel.get(0) instanceof BlottoResult) {
				result = ((BlottoResult)cel.get(0)).getResult();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		this.gameList.addGame(result, this.getLocalName(), resultMsg.getSender().getLocalName());
	}
	
	public ACLMessage handleSidekickResult(ACLMessage resultMsg, ACLMessage accMsg) throws FIPAException {
		ContentElementList cel = this.getContents(resultMsg);
		BlottoResult res = (BlottoResult) cel.get(0);
		
		this.gameList.addGame(res.getResult(), accMsg.getSender().getLocalName(), getLocalName());
		
		ACLMessage reply = accMsg.createReply();
		reply.setPerformative(ACLMessage.INFORM);
		reply.setContent(resultMsg.getContent());
		return reply;
	}
	
	private int assignUnits() {
		return this.units > 3 ? 3 : this.units;
	}

	protected void takeDown() {
		try {
			DFService.deregister(this);
		} catch (FIPAException e) {
			System.out.println("Could not deregister from DF: "
					+ e.getMessage());
		}
		displayGameInfo();
	}
	
	protected void displayGameInfo() {
		System.out.println(this.gameList.toString());
	}
}
