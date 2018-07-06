package someAPI.getinfo;


import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import org.apache.commons.io.IOUtils;

public class weather {
    public static void main(String[] args) throws Exception {
        json();
    }
    public static void json() throws Exception{
        String city = java.net.URLEncoder.encode("北京", "utf-8");
        String apiUrl = String.format("https://www.sojson.com/open/api/weather/json.shtml?city=%s",city);
        URL url= new URL(apiUrl);
        URLConnection open = url.openConnection();
        InputStream input = open.getInputStream();
        String result = IOUtils.toString(input,"utf-8");
        System.out.println(result);
    }
}