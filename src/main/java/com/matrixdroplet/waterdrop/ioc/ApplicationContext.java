package com.matrixdroplet.waterdrop.ioc;

import com.matrixdroplet.waterdrop.ioc.annotation.Autowired;
import com.matrixdroplet.waterdrop.ioc.config.Config;
import com.matrixdroplet.waterdrop.ioc.config.JsonConfigReader;
import com.matrixdroplet.waterdrop.ioc.injector.FiledInjector;
import com.matrixdroplet.waterdrop.ioc.load.IocLoader;
import com.matrixdroplet.waterdrop.ioc.load.SimpleIocLoader;
import com.matrixdroplet.waterdrop.jdbc.pool.ConnectionPool;
import com.matrixdroplet.waterdrop.web.load.Register;
import com.matrixdroplet.waterdrop.web.load.RequestMappingPool;
import com.matrixdroplet.waterdrop.web.load.RequestRegister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by li on 2016/4/13.
 */
public class ApplicationContext {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationContext.class);
    public Ioc ioc;
    private IocLoader iocLoader;
    private Register register;
    private RequestMappingPool requestMappingPool = new RequestMappingPool();
    private Config config;

    public ApplicationContext() {
        long start = System.currentTimeMillis();
        LOGGER.info("ApplicationContext初始化...");
        init();
        LOGGER.info("waterdrop框架初始化完成  in {} ms", System.currentTimeMillis() - start);
    }

    public void init() {
        config = new JsonConfigReader().getConfig();
        this.ioc = new SimpleIoc();
        this.iocLoader = new SimpleIocLoader(config.getPackageNames());
        this.register = new RequestRegister();
        iocLoader.load(ioc);
        //依赖注入
        LOGGER.info("正在进行依赖注入");
        List<BeanDefinition> beanDifinitions = ioc.getBeanDifinitions();
        for (BeanDefinition beanDifinition : beanDifinitions) {
            Class<?> clazz = beanDifinition.getType();
            Field[] declaredFields = clazz.getDeclaredFields();
            for (Field field : declaredFields) {
                if (field.getAnnotation(Autowired.class) != null)
                    new FiledInjector(ioc, field).inject(beanDifinition.getBean());
            }
        }
        //是否进行web请求注册
        boolean isRequestRegister = config.getWeb();
        if (isRequestRegister)
            register.register(ioc, requestMappingPool);
        //数据库连接池初始化
        LOGGER.info("正在进行数据库初始化");
        ioc.getBean(ConnectionPool.class).init(config);
    }

    public RequestMappingPool getRequestMappingPool() {
        return requestMappingPool;
    }

    public Config getConfig() {
        return config;
    }
}

