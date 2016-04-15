package hello.dao;

import java.util.List;
import java.util.Map;

/**
 * Created by li on 2016/4/15.
 */
public interface TestDao {
    List<Map<String,Object>> selectAllArticles();
}
