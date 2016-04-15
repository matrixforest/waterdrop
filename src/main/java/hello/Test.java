package hello;

/**
 * Created by li on 2016/4/13.
 */
public class Test {
    public static void main(String[] args) {
        Object l=new HelloWorld();
        System.out.println(l.getClass().getName());
    }

    /*public static void main(String[] args) {
        ApplicationContext applicationContext = new ApplicationContext();
        Ioc ioc=applicationContext.ioc;
        List<BeanDefinition> beanDifinitions = ioc.getBeanDifinitions();
        System.out.println(beanDifinitions.size());
        for (BeanDefinition beanDifinition : beanDifinitions) {
//            System.out.println(beanDifinition.getType().getName());
            Object bean=beanDifinition.getBean();
            if(bean instanceof GreenTea){
                ((GreenTea) bean).setHelloWorld();
                System.out.println("***yes!***");
            }
            if(bean instanceof HelloWorld){
                ((HelloWorld) bean).sayHello();
                System.out.println("***yes!***");
            }
        }
//        new GreenTea().setHelloWorld();
    }*/
}
