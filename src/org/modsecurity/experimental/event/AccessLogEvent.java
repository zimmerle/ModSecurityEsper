package org.modsecurity.experimental.event;


/**
 * Access Log Event Class.
 *
 * <p/>See http://esper.codehaus.org/tutorials/tutorial/quickstart.html
 * <p/>See http://esper.codehaus.org/esperio-4.3.0/doc/reference/en/html/adapter_http.html
 *
 * @author <a href=mailto:iamtedwon@gmail.com">Ted Won</a>
 * @version 1.0
 */
public class AccessLogEvent {

    private String ipAddress;

    private String page;

    private String date;
    private String name;

    public AccessLogEvent() {
    		this.name = "AccessLog";
    }

    public AccessLogEvent(String ipAddress, String page, String date) {
        this.ipAddress = ipAddress;
        this.page = page;
        this.date = date;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "AccessLogEvent{" +
                "ipAddress='" + ipAddress + '\'' +
                ", page='" + page + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}

