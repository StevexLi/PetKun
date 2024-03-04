package PetKunMain;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Random;

/**
 * PetKun的主界面
 *
 * @author 张志韩 李天桓
 * @date 2022/12/26
 */
public class Main extends Application {
    /**
     * 鼠标横坐标
     */
    double x1, /**
     * 鼠标纵坐标
     */
    y1;
    /**
     * 坤宠横坐标
     */
    double x_stage, /**
     * 坤宠纵坐标
     */
    y_stage;
    /**
     * 坤宠走动定时器
     */
    private AnimationTimer timer;
    /**
     * 坤宠未操作时间间隔
     */
    private Integer i=0;
    /**
     * DDL日历舞台
     */
    static Stage calendar_stage = new Stage();
    /**
     * 番茄钟舞台
     */
    static Stage tomato_stage = new Stage();

    /**
     * 程序的入口
     *
     * @param args 命令行传入的字符串(本程序无需)
     */
    public static void main(String[] args) {
        Application.launch(args);
    }

    /**
     * DDL日历实例
     */
    localtools.PetKunCalendar calendar =new localtools.PetKunCalendar();

    /**
     * 番茄钟实例
     */
    localtools.TomatoClock clock=new localtools.TomatoClock();
    /**
     * 屏幕大小
     */
    Rectangle2D screenRectangle = Screen.getPrimary().getBounds();
    /**
     * 屏幕宽
     */
    double swidth = screenRectangle.getWidth();
    /**
     * 屏幕高
     */
    double sheight = screenRectangle.getHeight();
    /**
     * 坤宠窗口宽度
     */
    int width=250, /**
     * 坤宠窗口高度
     */
    height=150;
    /**
     * 坤宠宽度
     */
    int kun_width=100, /**
     * 坤宠高度
     */
    kun_height=130;
    /**
     * 菜单是否打开的指示变量
     */
    boolean Option_open=false;
    /**
     * 坤宠上下移动的指示变量
     */
    boolean updown, /**
     * 坤宠左右移动的指示变量
     */
    leftright, /**
     * 强制向上
     */
    mustup=false, /**
     * 强制向下
     */
    mustdown=false, /**
     * 强制向左
     */
    mustleft=false, /**
     * 强制向右
     */
    mustright=false;
    /**
     * 坤宠上下迈步的指示变量
     */
    Integer step_updown, /**
     * 坤宠左右迈步的指示变量
     */
    step_leftright;

