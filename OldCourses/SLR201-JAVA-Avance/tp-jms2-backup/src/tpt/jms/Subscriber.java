package tpt.jms;

import java.util.Enumeration;

import javax.jms.*;
import javax.naming.*;


//synchronous message reader from a destination; reads messages without consuming them.
public class Subscriber{
	
	//connection identifiers
	public static final String USERNAME = "guolieqiang";
	public static final String PSW = "glq";

    public static void main(String[] args) 
    {
    	//admin objects
        Context context = null;
        ConnectionFactory factory = null;
        //naming configs
        String factoryName = "MyConnectionFactory";
        String destName = null;
        Destination dest = null;
        //jms
        JMSContext jmsContext = null;
        JMSConsumer subscriber = null;
        //
//        int count = 1;
        
      //check arguments
//        if (args.length < 1 || args.length > 2){
//        	System.out.println("usage: Subscriber <destination> [count]");
//        	System.exit(1);
//	    }
        
        //get the name of the Destination (queue or topic) 
        destName = args[0];

        //get the number of messages to be received
//        if (args.length == 2){
//        	count = Integer.parseInt(args[1]);
//	    }

        try{
        	// create the JNDI initial context
        	context = new InitialContext();
		
        	// look up the ConnectionFactory
        	factory = (ConnectionFactory) context.lookup(factoryName);
		
        	// look up the Destination
        	dest = (Destination) context.lookup(destName);
        	
        	//make sure the destination is queue, then cast it
//        	if(!(dest instanceof Queue)){
//        		System.out.println("Warning: can only receive from a destination of type queue. "
//        				+ "Since destination " + destName + " is *not* a queue, the subscriber will return...");
//        		return;
//        	}
        	//else
        	
        	// # Queue queue = (Queue)dest;
        	
        	//close intitialContext
        	context.close();
		
        	//create the jms context (replacing connection & session in JMS1)
        	//the session will be non-transacted and messages received by this session will be acknowledged automatically 
        		//--> JMSContext.AUTO_ACKNOWLEDGE is the default mode 
			jmsContext = factory.createContext(USERNAME, PSW);

			// create the subscriber
			subscriber = jmsContext.createConsumer(dest);

			System.out.println("Subscriber Ready ...");
			
		
			//receive messages
			
//			String textMessage;
			
			subscriber.setMessageListener(new MsgListener());
			
			
			//close JMSConext
			//jmsContext.close();
			
	    } 
        catch (Exception exception){
        	exception.printStackTrace();
	    } 
	
    }

}