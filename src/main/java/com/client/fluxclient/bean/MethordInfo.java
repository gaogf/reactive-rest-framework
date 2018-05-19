package com.client.fluxclient.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpMethod;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * @ClassName MethordInfo
 * @Description TODO
 * @Author gaogf
 * @Date 2018/5/19 16:57
 * @Version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MethordInfo {
    private String url;
    private HttpMethod method;
    private Map<String,Object> params;
    private Mono body;

    private Class<?> bodyElementType;

    //返回时flux还是mono
    private boolean returnFlux;
    //返回对象的类型
    private Class<?> returnType;
}
