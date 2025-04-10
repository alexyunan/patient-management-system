package dev.alexgiou.patientservice.grpc;

import billing.BillingRequest;
import billing.BillingResponse;
import billing.BillingServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BillingServiceGrpcClient {

  private final BillingServiceGrpc.BillingServiceBlockingStub blockingStub;

  public BillingServiceGrpcClient(
      @Value("${billing-service.address:192.168.56.123}") String serverAddress,
      @Value("${billing-service.grpc.port:9001}") int serverPort
  ) {
    log.info("Connecting to billing GRPC service at {}:{}", serverAddress, serverPort);

    // Establish a connection with the billing service
    ManagedChannel channel = ManagedChannelBuilder.forAddress(serverAddress, serverPort)
        // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid for local development
        .usePlaintext()
        .build();

    blockingStub = BillingServiceGrpc.newBlockingStub(channel);
  }

  public BillingResponse createBillingAccount(String patientId, String name, String email) {

    BillingRequest request = BillingRequest.newBuilder()
        .setPatientId(patientId)
        .setName(name)
        .setEmail(email)
        .build();

    BillingResponse response = blockingStub.createBillingAccount(request);
    log.info("Received response from billing service via GRPC: {}", response);
    return response;
  }
}
