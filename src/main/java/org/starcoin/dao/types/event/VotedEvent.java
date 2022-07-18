package org.starcoin.dao.types.event;


import org.starcoin.types.AccountAddress;

public final class VotedEvent {
    public final @com.novi.serde.Unsigned Long proposal_id;
    public final AccountAddress voter;
    public final @com.novi.serde.Unsigned Byte choice;
    public final java.math.@com.novi.serde.Unsigned @com.novi.serde.Int128 BigInteger vote_weight;

    public VotedEvent(@com.novi.serde.Unsigned Long proposal_id, AccountAddress voter, @com.novi.serde.Unsigned Byte choice, java.math.@com.novi.serde.Unsigned @com.novi.serde.Int128 BigInteger vote_weight) {
        java.util.Objects.requireNonNull(proposal_id, "proposal_id must not be null");
        java.util.Objects.requireNonNull(voter, "voter must not be null");
        java.util.Objects.requireNonNull(choice, "choice must not be null");
        java.util.Objects.requireNonNull(vote_weight, "vote_weight must not be null");
        this.proposal_id = proposal_id;
        this.voter = voter;
        this.choice = choice;
        this.vote_weight = vote_weight;
    }

    public void serialize(com.novi.serde.Serializer serializer) throws com.novi.serde.SerializationError {
        serializer.increase_container_depth();
        serializer.serialize_u64(proposal_id);
        voter.serialize(serializer);
        serializer.serialize_u8(choice);
        serializer.serialize_u128(vote_weight);
        serializer.decrease_container_depth();
    }

    public byte[] bcsSerialize() throws com.novi.serde.SerializationError {
        com.novi.serde.Serializer serializer = new com.novi.bcs.BcsSerializer();
        serialize(serializer);
        return serializer.get_bytes();
    }

    public static VotedEvent deserialize(com.novi.serde.Deserializer deserializer) throws com.novi.serde.DeserializationError {
        deserializer.increase_container_depth();
        Builder builder = new Builder();
        builder.proposal_id = deserializer.deserialize_u64();
        builder.voter = AccountAddress.deserialize(deserializer);
        builder.choice = deserializer.deserialize_u8();
        builder.vote_weight = deserializer.deserialize_u128();
        deserializer.decrease_container_depth();
        return builder.build();
    }

    public static VotedEvent bcsDeserialize(byte[] input) throws com.novi.serde.DeserializationError {
        if (input == null) {
             throw new com.novi.serde.DeserializationError("Cannot deserialize null array");
        }
        com.novi.serde.Deserializer deserializer = new com.novi.bcs.BcsDeserializer(input);
        VotedEvent value = deserialize(deserializer);
        if (deserializer.get_buffer_offset() < input.length) {
             throw new com.novi.serde.DeserializationError("Some input bytes were not read");
        }
        return value;
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        VotedEvent other = (VotedEvent) obj;
        if (!java.util.Objects.equals(this.proposal_id, other.proposal_id)) { return false; }
        if (!java.util.Objects.equals(this.voter, other.voter)) { return false; }
        if (!java.util.Objects.equals(this.choice, other.choice)) { return false; }
        if (!java.util.Objects.equals(this.vote_weight, other.vote_weight)) { return false; }
        return true;
    }

    public int hashCode() {
        int value = 7;
        value = 31 * value + (this.proposal_id != null ? this.proposal_id.hashCode() : 0);
        value = 31 * value + (this.voter != null ? this.voter.hashCode() : 0);
        value = 31 * value + (this.choice != null ? this.choice.hashCode() : 0);
        value = 31 * value + (this.vote_weight != null ? this.vote_weight.hashCode() : 0);
        return value;
    }

    public static final class Builder {
        public @com.novi.serde.Unsigned Long proposal_id;
        public AccountAddress voter;
        public @com.novi.serde.Unsigned Byte choice;
        public java.math.@com.novi.serde.Unsigned @com.novi.serde.Int128 BigInteger vote_weight;

        public VotedEvent build() {
            return new VotedEvent(
                proposal_id,
                voter,
                choice,
                vote_weight
            );
        }
    }
}
