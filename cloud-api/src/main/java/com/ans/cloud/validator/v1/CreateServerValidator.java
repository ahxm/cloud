package com.ans.cloud.validator.v1;

import com.ans.cloud.model.v1.CreateServerRequest;
import com.ans.cloud.validator.RequestValidator;

/**
 * Created by anzhen on 2016/6/8.
 */
public class CreateServerValidator extends RequestValidator<CreateServerRequest> {

    /**
     * 检验请求参数是否合法
     *
     * @param request 请求
     * @return
     */
    @Override
    public String validate(CreateServerRequest request) {
        String result =  super.validate(request);
        if(result !=null){
            return result;
        }else if(!isChineseName(request.getServerName())){
            return "serverName is invalid.";

        }else if(!isDescription(request.getDescription())){
            return "description is invalid.";
        }else if(request.getCount() <= 0){
            return "count is invalid.";
        }else  if(!isPassword(request.getUserPassword())){
            return "userPassword is invalid.";
        }else if(isEmpty(request.getImageId())){
            return "imageId is invalid.";
        }

        return null;
    }
}
