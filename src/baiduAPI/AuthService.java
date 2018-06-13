package baiduAPI;

import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * 获取token类
 */
public class AuthService {
    public static String access_token = "24.ccefb9f178fc2088647deff1bba45895.2592000.1531186829.282335-11376628";

    public static void main(String[] args) {
        getAuth();
    }
    /**
     result:{
     "access_token":"24.ccefb9f178fc2088647deff1bba45895.2592000.1531186829.282335-11376628",
     "session_key":"9mzdWBP3cVRADhAm1203p0M6QRlc3HAftRBlWL85zAGS5kkuWN7XSKgpy0Oqyod6jnmmwh0HKgq6+HhAY\/Tt5ENSNXy9Qg==",
     "scope":"public nlp_simnet nlp_wordemb nlp_comtag nlp_dnnlm_cn brain_nlp_lexer brain_all_scope brain_nlp_comment_tag brain_nlp_dnnlm_cn brain_nlp_word_emb_vec brain_nlp_word_emb_sim brain_nlp_sentiment_classify brain_nlp_simnet brain_nlp_depparser brain_nlp_wordembedding brain_nlp_dnnlm_cn_legacy brain_nlp_simnet_legacy brain_nlp_comment_tag_legacy brain_nlp_lexer_custom brain_nlp_keyword brain_nlp_topic wise_adapt lebo_resource_base lightservice_public hetu_basic lightcms_map_poi kaidian_kaidian ApsMisTest_Test\u6743\u9650 vis-classify_flower lpq_\u5f00\u653e cop_helloScope ApsMis_fangdi_permission smartapp_snsapi_base iop_autocar",
     "refresh_token":"25.6d2325514e6951dd09514ce5bfcef4ea.315360000.1843954829.282335-11376628",
     "session_secret":"4ff605ac24541e2357a37b2612a94b37","expires_in":2592000}
     */
    public static String getAuth() {
        // 官网获取的 API Key 更新为你注册的
        String clientId = "fac7RyNu4hVuqdXmtbUTKDMU";
        // 官网获取的 Secret Key 更新为你注册的
        String clientSecret = "TMpIbcHdazmEYUN6fudsv1jyOrG6GG6d";
        return getAuth(clientId, clientSecret);
    }

    /**
     * 获取API访问token
     * 该token有一定的有效期，需要自行管理，当失效时需重新获取.
     * @param ak - 百度云官网获取的 API Key
     * @param sk - 百度云官网获取的 Securet Key
     * @return assess_token 示例：
     * "24.460da4889caad24cccdb1fea17221975.2592000.1491995545.282335-1234567"
     */
    public static String getAuth(String ak, String sk) {
        // 获取token地址
        String authHost = "https://aip.baidubce.com/oauth/2.0/token?";
        String getAccessTokenUrl = authHost
                // 1. grant_type为固定参数
                + "grant_type=client_credentials"
                // 2. 官网获取的 API Key
                + "&client_id=" + ak
                // 3. 官网获取的 Secret Key
                + "&client_secret=" + sk;
        try {
            URL realUrl = new URL(getAccessTokenUrl);
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.err.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String result = "";
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            /**
             * 返回结果示例
             */

            JSONObject jsonObject = new JSONObject(result);
            String access_token = jsonObject.getString("access_token");
            return access_token;
        } catch (Exception e) {
            System.err.printf("获取token失败！");
            e.printStackTrace(System.err);
        }
        return null;
    }

}
