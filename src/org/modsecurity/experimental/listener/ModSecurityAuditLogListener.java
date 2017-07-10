package org.modsecurity.experimental.listener;

import org.modsecurity.experimental.input.EsperHttpInputAdapter;

import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

public class ModSecurityAuditLogListener implements UpdateListener {
	private boolean showLogs = false;

	public ModSecurityAuditLogListener() {
        String expression = "select * from ModSecurityAuditLogEvent";
        EPStatement statement = EsperHttpInputAdapter.epService.getEPAdministrator().createEPL(expression);
        statement.addListener(this);
	}
	public void update(EventBean[] newEvents, EventBean[] oldEvents) {
		if (showLogs) {
			System.out.println("ModSecurity Event");
			System.out.println(" - " + newEvents[0]);
		}
	}
}