package com.matrixdroplet.waterdrop.web.load;

import com.google.common.collect.Maps;
import com.matrixdroplet.waterdrop.web.handle.RequestHandler;

import java.util.Map;

/**
 * Created by li on 2016/4/14.
 */
public class RequestMappingPool {
    private Map<String,RequestHandler> requestMappingPool= Maps.newHashMap();

    public RequestHandler getRequestHander(String requestInfo){
        return requestMappingPool.get(requestInfo);
    }

    public void setRequestHandler(String requestInfo,RequestHandler requestHandler){
        requestMappingPool.put(requestInfo,requestHandler);
    }
}
