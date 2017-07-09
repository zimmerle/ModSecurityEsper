package org.modsecurity.experimental.input;


import java.util.Properties;

import org.modsecurity.experimental.ModSecurityEsperMain;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;
import com.espertech.esperio.http.EsperIOHTTPAdapterPlugin;

/**
 * Esper Quick Start Class.
 * <p/>See http://esper.codehaus.org/tutorials/tutorial/quickstart.html
 * <p/>See http://esper.codehaus.org/esperio-4.3.0/doc/reference/en/html/adapter_http.html
 * <p/>HTTP Request URI Sample: http://localhost:8079/sendevent?stream=MyFirewallEvent&name=Joe&changed=true
 *
 * @author <a href=mailto:tedd824@gmail.com">Ted Won</a>
 * @version 1.0
 */
public class EsperHttpInputAdapter {
    EPServiceProvider epService;
    public static String HTTP_PORT = "8085";
    
    public void run() throws Exception {

        boolean isNio = true;

        String esperIOHTTPConfig = "<esperio-http-configuration>\n" +
                "\t<service name=\"service1\" port=\"" + HTTP_PORT + "\" nio=\"" + isNio + "\"/>\n" +
                "\t<get service=\"service1\" pattern=\"*\"/>\n" +
                "</esperio-http-configuration>";

        System.out.println();
        System.out.println(esperIOHTTPConfig);
        System.out.println();

        Configuration config = new Configuration();
        config.addPluginLoader("EsperIOHTTPAdapter", EsperIOHTTPAdapterPlugin.class.getName(), new Properties(), esperIOHTTPConfig);

        config.addEventTypeAutoName("org.modsecurity.experimental.event");
        epService = EPServiceProviderManager.getDefaultProvider(config);
        
        String expression = "select date from AccessLogEvent.win:time(30 sec)";
        EPStatement statement = epService.getEPAdministrator().createEPL(expression);
        
        String expressionQuit = "select * from QuitEvent";
        EPStatement statementQuit = epService.getEPAdministrator().createEPL(expressionQuit);
        
        MyListener listener = new MyListener();
        statement.addListener(listener);
        MyListenerQuit listenerQuit = new MyListenerQuit();
        statementQuit.addListener(listenerQuit);

    }

    public class MyListener implements UpdateListener {
        public void update(EventBean[] newEvents, EventBean[] oldEvents) {
    			for (int i = 0; i < newEvents.length; i ++) {
    				EventBean event = newEvents[0];
    				System.out.println("date=" + event.get("date"));
    			}
        }
    }
    public class MyListenerQuit implements UpdateListener {
        public void update(EventBean[] newEvents, EventBean[] oldEvents) {
        		System.out.println("Quit!");
        		ModSecurityEsperMain.running = false;
        }
    }
    public void destroy() {
        epService.destroy();
    }
}

