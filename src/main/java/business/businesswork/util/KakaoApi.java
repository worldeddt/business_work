package business.businesswork.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

@Component
public class KakaoApi {

    private static final Logger logger = LoggerFactory.getLogger(KakaoApi.class);

    public String getAccessToken(String code) {
        String accessToken = "";
        String refreshToken = "";
        String reqUrl = "https://kauth.kakao.com/oauth/token";

        logger.info("========== code : "+code);

        try {
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));

            String stringBuilder = "grant_type=authorization_code" +
                    "&client_id=768385b740a92387979258c4e7a5eb83" +
                    "&redirect_uri=http://localhost:8090/login" +
                    "&code=" + code;
            bufferedWriter.write(stringBuilder);
            bufferedWriter.flush();

            logger.info("resonseCode = "+conn.getResponseCode());

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = "";
            StringBuilder result = new StringBuilder();

            while((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }

            JsonObject element = JsonParser.parseString(result.toString()).getAsJsonObject();
            logger.info("response body element = "+ element);

            accessToken = element.getAsJsonObject().get("access_token").getAsString();

            logger.info("response body accessToken = "+ accessToken);
            refreshToken = element.getAsJsonObject().get("refresh_token").getAsString();

            bufferedReader.close();
            bufferedWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return accessToken;
    }

    public HashMap<String, Object> getUserInfo(String accessToken) {

        HashMap<String, Object> userInfo = new HashMap<String, Object>();
        String reqUrl = "https://kapi.kakao.com/v2/user/me";

        logger.info("====== accessToken = "+accessToken);
        try {
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer "+accessToken);

            logger.info("resonseCode = "+conn.getResponseCode());

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = "";
            StringBuilder result = new StringBuilder();

            while((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }

            logger.info("response body = "+result);

            JsonObject jsonObject = JsonParser.parseString(result.toString()).getAsJsonObject();

            JsonObject properties = jsonObject.get("properties").getAsJsonObject();
            JsonObject kakaoAccount = jsonObject.get("kakao_account").getAsJsonObject();

            String nickName = properties.getAsJsonObject().get("nickname").getAsString();
            String email = kakaoAccount.getAsJsonObject().get("email").getAsString();

            userInfo.put("nickName", nickName);
            userInfo.put("email", email);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return userInfo;
    }

    public void kakaoLogout(String accessToken) {
        String reqUrl = "https://kapi.kakao.com/v1/user/logout";

        try {
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer "+accessToken);
            logger.info("resonseCode = "+conn.getResponseCode());

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = "";
            StringBuilder result = new StringBuilder();

            while((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }

            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
