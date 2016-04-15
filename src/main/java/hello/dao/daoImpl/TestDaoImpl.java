package hello.dao.daoImpl;

import com.matrixdroplet.waterdrop.ioc.annotation.Autowired;
import com.matrixdroplet.waterdrop.ioc.annotation.Component;
import com.matrixdroplet.waterdrop.jdbc.JdbcTemplate;
import hello.dao.TestDao;

import java.util.List;
import java.util.Map;

/**
 * Created by li on 2016/4/15.
 */
@Component
public class TestDaoImpl implements TestDao{
    @Autowired
    JdbcTemplate jdbcTemplate;
    public List<Map<String, Object>> selectAllArticles() {
        return jdbcTemplate.select("select * from article");
    }
}
