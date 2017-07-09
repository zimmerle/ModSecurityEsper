package org.modsecurity.experimental.input;


import java.util.Properties;

import org.modsecurity.experimental.support.SupportHTTPClient;
import org.modsecurity.experimental.support.SupportHTTPServer;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;
import com.espertech.esperio.http.EsperIOHTTPAdapter;
import com.espertech.esperio.http.EsperIOHTTPAdapterPlugin;
import com.espertech.esperio.http.config.ConfigurationHTTPAdapter;
import com.espertech.esperio.http.config.Request;

/**
 * Esper Quick Start Demo Class with HTTP Input/Output Adapter.
 * <p/>
 * <p/>See http://esper.codehaus.org/tutorials/tutorial/quickstart.html
 * <p/>See http://esper.codehaus.org/esperio-4.3.0/doc/reference/en/html/adapter_http.html
 * <p/>HTTP Request URI Sample: http://localhost:8079/sendevent?stream=MyFirewallEvent&name=Joe&changed=true
 *
 * @author <a href=mailto:iamtedwon@gmail.com">Ted Won</a>
 * @version 1.0
 */
public class EsperHttpInputOutputAdapterDemo {

    private final static String ENGINE_URI = "CEP-SERVICE-0000";

    public void run() throws Exception {

        String port = "8083";
        boolean isNio = true;

        /**
         * Configure HTTP Output Adapter
         */
        ConfigurationHTTPAdapter adapterConfig = new ConfigurationHTTPAdapter();

        Request requestOne = new Request();
        requestOne.setStream("SupportBean");
        requestOne.setUri("http://localhost:8084/root");
        adapterConfig.getRequests().add(requestOne);

        Request requestTwo = new Request();
        requestTwo.setStream("SupportBean");
        requestTwo.setUri("http://localhost:8085/root/${stream}/${ipAddress}/${page}");
        adapterConfig.getRequests().add(requestTwo);

        EsperIOHTTPAdapter httpOutputAdapter = new EsperIOHTTPAdapter(adapterConfig, ENGINE_URI);

        /**
         * Configure HTTP Input Adapter
         */
        String esperIOHTTPConfig = "<esperio-http-configuration>\n" +
                "<service name=\"service1\" port=\"" + port + "\" nio=\"" + isNio + "\"/>" +
                "<get service=\"service1\" pattern=\"*\"/>" +
                "</esperio-http-configuration>";

        Configuration config = new Configuration();
        config.addPluginLoader("EsperIOHTTPAdapter", EsperIOHTTPAdapterPlugin.class.getName(), new Properties(), esperIOHTTPConfig);

        config.addEventTypeAutoName("org.modsecurity.experimental.event");

        /**
         * Create CEP Engine Instance
         */
        EPServiceProvider epService = EPServiceProviderManager.getProvider(ENGINE_URI, config);


        /**
         * Publish EPL Statement
         */
        String expression = "insert into SupportBean select ipAddress, page, date from AccessLogEvent.win:time(30 sec)";
        EPStatement statement = epService.getEPAdministrator().createEPL(expression);

        /**
         * Start up HTTP Output Adapter
         */
        httpOutputAdapter.start();

        /**
         * Run HTTP Adapter for TEST
         */
        SupportHTTPServer server8084 = new SupportHTTPServer(8084);
        server8084.start();

        SupportHTTPServer server8085 = new SupportHTTPServer(8085);
        server8085.start();

        /**
         * Subscribe EPL Statement Listener
         */
        MyListener listener = new MyListener();
        statement.addListener(listener);

        /**
         * Send sample Event for TEST
         */
        SupportHTTPClient client = new SupportHTTPClient();
        client.request(8083, "sendevent", "stream", "AccessLogEvent", "ipAddress", "localhost", "page", "mypage", "date", "mydate");

        /**
         * Stop down HTTP Output Adapter
         */
        server8084.stop();
        server8085.stop();

        /**
         * Destory CEP Engine Instance
         */
        epService.destroy();
    }

    /**
     * Sample EPL Statement Listener ==> Output Adapter
     */
    public class MyListener implements UpdateListener {

        public void update(EventBean[] newEvents, EventBean[] oldEvents) {

            EventBean event = newEvents[0];
            System.out.println("ipAddress=" + event.get("ipAddress"));
            System.out.println("page=" + event.get("page"));
            System.out.println("date=" + event.get("date"));
        }
    }

    public static void main(String[] args) throws Exception {

        EsperHttpInputOutputAdapterDemo startMainClass = new EsperHttpInputOutputAdapterDemo();

        startMainClass.run();
    }
}

