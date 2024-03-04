package client;

import client.ui.LoginFrame;

import javax.swing.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * 客户端入口
 *
 * @author 苏云鹤
 */

public class ClientMain {

    /**
     * 客户端程序入口
     */
    public static void main() {
        //连接到服务器
        if(!connection()){
            return;
        }

        //TODO：以下几行是设置外观
        JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);
        try {
            //UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            String lookAndFeel = UIManager.getSystemLookAndFeelClassName();
            UIManager.setLookAndFeel(lookAndFeel);
//            UIManager.setLookAndFeel(new SubstanceBusinessLookAndFeel());

        } catch (Exception e) {
            e.printStackTrace();
        }

        new LoginFrame();  //启动登录窗体
    }

    /**
     * 连接到服务器
     * @return boolean
     */
    public static boolean connection() {
        String ip = DataBuffer.configProp.getProperty("ip"); // 获取服务器配置参数属性集中的ip
        int port = Integer.parseInt(DataBuffer.configProp.getProperty("port")); //获取服务器配置参数属性集中的port
        try {
            DataBuffer.clientSeocket = new Socket(ip, port); //创建socket，和服务器连接
            DataBuffer.oos = new ObjectOutputStream(DataBuffer.clientSeocket.getOutputStream());
            DataBuffer.ois = new ObjectInputStream(DataBuffer.clientSeocket.getInputStream());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(new JFrame(),
                    "连接服务器失败,请检查!","服务器未连上", JOptionPane.ERROR_MESSAGE);//否则连接失败
            return false;
        }
        return true;
    }
}
