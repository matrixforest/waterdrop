package hello.controller;

import com.matrixdroplet.waterdrop.ioc.annotation.Autowired;
import com.matrixdroplet.waterdrop.ioc.annotation.Controller;
import com.matrixdroplet.waterdrop.web.annotation.RequestMapping;
import com.matrixdroplet.waterdrop.web.model.Model;
import hello.service.TestService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by li on 2016/4/14.
 */
@Controller
@RequestMapping(value = "/test")
public class TestController {
    @Autowired
    private TestService testService;

    @RequestMapping(value = "/hello")
    public String hello(HttpServletRequest request, HttpServletResponse response, Model model) {
        System.out.println("/hello调用");
        List<Map<String, Object>> list = testService.selectAllArticles();
        model.addAttribute("list", list);
        return "test";
    }
}
