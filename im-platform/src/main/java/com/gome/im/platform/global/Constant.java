package com.gome.im.platform.global;

/**
 * Created by wangshikai on 2016/3/30.
 */
public class Constant {

    /**
     * 服务器类型
     */
    public enum SERVER_TYPE {
        ALL(0), // 所有
        GATEWAY(1), // 接入
        LOGIC(2), // 逻辑
        API(3), // api
        FILE(4); // 文件
        public int value;
        private SERVER_TYPE(int value) {
            this.value = value;
        }
    }

}
