package dockerx.spring.boot.rxjava.asyncresult;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

/**
 * @author Author  知秋
 * @email fei6751803@163.com
 * @time Created by Auser on 2018/3/27 2:21.
 */
public class ObservableSseEmitter<T> extends SseEmitter {

    public ObservableSseEmitter(Observable<T> observable) {
        this(null, observable);
    }

    public ObservableSseEmitter(MediaType mediaType, Observable<T> observable) {
        this(null, mediaType, observable);
    }

    public ObservableSseEmitter(Long timeout, MediaType mediaType, Observable<T> observable) {
        super(timeout);
        observable.subscribe(new ResponseBodyEmitterObserver<>(mediaType, this));
    }

    static final class ResponseBodyEmitterObserver<T> extends DisposableObserver<T> implements Runnable {

        private final MediaType mediaType;

        private final ResponseBodyEmitter responseBodyEmitter;

        private boolean completed;

        public ResponseBodyEmitterObserver(MediaType mediaType, ResponseBodyEmitter responseBodyEmitter) {

            this.mediaType = mediaType;
            this.responseBodyEmitter = responseBodyEmitter;
            this.responseBodyEmitter.onTimeout(this);
            this.responseBodyEmitter.onCompletion(this);
        }

        public void onNext(T value) {

            try {
                if(!completed) {
                    responseBodyEmitter.send(value, mediaType);
                }
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }

        public void onError(Throwable e) {
            responseBodyEmitter.completeWithError(e);
        }

        public void onComplete() {
            if(!completed) {
                completed = true;
                responseBodyEmitter.complete();
            }
        }

        public void run() {
            this.dispose();
        }
    }

}
