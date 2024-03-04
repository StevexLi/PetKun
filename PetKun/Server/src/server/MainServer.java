package server;

import server.controller.RequestProcessor;
import server.model.service.UserService;

import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *  主服务
 * @author 张楷睿 苏云鹤
 */

public class MainServer {
    public static void main(String[] args) {
        int port = Integer.parseInt(DataBuffer.configProp.getProperty("port"));
        //初始化服务器套节字
        try {
            DataBuffer.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        new Thread(new Runnable() {//启动新线程进行客户端连接监听
            public void run() {
                try {
                    while (true) {
                        // 监听客户端的连接
                        Socket socket = DataBuffer.serverSocket.accept();
                        System.out.println("客户来了："
                                + socket.getInetAddress().getHostAddress()
                                + ":" + socket.getPort());

                        //针对每个客户端启动一个线程，在线程中调用请求处理器来处理每个客户端的请求
                        new Thread(new RequestProcessor(socket)).start();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new UserService().initUser();
    }
}
