import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by wangshikai on 2016/5/10.
 */
public class LoggerOperation implements IOperation {
    private static Logger logger = LoggerFactory.getLogger(LoggerOperation.class);

    @Override
    public void start(String args) {
        logger.info("method start!args:{}", args);
    }

    @Override
    public void end(String args) {
        logger.info("method end!args:{}", args);
    }

    public static void main(String[] args) {
        Model m = new Model();
        List<String> list = new ArrayList<>();
        list.add("123");
        list.add("456");
        list.add("789");
        m.setList(list);
        System.out.println(m.getList().toString());
        Collections.shuffle(m.getList());
        System.out.println(m.getList().toString());

    }
}
