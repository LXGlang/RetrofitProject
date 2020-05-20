package com.lxg.work.retrofit.re.util;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;

/**
 * @ProjectName: SXPoliceCheckApp
 * @Package: com.dynalink.inspector.utils.rxutils
 * @ClassName: RxBus
 * @Description: 同步消息传输
 * @Author: lxg
 * @CreateDate: 2020/5/18 10:16
 */
public class RxBus {
    private static volatile RxBus defaultInstance;

    private final Subject<Object> bus;
    // PublishSubject只会把在订阅发生的时间点之后来自原始Observable的数据发射给观察者
    public RxBus() {
        bus =  PublishSubject.create().toSerialized();
    }
    // 单例RxBus
    public static RxBus getDefault() {
        if (defaultInstance == null) {
            synchronized (RxBus.class) {
                if (defaultInstance == null) {
                    defaultInstance = new RxBus();
                }
            }
        }
        return defaultInstance ;
    }

    /**
     *  发送一个新的事件
     * @param o
     */
    public void post (Object o) {
        bus.onNext(o);
    }

    /**
     * 接受结果
     * @param eventType
     * @param <T>
     * @return
     */
    public <T> Observable<T> toObservable (Class<T> eventType) {
        return bus.ofType(eventType);
    }
}
