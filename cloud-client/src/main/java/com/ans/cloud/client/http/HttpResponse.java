package com.ans.cloud.client.http;

import com.ans.cloud.client.exception.ClientException;
import com.ans.cloud.model.Request;
import com.ans.cloud.model.Response;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by anzhen on 2016/6/9.
 */
public class HttpResponse {

    // 响应码
    protected int status;
    // 响应体
    protected String content = null;
    // 响应体转换的类型
    private Class type;

    public HttpResponse(String content) {
        this.content = content;
    }


    public HttpResponse(InputStream content) throws ClientException {
        this.content = readContent(content);
    }

    public static HttpResponse wrap(InputStream content) throws ClientException {
        return new HttpResponse(content);
    }

    public static HttpResponse wrap(String content) throws ClientException {
        return new HttpResponse(content);
    }

    public HttpResponse type(Class type) {
        this.type = type;
        return this;
    }

    public HttpResponse status(int status) {
        this.status = status;
        return this;
    }

    public Response unwrap() throws ServerException, ClientException {
        return new JsonReader(this).read();
    }

    public int getStatus() {
        return status;
    }

    public String getContent() {
        return content;
    }

    public Class getType() {
        return type;
    }

    private static String readContent(InputStream content) throws ClientException {
        if (content == null) {
            return null;
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buff = new byte[1024];

        while (true) {
            int read = -1;
            try {
                read = content.read(buff);
            } catch (IOException e) {
                // 读数据流发生异常(已关闭?)， 需重试
                throw new ClientException(e, true);
            }
            if (read == -1) break;
            outputStream.write(buff, 0, read);
        }

        return new String(outputStream.toByteArray(), Request.CHARSET_UTF8);
    }
}
