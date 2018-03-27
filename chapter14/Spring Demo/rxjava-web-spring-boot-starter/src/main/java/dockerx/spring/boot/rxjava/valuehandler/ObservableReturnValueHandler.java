package dockerx.spring.boot.rxjava.valuehandler;

import dockerx.spring.boot.rxjava.asyncresult.ObservableDeferredResult;
import io.reactivex.Observable;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.async.WebAsyncUtils;
import org.springframework.web.method.support.AsyncHandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @author Author  知秋
 * @email fei6751803@163.com
 * @time Created by Auser on 2018/3/27 21:26.
 */
public class ObservableReturnValueHandler implements AsyncHandlerMethodReturnValueHandler {
    public boolean isAsyncReturnValue(Object returnValue, MethodParameter returnType) {
        return returnValue != null && supportsReturnType(returnType);
    }

    public boolean supportsReturnType(MethodParameter returnType) {
        return Observable.class.isAssignableFrom(returnType.getParameterType());
    }

    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        if (returnValue == null) {
            mavContainer.setRequestHandled(true);
            return;
        }

        final Observable<?> observable = Observable.class.cast(returnValue);
        WebAsyncUtils.getAsyncManager(webRequest)
                     .startDeferredResultProcessing(new ObservableDeferredResult(observable), mavContainer);
    }

}
