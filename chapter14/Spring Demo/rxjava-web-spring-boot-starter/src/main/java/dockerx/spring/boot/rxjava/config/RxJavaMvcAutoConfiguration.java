package dockerx.spring.boot.rxjava.config;

import dockerx.spring.boot.rxjava.valuehandler.ObservableReturnValueHandler;
import dockerx.spring.boot.rxjava.valuehandler.SingleReturnValueHandler;
import io.reactivex.Observable;
import io.reactivex.Single;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.AsyncHandlerMethodReturnValueHandler;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;


import java.util.ArrayList;
import java.util.List;

/**
 * @author Author  知秋
 * @email fei6751803@163.com
 * @time Created by Auser on 2018/3/27 21:33.
 */
@Configuration
@ConditionalOnProperty(value = "rxjava.mvc.enabled", matchIfMissing = true)
public class RxJavaMvcAutoConfiguration {
    @Bean
    @RxMVC
    @ConditionalOnMissingBean
    @ConditionalOnClass(Observable.class)
    public ObservableReturnValueHandler observableReturnValueHandler() {
        return new ObservableReturnValueHandler();
    }

    @Bean
    @RxMVC
    @ConditionalOnMissingBean
    @ConditionalOnClass(Single.class)
    public SingleReturnValueHandler singleReturnValueHandler() {
        return new SingleReturnValueHandler();
    }

    @Configuration
    public static class RxJavaWebConfiguration {

        @RxMVC
        @Autowired
        private List<AsyncHandlerMethodReturnValueHandler> handlers = new ArrayList<>();

        @Bean
        public WebMvcConfigurationSupport rxJavaWebMvcConfiguration() {
            return new WebMvcConfigurationSupport(){
                @Override
                protected void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
                    super.addReturnValueHandlers(returnValueHandlers);
                    returnValueHandlers.addAll(handlers);
                }
            };
        }
    }
}
