package org.starcoin.ipfs.api.okhttp;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class FileRequestBody {
    private String filename;
    private RequestBody requestBody;

    public FileRequestBody(String filename, RequestBody requestBody) {
        this.filename = filename;
        this.requestBody = requestBody;
    }

    public static FileRequestBody create(String filename, byte[] content, MediaType mediaType) {
        return new FileRequestBody(filename, RequestBody.create(content, mediaType));
    }

    public MultipartBody.Part toPart() {
        return MultipartBody.Part.create(Headers.of(
                "Content-Disposition", "file; filename=\"" + filename + "\""
        ), requestBody);
    }
}