package com.ans.cloud.client.http;

import com.ans.cloud.client.exception.ClientException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.rmi.ServerException;

/**
 *
 * HTTP请求执行器
 * Created by anzhen on 2016/6/9.
 */
public class HttpRequestExecutor {

    public static final String X_SDK_CLIENT = "X-sdk-client";
    public static final String HEADER_ACCEPT = "Accept";
    public static final String ACCEPT_JSON = "application/json";

    public HttpRequest execute(HttpRequest request) throws ClientException,ServerException{

        InputStream content = null;
        HttpURLConnection connection = null;
        connection =

    }

    /**
     * 创建应答
     *
     * @param connection
     * @param request
     * @param content
     * @return
     * @throws ClientException
     */
    protected HttpResponse createResponse(HttpURLConnection connection, HttpRequest request, InputStream content) throws ClientException{

        return HttpResponse.w

    }


    /**
     * 连接
     *
     * @param request
     * @return
     * @throws ClientException
     */

    protected HttpURLConnection connect(final HttpRequest request) throws ClientException{


        try {
            URL url =  new URL(request.getUrl());
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod(request.getMethod());
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setConnectTimeout(request.getConfig().getConnectTimeout());
            httpURLConnection.setReadTimeout(request.getConfig().getReadTimeout());
            httpURLConnection.setRequestProperty(X_SDK_CLIENT,"java/1.0.0");
            String accept = ACCEPT_JSON;
            if(request.getAccept()!=null){
                accept = request.getAccept();
            }
            httpURLConnection.setRequestProperty(HEADER_ACCEPT, accept);
            httpURLConnection.connect();
            return httpURLConnection;
        } catch (IOException e) {
            // 连接时发生异常需重试
            throw new ClientException(e, true);
        }
    }
}
