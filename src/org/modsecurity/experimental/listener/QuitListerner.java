package org.modsecurity.experimental.listener;

import org.modsecurity.experimental.ModSecurityEsperMain;
import org.modsecurity.experimental.input.EsperHttpInputAdapter;

import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

public class QuitListerner implements UpdateListener {
	public QuitListerner() {
        String expression = "select * from QuitEvent";
        EPStatement statement = EsperHttpInputAdapter.epService.getEPAdministrator().createEPL(expression);
        statement.addListener(this);
	}

	public void update(EventBean[] newEvents, EventBean[] oldEvents) {
		System.out.println("Quit!");
		ModSecurityEsperMain.running = false;
	}
}
