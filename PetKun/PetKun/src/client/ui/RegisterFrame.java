package client.ui;

import client.util.ClientUtil;
import common.model.entity.Request;
import common.model.entity.Response;
import common.model.entity.ResponseStatus;
import common.model.entity.User;
import server.DataBuffer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

/**
 *  注册界面
 *
 * @author 苏云鹤
 */

public class RegisterFrame extends JFrame {
    private static final long serialVersionUID = -768631070458723803L;
    private JPasswordField pwdFld;
    private JPasswordField pwd2Fld;
    private JTextField nickname;
    private JComboBox head;
    private JRadioButton sex0;
    private JRadioButton sex1;
    private JButton ok;
    private JButton reset;
    private JButton cancel;

    public RegisterFrame(){
        this.init();
        setVisible(true);
    }

    public void init(){
        this.setTitle("注册JQ新账号");//设置标题
        setBounds((DataBuffer.screenSize.width - 800)/2,
                (DataBuffer.screenSize.height - 600)/2,
                800, 600);
        getContentPane().setLayout(null);
        setResizable(false);

        JLabel label = new JLabel("昵称:"); //label显示
        label.setBounds(100, 100, 100, 60);
        getContentPane().add(label);

        nickname = new JTextField(); //昵称
        nickname.setBounds(200, 100, 400, 60);
        getContentPane().add(nickname);

        JLabel label5 = new JLabel("密码:*");
        label5.setBounds(100, 180, 100, 60);
        getContentPane().add(label5);

        JLabel label3 = new JLabel("确认密码:*");
        label3.setBounds(50, 260, 150, 60);
        getContentPane().add(label3);

        pwdFld = new JPasswordField();//密码框
        pwdFld.setBounds(200, 180, 400, 60);
        getContentPane().add(pwdFld);

        pwd2Fld = new JPasswordField();
        pwd2Fld.setBounds(200, 260, 400, 60);
        getContentPane().add(pwd2Fld);

        JLabel label4 = new JLabel("性别:");
        label4.setBounds(100, 30, 100, 60);
        getContentPane().add(label4);

        sex1 = new JRadioButton("男",true);
        sex1.setBounds (250, 30,100, 60);
        getContentPane().add(sex1);
        sex0 = new JRadioButton("女");
        sex0.setBounds(400, 30, 100, 60);
        getContentPane().add(sex0);
        ButtonGroup buttonGroup = new ButtonGroup();//单选按钮组
        buttonGroup.add(sex0);
        buttonGroup.add(sex1);

        JLabel label6 = new JLabel("头像:");
        label6.setBounds(100, 340, 100, 60);
        getContentPane().add(label6);

        head = new JComboBox();//下拉列表图标
        head.setBounds(200, 340, 100, 60);
        head.setMaximumRowCount(5);
        for (int i = 0; i < 11; i++) {
            head.addItem(new ImageIcon("images/" + i + ".png"));
            //通过循环添加图片，注意图片名字要取成1,2,3,4,5,等
        }
        head.setSelectedIndex(0);
        getContentPane().add(head);

        //按钮
        ok = new JButton("确认");
        ok.setBounds(150, 420, 100, 60);
        getContentPane().add(ok);

        reset = new JButton("重填");
        reset.setBounds(350, 420, 100, 60);
        getContentPane().add(reset);

        cancel = new JButton("取消");
        cancel.setBounds(550, 420, 100, 60);
        getContentPane().add(cancel);

        //////////////////////注册事件监听器////////////////////////
        //取消按钮监听事件处理
        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent event) {
                RegisterFrame.this.dispose();
            }
        });
        //关闭窗口
        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e) {
                RegisterFrame.this.dispose();
            }
        });

        // 重置按钮监听事件处理
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                nickname.setText("");
                pwdFld.setText("");
                pwd2Fld.setText("");
                nickname.requestFocusInWindow();//用户名获得焦点
            }
        });

        //确认按钮监听事件处理
        ok.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                if (pwdFld.getPassword().length==0 || pwd2Fld.getPassword().length==0) {
                    JOptionPane.showMessageDialog(RegisterFrame.this, "带 “ * ” 为必填内容!");
                    //判断用户名和密码是否为空
                } else if (!new String(pwdFld.getPassword()).equals(new String(pwd2Fld.getPassword()))) {
                    JOptionPane.showMessageDialog(RegisterFrame.this, "两次输入密码不一致!");
                    pwdFld.setText("");
                    pwd2Fld.setText("");
                    pwdFld.requestFocusInWindow();
                    //判断两次密码是否一致
                } else {
                    User user = new User(new String(pwdFld.getPassword()),
                            nickname.getText(),
                            sex0.isSelected() ? 'm' : 'f',
                            head.getSelectedIndex());
                    try {
                        RegisterFrame.this.register(user);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } catch (ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }
    //注册方法
    private void register(User user) throws IOException, ClassNotFoundException{
        Request request = new Request();
        request.setAction("userRegister");
        request.setAttribute("user", user);

        //获取响应
        Response response = ClientUtil.sendTextRequest(request);

        ResponseStatus status = response.getStatus();
        switch(status){
            case OK:
                User user2 = (User)response.getData("user");
                JOptionPane.showMessageDialog(RegisterFrame.this,
                        "恭喜您，您的Q号:"+ user2.getId() + ",请牢记!!!",
                        "注册成功",JOptionPane.INFORMATION_MESSAGE);
                this.setVisible(false);
                break;
            default:
                JOptionPane.showMessageDialog(RegisterFrame.this,
                        "注册失败，请稍后再试！！！","服务器内部错误！",JOptionPane.ERROR_MESSAGE);
        }
    }
}
