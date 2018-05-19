package com.client.fluxclient;

import com.client.fluxclient.api.IUserApi;
import com.client.fluxclient.iface.ProxyCreator;
import com.client.fluxclient.proxys.JDKProxyCreator;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FluxclientApplication {

    public static void main(String[] args) {
        SpringApplication.run(FluxclientApplication.class, args);
    }

    @Bean
    public ProxyCreator jdkProxyCreator(){
        return new JDKProxyCreator();
    }


    @Bean
    public FactoryBean<IUserApi> userApiFactoryBean(ProxyCreator creator){
        return new FactoryBean<IUserApi>() {
            /**
            *@ClassName FluxclientApplication
            *@Description 返回代理对象
            *@Author gaogf
            *@Date 2018/5/19 16:43
            *@Version 1.0
            */
            @Override
            public IUserApi getObject() throws Exception {
                return (IUserApi) creator.createProxy(this.getObjectType());
            }

            @Override
            public Class<?> getObjectType() {
                return IUserApi.class;
            }
        };
    }

}