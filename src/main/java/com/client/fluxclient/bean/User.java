package com.client.fluxclient.bean;

import lombok.Builder;
import lombok.Data;

/**
 * @ClassName User
 * @Description TODO
 * @Author gaogf
 * @Date 2018/5/17 17:32
 * @Version 1.0
 */
@Data
@Builder
public class User {
    private String id;
    private String name;
    private int age;
}
