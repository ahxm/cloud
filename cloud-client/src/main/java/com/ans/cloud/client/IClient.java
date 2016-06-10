package com.ans.cloud.client;


import com.ans.cloud.client.exception.ClientException;
import com.ans.cloud.model.Request;
import com.ans.cloud.model.Response;

import java.rmi.ServerException;

/**
 * Created by anzhen on 2016/6/8.
 */
public interface IClient {
    /**
     *
     * @param request
     * @param <T>
     * @param <R>
     * @return
     */
    <T extends Response, R extends Request> T send(R request) throws ClientException, ServerException;

    /**
     *
     * @param request
     * @param <T>
     * @param <R>
     * @return
     */
    <T extends Response, R extends Request> T send(R request, Class<T> type) throws ClientException, ServerException;

}
