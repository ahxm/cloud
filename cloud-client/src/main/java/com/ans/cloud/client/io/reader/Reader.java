package com.ans.cloud.client.io.reader;

import com.ans.cloud.client.exception.ClientException;
import com.ans.cloud.model.Response;

import java.rmi.ServerException;

/**
 * Created by anzhen on 2016/6/9.
 */
public interface Reader {

    public Response read() throws ClientException,ServerException;

}
