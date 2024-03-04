package localtools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * 番茄钟
 *
 * @author 李天桓
 * @date 2022/12/23
 */
public class TomatoClock extends Application {

    /**
     * 计时器
     */
    Timer timer;
    /**
     * 番茄钟开始按钮
     */
    Button btn_tomato_clock_start = new Button("开始");
    /**
     * 番茄钟暂停按钮
     */
    Button btn_tomato_clock_pause = new Button("暂停");
    /**
     * 番茄时钟设置按钮
     */
    Button btn_tomato_clock_set = new Button("设置");
    /**
     * 剩余时间文本
     */
    Text textTimeleft = new Text("HH:MM:SS");
    /**
     * 设置时间文本框
     */
    TextField textSet = new TextField("00:25:00");
    /**
     * 现在时间的日历
     */
    Calendar timenow =  Calendar.getInstance();
    /**
     * 计时开始时的日历
     */
    Calendar timeZero =  Calendar.getInstance();
    /**
     * 暂停按钮
     */
    boolean bPause = false;

    /**
     * 计时器任务
     */
    TimerTask timerTask = new TimerTask() {

        @Override
        public void run() {

            if (bPause) {
                if(timenow.compareTo(timeZero)>0) {
                    timenow.set(Calendar.SECOND, timenow.get(Calendar.SECOND)-1);
                    if ((timenow.getTimeInMillis() - timeZero.getTimeInMillis()) <= 10000) {
                        flashText();
                    }
                }else {
                    bPause = false;
                }
                //display
                SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
                String dateString = formatter.format(timenow.getTime());
                textTimeleft.setText(dateString);
            }
        }
    };

    /**
     * 剩余时间样式切换指示变量
     */
    boolean bToggle = false;

    /**
     * 剩余时间样式切换
     */
    private void flashText() {

        bToggle = !bToggle;
        if(bToggle) {
            textTimeleft.setFont(Font.font("Tahoma", FontWeight.BOLD, 64));
        }else {
            textTimeleft.setFont(Font.font("Tahoma", FontWeight.NORMAL, 64));
        }
    }

    /**
     * 主要
     *
     * @param args 命令行传入的字符串(本程序无需)
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * 开始
     *
     * @param primaryStage 番茄钟开始舞台
     * @throws Exception 异常
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        timer = new Timer();
        timerTask = new TimerTask() {
                @Override
                public void run() {

                    if (bPause) {
                        if(timenow.compareTo(timeZero)>0) {
                            timenow.set(Calendar.SECOND, timenow.get(Calendar.SECOND)-1);
                            if ((timenow.getTimeInMillis() - timeZero.getTimeInMillis()) <= 10000) {
                                flashText();
                            }
                        }else {
                            bPause = false;
                        }
                        //display
                        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
                        String dateString = formatter.format(timenow.getTime());
                        textTimeleft.setText(dateString);
                    }
                }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            Date dateZero = sdf.parse("00:00");
            Date dataSet = sdf.parse("00:25");
            timenow.setTime(dataSet);
            timeZero.setTime(dateZero);
            textTimeleft.setText("00:25:00");
        } catch (ParseException e) {
        }

        btn_tomato_clock_start.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                bPause = true;
            }
        });

        btn_tomato_clock_pause.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                bPause = false;
            }
        });

        btn_tomato_clock_set.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                try {
                    timenow.setTime(sdf.parse(textSet.getText()));
                    sdf = new SimpleDateFormat("HH:mm:ss");
                    String dateString = sdf.format(timenow.getTime());
                    textTimeleft.setText(dateString);
                    bPause = false;
                } catch (ParseException e) {
                }
            }
        });


        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(25, 25, 25, 25));

        grid.add(btn_tomato_clock_start, 0, 0);
        grid.add(btn_tomato_clock_pause, 2, 0);
        grid.add(btn_tomato_clock_set, 2, 2);
        textTimeleft.setFont(Font.font("Tahoma", FontWeight.NORMAL, 64));
        grid.add(textTimeleft, 0, 1, 2, 1);
        GridPane.setHalignment(textTimeleft, HPos.CENTER);
        GridPane.setColumnSpan(textTimeleft, GridPane.REMAINING);
        GridPane.setHgrow(textTimeleft, Priority.ALWAYS);
        grid.add(textSet, 0, 2);

        Scene scene = new Scene(grid);
        primaryStage.setScene(scene);

        primaryStage.setTitle("Tomato Clock");
        primaryStage.show();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                timer.cancel();
            }
        });
    }
}