package org.modsecurity.experimental.event;

import java.util.Arrays;

/*
private String response_headers;
private String producer_modsec;
private String producer_connector;
private String[] components;

    yajl_gen_string(g,
        reinterpret_cast<const unsigned char*>("messages"),
        strlen("messages"));
    yajl_gen_array_open(g);
    for (auto a : m_rulesMessages) {
        yajl_gen_map_open(g);
        LOGFY_ADD("message", a.m_message.c_str());
        yajl_gen_string(g,
            reinterpret_cast<const unsigned char*>("details"),
            strlen("details"));
        yajl_gen_map_open(g);
        LOGFY_ADD("match", a.m_match.c_str());
        LOGFY_ADD("reference", a.m_reference.c_str());
        LOGFY_ADD("ruleId", std::to_string(a.m_ruleId).c_str());
        LOGFY_ADD("file", a.m_ruleFile.c_str());
        LOGFY_ADD("lineNumber", std::to_string(a.m_ruleLine).c_str());
        LOGFY_ADD("data", a.m_data.c_str());
        LOGFY_ADD("severity", std::to_string(a.m_severity).c_str());
        LOGFY_ADD("ver", a.m_ver.c_str());
        LOGFY_ADD("rev", a.m_rev.c_str());

        yajl_gen_string(g,
            reinterpret_cast<const unsigned char*>("tags"),
            strlen("tags"));
        yajl_gen_array_open(g);
        for (auto b : a.m_tags) {
            yajl_gen_string(g,
                reinterpret_cast<const unsigned char*>(b.c_str()),
                strlen(b.c_str()));
        }
        yajl_gen_array_close(g);

        LOGFY_ADD("maturity", std::to_string(a.m_maturity).c_str());
        LOGFY_ADD("accuracy", std::to_string(a.m_accuracy).c_str());
        yajl_gen_map_close(g);
        yajl_gen_map_close(g);
    }
    yajl_gen_array_close(g);
}
*/

public class ModSecurityAuditLogEvent {
	private String client_ip;
	private String time_stamp;
	private String server_id;
	private String client_port;
	private String host_ip;
	private String host_port;
	private String request_method;
	private String request_http_version;
	private String request_uri;
	private String[] request_headers;
	private String response_http_code;

	public ModSecurityAuditLogEvent() { }

	public String getClient_ip() {
		return client_ip;
	}

	public void setClient_ip(String client_ip) {
		this.client_ip = client_ip;
	}

	public String getTime_stamp() {
		return time_stamp;
	}

	public void setTime_stamp(String time_stamp) {
		this.time_stamp = time_stamp;
	}

	public String getServer_id() {
		return server_id;
	}

	public void setServer_id(String server_id) {
		this.server_id = server_id;
	}

	public String getClient_port() {
		return client_port;
	}

	public void setClient_port(String client_port) {
		this.client_port = client_port;
	}

	public String getHost_ip() {
		return host_ip;
	}

	public void setHost_ip(String host_ip) {
		this.host_ip = host_ip;
	}

	public String getHost_port() {
		return host_port;
	}

	public void setHost_port(String host_port) {
		this.host_port = host_port;
	}

	private String id;
	
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRequest_method() {
		return request_method;
	}

	public void setRequest_method(String request_method) {
		this.request_method = request_method;
	}

	public String getRequest_http_version() {
		return request_http_version;
	}

	public void setRequest_http_version(String request_http_version) {
		this.request_http_version = request_http_version;
	}

	public String getRequest_uri() {
		return request_uri;
	}

	public void setRequest_uri(String request_uri) {
		this.request_uri = request_uri;
	}

	public String[] getRequest_headers() {
		return request_headers;
	}

	public String[] getAllRequest_headers() {
		return request_headers;
	}
	public void setRequest_headers(String[] request_headers) {
		this.request_headers = request_headers;
	}
	
	public String getRequest_headers(int index) {
		return request_headers[index];
	}
	
	
    public String getResponse_http_code() {
		return response_http_code;
	}

	public void setResponse_http_code(String response_http_code) {
		this.response_http_code = response_http_code;
	}

	@Override
	public String toString() {
		return "ModSecurityAuditLogEvent [client_ip=" + client_ip + ", time_stamp=" + time_stamp + ", server_id="
				+ server_id + ", client_port=" + client_port + ", host_ip=" + host_ip + ", host_port=" + host_port
				+ ", request_method=" + request_method + ", request_http_version=" + request_http_version
				+ ", request_uri=" + request_uri + ", request_headers=" + Arrays.toString(request_headers)
				+ ", response_http_code=" + response_http_code + ", id=" + id + "]";
	}
}
