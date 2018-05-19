package com.client.fluxclient.proxys;

import com.client.fluxclient.annotation.ApiServer;
import com.client.fluxclient.bean.MethordInfo;
import com.client.fluxclient.bean.ServerInfo;
import com.client.fluxclient.handlers.ClientRestHandler;
import com.client.fluxclient.iface.ProxyCreator;
import com.client.fluxclient.iface.RestHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @ClassName JDKProxyCreator
 * @Description 使用jdk动态代理实现代理类
 * @Author gaogf
 * @Date 2018/5/19 16:50
 * @Version 1.0
 */
@Slf4j
public class JDKProxyCreator implements ProxyCreator {
    @Override
    public Object createProxy(Class<?> type) {
        log.info("111" + type);
        //根据接口得到API服务器信息
        ServerInfo serverInfo = extractServerInfo(type);

        //给每一个代理一个实现
        RestHandler handler = new ClientRestHandler();

        //初始化服务器信息
        handler.init(serverInfo);

        return Proxy.newProxyInstance(this.getClass().getClassLoader(),
                new Class[]{type}, new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        //根据方法和参数得到调用信息
                        MethordInfo methordInfo = extractMethodInfo(method,args);

                        //调用rest
                        return handler.invokeRest(methordInfo);
                    }
                });
    }

    private MethordInfo extractMethodInfo(Method method, Object[] args) {

        MethordInfo methodInfo = new MethordInfo();
        extractUrlAndMethod(method, methodInfo);
        extractRequestParamAndBody(method, args, methodInfo);
        extractReturnInfo(method,methodInfo);
        return methodInfo;
    }

    private void extractReturnInfo(Method method, MethordInfo methodInfo) {
        //返回flux还是mono
        boolean isFlux = method.getReturnType().isAssignableFrom(Flux.class);
        methodInfo.setReturnFlux(isFlux);

        Class<?> elementType = extractElementType(method.getGenericReturnType());
        methodInfo.setReturnType(elementType);
    }

    private Class<?> extractElementType(Type genericReturnType) {

        Type[] types = ((ParameterizedType) genericReturnType).getActualTypeArguments();
        return (Class<?>) types[0];
    }

    private void extractRequestParamAndBody(Method method, Object[] args, MethordInfo methodInfo) {
        //得到调用参数和body
        Parameter[] parameters = method.getParameters();
        Map<String, Object> params = new LinkedHashMap<>();
        methodInfo.setParams(params);
        for (int i=0; i<parameters.length;i++){
            //是否带有PathVariable
            PathVariable variable = parameters[i].getAnnotation(PathVariable.class);
            if (variable != null){
                params.put(variable.value(),args[i]);
            }
            //是否带了RequestBody
            RequestBody body = parameters[i].getAnnotation(RequestBody.class);
            if (body != null){
                methodInfo.setBody((Mono<?>) args[i]);
                methodInfo.setBodyElementType(extractElementType(parameters[i].getParameterizedType()));
            }
        }
    }

    private void extractUrlAndMethod(Method method, MethordInfo methodInfo) {
        Annotation[] annotations = method.getAnnotations();
        for (Annotation annotation: annotations){
            //GET
            if (annotation instanceof GetMapping){
                GetMapping a = (GetMapping) annotation;
                methodInfo.setUrl(a.value()[0]);
                methodInfo.setMethod(HttpMethod.GET);
            }else if (annotation instanceof PostMapping){
                PostMapping a = (PostMapping) annotation;
                methodInfo.setUrl(a.value()[0]);
                methodInfo.setMethod(HttpMethod.POST);
            } else if (annotation instanceof DeleteMapping){
                DeleteMapping a = (DeleteMapping) annotation;
                methodInfo.setUrl(a.value()[0]);
                methodInfo.setMethod(HttpMethod.DELETE);
            }else if (annotation instanceof PutMapping){
                PutMapping a = (PutMapping) annotation;
                methodInfo.setUrl(a.value()[0]);
                methodInfo.setMethod(HttpMethod.PUT);
            }
        }
    }

    private ServerInfo extractServerInfo(Class<?> type) {

        ServerInfo serverInfo = new ServerInfo();
        ApiServer annotation = type.getAnnotation(ApiServer.class);
        serverInfo.setUrl(annotation.value());
        return serverInfo;
    }
}
