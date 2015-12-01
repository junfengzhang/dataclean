package com.molbase.ArticlePush;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;
import org.apache.http.ConnectionClosedException;
import org.apache.http.ExceptionLogger;
import org.apache.http.Header;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.config.SocketConfig;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.bootstrap.HttpServer;
import org.apache.http.impl.bootstrap.ServerBootstrap;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpProcessor;
import org.apache.http.protocol.HttpRequestHandler;
import org.testng.annotations.Test;
public class TestHttpClientServer {

	@Test
	public void server() throws Exception{
		
		HttpRequestHandler requestHandler = new HttpRequestHandler() {
			
			public void handle(HttpRequest paramHttpRequest,
					HttpResponse paramHttpResponse, HttpContext paramHttpContext)
					throws HttpException, IOException {								
				Header header[] = paramHttpRequest.getAllHeaders();				
				paramHttpResponse.setEntity(new StringEntity("{code: 1,msg:成功 }","utf-8"));
				System.out.println(paramHttpContext.getAttribute("a"));
				
				
			}
		};
		
		HttpProcessor httpProcessor = null;
		SocketConfig socketConfig = SocketConfig.custom()
		        .setSoTimeout(15000)
		        .setTcpNoDelay(true)
		        .build();
		final HttpServer server = ServerBootstrap.bootstrap()
		        .setListenerPort(8081)
		        .setHttpProcessor(httpProcessor)
		        .setSocketConfig(socketConfig)
		        .setExceptionLogger(new StdErrorExceptionLogger())
		        .registerHandler("*", requestHandler)
		        .create();
		server.start();
		server.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);

		Runtime.getRuntime().addShutdownHook(new Thread() {
		    @Override
		    public void run() {
		        server.shutdown(5, TimeUnit.SECONDS);
		    }
		});
		
	}
	
	static class StdErrorExceptionLogger implements ExceptionLogger {

        public void log(final Exception ex) {
            if (ex instanceof SocketTimeoutException) {
                System.err.println("Connection timed out");
            } else if (ex instanceof ConnectionClosedException) {
                System.err.println(ex.getMessage());
            } else {
                ex.printStackTrace();
            }
        }

    }
	
}
