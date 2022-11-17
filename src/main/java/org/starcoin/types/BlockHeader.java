package org.starcoin.types;

import java.util.Arrays;
import java.util.Optional;

public class BlockHeader {
    private HashValue parentHash;
    private Long timestamp;//uint64
    private Long number;//uint64
    private AccountAddress author;//AccountAddress
    private java.util.Optional<AuthenticationKey> authenticationKey;//*AuthenticationKey
    private HashValue txnAccumulatorRoot;//HashValue
    private HashValue blockAccumulatorRoot;//HashValue
    private HashValue stateRoot;//HashValue
    private Long gasUsed;//uint64
    private byte[] difficulty;//[32]uint8
    private HashValue bodyHash;//HashValue
    private ChainId chainId;
    private Integer nonce;//uint32
    private byte[] extra;//BlockHeaderExtra //[4]uint8

    public void serialize(com.novi.serde.Serializer serializer) throws com.novi.serde.SerializationError {
        serializer.increase_container_depth();
        parentHash.serialize(serializer);
        serializer.serialize_u64(timestamp);
        serializer.serialize_u64(number);
        author.serialize(serializer);
        TraitHelpers.serialize_option_AuthenticationKey(authenticationKey, serializer);
        txnAccumulatorRoot.serialize(serializer);
        blockAccumulatorRoot.serialize(serializer);
        stateRoot.serialize(serializer);
        serializer.serialize_u64(gasUsed);
        TraitHelpers.serialize_array32_u8_array(difficulty, serializer);
        bodyHash.serialize(serializer);
        chainId.serialize(serializer);
        serializer.serialize_u32(nonce);
        TraitHelpers.serialize_array4_u8_array(extra, serializer);
        serializer.decrease_container_depth();
    }

    public byte[] bcsSerialize() throws com.novi.serde.SerializationError {
        com.novi.serde.Serializer serializer = new com.novi.bcs.BcsSerializer();
        serialize(serializer);
        return serializer.get_bytes();
    }

    public HashValue getParentHash() {
        return parentHash;
    }

    public void setParentHash(HashValue parentHash) {
        this.parentHash = parentHash;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public AccountAddress getAuthor() {
        return author;
    }

    public void setAuthor(AccountAddress author) {
        this.author = author;
    }

    public Optional<AuthenticationKey> getAuthenticationKey() {
        return authenticationKey;
    }

    public void setAuthenticationKey(Optional<AuthenticationKey> authenticationKey) {
        this.authenticationKey = authenticationKey;
    }

    public HashValue getTxnAccumulatorRoot() {
        return txnAccumulatorRoot;
    }

    public void setTxnAccumulatorRoot(HashValue txnAccumulatorRoot) {
        this.txnAccumulatorRoot = txnAccumulatorRoot;
    }

    public HashValue getBlockAccumulatorRoot() {
        return blockAccumulatorRoot;
    }

    public void setBlockAccumulatorRoot(HashValue blockAccumulatorRoot) {
        this.blockAccumulatorRoot = blockAccumulatorRoot;
    }

    public HashValue getStateRoot() {
        return stateRoot;
    }

    public void setStateRoot(HashValue stateRoot) {
        this.stateRoot = stateRoot;
    }

    public Long getGasUsed() {
        return gasUsed;
    }

    public void setGasUsed(Long gasUsed) {
        this.gasUsed = gasUsed;
    }

    public byte[] getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(byte[] difficulty) {
        this.difficulty = difficulty;
    }

    public HashValue getBodyHash() {
        return bodyHash;
    }

    public void setBodyHash(HashValue bodyHash) {
        this.bodyHash = bodyHash;
    }

    public ChainId getChainId() {
        return chainId;
    }

    public void setChainId(ChainId chainId) {
        this.chainId = chainId;
    }

    public Integer getNonce() {
        return nonce;
    }

    public void setNonce(Integer nonce) {
        this.nonce = nonce;
    }

    public byte[] getExtra() {
        return extra;
    }

    public void setExtra(byte[] extra) {
        this.extra = extra;
    }

    @Override
    public String toString() {
        return "BlockHeader{" +
                "parentHash=" + parentHash +
                ", timestamp=" + timestamp +
                ", number=" + number +
                ", author=" + author +
                ", authenticationKey=" + authenticationKey +
                ", txnAccumulatorRoot=" + txnAccumulatorRoot +
                ", blockAccumulatorRoot=" + blockAccumulatorRoot +
                ", stateRoot=" + stateRoot +
                ", gasUsed=" + gasUsed +
                ", difficulty=" + Arrays.toString(difficulty) +
                ", bodyHash=" + bodyHash +
                ", chainId=" + chainId +
                ", nonce=" + nonce +
                ", extra=" + extra +
                '}';
    }
}
