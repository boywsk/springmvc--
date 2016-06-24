/**
 * Created by wangshikai on 2016/5/10.
 */
public class HelloLog implements IHelloLog{
    @Override
    public void helloWorld1(String args) {
        System.out.println("helloWorld1"+args);
    }

    @Override
    public void helloWorld2(String args) {
        System.out.println("helloWorld2"+args);
    }
}
