package org.modsecurity.experimental.input;


import java.util.Properties;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esperio.http.EsperIOHTTPAdapterPlugin;

public class EsperHttpInputAdapter {
    public static EPServiceProvider epService;
    public static String HTTP_PORT = "8085";
    
    public void run() throws Exception {
        boolean isNio = true;

        String esperIOHTTPConfig = "<esperio-http-configuration>\n" +
                "\t<service name=\"service1\" port=\"" + HTTP_PORT + "\" nio=\"" + isNio + "\"/>\n" +
                "\t<get service=\"service1\" pattern=\"*\"/>\n" +
                "</esperio-http-configuration>";

        Configuration config = new Configuration();
        config.addPluginLoader("EsperIOHTTPAdapter", EsperIOHTTPAdapterPlugin.class.getName(), new Properties(), esperIOHTTPConfig);

        config.addEventTypeAutoName("org.modsecurity.experimental.event");
        epService = EPServiceProviderManager.getDefaultProvider(config);
    }

    public void destroy() {
        epService.destroy();
    }
}

