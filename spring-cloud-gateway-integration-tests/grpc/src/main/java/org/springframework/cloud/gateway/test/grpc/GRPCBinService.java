package org.springframework.cloud.gateway.test.grpc;

import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;

import org.springframework.cloud.gateway.tests.grpc.EmptyMessage;
import org.springframework.cloud.gateway.tests.grpc.GRPCBinGrpc;
import org.springframework.cloud.gateway.tests.grpc.IndexReply;

/**
 * @author Ollie Hughes
 */
@GRpcService
public class GRPCBinService extends GRPCBinGrpc.GRPCBinImplBase {

	@Override
	public void index(EmptyMessage request, StreamObserver<IndexReply> responseObserver) {
		IndexReply.newBuilder().setDescription("Index of methods");

		final IndexReply.Builder indexBuilder =
				IndexReply.newBuilder().addEndpoints(IndexReply.Endpoint
						.newBuilder()
						.setDescription("Index")
						.setPath(GRPCBinGrpc.getIndexMethod().getBareMethodName()));
		responseObserver.onNext(indexBuilder.build());
		responseObserver.onCompleted();
	}
}
