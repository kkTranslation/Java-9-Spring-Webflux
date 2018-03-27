package dockerx.spring.boot.rxjava.asyncresult;

import io.reactivex.Observable;
import org.springframework.util.Assert;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.List;

/**
 * @author Author  知秋
 * @email fei6751803@163.com
 * @time Created by Auser on 2018/3/27 1:37.
 */
public class ObservableDeferredResult<T> extends DeferredResult<List<T>> {


    public ObservableDeferredResult(Observable<T> observable) {
        this(null, observable);
    }


    public ObservableDeferredResult(Long timeout, Observable<T> observable) {
        super(timeout);
        Assert.notNull(observable, "observable can not be null");
        observable.toList().toObservable().subscribe(new SingleDeferredResult.DeferredResultObserver<List<T>>(this));
    }
}
