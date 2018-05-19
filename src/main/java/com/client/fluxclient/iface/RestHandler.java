package com.client.fluxclient.iface;

import com.client.fluxclient.bean.MethordInfo;
import com.client.fluxclient.bean.ServerInfo;

/**
 * @ClassName RestHandler
 * @Description TODO
 * @Author gaogf
 * @Date 2018/5/19 17:02
 * @Version 1.0
 */
public interface RestHandler {
    /**
    *@ClassName RestHandler
    *@Description 初始化服务器信息
    *@Author gaogf
    *@Date 2018/5/19 17:06
    *@Version 1.0
    */
    void init(ServerInfo serverInfo);

    /***
    *@ClassName RestHandler
    *@Description 调用rest请求，返回接口
    *@Author gaogf
    *@Date 2018/5/19 17:06
    *@Version 1.0
    */
    Object invokeRest(MethordInfo methordInfo);
}
