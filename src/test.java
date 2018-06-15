import db.DBopLogAndSign;
import db.DBopeartion;

import java.sql.SQLException;

public class test {
    public static void main(String[] args)throws SQLException ,ClassNotFoundException{
        //        InputStream is = null;
//        InputStreamReader isr = null;
//        BufferedReader br= null;
//        StringBuilder sb = new StringBuilder();
//        URL url = new URL("http://www.textvalve.com/htdatasub/subscribe/articles/v2/article-"+id);
//        is = url.openStream();
//        isr = new InputStreamReader(is,"UTF-8");
//        br = new BufferedReader(isr);
//        String str = null;
//        while((str = br.readLine())!=null) {
//            sb.append(str);
//        }
//        json = sb.toString();
//        sb.setLength(0);
//        System.out.println("articleAjax"+json);

        DBopeartion dBopeartion = new DBopeartion();
        dBopeartion.Connection();
//        dBopeartion.select2("select * from article2 where id = '111'");
        dBopeartion.select2("select title,afeng from article where id = '1082993'");
        dBopeartion.close();
    }
}
