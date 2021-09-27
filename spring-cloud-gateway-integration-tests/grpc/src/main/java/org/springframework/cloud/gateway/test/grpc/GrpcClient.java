package org.springframework.cloud.gateway.test.grpc;

import java.util.concurrent.ExecutionException;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.cloud.gateway.tests.grpc.EmptyMessage;
import org.springframework.cloud.gateway.tests.grpc.GRPCBinGrpc;
import org.springframework.cloud.gateway.tests.grpc.IndexReply;

/**
 * @author Ollie Hughes
 */
public class GrpcClient {

	static Logger log = LoggerFactory.getLogger(GrpcClient.class);
	public static void main(String[] args) throws ExecutionException, InterruptedException {
		final ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080).usePlaintext().build();
		final IndexReply indexReply;
		indexReply = GRPCBinGrpc.newBlockingStub(channel).index(EmptyMessage.newBuilder().build());
		log.info(indexReply.toString());
	}
}
