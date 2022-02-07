package com.nzcs.s2s.config;

import feign.Client;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.server.Ssl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import javax.net.ssl.SSLSocketFactory;
import java.io.FileInputStream;
import java.security.KeyStore;

@Configuration
public class HttpClientConfig {

    @Bean
    @ConfigurationProperties(prefix = "server.ssl")
    public Ssl ssl() {
        return new Ssl();
    }


    @Bean
    public Client httpClient() {
        return new Client.Default(getSSLSocketFactory(ssl(), ssl().getKeyAlias()), NoopHostnameVerifier.INSTANCE);
    }

    private SSLSocketFactory getSSLSocketFactory(Ssl ssl, String alias) {
        try {
            FileInputStream inputStream = new FileInputStream(ResourceUtils.getFile(ssl.getKeyStore()));

            KeyStore keyStore = KeyStore.getInstance(ssl.getKeyStoreType());
            keyStore.load(inputStream, ssl.getKeyStorePassword().toCharArray());

            SSLContextBuilder builder = new SSLContextBuilder()
                    .loadTrustMaterial(new TrustSelfSignedStrategy())
                    .loadKeyMaterial(keyStore, ssl.getKeyStorePassword().toCharArray(), (aliases, socket) -> alias);

            return builder.build().getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
