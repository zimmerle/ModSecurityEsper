package org.modsecurity.experimental;

import org.modsecurity.experimental.input.EsperHttpInputAdapter;
import org.modsecurity.experimental.listener.LiveListerner;
import org.modsecurity.experimental.listener.ModSecurityAuditLogListener;
import org.modsecurity.experimental.listener.QuitListerner;
import org.modsecurity.experimental.listener.security.BruteForce;
import org.modsecurity.experimental.listener.security.MisbehaveClient;
import org.modsecurity.experimental.listener.security.PossibleExplorationAttempt;
import org.modsecurity.experimental.listener.security.SiteScannerListener;
import org.modsecurity.experimental.listener.security.SiteScannerNiktoListener;
import org.modsecurity.experimental.utils.SupportHTTPClient;

public class ModSecurityEsperMain {
	public static boolean running = true;

    public static void main(String[] args) throws Exception {
        EsperHttpInputAdapter test = new EsperHttpInputAdapter();
        test.run();

        /* Testing */
        new LiveListerner();
        new QuitListerner();

        /* Default listener */
        new ModSecurityAuditLogListener();

        /* Security stuff */
        new SiteScannerNiktoListener();
        new SiteScannerListener();
        new PossibleExplorationAttempt();
        new MisbehaveClient();
        new BruteForce();

        SupportHTTPClient client = new SupportHTTPClient();
        client.request(Integer.parseInt(EsperHttpInputAdapter.HTTP_PORT), "sendevent", "stream", "LiveEvent");
        while (running) {
        		try {
        			Thread.sleep(1000);
        		} catch (InterruptedException e) {
        			System.out.println("Failed to process event.");
        			e.printStackTrace();
        		}
        }

        test.destroy();
    }
}
