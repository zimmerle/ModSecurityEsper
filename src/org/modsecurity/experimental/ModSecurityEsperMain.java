package org.modsecurity.experimental;

import org.modsecurity.experimental.input.EsperHttpInputAdapter;
import org.modsecurity.experimental.utils.SupportHTTPClient;

public class ModSecurityEsperMain {
	public static boolean running = true;

    public static void main(String[] args) throws Exception {
        EsperHttpInputAdapter test = new EsperHttpInputAdapter();
        test.run();

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
