

import jade.content.onto.BasicOntology;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.content.schema.AgentActionSchema;
import jade.content.schema.ConceptSchema;
import jade.content.schema.PredicateSchema;
import jade.content.schema.PrimitiveSchema;

public class BlottoOntology extends Ontology {
	private static final long serialVersionUID = -1093176511098622609L;

	public static final String ONTOLOGY_NAME = "blotto-ontology";
	
	//predicates
	public static final String UNITS = "commited-units";
	public static final String UNITS_COUNT = "count";
	
	public static final String RESULT = "blotto-result";
	public static final String RESULT_RESULT = "result";
	
	
	//concepts
	public static final String ALLOCATION = "allocation";
	public static final String ALLOCATION_ASSIGNMENT = "assignment";

	//actions
	public static final String PLAY = "play-blotto";

	public static final String GET_RESULT = "get-blotto-result";
	public static final String GET_RESULT_ALLOCATION = "allocation";
	

	private static Ontology instance;
	
	public static Ontology getInstance() {
		if (instance == null)
			instance = new BlottoOntology();
		
		return instance;
	}

	private BlottoOntology() {
		super(ONTOLOGY_NAME, BasicOntology.getInstance());
		try {
			PredicateSchema units = new PredicateSchema(UNITS);
			add(units, BlottoUnits.class);
			units.add(UNITS_COUNT, (PrimitiveSchema)getSchema(BasicOntology.INTEGER), ConceptSchema.MANDATORY);

			PredicateSchema result = new PredicateSchema(RESULT);
			add(result, BlottoResult.class);
			result.add(RESULT_RESULT, (PrimitiveSchema)getSchema(BasicOntology.INTEGER), ConceptSchema.MANDATORY);
			
			ConceptSchema allocation = new ConceptSchema(ALLOCATION);
			add(allocation, BlottoAllocation.class);
			allocation.add(ALLOCATION_ASSIGNMENT, (PrimitiveSchema)getSchema(BasicOntology.INTEGER), 5, 5);
			
			AgentActionSchema actSchema = new AgentActionSchema(PLAY);
			add(actSchema, BlottoAgentAction.class);
			
			AgentActionSchema resSchema = new AgentActionSchema(GET_RESULT);
			add(resSchema, BlottoResultAction.class);
			resSchema.add(GET_RESULT_ALLOCATION, (ConceptSchema)getSchema(ALLOCATION), ConceptSchema.MANDATORY);
			
		} catch (OntologyException e) {
			System.out.println(e.getMessage());
		}
	}
	
}
