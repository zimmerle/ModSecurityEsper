package org.modsecurity.experimental.old;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

public class ModSecurityEsper {

    public void run() {

        /**
         * Configure CEP Engine Option
         */
        Configuration config = new Configuration();
        config.addEventTypeAutoName("org.modsecurity.experimental");

        /**
         * Create CEP Engine Instance
         */
        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(config);

        /**
         * Publish EPL Statement
         */
        String expression = "select Math.max(2, 3) as mymax, itemName, avg(price) from org.modsecurity.experimental.EsperQuickStartDemo$OrderEvent.win:time(30 sec)";
//        String expression = "select Math.max(2, 3) as mymax, itemName, avg(price) from org.modsecurity.experimental.EsperQuickStartDemo$OrderEvent.win:time(1 sec)";
//        String expression = "select Math.max(2, 3) as mymax, itemName, avg(price) from org.modsecurity.experimental.EsperQuickStartDemo$OrderEvent.win:length(2)";
//        String expression = "select Math.max(2, 3) as mymax, itemName, avg(price) from org.modsecurity.experimental.EsperQuickStartDemo$OrderEvent.win:length(1)";
        EPStatement statement = epService.getEPAdministrator().createEPL(expression);

        /**
         * Subscribe EPL Statement Listener
         */
        MyListener listener = new MyListener();
        statement.addListener(listener);

        /**
         * Send sample Event for TEST
         */
        OrderEvent event = new OrderEvent("shirts", 1);
        epService.getEPRuntime().sendEvent(event);


        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {

        }

        /**
         * Send sample Event for TEST
         */
        OrderEvent event2 = new OrderEvent("pants", 3);
        epService.getEPRuntime().sendEvent(event2);

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
            System.out.println();
            System.out.println("itemName=" + event.get("itemName"));
            System.out.println("avg=" + event.get("avg(price)"));
            System.out.println("mymax=" + event.get("mymax"));
            System.out.println();
        }
    }


    /**
     * Sample Order Event
     */
    public class OrderEvent {

        private String itemName;

        private double price;

        public OrderEvent(String itemName, double price) {
            this.itemName = itemName;
            this.price = price;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        @Override
        public String toString() {
            return "OrderEvent{" +
                    "itemName='" + itemName + '\'' +
                    ", price=" + price +
                    '}';
        }
    }


    public static void main(String[] args) {
        ModSecurityEsper startMainClass = new ModSecurityEsper();
        startMainClass.run();
    }
}
