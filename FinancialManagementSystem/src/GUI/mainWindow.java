package GUI;
import JDBCData.*;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class mainWindow {
    public JFrame jframe = new JFrame("财务管理系统");
    public static void main(String[] args) {
        new mainWindow();
    }

    /**
     * 启动主窗口
     */
    public void initFrame() {
        Default();
        JPanel GImage = new JPanel(null);
        JButton jButton =getJButton("登录",98 , 231, 70, 35);//登录图标按钮
        JButton jButton1 = getJButton("注册",0, 288, 70, 35);//注册图标
        JButton jButton2 = getJButton("注销",352, 288, 70, 35);//注册图标
        JButton jButton3 = getJButton("更改密码",220, 231, 100, 35);//注册图标
        JLabel jLabel = getJLable("用户名", 70, 110, 70, 20);//用户名标签
        JLabel jLabel1 = getJLable("密码", 90, 150, 70, 20);//密码标签
        JLabel jLabel2 = getJLable("请先输用户名和密码再点击按钮", 83, 23, 300, 40);
        JTextField jTextField = getJTextField("",154, 104, 150, 30);//文本框
        JPasswordField jPasswordField = new JPasswordField();//密码框
        jPasswordField.setBounds(154, 150, 150, 30);
        GImage.add(jPasswordField);
        GImage.add(jTextField);
        GImage.add(jLabel);
        GImage.add(jLabel1);
        GImage.add(jLabel2);
        GImage.add(jButton);
        GImage.add(jButton1);
        GImage.add(jButton2);
        GImage.add(jButton3);
        jframe.setBounds(557, 272, 435, 360);
        jframe.add(GImage);
        /**
         * 登录按钮，成功或失败弹出提示语对话框
         */
        jButton.addActionListener(new ActionListener() {
            @Override

            public void actionPerformed(ActionEvent e) {
                Account account = getAccount(jTextField,jPasswordField);
                if(!Integrity(account))return;
                if (account.selectAccount() != null){
                    jframe.dispose();
                    JOptionPane.showMessageDialog(jframe, "用户’"+account.getUserName()+"‘您好:\n即将显示您的个人财务管理系统", "登录成功", JOptionPane.INFORMATION_MESSAGE);
                    new SecondaryWindow(account);
                    JOptionPane.showMessageDialog(jframe, "这是你的个人财务管理系统\n你可以添加/删除你的财务记录", "添加成功", JOptionPane.INFORMATION_MESSAGE);
                }
                else {
                    JOptionPane.showMessageDialog(jframe, "用户名或密码错误", "error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        /**
         * 注册按钮，成功或失败弹出提示对话框
         */
        jButton1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Account account = getAccount(jTextField,jPasswordField);
                if(!Integrity(account))return;
                boolean b = account.selectUserName();
                if(b){
                    JOptionPane.showMessageDialog(jframe, "用户已存在", "error", JOptionPane.ERROR_MESSAGE);
                }else{
                    account.insertAccount();
                    JOptionPane.showMessageDialog(jframe, "添加成功", "添加成功", JOptionPane.INFORMATION_MESSAGE);
                }
            }

        });
        /**
         * 注销按钮，成功或失败弹出提示对话框
         */
        jButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Account account = getAccount(jTextField,jPasswordField);
                if(!Integrity(account))return;
                if(account.selectAccount()!=null){
                    JOptionPane.showMessageDialog(jframe, "用户已注销", "注销成功", JOptionPane.INFORMATION_MESSAGE);
                    account.deleteAccount();
                }else{
                    JOptionPane.showMessageDialog(jframe, "账户名或密码错误", "error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        /**
         * 更改密码按钮，成功或失败弹出提示对话框
         */
        jButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Account account = getAccount(jTextField,jPasswordField);
                if(!Integrity(account))return;
                if(account.selectAccount()!=null){
                    jframe.setVisible(false);
                    setNewPassword(account,jframe);
                }
                else JOptionPane.showMessageDialog(jframe, "账户名或密码错误", "error", JOptionPane.ERROR_MESSAGE);
            }
        });//更改密码
    }

    /**
     * 设置新密码
     * @param account
     * @param jframe
     */
    public void setNewPassword(Account account,JFrame jframe){
        JFrame j = new JFrame("新密码");
        j.setLayout(null);
        JPasswordField jPasswordField = new JPasswordField();
        JLabel jLabel =getJLable("新密码:",42,37,70,40);
        JButton jButton = getJButton("确定",120,103,70,30);
        jPasswordField.setBounds(112,43,150,30);
        j.add(jButton);
        j.add(jLabel);
        j.add(jPasswordField);
        j.setBounds(610,313,310,192);
        j.setVisible(true);
        j.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = jPasswordField.getText();
                if("".equals(text)||text==null){
                    JOptionPane.showMessageDialog(jframe, "新密码不能为空", "error", JOptionPane.ERROR_MESSAGE);
                    jPasswordField.setText("");
                }
                else {
                    account.setPassword(text);
                    account.updateAccount();
                    JOptionPane.showMessageDialog(jframe, "密码更改成功", "更改成功", JOptionPane.INFORMATION_MESSAGE);
                    j.dispose();
                    jframe.setVisible(true);
                }
            }
        });
    }

    /**
     * 设置主窗口默认值
      */
    public void Default() {
        jframe.setResizable(false);
        jframe.setVisible(true);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public mainWindow() {
        initFrame();
    }

    /**
     * 获取一个指定属性的Jlabel
     * @param name
     * @param x
     * @param y
     * @param width
     * @param height
     * @return
     */
    public JLabel getJLable(String name, int x, int y, int width, int height) {
        JLabel jLabel = new JLabel(name);
        jLabel.setBounds(x, y, width, height);
        jLabel.setFont(new Font("宋体", 1, 18));
        jLabel.setForeground(Color.red);
        return jLabel;
    }

    /**
     * 获取一个指定按钮的JButton
      * @param title
     * @param x
     * @param y
     * @param width
     * @param height
     * @return
     */
    public JButton getJButton(String title,int x,int y,int width,int height){
        JButton jButton = new JButton(title);
        jButton.setBounds(x,y,width,height);
        return jButton;
    }

    /**
     * 获取一个指定属性的JtextField
     * @param text
     * @param x
     * @param y
     * @param width
     * @param height
     * @return
     */
    public JTextField getJTextField(String text,int x,int y,int width,int height){
        JTextField jTextField = new JTextField(text);
        jTextField.setBounds(x,y,width,height);
        return  jTextField;
    }

    /**
     * 获得一个指定属性的账户
     * @param jTextField
     * @param jPasswordField
     * @return
     */
    public Account getAccount(JTextField jTextField,JPasswordField jPasswordField){
        Account account = new Account(jTextField.getText(), jPasswordField.getText());
        jTextField.setText("");
        jPasswordField.setText("");
        return account;
    }

    /**
     * 检查账户的完整性
     * @param account
     * @return
     */
    public boolean Integrity(Account account){
        if("".equals(account.getUserName())||"".equals(account.getPassword())){
            JOptionPane.showMessageDialog(jframe, "请输入完整的用户名和密码", "error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    /**
     * 副窗口
     */
    public class SecondaryWindow {
        public SecondaryWindow(Account account){
            init(account);
        }

        /**
         * 启动副窗口
          * @param account
         */
        public  void init(Account account){
            JFrame jFrame = new JFrame("个人财务管理清单");
            jFrame.setLayout(null);
            jFrame.setResizable(false);
            jFrame.setBounds(558,248,414,400);
            jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            JPanel jPanel = new JPanel(new BorderLayout());
            JPanel jPanel1 = new JPanel(null);
            jPanel.setBounds(0,0,400,330);
            jPanel1.setBounds(0,330,400,40);
            Vector col = getVectorcol();//获得表头
            Vector vector =getVectorData(account,jPanel1);//获得表数据
            DefaultTableModel tableModel = new DefaultTableModel(vector,col);
            JTable table = new JTable(tableModel){
                public boolean isCellEditable(int row, int column) {                // 表格不可编辑
                    return false;
                }
            };//设置表格不可通过单元格编辑
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );//设置滚动条总是存在
            JButton button1 =getJButton("添加记录",0,0,90,40);
            JButton button2 = getJButton("删除记录",324,0,85,40);
            button1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    addData(account,jFrame);
                }
            });//添加按钮
            button2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Object o = JOptionPane.showInputDialog(null, "请输入你要删除的序号", "删除框", JOptionPane.PLAIN_MESSAGE, null, null, "在这输入");
                    String x=(String )o;
                    if("在这输入".equals(x)||o==null||"".equals(x)){
                        JOptionPane.showMessageDialog(jFrame, "删除失败，编号不存在", "删除失败", JOptionPane.ERROR_MESSAGE);
                        return ;
                    }
                    int index=Integer.parseInt(x);
                    java.util.List<costList> costLists = account.selectCostList();
                    Collections.sort(costLists);
                    Iterator<costList> iterator = costLists.iterator();
                    int i=1;
                    while(iterator.hasNext()){
                        costList next = iterator.next();
                        if(i==index){
                            account.deleteCostList(next.getEvent(),next.getCount(),next.getReason());
                            break;
                        }
                        i++;
                    }
                    JOptionPane.showMessageDialog(jFrame, "删除成功", "删除成功", JOptionPane.INFORMATION_MESSAGE);
                    jFrame.dispose();
                    init(account);
                }
            });//删除按钮
            jPanel.add(scrollPane);
            jPanel1.add(button1);
            jPanel1.add(button2);
            jFrame.add(jPanel1);
            jFrame.add(jPanel);
            jFrame.setVisible(true);
        }

        /**
         * 获得表头
         * @return
         */
        public  Vector getVectorcol(){
            Vector col = new Vector();
            col.add("序号");
            col.add("支出/收入");
            col.add("原因");
            return col;
        }

        /**
         * 获取表格数据
         * @param account
         * @param jPanel
         * @return
         */
        public  Vector getVectorData(Account account,JPanel jPanel){
            List<costList> costLists = account.selectCostList();
             Collections.sort(costLists);
            Iterator<costList> iterator = costLists.iterator();
            Vector vector = new Vector();
            long sum=0,sum1=0;
            int i=1;
            while(iterator.hasNext()){
                costList next = iterator.next();
                Vector<Object> vector1 = new Vector<Object>();
                vector1.add(i++);
                vector1.add(next.getCount());
                vector1.add(next.getReason());
                vector.add(vector1);
                if(next.getCount()>=0)sum+=next.getCount();
                else sum1+=next.getCount();
            }
            String s = String.valueOf(sum);
            String s1 = String.valueOf(sum1);
            JLabel jLabel = getJLable("收入" + s,150,0,80,40);
            JLabel jLabel1 = getJLable("支出" + s1,230,0,80,40);
            JLabel jLabel2 = getJLable("用户:"+account.getUserName(),90,0,70,40);
            jLabel.setFont(null);
            jLabel1.setFont(null);
            jLabel2.setFont(null);
            jLabel2.setForeground(Color.red);
            jPanel.add(jLabel);
            jPanel.add(jLabel1);
            jPanel.add(jLabel2);
            return vector;
        }

        /**
         * 向表格中添加数据
         * @param account
         * @param jFrame
         */
        public  void addData(Account account,JFrame jFrame){
            JDialog dialog = new JDialog();
            JPanel GImage = new JPanel();
            GImage.setLayout(null);
            dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            JButton button = getJButton("确定",176,250,70,30);
            JLabel jLabel = getJLable("支出/收入", 70, 110, 100, 20);
            JLabel jLabel1 = getJLable("原因", 90, 150, 70, 20);
            JTextField jTextField = getJTextField("0",154, 104, 150, 30);
            JTextField jTextField1 = getJTextField("暂无原因",154, 150, 150, 30);
            GImage.add(jLabel);
            GImage.add(jLabel1);
            GImage.add(jTextField1);
            GImage.add(jTextField);
            GImage.add(button);
            dialog.add(GImage);
            dialog.setBounds(557, 272, 435, 360);
            dialog.setVisible(true);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String text = jTextField.getText();
                    String text1 = jTextField1.getText();
                    account.insertCostList(text,text1);
                    dialog.dispose();
                    JOptionPane.showMessageDialog(jFrame, "添加成功，数据已按降序排序", "添加成功", JOptionPane.INFORMATION_MESSAGE);
                    jFrame.dispose();
                    init(account);
                }
            });//确定按钮
        }
    }
}


