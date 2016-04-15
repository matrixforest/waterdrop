package com.matrixdroplet.waterdrop.web.load;

import com.matrixdroplet.waterdrop.ioc.Ioc;

/**
 * Created by li on 2016/4/14.
 */
public interface Register {
    void register(Ioc ioc,RequestMappingPool requestMappingPool);
}
