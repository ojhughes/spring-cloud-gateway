package org.springframework.cloud.gateway.tests.http2;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.netty.ByteBufFlux;
import reactor.netty.http.HttpProtocol;
import reactor.netty.http.client.HttpClient;
import reactor.netty.http.client.HttpClientResponse;

/**
 * @author Ollie Hughes
 */
public class TestClient {

	static Logger log = LoggerFactory.getLogger(TestClient.class);
	public static void main(String[] args) throws URISyntaxException {
		TestClient testClient = new TestClient();
		testClient.run();
	}

	private void run() throws URISyntaxException {
		final HttpClient httpClient = HttpClient
				.newConnection()
				.protocol(HttpProtocol.H2C)
				.wiretap(true)
				.headers(h -> h.add("Content-Type", "application/grpc").add("TE", "trailers"));
		URI inputStream = this.getClass()
							  .getClassLoader()
							  .getResource("frame.bin").toURI();
		File file = new File(inputStream);
		final String response = httpClient.post()
												   .uri("grpc://localhost:6565/org.springframework.cloud.gateway.tests.grpc.GRPCBin/Index")
//										  .uri("grpc://grpcb.in:9000/grpcbin.GRPCBin/Index")
										  .send(ByteBufFlux.fromPath(file.toPath()))
										  .responseContent()
										  .aggregate()
										  .asString()

										  .block();

		log.info(response);
		final HttpClientResponse res2 = httpClient.post()
												  .uri("grpc://localhost:6565/org.springframework.cloud.gateway.tests.grpc.GRPCBin/Index")
//										  .uri("grpc://grpcb.in:9000/grpcbin.GRPCBin/Index")
												  .send(ByteBufFlux.fromPath(file.toPath()))
												  .response().block();
		log.info(res2.status().toString());
	}
}
