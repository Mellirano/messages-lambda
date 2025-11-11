package ua.udunt.hmnd.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;

@ApplicationScoped
public class DynamoDbClientConfig {

    @Produces
    DynamoDbAsyncClient dynamoDbAsyncClient() {
        AwsBasicCredentials credentials = AwsBasicCredentials.builder()
                .accessKeyId(System.getenv("AWS_ACCESS_KEY_ID"))
                .secretAccessKey(System.getenv("AWS_SECRET_ACCESS_KEY"))
                .build();

        return DynamoDbAsyncClient.builder()
                .region(Region.of(System.getenv().getOrDefault("AWS_REGION", "eu-west-1")))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();
    }

}
