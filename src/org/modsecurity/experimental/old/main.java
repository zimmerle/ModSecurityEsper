package org.modsecurity.experimental.old;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esperio.socket.EsperIOSocketAdapter;
import com.espertech.esperio.socket.config.ConfigurationSocketAdapter;
import com.espertech.esperio.socket.config.DataType;
import com.espertech.esperio.socket.config.SocketConfig;

public class main {

	public static void main(String[] args) {
        EsperHttpInputOutputAdapterDemo startMainClass = new EsperHttpInputOutputAdapterDemo();
        try {
			startMainClass.run();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		/*
		boolean exit = false;
		Configuration configuration = new Configuration();		
		configuration.configure("modsec.esper.cfg.xml");
		configuration.addImport("org.modsecurity.experimental.*");

		configuration.addEventType(PersonEvent.class);
		configuration.addEventType(ModSecurityAuditLogEvent.class);

		EPServiceProvider epService = EPServiceProviderManager.getProvider("modsec", configuration);

		String epl = "select name, age from PersonEvent";
		EPStatement statement = epService.getEPAdministrator().createEPL(epl);
		
		ConfigurationSocketAdapter adapterConfig = new ConfigurationSocketAdapter();

		
		//EsperIOHTTPAdapter httpOutputAdapter = new EsperIOHTTPAdapter(adapterConfig, ENGINE_URI);
		
		
		SocketConfig socket = new SocketConfig();
		socket.setDataType(DataType.CSV);
		socket.setPort(7100);
		adapterConfig.getSockets().put("SocketService", socket);
		
		statement.addListener( (newData, oldData) -> {
			  String name = (String) newData[0].get("name");
			  int age = (int) newData[0].get("age");
			  System.out.println(String.format("Name: %s, Age: %d", name, age));
			});
		
		// start adapter
		EsperIOSocketAdapter socketAdapter = new EsperIOSocketAdapter(adapterConfig, "engineURI");
		socketAdapter.start();
		epService.getEPRuntime().sendEvent(new PersonEvent("Peter", 10));

		epService.initialize();
		
while (!exit) {
	try {
	    Thread.sleep(1000);                 //1000 milliseconds is one second.
	} catch(InterruptedException ex) {
	    Thread.currentThread().interrupt();
	}
}
		socketAdapter.destroy();	
	}
*/
}
