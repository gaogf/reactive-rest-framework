package com.client.fluxclient.handlers;

import com.client.fluxclient.bean.MethordInfo;
import com.client.fluxclient.bean.ServerInfo;
import com.client.fluxclient.iface.RestHandler;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @ClassName ClientRestHandler
 * @Description TODO
 * @Author gaogf
 * @Date 2018/5/19 17:32
 * @Version 1.0
 */
public class ClientRestHandler implements RestHandler {
    private WebClient client;

    @Override
    public void init(ServerInfo serverInfo) {
        this.client = WebClient.create(serverInfo.getUrl());
    }

    /**
    *@ClassName ClientRestHandler
    *@Description 处理rest请求
    *@Author gaogf
    *@Date 2018/5/19 17:40
    *@Version 1.0
    */
    @Override
    public Object invokeRest(MethordInfo methordInfo) {
        Object result = null;

        WebClient.RequestBodySpec request = this.client
                //请求的方法
                .method(methordInfo.getMethod())
                //请求的url
                .uri(methordInfo.getUrl())
                .accept(MediaType.APPLICATION_JSON_UTF8);
        WebClient.ResponseSpec retrieve = null;
        //判断是否带了body
        if (methordInfo.getBody() != null){
            //发出请求
            retrieve = request.body(methordInfo.getBody(),methordInfo.getBodyElementType()).retrieve();
        }else {
            retrieve = request.retrieve();
        }
        if (methordInfo.isReturnFlux()){
            result = retrieve.bodyToFlux(methordInfo.getReturnType());
        }else {
            result = retrieve.bodyToMono(methordInfo.getReturnType());
        }
        return result;
    }
}
