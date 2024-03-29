package client.ui;

import client.DataBuffer;
import client.util.ClientUtil;
import common.model.entity.Request;
import common.model.entity.Response;
import common.model.entity.ResponseStatus;
import common.model.entity.User;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.List;

/**
 *  登录界面
 *
 * @author 苏云鹤
 */

public class LoginFrame extends JFrame {
    private static final long serialVersionUID = -3426717670093483287L;

    private JTextField idTxt;
    private JPasswordField pwdFld;

    public LoginFrame(){
        this.init();
        setVisible(true);
    }

    public void init(){
        this.setTitle("PetKun登录");
//        this.setSize(330, 230);
//        this.setSize(660, 460);
        this.setSize(800, 600);
//        this.setResizable(true);
        //设置默认窗体在屏幕中央
        int x = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        int y = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((x - this.getWidth()) / 2, (y-this.getHeight())/ 2);
        this.setResizable(true);

        //把Logo放置到JFrame的北边
        Icon icon = new ImageIcon("images/logo.png");
        JLabel label = new JLabel(icon);
        label.setPreferredSize(new Dimension(324,47));
        this.add(label, BorderLayout.NORTH);

        //登录信息面板
        JPanel mainPanel = new JPanel();
        Border border = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        mainPanel.setBorder(BorderFactory.createTitledBorder(border, "输入登录信息", TitledBorder.CENTER,TitledBorder.TOP));
        this.add(mainPanel, BorderLayout.CENTER);
        mainPanel.setLayout(null);

        JLabel nameLbl = new JLabel("账号:");
        nameLbl.setBounds(100, 70, 200, 100);
        nameLbl.setFont(new Font("MS Mincho",Font.PLAIN, 40));
        mainPanel.add(nameLbl);
        idTxt = new JTextField();
        idTxt.setBounds(250, 70, 400, 100);
        idTxt.requestFocusInWindow();//用户名获得焦点
        idTxt.setFont(new Font("MS Mincho",Font.PLAIN, 40));
        mainPanel.add(idTxt,BorderLayout.CENTER);

        JLabel pwdLbl = new JLabel("密码:");
        pwdLbl.setBounds(100, 230, 200, 100);
        pwdLbl.setFont(new Font("MS Mincho",Font.PLAIN, 40));
        mainPanel.add(pwdLbl);
        pwdFld = new JPasswordField();
        pwdFld.setBounds(250, 230, 400, 100);
        pwdFld.setFont(new Font("MS Mincho",Font.PLAIN, 40));
        mainPanel.add(pwdFld,BorderLayout.CENTER);

        //按钮面板放置在JFrame的南边
        JPanel btnPanel = new JPanel();
        this.add(btnPanel, BorderLayout.SOUTH);
        btnPanel.setLayout(new BorderLayout());
        btnPanel.setBorder(new EmptyBorder(2, 8, 4, 8));

        JButton registerBtn = new JButton("注册");
        btnPanel.add(registerBtn, BorderLayout.WEST);
        JButton submitBtn = new JButton("登录");
        btnPanel.add(submitBtn, BorderLayout.EAST);
        this.setPreferredSize(new Dimension(800,600));
        this.pack();

        //关闭窗口
        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e) {
                Request req = new Request();
                req.setAction("exit");
                try {
                    ClientUtil.sendTextRequest(req);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }finally{
//                    System.exit(0);
                }
            }
        });

        //注册
        registerBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                new RegisterFrame();  //打开注册窗体
            }
        });

        //"登录"
        submitBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
    }

    /** 登录 */
    @SuppressWarnings("unchecked")
    private void login() {
        if (idTxt.getText().length() == 0
                || pwdFld.getPassword().length == 0) {
            JOptionPane.showMessageDialog(LoginFrame.this,
                    "账号和密码是必填的",
                    "输入有误",JOptionPane.ERROR_MESSAGE);
            idTxt.requestFocusInWindow();
            return;
        }

        if(!idTxt.getText().matches("^\\d*$")){
            JOptionPane.showMessageDialog(LoginFrame.this,
                    "账号必须是数字",
                    "输入有误",JOptionPane.ERROR_MESSAGE);
            idTxt.requestFocusInWindow();
            return;
        }

        Request req = new Request();
        req.setAction("userLogin");
        req.setAttribute("id", idTxt.getText());
        req.setAttribute("password", new String(pwdFld.getPassword()));

        //获取响应
        Response response = null;
        try {
            response = ClientUtil.sendTextRequest(req);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        if(response.getStatus() == ResponseStatus.OK){
            //获取当前用户
            User user2 = (User)response.getData("user");
            if(user2!= null){ //登录成功
                DataBuffer.currentUser = user2;
                //获取当前在线用户列表
                DataBuffer.onlineUsers = (List<User>)response.getData("onlineUsers");

                LoginFrame.this.dispose();
                new ChatFrame();  //打开聊天窗体
            }else{ //登录失败
                String str = (String)response.getData("msg");
                JOptionPane.showMessageDialog(LoginFrame.this,
                        str,
                        "登录失败",JOptionPane.ERROR_MESSAGE);
            }
        }else{
            JOptionPane.showMessageDialog(LoginFrame.this,
                    "服务器内部错误，请稍后再试！！！","登录失败",JOptionPane.ERROR_MESSAGE);
        }
    }
}
