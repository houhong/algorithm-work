package com.houhong.thirdpay.http.client;

import com.houhong.thirdpay.config.WxPayConfig;
import com.houhong.thirdpay.http.client.common.AbstractSSLHttpClient;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.ssl.SSLContexts;


import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;

/**
 * 
 * 微信支付专用HTTP SSL客户端
 *
 * @author mac
 */
public class HttpsWxClient extends AbstractSSLHttpClient {
    
    private static final HttpsWxClient CLIENT = new HttpsWxClient();
    
    private HttpsWxClient(){}
    
    @Override
    protected SSLConnectionSocketFactory getSSLSocketFactory() {
        FileInputStream fis = null;
        try {
            /**
             * 加载本地的证书
             * 不同的第三方接口，需要使用不同的证书
             */
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            fis = new FileInputStream(new File(WxPayConfig.getCertPath()));
            keyStore.load(fis, WxPayConfig.getCertPassword().toCharArray());
            
            /**
             * 构造SSL套接字工厂
             */
            SSLContext sslcontext = SSLContexts.custom()
                    .loadKeyMaterial(keyStore, WxPayConfig.getCertPassword().toCharArray())
                    .build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    sslcontext,
                    new String[]{"TLSv1"},
                    null,
                    new DefaultHostnameVerifier());
            
            return sslsf;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if(fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    fis = null;
                }
            }
        }
    }
    
    public static HttpsWxClient getClient(){
        return CLIENT;
    }
}
