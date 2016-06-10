package com.ans.cloud.client;

import com.ans.cloud.client.auth.Credential;
import com.ans.cloud.client.policy.DefaultRetryPolicy;
import com.ans.cloud.client.policy.RetryPolicy;

/**
 * Created by anzhen on 2016/6/8.
 */
public class ClientConfig {

    private Credential credential;

    private RetryPolicy retryPolicy = new DefaultRetryPolicy();
}
