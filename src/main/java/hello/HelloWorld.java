package hello;

import com.matrixdroplet.waterdrop.ioc.annotation.Controller;

/**
 * Created by li on 2016/4/13.
 */
@Controller("ccc")
public class HelloWorld implements Hi{
    public void sayHello(){
        System.out.print("i'm helloWorld");
    }

    public String hi() {
        return "i'm hi method";
    }
}