    /**
     * 开始
     *
     * @param stage 坤宠的初始舞台
     * @throws Exception 异常
     */
    @Override
    public void start(Stage stage) throws Exception {
        stage.setAlwaysOnTop(true);
//        System.out.println(sheight);
//        System.out.println(swidth);
        Pane pane=new Pane();
        pane.setPadding(new Insets(5,5,5,5));
        Button b1=new Button("日历");
        Button b2=new Button("番茄钟");
        Button b3=new Button("聊天室");
        b1.setLayoutX(kun_width+10);
        b1.setLayoutY(0);
        b1.setPrefSize(50,30);
        b1.setFont(Font.font("sans-serif",12));
        b2.setLayoutX(kun_width+20);
        b2.setLayoutY(50);
        b2.setPrefSize(60,30);
        b2.setFont(Font.font("sans-serif",12));
        b3.setLayoutX(kun_width+10);
        b3.setLayoutY(100);
        b3.setPrefSize(60,30);
        b3.setFont(Font.font("sans-serif",12));
        b1.setStyle(
                "-fx-background-color:#8E4215;"+         //设置背景颜色
                        "-fx-background-radius:20;"+     //设置背景圆角
                        "-fx-text-fill:#ffffff;"+        //设置字体颜色
                        "-fx-border-radius:20;"+         //设置边框圆角
                        "-fx-border-color:#321503;"+     //设置边框颜色
                        //设置边框样式
                        "-fx-border-width:3;"+           //设置边框宽度
                        "-fx-border-insets:-1"           //设置边框插入值
        );
        b2.setStyle(
                "-fx-background-color:#8E4215;"+         //设置背景颜色
                        "-fx-background-radius:20;"+     //设置背景圆角
                        "-fx-text-fill:#FFFFFF;"+        //设置字体颜色
                        "-fx-border-radius:20;"+         //设置边框圆角
                        "-fx-border-color:#321503;"+     //设置边框颜色
                        //设置边框样式
                        "-fx-border-width:3;"+           //设置边框宽度
                        "-fx-border-insets:-1"           //设置边框插入值
        );
        b3.setStyle(
                "-fx-background-color:#8E4215;"+         //设置背景颜色
                        "-fx-background-radius:20;"+     //设置背景圆角
                        "-fx-text-fill:#FFFFFF;"+        //设置字体颜色
                        "-fx-border-radius:20;"+         //设置边框圆角
                        "-fx-border-color:#321503;"+     //设置边框颜色
                        //设置边框样式
                        "-fx-border-width:3;"+           //设置边框宽度
                        "-fx-border-insets:-1"           //设置边框插入值
        );
        Scene scene = new Scene(pane,width,height);

        Image petkun_img= new Image("file:images/KunPet1.png");
        Image petkun_img1=new Image("file:images/KunPet.png");
        Image petkunwalk_img1=new Image("file:images/KunPetWalk1.png");
        Image petkunwalk_img2=new Image("file:images/KunPetWalk2.png");
        Image petkunwalk_img3=new Image("file:images/KunPetWalk3.png");
        Image petkunwalk_img4=new Image("file:images/KunPetWalk2.png");
        Image petkunwalk_img5=new Image("file:images/KunPetWalk5.png");
        Image petkunwalk_img6=new Image("file:images/KunPetWalk6.png");
        Image petkunwalk_img7=new Image("file:images/KunPetWalk7.png");
        Image petkunwalk_img8=new Image("file:images/KunPetWalk8.png");
        Image[] petkunwalkright=new Image[4];
        petkunwalkright[0]=petkunwalk_img1;
        petkunwalkright[1]=petkunwalk_img2;
        petkunwalkright[2]=petkunwalk_img3;
        petkunwalkright[3]=petkunwalk_img4;

        Image[] petkunwalkleft=new Image[4];
        petkunwalkleft[0]=petkunwalk_img5;
        petkunwalkleft[1]=petkunwalk_img6;
        petkunwalkleft[2]=petkunwalk_img7;
        petkunwalkleft[3]=petkunwalk_img8;

        ImageView iv1=new ImageView(petkun_img);
        iv1.setY(20);
        iv1.setFitHeight(kun_height);
        iv1.setFitWidth(kun_width);
        Integer timegap=300;//走动的时间周期，修改会相应改变kunkun一次走动的路程
        Random r=new Random();
        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if(i%timegap==0){
                    updown=r.nextBoolean();
                    leftright=r.nextBoolean();
                    updown=mustup?false:updown;
                    updown=mustdown?true:updown;
                    leftright=mustleft?false:leftright;
                    leftright=mustright?true:leftright;
                    step_updown=updown?1:-1;
                    step_leftright=leftright?1:-1;
                    mustright=x_stage>5?false:true;
                    mustleft=x_stage<swidth-kun_width-5?false:true;
                    mustdown=y_stage>-15?false:true;
                    mustup=y_stage<sheight-height-25?false:true;
                    x_stage=stage.getX();
                    y_stage=stage.getY();
                }
                if(i/timegap%2==1) {
                    pane.getChildren().removeAll(b1,b2,b3);
                    iv1.setImage(petkun_img);
                    Option_open=false;
                    if (i % 1 == 0) {
                        x_stage = x_stage + step_leftright;
                        y_stage = y_stage + step_updown;
                    }
                    if(x_stage>0&&x_stage<swidth-kun_width&&y_stage>-20&&y_stage<sheight-height-20) {
                        stage.setX(x_stage);
                        stage.setY(y_stage);
                        if(!leftright){
                            iv1.setImage(petkunwalkleft[i / 10 % 4]);
                        }else{
                            iv1.setImage(petkunwalkright[i / 10 % 4]);
                        }
                    }else{
                        if(x_stage<=0){
                            mustright=true;
                            stage.setX(0);

                        }
                        if(x_stage>=swidth-kun_width){
                            mustleft=true;
                            stage.setX(swidth-kun_width);
                        }
                        if(y_stage<=-20){
                            mustdown=true;
                            stage.setY(-20);
                        }
                        if(y_stage>=sheight-height-20){
                            mustup=true;
                            stage.setY(sheight-height-20);
                        }
                    }
                }
                i++;
            }

        };
        pane.getChildren().add(iv1);
        pane.setStyle("-fx-background:transparent;");//布局背景透明
        scene.setFill(null);//场景背景透明
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);//舞台背景透明
        stage.show();
        //鼠标拖动事件
        scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent m) {
                //计算
                stage.setX(x_stage + m.getScreenX() - x1);
                stage.setY(y_stage + m.getScreenY() - y1);
            }
        });
        scene.setOnDragEntered(null);
        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent m) {
                //按下鼠标后，记录当前鼠标的坐标
                x1 =m.getScreenX();
                y1 =m.getScreenY();
                x_stage = stage.getX();
                y_stage = stage.getY();
                MouseButton button = m.getButton();
                i=0;
                timer.start();
                //双击操作
                scene.setOnMouseReleased(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (m.getClickCount() == 1) {
                            if(!Option_open) {
                                timer.stop();
                                pane.getChildren().addAll(b1, b2, b3);
                                iv1.setImage(petkun_img1);
                                Option_open=true;
                            }else {
                                pane.getChildren().removeAll(b1,b2,b3);
                                iv1.setImage(petkun_img);
                                Option_open=false;
                            }
                        }
                        timer.start();
                    }
                });
                if (m.getClickCount() == 3) {
                    System.exit(0);
                }
            }
        });
        x_stage = stage.getX();
        y_stage = stage.getY();
        timer.start();
        b1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
//                    System.out.println("打开日历");
                    calendar.start(calendar_stage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        b2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
//                    System.out.println("打开番茄钟");
                    clock.start(tomato_stage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        b3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    //server.MainServer.main();
                    client.ClientMain.main();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}