package com.client.fluxclient.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName ServerInfo
 * @Description TODO
 * @Author gaogf
 * @Date 2018/5/19 16:52
 * @Version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServerInfo {
    //服务器URL
    private String url;
}
