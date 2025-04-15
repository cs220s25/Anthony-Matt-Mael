package edu.moravian;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.*;

public class AWSSecretsManagerUtil {

    public static String getSecret(String secretName) {
        SecretsManagerClient client = SecretsManagerClient.builder()
                .region(Region.US_EAST_1)
                .build();

        GetSecretValueRequest getSecretValueRequest = GetSecretValueRequest.builder()
                .secretId(secretName)
                .build();

        GetSecretValueResponse getSecretValueResponse = client.getSecretValue(getSecretValueRequest);

        return getSecretValueResponse.secretString();
    }
}