package server;

import common.model.entity.User;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 *  服务器端数据缓存
 *
 * @author 张楷睿 苏云鹤
 */

public class DataBuffer {

    /** 服务器端套接字 */
    public static ServerSocket serverSocket;

    /** 在线用户的IO Map*/
    public static Map<Long, OnlineClientIOCache> onlineUserIOCacheMap;

    /** 在线用户Map*/
    public static Map<Long, User> onlineUsersMap;

    /**服务器配置参数属性集*/
    public static Properties configProp;

    static{
        // 初始化
        onlineUserIOCacheMap = new ConcurrentSkipListMap<Long,OnlineClientIOCache>();
        onlineUsersMap = new ConcurrentSkipListMap<Long, User>();
        configProp = new Properties();

        // 加载服务器配置文件
        try {
            configProp.load(Thread.currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream("serverconfig.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
