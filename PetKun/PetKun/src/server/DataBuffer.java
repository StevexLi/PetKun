package server;

import common.model.entity.User;
import server.model.entity.OnlineUserTableModel;
import server.model.entity.RegisterdUserTableModel;

import java.awt.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 *  服务器端数据缓存
 *
 * @author 苏云鹤 张楷睿
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

    /**已注册用户表的Model*/
    public static RegisterdUserTableModel registerdUserTableModel;

    /**当前在线用户表的Model*/
    public static OnlineUserTableModel onlineUserTableModel;

    /**当前服务器所在系统的屏幕尺寸*/
    public static Dimension screenSize;

    static{
        // 初始化
        onlineUserIOCacheMap = new ConcurrentSkipListMap<Long,OnlineClientIOCache>();
        onlineUsersMap = new ConcurrentSkipListMap<Long, User>();
        configProp = new Properties();
        registerdUserTableModel = new RegisterdUserTableModel();
        onlineUserTableModel = new OnlineUserTableModel();
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();

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
