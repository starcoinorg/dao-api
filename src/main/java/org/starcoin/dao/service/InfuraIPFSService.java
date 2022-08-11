package org.starcoin.dao.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.starcoin.dao.vo.DaoDescriptor;
import org.starcoin.dao.vo.ProposalDescriptor;
import org.starcoin.ipfs.api.MerkleNode;
import org.starcoin.ipfs.api.okhttp.IPFS;

import java.io.IOException;

@Service
public class InfuraIPFSService {
    @Value("${infura.ipfs.project-id}")
    private String projectId;
    @Value("${infura.ipfs.project-secret}")
    private String projectSecret;
    @Value("${infura.ipfs.api-endpoint}")
    private String apiEndpoint = "https://ipfs.infura.io:5001";

    public String add(DaoDescriptor descriptor) throws IOException {
        String filename = "DaoDescriptor.json";
        return addTextFile(getObjectMapper().writeValueAsString(descriptor), filename);
    }

    public String add(ProposalDescriptor descriptor) throws IOException {
        String filename = "ProposalDescriptor.json";
        return addTextFile(getObjectMapper().writeValueAsString(descriptor), filename);
    }

    /**
     * Add text file to IPFS and return the hash.
     */
    private String addTextFile(String content, String filename) throws IOException {
        boolean wrap = false;
        boolean hashOnly = false;
        IPFS ipfs = new IPFS(apiEndpoint);
        MerkleNode merkleNode = ipfs.add(filename, content.getBytes(), wrap, hashOnly,
                IPFS.basicAuthenticator(projectId, projectSecret));
        return merkleNode.hash;
    }

    private ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectSecret() {
        return projectSecret;
    }

    public void setProjectSecret(String projectSecret) {
        this.projectSecret = projectSecret;
    }

    public String getApiEndpoint() {
        return apiEndpoint;
    }

    public void setApiEndpoint(String apiEndpoint) {
        this.apiEndpoint = apiEndpoint;
    }
}
