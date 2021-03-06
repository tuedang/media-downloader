package com.demo.music.downloader.httpdownloader;

import com.demo.music.downloader.HttpDownloader;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.*;
import java.net.URI;

public class SimpleHttpDownloader implements HttpDownloader {
    public void download(String url, OutputStream out) throws IOException {
        if(url==null || url.isEmpty()) {
            return;
        }
        HttpClient client = HttpClientBuilder.create()
                .setUserAgent("Mozilla/5.0")
                .setRedirectStrategy(new DefaultRedirectStrategy() {
                    @Override
                    protected URI createLocationURI(final String location) throws ProtocolException {
                        return super.createLocationURI(location.replaceAll(" ", "%20"));
                    }
                }).build();
        HttpGet httpGet = new HttpGet(url);

        HttpResponse response = client.execute(httpGet);

        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            InputStream in = new BufferedInputStream(response.getEntity().getContent());
            IOUtils.copy(in, out);
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(out);

            out.flush();
            out.close();
        }

    }

    @Override
    public void download(String url, File target) throws IOException {
        OutputStream os = new FileOutputStream(target);
        download(url, os);
    }
}
