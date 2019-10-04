package com.keminapera.leo;

import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class HttpTest {
    public static void main(String[] args) throws UnsupportedEncodingException {
        /*Map<String, Object> variableMap = new HashMap<>();
        variableMap.put("cb", "jQuery110208899853517868435_1569053470630");
        variableMap.put("appid", "4001");
        variableMap.put("com", "tiantian");
        variableMap.put("nu", "TT6600322736156");*/
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://sp0.baidu.com/9_Q4sjW91Qh3otqbppnN2DJv/pae/channel/data/asyncqury?cb=jQuery110208899853517868435_1569053470630&appid=4001&com=tiantian&nu=TT6600322736156";
        String result = restTemplate.getForObject(url, String.class);
        String decodeStr = URLDecoder.decode(result, "unicode");
        System.out.println(decodeStr);
        System.out.println("--=====================--");
        System.out.println(result);
        System.out.println("--=============" + "这是发送" + "====================--");
    }
}
