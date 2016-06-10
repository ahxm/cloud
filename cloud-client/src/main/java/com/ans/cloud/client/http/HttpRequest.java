package com.ans.cloud.client.http;

import com.ans.cloud.model.Request;

/**
 * Created by anzhen on 2016/6/9.
 */
public class HttpRequest {

    private String method = "GET";

    private Request request;

    private String accept = "application/json";

    private String params;

    private HttpConfig config;

    private Class type;

    public HttpRequest params(String params){
        this.params  = params;
        return this;
    }


    public HttpRequest config(HttpConfig config){
        this.config = config;
        return this;
    }

    public HttpRequest type(Class tpye){
        this.type = type;
        return this;
    }


    public static HttpRequest wrap(Request request){
        return new HttpRequest(request);
    }

    public HttpRequest(Request request) {
        this.request = request;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public String getAccept() {
        return accept;
    }

    public void setAccept(String accept) {
        this.accept = accept;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public HttpConfig getConfig() {
        return config;
    }

    public void setConfig(HttpConfig config) {
        this.config = config;
    }

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public String getUrl(){
        StringBuilder sb  = new StringBuilder();
        sb.append(config.getEndpoint());
        String module = request.getModule();
        if(sb.charAt(sb.length()-1)!='/'){
            sb.append("/");
        }
        sb.append(module);

        if(params!=null && params.length()>0){
            if(sb.charAt(sb.length()-1)!='?'){
                sb.append("/");
            }
            sb.append(params);
        }

        return sb.toString();
    }
}
