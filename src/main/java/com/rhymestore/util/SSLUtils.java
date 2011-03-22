/**
 * Copyright (c) 2010 Enric Ruiz, Ignasi Barrera
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.rhymestore.util;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Utility methods to work with SSl.
 * 
 * @author Ignasi Barrera
 */
public class SSLUtils
{
    /**
     * Installs a {@link TrustManager} that ignores all SSL certificates in order to allow SSL
     * connections to any host.
     * 
     * @throws Exception If the <code>TrustManager</code> cannot be installed.
     */
    public static void installIgnoreCertTrustManager() throws Exception
    {
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, new TrustManager[] {new IgnoreCertTrustManager()}, new SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        HttpsURLConnection.setDefaultHostnameVerifier(new AcceptAllHostnameVerifier());
    }

    /**
     * {@link TrustManager} implementation that ignores the certificates.
     * 
     * @author Ignasi Barrera
     */
    private static class IgnoreCertTrustManager implements X509TrustManager
    {
        @Override
        public X509Certificate[] getAcceptedIssuers()
        {
            return null;
        }

        @Override
        public void checkClientTrusted(X509Certificate[] certs, String authType)
        {
            // Do nothing => client trusted
        }

        @Override
        public void checkServerTrusted(X509Certificate[] certs, String authType)
        {
            // Do nothing => server trusted
        }
    };

    /**
     * {@link HostnameVerifier} implementation that accepts all hosts.
     * 
     * @author Ignasi Barrera
     */
    private static class AcceptAllHostnameVerifier implements HostnameVerifier
    {
        @Override
        public boolean verify(String urlHostName, SSLSession session)
        {
            // Allow all hosts
            return true;
        }

    }
}
