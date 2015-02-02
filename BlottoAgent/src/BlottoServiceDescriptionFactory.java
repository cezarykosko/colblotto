

import jade.domain.FIPAAgentManagement.ServiceDescription;

public class BlottoServiceDescriptionFactory {
	public static ServiceDescription getBlottoDescription() {
		ServiceDescription service = new ServiceDescription();
		service.setType("Blotto");
		/*service.addLanguages("fipa-sl");
		service.addProtocols("fipa-contract-net");
		service.addOntologies("blotto-ontology");*/
		return service;
	}
}
