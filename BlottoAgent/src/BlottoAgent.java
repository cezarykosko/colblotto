
import java.util.ArrayList;
import java.util.List;

import jade.content.ContentElement;
import jade.content.ContentElementList;
import jade.core.AID;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.ContractNetResponder;

public class BlottoAgent extends MetaBlottoAgent {

	private static final long serialVersionUID = 8380062308814956970L;

	protected void setupCM() {
		getContentManager().registerLanguage(BlottoGlobals.getCodec());
		getContentManager().registerOntology(BlottoOntology.getInstance());
	}

	protected void setupDF() {
		DFAgentDescription dfd = new DFAgentDescription();

		dfd.addLanguages(FIPANames.ContentLanguage.FIPA_SL);
		dfd.addOntologies(BlottoOntology.ONTOLOGY_NAME);
		dfd.addProtocols(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET);

		ServiceDescription service = BlottoServiceDescriptionFactory
				.getBlottoDescription();
		service.setName("Blotto:" + getName());

		dfd.setName(getAID());
		dfd.addServices(service);

		try {
			DFService.register(this, dfd);
		} catch (FIPAException e) {
			System.out.println(e.getMessage());
		}
	}

	public void setupResponder() {
		MessageTemplate template = ContractNetResponder
				.createMessageTemplate(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET);
		addBehaviour(new BlottoResponder(this, template));
	}

	protected List<AID> queryDF_Players() {
		ArrayList<AID> result = new ArrayList<>();
		DFAgentDescription dfd = new DFAgentDescription();

		dfd.addLanguages(FIPANames.ContentLanguage.FIPA_SL);
		dfd.addOntologies(BlottoOntology.ONTOLOGY_NAME);
		dfd.addProtocols(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET);
		
		ServiceDescription service = BlottoServiceDescriptionFactory
				.getBlottoDescription();

		dfd.addServices(service);

		try {
			DFAgentDescription[] queryResult = DFService.search(this, dfd);
			for (DFAgentDescription desc : queryResult) {
				result.add(desc.getName());
			}
		} catch (FIPAException e) {
			System.out.println(e.getMessage());
		}

		return result;
	}

	public AID queryDF_Judge() {
		AID result = null;
		DFAgentDescription dfd = new DFAgentDescription();

		ServiceDescription service = new ServiceDescription();
		service.setType("Blotto-Play");

		dfd.addServices(service);

		try {
			DFAgentDescription[] queryResult = DFService.search(this, dfd);
			result = queryResult[0].getName();
		} catch (FIPAException e) {
			System.out.println(e.getMessage());
		}

		return result;

	}

	public ContentElementList getContents(ACLMessage msg) throws FIPAException {
		if (!BlottoGlobals.LANGUAGE.equals(msg.getLanguage())
				|| !BlottoOntology.ONTOLOGY_NAME.equals(msg.getOntology()))
			throw new FIPAException("nie dziala cos z onto");

		try {
			ContentElement ce = getContentManager().extractContent(msg);
			if (ce instanceof ContentElementList) {
				return (ContentElementList) ce;
			} else {

				ContentElementList cel = new ContentElementList();
				cel.add(ce);
				return cel;
			}
		} catch (Exception e) {
			throw new FIPAException("wywala sie na ekstraktkontent");
		}
	}
}
