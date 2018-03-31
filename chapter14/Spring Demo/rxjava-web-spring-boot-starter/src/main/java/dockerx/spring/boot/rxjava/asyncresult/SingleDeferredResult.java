package dockerx.spring.boot.rxjava.asyncresult;

import io.reactivex.Single;
import io.reactivex.observers.DisposableObserver;
import org.springframework.util.Assert;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * @author Author  知秋
 * @email fei6751803@163.com
 * @time Created by Auser on 2018/3/27 1:03.
 */
public class SingleDeferredResult<T> extends DeferredResult<T> {


    public SingleDeferredResult(Single<T> single) {
        this(null,  single);
    }


    public SingleDeferredResult(Long timeout, Single<T> single) {
        super(timeout);
        Assert.notNull(single, "single can not be null");
        single.toObservable().subscribe(new DeferredResultObserver<>(this));
    }

    static final class DeferredResultObserver<T> extends DisposableObserver<T> implements Runnable {

        private final DeferredResult<T> deferredResult;

        public DeferredResultObserver( DeferredResult<T> deferredResult) {
            this.deferredResult = deferredResult;
            this.deferredResult.onTimeout(this);
            this.deferredResult.onCompletion(this);
        }


        public void onNext(T t) {
            deferredResult.setResult(t);
        }

        public void onError(Throwable throwable) {
            deferredResult.setErrorResult(throwable);
        }

        public void onComplete() {

        }

        public void run() {
            this.dispose();
        }
    }
}
