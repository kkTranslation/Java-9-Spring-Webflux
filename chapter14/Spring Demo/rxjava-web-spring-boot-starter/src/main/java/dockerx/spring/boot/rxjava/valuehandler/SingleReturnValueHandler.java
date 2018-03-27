package dockerx.spring.boot.rxjava.valuehandler;

import dockerx.spring.boot.rxjava.asyncresult.SingleDeferredResult;
import io.reactivex.Single;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.async.WebAsyncUtils;
import org.springframework.web.method.support.AsyncHandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 单值返回值(也就是单值源)处理
 * @author Author  知秋
 * @email fei6751803@163.com
 * @time Created by Auser on 2018/3/27 21:28.
 */
public class SingleReturnValueHandler  implements AsyncHandlerMethodReturnValueHandler {
    public boolean isAsyncReturnValue(Object returnValue, MethodParameter returnType) {
        return returnValue != null && supportsReturnType(returnType);
    }

    public boolean supportsReturnType(MethodParameter returnType) {
        return Single.class.isAssignableFrom(returnType.getParameterType());
    }

    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        if (returnValue == null) {
            mavContainer.setRequestHandled(true);
            return;
        }

        final Single<?> single = Single.class.cast(returnValue);
        WebAsyncUtils.getAsyncManager(webRequest)
                     .startDeferredResultProcessing(new SingleDeferredResult(single), mavContainer);
    }
}
