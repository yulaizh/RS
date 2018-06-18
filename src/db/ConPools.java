package db;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;

public class ConPools{

    private static class ConnectionTime{
        Connection connection = null;
        long time;
    }

    private static LinkedList<ConnectionTime> listConnections = new LinkedList<ConnectionTime>();
    private static ConPools instance;
    private static String url = "jdbc:mysql://localhost:3306/RS?useUnicode=true&characterEncoding=UTF-8&useOldAliasMetadataBehavior=true";
    private static String name = "root";
    private static String password = "123456";
    private final static int MAX = 100;  //存在的最大连接数
    private final static int MIN = 10;  //最小连接数
    private static int counter = 0;    //记录使用中的连接的数量
    final static long clearTime = 1000 * 3600;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            for (int i=0;i < MIN;i++){
                Connection con = DriverManager.getConnection(url,name,password);
                ConnectionTime ct = new ConnectionTime();
                ct.connection = con;
                ct.time = System.currentTimeMillis();
                listConnections.add(ct);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }




    /*
     * 私有构造方法，
     * 保证该类只存在一个实例
     * */
    private ConPools(){

    }

    public static synchronized ConPools getInstance(){
        if (instance == null){
            System.out.println("创建了一个实例");
            instance = new ConPools();
        }
        return instance;
    }



    /*
     * 在获取连接时检查清理不需要的连接。
     * 没有连接就创建连接，分配的连接太多了就等一等
     * */
    public synchronized Connection getConnection()throws InterruptedException,SQLException{
        clearConnection();
        if (listConnections.size()>0){       //如果集合中还存在，就拿出一个分配给一个用户。
            final ConnectionTime ct = listConnections.removeLast();
            counter++;   //使用数量增加
            System.out.println("分配一个连接"+counter);
            return (Connection)Proxy.newProxyInstance(ConPools.class.getClassLoader(),//返回代理的Connection对象，目前只包装了close方法。
                    ct.connection.getClass().getInterfaces(), new ConHandler(ct.connection));   //close时释放连接到连接池
        }else if(counter < MAX){             //如果集合中不存在了，分配出的数量还可以接受。
            Connection conn = DriverManager.getConnection(url,name,password);
            return (Connection)Proxy.newProxyInstance(ConPools.class.getClassLoader(),//返回代理的Connection对象，目前只包装了close方法。
                    conn.getClass().getInterfaces(), new ConHandler(conn));

        }else{
            System.out.println("服务器繁忙，请等一等!");
            wait(1000);
            return getConnection();
        }
    }




    private static void clearConnection()throws SQLException{
        while(System.currentTimeMillis()-listConnections.getFirst().time>clearTime){
            listConnections.removeFirst().connection.close();
        }
    }


    /*
     * 事务处理器
     * 调用close方法再次清除连接，
     * 将连接放回集合
     * */
    private static class ConHandler implements InvocationHandler{
        Connection con;

        ConHandler(Connection con){
            this.con = con;
        }

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (!method.getName().equals("close")){
                return method.invoke(con,args);
            }else {
                ConPools.clearConnection();
                if (listConnections.size() + counter < MAX){
                    ConnectionTime ct = new ConnectionTime();
                    ct.connection = con;
                    ct.time = System.currentTimeMillis();
                    listConnections.add(ct);
                    counter--;
                    return null;
                }else {
                    con.close();
                    counter--;
                    return null;
                }
            }
        }

    }

    public static int conNumber(){
        return counter;
    }

    public static int conNuber2(){
        return listConnections.size();
    }

}
