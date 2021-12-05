package com.wenqi.example.chapter3;

import java.util.Collection;

/**
 * 线程封闭
 * @author liangwenqi
 * @date 2021/8/9
 */
public class ThreadConfinement {

    public void loadTheArk(Collection<String> candidates) {

    }

    public String getString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("相关操作");
        return stringBuilder.toString();
    }
}
