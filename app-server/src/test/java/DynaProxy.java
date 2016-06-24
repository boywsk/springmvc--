import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by wangshikai on 2016/5/10.
 */
public class DynaProxy implements InvocationHandler{
    private Object proxy;
    private Object delegate;

    public Object bind(Object delegate,Object proxy){
        this.proxy = proxy;
        this.delegate = delegate;
        return Proxy.newProxyInstance(this.delegate.getClass().getClassLoader(),this.delegate.getClass().getInterfaces(),this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Class clazz = this.proxy.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for(Method m : methods){
            m.invoke(this.proxy,args);
        }
        method.invoke(this.delegate,args);
        return null;
    }


    public static void main(String[] args) {
        IHelloLog hello = (IHelloLog)new DynaProxy().bind(new HelloLog(),new LoggerOperation());
        hello.helloWorld1("helloWorld1 J");
        hello.helloWorld2("helloWorld2 J");
    }
}
