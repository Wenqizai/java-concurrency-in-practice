package com.wenqi.example.chapter3;

import org.w3c.dom.events.EventListener;

import java.awt.*;

/**
 * 逸出对象导致的线程不安全
 * @author liangwenqi
 * @date 2021/7/10
 */
public class EscapeObject {

    //class ThisEscape {
    //    public ThisEscape (EventSource source) {
    //        source.registerListener(
    //            new EventListener() {
    //                public void onEvent(Event event) {
    //                    doSomething(event);
    //                }
    //            }
    //        );
    //    }
    //}
    //
    //static class SafeListener {
    //
    //    private final EventListener eventListener;
    //
    //    private SafeListener() {
    //        this.eventListener = new EventListener() {
    //            public void onEvent(Event event) {
    //                doSomething(event);
    //            }
    //        };
    //    }
    //
    //    public static SafeListener newInstance(EventSource source) {
    //        SafeListener safe = new SafeListener();
    //        //source.registerListener(safe.eventListener);
    //        return safe;
    //    }
    //}
}
