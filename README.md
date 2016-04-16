# waterdrop   
a simple mvc web framework based on java   
#feature  
1.IoC container   
2.web mvc   
3.simplify jdbc   
4.json configuration   

#waterdrop   
一个简单的java web框架  
#特点   
1.实现IoC容器，基于注解依赖注入   
2.注解式mvc   
3.简单的jdbc封装   
4.json配置


# Getting Start

## 依赖 Dependencies
*guava,slf4j,fastjson,dbcp*
```
<dependency>
     <groupId>com.google.guava</groupId>
     <artifactId>guava</artifactId>
     <version>19.0</version>
</dependency>
<dependency>
     <groupId>org.slf4j</groupId>
     <artifactId>slf4j-api</artifactId>
     <version>1.7.5</version>
     </dependency>
<dependency>
     <groupId>org.slf4j</groupId>
     <artifactId>slf4j-log4j12</artifactId>
     <version>1.7.5</version>
</dependency>
<dependency>
     <groupId>com.alibaba</groupId>
     <artifactId>fastjson</artifactId>
     <version>1.2.8</version>
</dependency>
<dependency>
     <groupId>commons-dbcp</groupId>
     <artifactId>commons-dbcp</artifactId>
     <version>1.4</version>
</dependency>
```

## json 配置(json configuration)
```
{
  "waterdrop": {
    "web": true,
    "packageNames": [
      {
        "packageName": "hello.*"
      },
      {
        "packageName": "world.*"
      }
    ],
    "view_pref": "/WEB-INF/jsp/",
    "view_suffix": ".jsp",
    "staticFilePaths": [
      {
        "staticFilePath": "此处实现存在问题，待完善"
      }
    ],
    "database":{
      "driver":"com.mysql.jdbc.Driver",
      "url":"jdbc:mysql://localhost:3306/blog",
      "username":"root",
      "password":"123456"
    }
  }
}
```

## hello world 用例
*dao层*
```java
    @Repository
    public class TestDaoImpl implements TestDao{
        @Autowired
        JdbcTemplate jdbcTemplate;
        public List<Map<String, Object>> selectAllArticles() {
            return jdbcTemplate.select("select * from article");
        }
    }
```
*service层*
```java
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
```
*controller层*
```java
@Controller
@RequestMapping(value = "/test")
public class TestController {
    @Autowired
    private TestService testService;

    @RequestMapping(value = "/hello")
    public String hello(HttpServletRequest request, HttpServletResponse response, Model model){
        System.out.println("/hello调用");
        List<Map<String, Object>> list = testService.selectAllArticles();
        model.addAttribute("list",list);
        return "test";
    }
}
```

### 说明
三天时间仓促完成，功能还很简陋   
已初步实现IoC容器，基于注解依赖注入，目前仅支持属性注入   
web请求通过 DispatcherServlet 进行拦截并下发处理请求，静态资源过滤目前要通过配置web.xml来实现，
StaticFileHandler这个静态资源过滤实现还存在问题，转发请求会被拦截   
jdbc用JdbcTemplate进行简单封装，数据库连接池为dbcp   
整体风格类似spring  




