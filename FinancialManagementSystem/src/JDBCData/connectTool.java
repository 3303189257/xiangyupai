package JDBCData;

import org.junit.*;

import java.io.*;
import java.lang.reflect.*;
import java.sql.*;
import java.util.*;

public class connectTool {
    /**
     * 连接操作
     *
     * @return 返回连接
     * @throws Exception
     */
    public static Connection connectxx() throws Exception {
        //加载配置文件
        InputStream resourceAsStream = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc11.properties");
        Properties properties = new Properties();
        properties.load(resourceAsStream);
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        String url = properties.getProperty("url");
        String driverClass = properties.getProperty("driverClass");
        Class.forName(driverClass);//加载驱动
        Connection connection = DriverManager.getConnection(url, user, password);//获得连接
        return connection;
    }

    /**
     * 关闭连接的操作
     *
     * @param conn
     * @param ps
     */
    public static void closeResourse(Connection conn, Statement ps) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
    public static void closeResourse(Connection conn, Statement ps,ResultSet rs){
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if(rs !=null){
            try {
                rs.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    /**
     * 对数据库中数据的  增删改
     *
     * @param sql
     * @param args
     */
    public static void UPdate(String sql, Object... args) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = connectxx();
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResourse(conn, ps);
        }
    }
    /**
     *  说明是哪那个类的,查询操作,自己写Sql语句,填充占位符
     */
    public static <T> List<T> selectAll(Class<T> clazz,String sql, Object... args){
        Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
            conn=connectxx();//连接操作
            ps=conn.prepareStatement(sql);//预编译
            for(int i=0;i<args.length;i++){//填充占位符
                ps.setObject(i+1,args[i]);
            }
            rs= ps.executeQuery();//编译
             ArrayList<T> ts = new ArrayList<>();
            while(rs.next()){
                T instance = clazz.newInstance();//创建对象
                ResultSetMetaData rsmd = rs.getMetaData();//获得元数据
                for(int i=0;i<rsmd.getColumnCount();i++){
                    Object rsvalue = rs.getObject(i + 1);//获得数据
                    String colvalue = rsmd.getColumnLabel(i + 1);//获得别名
                    Field field = clazz.getDeclaredField(colvalue);//反射属性
                    field.setAccessible(true);//设置权限
                    field.set(instance,rsvalue);//反射赋值
                }
                ts.add(instance);
            }
            return ts;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        closeResourse(conn,ps,rs);
        }

        return null;
    }
}
