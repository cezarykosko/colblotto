import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class BlottoAgent extends Agent 
{
    public void setup() 
    {
       jade.util.leap.Iterator args = getAID().getAllAddresses();
       System.out.println("Hello. My name is "+getLocalName());
       System.out.println(getAID().getName());
       while (args.hasNext()) {
           System.out.println(args.next());
       }
       System.out.println(getArguments());       

       addBehaviour(new CyclicBehaviour() 
       {
          public void action()
          {
             ACLMessage msgRx = receive();
             if (msgRx != null)
             {
                System.out.println(msgRx);
                ACLMessage msgTx = msgRx.createReply();
                msgTx.setContent("Hello!");
                send(msgTx);
             }
             else
             {
                block();
             }
          }
       });
    }
}
