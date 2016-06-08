package com.ans.cloud.validator;

import com.ans.cloud.model.Request;

/**
 * Created by anzhen on 2016/6/8.
 */
public interface Validator<R extends Request> {


    String validate(R request);


}
