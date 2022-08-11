package org.starcoin.ipfs.api.okhttp;

import okhttp3.*;
import org.starcoin.ipfs.api.MerkleNode;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class IPFS {
    private final String apiEndpoint;
    private String apiVersion = "/api/v0/";

    public IPFS(String apiEndpoint) {
        this.apiEndpoint = apiEndpoint;
    }

    public IPFS(String apiEndpoint, String apiVersion) {
        this.apiEndpoint = apiEndpoint;
        this.apiVersion = apiVersion;
    }

    public static Authenticator basicAuthenticator(String username, String password) {
        return (route, response) -> {
            String credential = Credentials.basic(username, password);
            return response.request().newBuilder().header("Authorization", credential).build();
        };
    }

    public MerkleNode add(String filename, byte[] content, boolean wrap, boolean hashOnly, Authenticator authenticator) throws IOException {
        List<MultipartBody.Part> parts = Collections.singletonList(FileRequestBody.create(filename, content, getMediaType()).toPart());
        Response response = add(parts, wrap, hashOnly, authenticator);
        return MerkleNode.fromJSONObject(MerkleNode.getJSONMap(response.body().string()));
    }

    private Response add(List<MultipartBody.Part> parts, boolean wrap, boolean hashOnly, Authenticator authenticator) throws IOException {
        String url = getAddUrl(wrap, hashOnly);

        MultipartBody.Builder requestBodyBuilder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        parts.forEach(requestBodyBuilder::addPart);
        RequestBody requestBody = requestBodyBuilder.build();

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        if (authenticator != null) {
            clientBuilder.authenticator(authenticator);
        }
        OkHttpClient client = clientBuilder.build();

        Response response = client.newCall(request).execute();
        //System.out.println(response.body().string());
        return response;
    }

    private MediaType getMediaType() {
        return MediaType.parse("application/octet-stream");
    }

    private String getAddUrl(boolean wrap, boolean hashOnly) {
        return apiEndpoint + apiVersion + "add?stream-channels=true&w=" + wrap + "&n=" + hashOnly;
    }

    public String getApiEndpoint() {
        return apiEndpoint;
    }

    public String getApiVersion() {
        return apiVersion;
    }
}
