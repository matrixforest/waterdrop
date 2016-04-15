package hello.service.serviceImpl;

import com.matrixdroplet.waterdrop.ioc.annotation.Autowired;
import com.matrixdroplet.waterdrop.ioc.annotation.Service;
import hello.dao.TestDao;
import hello.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * Created by li on 2016/4/15.
 */
@Service
public class TestServiceImpl implements TestService{
    private static final Logger LOGGER = LoggerFactory.getLogger(TestServiceImpl.class);

    @Autowired
    TestDao testDao;

    public List<Map<String, Object>> selectAllArticles() {
        long start = System.currentTimeMillis();
        List<Map<String, Object>> rs = testDao.selectAllArticles();
        LOGGER.info("查询数据库,耗时:{}",System.currentTimeMillis()-start);
        return rs;
    }
}
