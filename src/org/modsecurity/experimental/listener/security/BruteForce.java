package org.modsecurity.experimental.listener.security;

import org.modsecurity.experimental.input.EsperHttpInputAdapter;

import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;
import com.espertech.esper.event.map.MapEventBean;

public class BruteForce implements UpdateListener {

	public BruteForce() {
        String expression = ""
            + "select * from "
            + "pattern[a=ModSecurityAuditLogEvent(response_http_code='200') "
            + "-> b=ModSecurityAuditLogEvent(request_uri=a.request_uri, client_ip=a.client_ip) "
            + "-> c=ModSecurityAuditLogEvent(request_uri=b.request_uri, client_ip=b.client_ip)]";
            //+ "-> timer:interval(2 seconds)]";

        EPStatement statement = EsperHttpInputAdapter.epService.getEPAdministrator().createEPL(expression);
        statement.addListener(this);
	}

	public void update(EventBean[] newEvents, EventBean[] oldEvents) {
		int ne = 0;
		int oe = 0;
		try {
			ne = newEvents.length;
		} catch (Exception e) {
		}
		
		try {
			ne = oldEvents.length;
		} catch (Exception e) {
		}

		try {
			System.out.println("There is a brute force attempt: ");
			System.out.println("  `- new events: " + ne);
			for (int i = 0; i < ne; i++) {
				MapEventBean me = (MapEventBean) newEvents[i];
				System.out.println("  " + me.getProperties());
			}
			System.out.println("  `- old events: " + oe);
			for (int i = 0; i < oe; i++) {
				System.out.println("  " + oldEvents[i]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}