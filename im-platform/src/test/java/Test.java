import com.alibaba.fastjson.JSON;
import com.gome.im.platform.global.Constant;
import com.gome.im.platform.utils.HttpUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by wangshikai on 2016/5/17.
 */
public class Test {
    public static void main(String[] args) {
//        List<Integer> parm = new ArrayList<>();
//        parm.add(Constant.SERVER_TYPE.GATEWAY.value);
//        parm.add(Constant.SERVER_TYPE.FILE.value);
//        String gatekeeperUrl = "http://10.69.211.31:8844/getServerRes";
//        String json = HttpUtil.httpRequest(gatekeeperUrl, JSON.toJSONString(parm));
//        System.out.println("json result:"+json);

        Random random = new Random();
        int index = random.nextInt(2);
        System.out.println(index);
    }
}
