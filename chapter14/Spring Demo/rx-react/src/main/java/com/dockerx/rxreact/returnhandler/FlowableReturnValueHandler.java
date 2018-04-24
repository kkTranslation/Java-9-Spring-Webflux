package com.dockerx.rxreact.returnhandler;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.context.request.async.WebAsyncUtils;
import org.springframework.web.method.support.AsyncHandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.List;

/**
 * @author Author  知秋
 * @email fei6751803@163.com
 * @time Created by Auser on 2018/4/23 18:59.
 */
public class FlowableReturnValueHandler implements AsyncHandlerMethodReturnValueHandler {
    @Override
    public boolean isAsyncReturnValue(Object returnValue, MethodParameter returnType) {
        return returnValue != null && supportsReturnType(returnType);
    }

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return Observable.class.isAssignableFrom(returnType.getParameterType());
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        if (returnValue == null) {
            mavContainer.setRequestHandled(true);
            return;
        }
        final Flowable<?> observable = Flowable.class.cast(returnValue);
        WebAsyncUtils.getAsyncManager(webRequest)
                     .startDeferredResultProcessing(new FlowableAdapter<>(observable), mavContainer);
    }

    public class FlowableAdapter<T> extends DeferredResult<List<T>> {
        FlowableAdapter(Flowable<T> flowable) {
            flowable.toList().subscribe(this::setResult, this::setErrorResult);
        }
    }
}
