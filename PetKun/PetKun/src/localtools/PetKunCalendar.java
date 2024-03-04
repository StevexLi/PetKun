package localtools;
import java.io.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * DDL管理日历
 *
 * @author 李天桓
 * @date 2022/12/23
 */
public class PetKunCalendar extends Application
{
    /**
     * 整个面板
     */
    public BorderPane wholePane;
    /**
     * 表示的当前日期的日历
     */
    Calendar currentDate=new GregorianCalendar();
    /**
     * 右侧组件
     *///以此刻的年月日初始化右侧文本标题
    RightOftheWholePane log=new RightOftheWholePane(currentDate.get(Calendar.YEAR),currentDate.get(Calendar.MONTH)+1,currentDate.get(Calendar.DAY_OF_MONTH));
    /**
     * 左侧组件
     *///以当前的年月设置左侧的部分
    BorderPane left=setLeftOftheWholePane1(currentDate.get(Calendar.YEAR),currentDate.get(Calendar.MONTH)+1,currentDate.get(Calendar.DAY_OF_MONTH));
    double x1,y1;
    double x_stage,y_stage;
    public Stage calendar_stage;
    /**
     * 开始
     *
     * @param primaryStage DDL日历的初始舞台
     */
    public void start(Stage primaryStage)
    {
        calendar_stage = primaryStage;
        primaryStage.setScene(null);
        BottomOftheWholePane bottom=new BottomOftheWholePane();
        TopOftheWholePane top=new TopOftheWholePane(currentDate.get(Calendar.YEAR),currentDate.get(Calendar.MONTH)+1);
        //调整块与块之间的间距
//        top.setPadding(new Insets(5,5,5,5));
//        left.setPadding(new Insets(5,5,5,5));
//        bottom.setPadding(new Insets(5,5,5,5));
//        log.setPadding(new Insets(5,5,5,5));
        wholePane = new BorderPane();
        wholePane.setTop(top);wholePane.setLeft(left);
        wholePane.setBottom(bottom);wholePane.setRight(log);
        Scene scene = new Scene(wholePane);
        primaryStage.setScene(scene);
        scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent m) {
                //计算
                calendar_stage.setX(x_stage + m.getScreenX() - x1);
                calendar_stage.setY(y_stage + m.getScreenY() - y1);
            }
        });
        scene.setOnDragEntered(null);
        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent m) {
                //按下鼠标后，记录当前鼠标的坐标
                x1 =m.getScreenX();
                y1 =m.getScreenY();
                x_stage = calendar_stage.getX();
                y_stage = calendar_stage.getY();
                MouseButton button = m.getButton();
            }
        });
        primaryStage.setTitle("DDL日历");
        primaryStage.show();
    }

    /**
     * 下部组件
     *
     * @author 李天桓
     * @date 2022/12/23
     *///下部组件
    class BottomOftheWholePane extends HBox
    {
        /**
         * 保存
         */
        public Button save;
        /**
         * 删除
         */
        public Button delete;

        /**
         * 底层整个面板
         */
        public BottomOftheWholePane()
        {
            Button btLogSave=new Button("保存日志");
            Button btLogDelete=new Button("删除日志");
            Button btLogRead=new Button("读取日志");

            btLogSave.setOnAction(e -> {
                try {
                    File file=new File(log.getURL());
                    FileWriter fileWriter = new FileWriter(file);
                    fileWriter.write(log.getTextArea());
                    fileWriter.close();
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("保存日志");
                    alert.setContentText("成功保存日志");
                    alert.showAndWait();
                }
                catch(Exception ex) {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("保存日志");
                    alert.setContentText("保存日志失败");
                    alert.showAndWait();
                }
            });
            btLogDelete.setOnAction(e -> {
                log.setTextArea("");
                try {
                    String s = log.getURL();
                    File f = new File(s);
                    f.delete();
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("删除日志");
                    alert.setContentText("成功删除日志");
                    alert.showAndWait();
                }
                catch(Exception ex) {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("删除日志");
                    alert.setContentText("删除日志失败˙");
                    alert.showAndWait();
                }
            });
            btLogRead.setOnAction(e -> {
                try {
                    BufferedReader input = new BufferedReader(new FileReader(log.getURL()));
                    String s1 = "";
                    String s2;
                    while((s2=input.readLine())!=null)
                        s1 += s2;
                    log.setTextArea(s1);
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("读取日志");
                    alert.setContentText("成功读取日志");
                    alert.showAndWait();
                    input.close();
                }
                catch(Exception ex) {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("读取日志");
                    alert.setContentText("日志不存在，读取日志失败！");
                    alert.showAndWait();
                }
            });
            setAlignment(Pos.CENTER);
            setSpacing(10);
            getChildren().addAll(btLogSave,btLogDelete);
        }


    }

    /**
     * 右侧组件
     *
     * @author 李天桓
     * @date 2022/12/23
     *///右部组件
    class RightOftheWholePane extends VBox
    {
        /**
         * 显示日期区域
         */
        public Label title;
        /**
         * 显示日期对应的DDL的区域
         */
        public TextArea logArea;
        /**
         * 存储DDL的文件路径
         */
        public String url;

        /**
         * 整个面板右侧
         * 构造方法，以给定年月日设置右边的文本区域
         *
         * @param year  年
         * @param month 月
         * @param day   日
         *///构造方法，以给定年月日设置右边的文本区域
        public RightOftheWholePane(int year,int month,int day)
        {
            title=new Label();
            logArea=new TextArea();
            logArea.setWrapText(true);
            title.setAlignment(Pos.CENTER);
            logArea.setPrefColumnCount(25);
            logArea.setPrefRowCount(21);
            String s=String.format("%04d%02d%02d",year,month,day);
            this.url=s+".txt";
            setTitle(year+"年"+month+"月"+day+"日");
            title.setStyle("-fx-pref-width: 350px;-fx-pref-height: 20px;-fx-background-color:pink");
            title.setFont(Font.font("Times New Roman",FontWeight.BOLD,15));
            title.setTextFill(Color.BLUE);
            setAlignment(Pos.CENTER);
            ScrollPane scrollPane=new ScrollPane(logArea);//支持滚动
            getChildren().addAll(title,logArea);
        }

        /**
         * 设置日期
         *
         * @param s year+"年"+month+"月"+day+"日"
         *///set
        public void setTitle(String s) {title.setText(s);}

        /**
         * 设置DDL区域
         *
         * @param s year+"年"+month+"月"+day+"日"
         */
        public void setTextArea(String s) {logArea.setText(s);}

        /**
         * 设置DDL的存储路径
         *
         * @param s year+"年"+month+"月"+day+"日"
         */
        public void setURL(String s) {this.url=s;}

        /**
         * 获取日期
         *
         * @return {@link String}
         *///get
        public String getTitle() {return title.getText();}

        /**
         * 获取DDL区域信息
         *
         * @return {@link String}
         */
        public String getTextArea() {return logArea.getText();}

        /**
         * 获取DDL存储区域信息
         *
         * @return {@link String}
         */
        public String getURL() {return this.url;}
    }

    /**
     * 上部组件
     *
     * @author 李天桓
     * @date 2022/12/23
     *///上部组件
    class TopOftheWholePane extends HBox
    {
        /**
         * 年
         */
        public int year, /**
     * 月
     */
    month;
        /**
         * 年文本
         */
        public TextField yearText = new TextField();
        /**
         * 月文本
         */
        public TextField monthText = new TextField();
        /**
         * 上年按钮
         */
        public Button btLastYear = new Button("上年");
        /**
         * 明年按钮
         */
        public Button btNextYear = new Button("下年");
        /**
         * 上月按钮
         */
        public Button btLastMonth = new Button("上月");
        /**
         * 下月按钮
         */
        public Button btNextMonth = new Button("下月");
        /**
         * 今天按钮
         */
        public Button btThisDay = new Button("今日");
        /**
         * 返回按钮
         */
        public Button btBackToMain = new Button("返回");

        /**
         * 上部组件
         *
         * @param year  年
         * @param month 月
         */
        public TopOftheWholePane(int year,int month)
        {
            //设置年月并初始化
            this.year=year;this.month=month;
            this.yearText.setText(Integer.toString(year));
            this.monthText.setText(Integer.toString(month));
            yearText.setAlignment(Pos.CENTER);//年份的文本域设置为居中
            monthText.setAlignment(Pos.CENTER);//月份的文本域设置为居中
            yearText.setEditable(true);
            monthText.setEditable(true);

            this.setAlignment(Pos.CENTER);//HBox对齐方式为居中
            this.setSpacing(5);//两个节点之间的间距
            yearText.setOnAction(e->{
                int newYear=Integer.parseInt(yearText.getText()),newMonth=Integer.parseInt(monthText.getText());
                setCalendarBaseOnGivenDate(newYear,newMonth);
            });
            monthText.setOnAction(e->{
                int newYear=Integer.parseInt(yearText.getText()),newMonth=Integer.parseInt(monthText.getText());
                setCalendarBaseOnGivenDate(newYear,newMonth);
            });
            btLastYear.setOnAction(e->{
                this.year--;
                yearText.setText(String.valueOf(this.year));
//                log.setTextArea("");
                wholePane.setLeft(setLeftOftheWholePane(this.year,this.month));
            });
            btNextYear.setOnAction(e->{
                this.year++;
                yearText.setText(String.valueOf(this.year));
//                log.setTextArea("");
                wholePane.setLeft(setLeftOftheWholePane(this.year,this.month));
            });
            btLastMonth.setOnAction(e->{
                if(this.month==1)
                {
                    this.month=12;this.year--;
                }
                else
                    this.month--;
                yearText.setText(String.valueOf(this.year));
                monthText.setText(String.valueOf(this.month));
//                log.setTextArea("");
                wholePane.setLeft(setLeftOftheWholePane(this.year,this.month));
            });
            btNextMonth.setOnAction(e->{
                if(this.month==12)
                {
                    this.month=1;this.year++;
                }
                else
                    this.month++;
                yearText.setText(String.valueOf(this.year));
                monthText.setText(String.valueOf(this.month));
//                log.setTextArea("");
                wholePane.setLeft(setLeftOftheWholePane(this.year,this.month));
            });
            btThisDay.setOnAction(e->{
                yearText.setText(String.valueOf(currentDate.get(Calendar.YEAR)));
                monthText.setText(String.valueOf(currentDate.get(Calendar.MONTH)+1));
                log.setTextArea("");
                wholePane.setLeft(setLeftOftheWholePane1(currentDate.get(Calendar.YEAR),currentDate.get(Calendar.MONTH)+1,currentDate.get(Calendar.DAY_OF_MONTH)));
            });
            btBackToMain.setOnAction(e->{
                try {
                    calendar_stage.setScene(null);
                    calendar_stage.close();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            });
            yearText.setPrefColumnCount(8);
            yearText.setPrefHeight(5);
            monthText.setPrefColumnCount(8);
            monthText.setPrefHeight(5);
            this.getChildren().addAll(btLastYear,yearText,btNextYear,btLastMonth,monthText,btNextMonth,btThisDay,btBackToMain);
        }

        /**
         * 设置日历基于给定日期
         *
         * @param year  年
         * @param month 月
         */
        public void setCalendarBaseOnGivenDate(int year,int month)
        {
            if(year>0 && month>0 && month<=12)
            {
                this.year=year;this.month=month;
                wholePane.setLeft(setLeftOftheWholePane(this.year,this.month));
            }
            else
            {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("设置日期");
                alert.setContentText("设置日期失败，请输入合法的日期");
                alert.showAndWait();
            }
        }

    }

    /**
     * 设置整个面板(不选中今日)
     *
     * @param year  年
     * @param month 月
     * @return {@link BorderPane}
     *///以指定日期设置左侧,方法返回组件
    public BorderPane setLeftOftheWholePane(int year,int month)
    {
        BorderPane leftOftheWholePane=new BorderPane();
        ClockPane clock=new ClockPane();//以当前时间建立一个时钟
        EventHandler <ActionEvent> eventHandler = e -> {
            clock.setCurrentTime();
        };
        Timeline animation = new Timeline(
                new KeyFrame(Duration.millis(1000),eventHandler));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();
        CalendarPane calender=new CalendarPane(year,month);//以给定的年月建立日历
        leftOftheWholePane.setCenter(calender);
        leftOftheWholePane.setBottom(clock);
        return leftOftheWholePane;
    }

    /**
     * 设置整个面板(直接选中今日)
     *
     * @param year    一年
     * @param month   月
     * @param thisday thisday
     * @return {@link BorderPane}
     */
    public BorderPane setLeftOftheWholePane1(int year,int month,int thisday)
    {
        BorderPane leftOftheWholePane=new BorderPane();
        ClockPane clock=new ClockPane();//以当前时间建立一个时钟
        EventHandler <ActionEvent> eventHandler = e -> {
            clock.setCurrentTime();
        };
        Timeline animation = new Timeline(
                new KeyFrame(Duration.millis(1000),eventHandler));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();
        CalendarPane calender=new CalendarPane(year,month,thisday);//以给定的年月建立日历
        leftOftheWholePane.setCenter(calender);
        leftOftheWholePane.setBottom(clock);
        return leftOftheWholePane;
    }

    /**
     * 日历面板
     *
     * @author 李天桓
     * @date 2022/12/23
     *///日历类
    class CalendarPane extends  GridPane
    {
        /**
         * 日历面板
         *
         * @param year  年
         * @param month 月
         */
        public CalendarPane(int year,int month)
        {
            Label sun=new Label("SUN日");sun.setFont(Font.font("Times New Roman",FontWeight.BOLD,12));sun.setTextFill(Color.RED);
            Label mon=new Label("MON一");mon.setFont(Font.font("Times New Roman",FontWeight.BOLD,12));
            Label tue=new Label("TUE二");tue.setFont(Font.font("Times New Roman",FontWeight.BOLD,12));
            Label wed=new Label("WED三");wed.setFont(Font.font("Times New Roman",FontWeight.BOLD,12));
            Label thu=new Label("THU四");thu.setFont(Font.font("Times New Roman",FontWeight.BOLD,12));
            Label fri=new Label("FRI五");fri.setFont(Font.font("Times New Roman",FontWeight.BOLD,12));
            Label sat=new Label("SAT六");sat.setFont(Font.font("Times New Roman",FontWeight.BOLD,12));sat.setTextFill(Color.BLUE);
            this.add(sun, 0, 0);
            this.add(mon, 1, 0);
            this.add(tue, 2, 0);
            this.add(wed,3, 0);
            this.add(thu, 4, 0);
            this.add(fri,5, 0);
            this.add(sat, 6, 0);


            Calendar c=new GregorianCalendar(year, month-1,1);//从这个月1号开始，0表示1月份
            this.setGridLinesVisible(true);//显示网格线
            int hang=1;
            int totalDaysOfThisMonth=c.getActualMaximum(Calendar.DAY_OF_MONTH);//获取当前月的天数
            Button[] dates=new Button[40];
            for(int i=1;i<=totalDaysOfThisMonth;i++)
            {
                int day=i;
                int dayOfWeek=c.get(Calendar.DAY_OF_WEEK)-1;//星期日是一周的第一天，星期六是一周的第7天
                dates[i]=new Button(Integer.toString(i));
                dates[i].setStyle("-fx-background-color:null;-fx-background-insets: 0;  -fx-pref-width: 42px;-fx-pref-height: 30px;");
                Button date=dates[i];
                int k=i;
                dates[i].setOnAction(e->{
                    date.setStyle(" -fx-pref-width: 42px;-fx-pref-height: 30px;-fx-background-color:pink");//选中的颜色
                    //此时只能有一个按钮的颜色是粉色
                    for(int j=1;j<=totalDaysOfThisMonth;j++)
                    {
                        if(j!=k)
                            dates[j].setStyle("-fx-background-color:null;-fx-background-insets: 0;  -fx-pref-width: 42px;-fx-pref-height: 30px;");
                    }
                    log.setTitle(year + "年" + month + "月" + day + "日");
                    String s=String.format("%04d%02d%02d",year,month,day);
                    s+=".txt";
                    log.setURL(s);
                    try {
                        log.setTextArea("");
//                        BufferedReader input = new BufferedReader(new FileReader(log.getURL()));
                        DataInputStream in1 = new DataInputStream(new FileInputStream(log.getURL()));
                        BufferedReader input  = new BufferedReader(new InputStreamReader(in1));
                        String s1 = "";
                        String s2;
                        StringBuilder sb1 = new StringBuilder();
                        while((s2=input.readLine())!=null){
//                            System.out.print(s2);
                            sb1.append(s2);
                        }
//                        System.out.println(s1);
                        log.setTextArea(sb1.toString());
//                        Alert alert = new Alert(AlertType.INFORMATION);
//                        alert.setTitle("读取日志");
//                        alert.setContentText("成功读取日志");
//                        alert.showAndWait();
                        input.close();
                    }
                    catch(Exception ex) {
//                        Alert alert = new Alert(AlertType.INFORMATION);
//                        alert.setTitle("读取日志");
//                        alert.setContentText("日志不存在，读取日志失败！");
//                        alert.showAndWait();
                    }
                });//每选中一个日期就把右侧的文本的标题设为该日期
                this.add(dates[i], dayOfWeek, hang);
                if(c.get(Calendar.DAY_OF_WEEK)==7) hang++;
                c.add(Calendar.DAY_OF_MONTH,1);//日子数加一，变成下一天
            }
        }

        /**
         * 日历面板
         *
         * @param year    年
         * @param month   月
         * @param thisday thisday
         */
        public CalendarPane(int year,int month,int thisday)
        {
            Label sun=new Label("SUN日");sun.setFont(Font.font("Times New Roman",FontWeight.BOLD,12));sun.setTextFill(Color.RED);
            Label mon=new Label("MON一");mon.setFont(Font.font("Times New Roman",FontWeight.BOLD,12));
            Label tue=new Label("TUE二");tue.setFont(Font.font("Times New Roman",FontWeight.BOLD,12));
            Label wed=new Label("WED三");wed.setFont(Font.font("Times New Roman",FontWeight.BOLD,12));
            Label thu=new Label("THU四");thu.setFont(Font.font("Times New Roman",FontWeight.BOLD,12));
            Label fri=new Label("FRI五");fri.setFont(Font.font("Times New Roman",FontWeight.BOLD,12));
            Label sat=new Label("SAT六");sat.setFont(Font.font("Times New Roman",FontWeight.BOLD,12));sat.setTextFill(Color.BLUE);
            this.add(sun, 0, 0);
            this.add(mon, 1, 0);
            this.add(tue, 2, 0);
            this.add(wed,3, 0);
            this.add(thu, 4, 0);
            this.add(fri,5, 0);
            this.add(sat, 6, 0);

            Calendar c=new GregorianCalendar(year, month-1,1);//从这个月1号开始，0表示1月份
            this.setGridLinesVisible(true);//显示网格线
            int hang=1;
            int totalDaysOfThisMonth=c.getActualMaximum(Calendar.DAY_OF_MONTH);//获取当前月的天数
            Button[] dates=new Button[40];
            for(int i=1;i<=totalDaysOfThisMonth;i++)
            {
                int day=i;
                int dayOfWeek=c.get(Calendar.DAY_OF_WEEK)-1;//星期日是一周的第一天，星期六是一周的第7天
                dates[i]=new Button(Integer.toString(i));
                dates[i].setStyle("-fx-background-color:null;-fx-background-insets: 0;  -fx-pref-width: 42px;-fx-pref-height: 30px;");
                Button date=dates[i];
                int k=i;
                dates[i].setOnAction(e->{
                    date.setStyle(" -fx-pref-width: 42px;-fx-pref-height: 30px;-fx-background-color:pink");//选中的颜色
                    //此时只能有一个按钮的颜色是粉色
                    for(int j=1;j<=totalDaysOfThisMonth;j++)
                    {
                        if(j!=k)
                            dates[j].setStyle("-fx-background-color:null;-fx-background-insets: 0;  -fx-pref-width: 42px;-fx-pref-height: 30px;");
                    }
                    log.setTitle(year + "年" + month + "月" + day + "日");
                    String s=String.format("%04d%02d%02d",year,month,day);
                    s+=".txt";
                    log.setURL(s);
                    try {
                        log.setTextArea("");
//                        BufferedReader input = new BufferedReader(new FileReader(log.getURL()));
                        DataInputStream in1 = new DataInputStream(new FileInputStream(log.getURL()));
                        BufferedReader input  = new BufferedReader(new InputStreamReader(in1));
                        String s1 = "";
                        String s2;
                        StringBuilder sb1 = new StringBuilder();
                        while((s2=input.readLine())!=null){
//                            System.out.print(s2);
                            sb1.append(s2);
                        }
//                        System.out.println(s1);
                        log.setTextArea(sb1.toString());
//                        Alert alert = new Alert(AlertType.INFORMATION);
//                        alert.setTitle("读取日志");
//                        alert.setContentText("成功读取日志");
//                        alert.showAndWait();
                        input.close();
                    }
                    catch(Exception ex) {
//                        Alert alert = new Alert(AlertType.INFORMATION);
//                        alert.setTitle("读取日志");
//                        alert.setContentText("日志不存在，读取日志失败！");
//                        alert.showAndWait();
                    }
                });//每选中一个日期就把右侧的文本的标题设为该日期
                this.add(dates[i], dayOfWeek, hang);
                if(c.get(Calendar.DAY_OF_WEEK)==7) hang++;
                c.add(Calendar.DAY_OF_MONTH,1);//日子数加一，变成下一天
            }
            Button date = dates[currentDate.get(Calendar.DAY_OF_MONTH)];
            date.setStyle(" -fx-pref-width: 42px;-fx-pref-height: 30px;-fx-background-color:pink");//选中的颜色
            log.setTitle(year + "年" + month + "月" + thisday + "日");
            String s=String.format("%04d%02d%02d",year,month,thisday);
            s+=".txt";
            log.setURL(s);
            try {
                log.setTextArea("");
                BufferedReader input = new BufferedReader(new FileReader(log.getURL()));
                String s1 = "";
                String s2;
                while((s2=input.readLine())!=null)
                    s1 += s2;
                log.setTextArea(s1);
//                        Alert alert = new Alert(AlertType.INFORMATION);
//                        alert.setTitle("读取日志");
//                        alert.setContentText("成功读取日志");
//                        alert.showAndWait();
                input.close();
            }
            catch(Exception ex) {
//                        Alert alert = new Alert(AlertType.INFORMATION);
//                        alert.setTitle("读取日志");
//                        alert.setContentText("日志不存在，读取日志失败！");
//                        alert.showAndWait();
            }
        }
    }

    /**
     * 时钟面板
     *
     * @author 李天桓
     * @date 2022/12/23
     *///时钟类,参考书上的代码
    class ClockPane extends Pane
    {
        /**
         * 小时
         */
        private int hour, /**
     * 分钟
     */
    minute, /**
     * 秒
     */
    second;

        /**
         * 时钟面板默认构造方法，设置现在的时间
         *///默认构造方法，设置现在的时间
        public ClockPane()
        {
            setCurrentTime();
        }

        /**
         * 时钟面板(带参数的构造方法)
         *
         * @param hour   小时
         * @param minute 分钟
         * @param second 秒
         *///带参数的构造方法
        public ClockPane(int hour,int minute,int second)
        {
            this.hour=hour;this.minute=minute;this.second=second;
        }

        /**
         * 获取小时
         *
         * @return int
         *///get
        public int getHour() {return hour;}

        /**
         * 获取分钟
         *
         * @return int
         */
        public int getMinute() {return minute;}

        /**
         * 获取秒
         *
         * @return int
         */
        public int getSecond() {return second;}

        /**
         * 设置小时
         *
         * @param h 小时
         *///set新的时间之后重新显示时钟
        public void setHour(int h) {this.hour=h;paintClock();}

        /**
         * 设置分钟
         *
         * @param m 分钟
         */
        public void setMinute(int m) {this.minute=m;paintClock();}

        /**
         * 设置秒
         *
         * @param s 秒
         */
        public void setSecond(int s) {this.second=s;paintClock();}

        /**
         * 设置当前时间
         *///
        public void setCurrentTime()
        {
            Calendar calendar=new GregorianCalendar();//公历
            this.hour=calendar.get(Calendar.HOUR_OF_DAY);
            this.minute=calendar.get(Calendar.MINUTE);
            this.second=calendar.get(Calendar.SECOND);
            paintClock();
        }

        /**
         * 显示时钟
         *///显示时钟
        public void paintClock()
        {
            getChildren().clear();
            double clockRadius=250*0.8*0.4;
            double centerX = 150 / 2.0 + 80;
            double centerY = 300 / 2.0 - 65;
            //59个圆点	，计算圆点的（x,y,r）
            for(int i = 0; i < 60; i++) {
                double x = centerX + Math.sin((i / 60.0) * 2 * Math.PI) * clockRadius;
                double y = centerY + Math.cos((i / 60.0) * 2 * Math.PI) * clockRadius;
                double radius= i%15==0?5:(i%5==0?3:1);//大圆点，中圆点，小圆点
                Circle c = new Circle(x,y,radius);
                if(i%5!=0 && i%15!=0) c.setFill(Color.BLACK);
                else c.setFill(Color.ORANGE);//颜色填充

                getChildren().add(c);//把外圈的圆点加入到栈组件中
            }
            //秒针
            double slength = clockRadius * 0.8;
            double secondX = centerX + slength * Math.sin((second / 60.0) * 2 * Math.PI);
            double secondY = centerY - slength * Math.cos((second / 60.0) * 2 * Math.PI);
            Line sLine = new Line(centerX,centerY,secondX,secondY);
            sLine.setStrokeWidth(1);
            sLine.setStroke(Color.RED);
            //分针
            double mlength = clockRadius * 0.65;
            double minuteX = centerX + mlength * Math.sin((Math.PI / 60) * 2 * minute);
            double minuteY = centerY - mlength * Math.cos((Math.PI / 60) * 2 * minute);
            Line mLine = new Line(centerX,centerY,minuteX,minuteY);
            mLine.setStrokeWidth(2);
            mLine.setStroke(Color.BLUE);
            //时针
            double hlength = clockRadius * 0.5;
            double hourX = centerX + hlength * Math.sin((Math.PI / 12) * 2 * (hour % 12 + minute / 60.0));
            double hourY = centerY - hlength * Math.cos((Math.PI / 12) * 2 * (hour % 12 + minute / 60.0));
            Line hLine = new Line(centerX,centerY,hourX,hourY);
            hLine.setStrokeWidth(4);
            hLine.setStroke(Color.ORANGE);
            setStyle("-fx-background-color:gray");
            getChildren().addAll(sLine,mLine,hLine);//把指针加入到栈组件中
        }
    }

    /**
     * DDL日历的程序入口
     *
     * @param args 命令行传入的字符串(本程序无需)
     */
    public static void main(String[] args)
    {
        launch(args);
    }

}