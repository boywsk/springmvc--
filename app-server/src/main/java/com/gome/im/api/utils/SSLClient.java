package com.gome.im.api.utils;

import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

//用于进行Https请求的HttpClient  
public class SSLClient extends DefaultHttpClient {
	final static String KEY_PATH = "E://root-cert.cer";
	public SSLClient() throws Exception {
//		SSLContext ctx = SSLContext.getInstance("TLS");
//		X509TrustManager tm = new X509TrustManager() {
//			@Override
//			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//			}
//
//			@Override
//			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//			}
//
//			@Override
//			public X509Certificate[] getAcceptedIssuers() {
//				return null;
//			}
//		};
//		ctx.init(null, new TrustManager[] { tm }, null);
//		SSLSocketFactory ssf = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		SSLSocketFactory ssf = getSSL();
		
		ClientConnectionManager ccm = this.getConnectionManager();
		SchemeRegistry sr = ccm.getSchemeRegistry();
		sr.register(new Scheme("https", 443, ssf));
	}
	
	private static SSLSocketFactory getSSL() throws Exception{
        InputStream ins = new FileInputStream(KEY_PATH);
        CertificateFactory cerFactory = CertificateFactory.getInstance("X.509");
        Certificate cer = cerFactory.generateCertificate(ins);
        String keyStoreType = KeyStore.getDefaultType();
        KeyStore keyStore = KeyStore.getInstance(keyStoreType);
        keyStore.load(null, null);
        keyStore.setCertificateEntry("trust", cer);

        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
        tmf.init(keyStore);

        SSLContext context = SSLContext.getInstance("TLS");
        context.init(null, tmf.getTrustManagers(), null);

        SSLSocketFactory factory = new SSLSocketFactory(keyStore);
        factory.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        return factory;
    }
}
