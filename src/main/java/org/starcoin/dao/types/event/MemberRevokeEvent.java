package org.starcoin.dao.types.event;


import org.starcoin.types.AccountAddress;

public final class MemberRevokeEvent {
    public final @com.novi.serde.Unsigned Long dao_id;
    public final @com.novi.serde.Unsigned Long member_id;
    public final AccountAddress address;
    public final java.math.@com.novi.serde.Unsigned @com.novi.serde.Int128 BigInteger sbt;

    public MemberRevokeEvent(@com.novi.serde.Unsigned Long dao_id, @com.novi.serde.Unsigned Long member_id, AccountAddress address, java.math.@com.novi.serde.Unsigned @com.novi.serde.Int128 BigInteger sbt) {
        java.util.Objects.requireNonNull(dao_id, "dao_id must not be null");
        java.util.Objects.requireNonNull(member_id, "member_id must not be null");
        java.util.Objects.requireNonNull(address, "address must not be null");
        java.util.Objects.requireNonNull(sbt, "sbt must not be null");
        this.dao_id = dao_id;
        this.member_id = member_id;
        this.address = address;
        this.sbt = sbt;
    }

    public void serialize(com.novi.serde.Serializer serializer) throws com.novi.serde.SerializationError {
        serializer.increase_container_depth();
        serializer.serialize_u64(dao_id);
        serializer.serialize_u64(member_id);
        address.serialize(serializer);
        serializer.serialize_u128(sbt);
        serializer.decrease_container_depth();
    }

    public byte[] bcsSerialize() throws com.novi.serde.SerializationError {
        com.novi.serde.Serializer serializer = new com.novi.bcs.BcsSerializer();
        serialize(serializer);
        return serializer.get_bytes();
    }

    public static MemberRevokeEvent deserialize(com.novi.serde.Deserializer deserializer) throws com.novi.serde.DeserializationError {
        deserializer.increase_container_depth();
        Builder builder = new Builder();
        builder.dao_id = deserializer.deserialize_u64();
        builder.member_id = deserializer.deserialize_u64();
        builder.address = AccountAddress.deserialize(deserializer);
        builder.sbt = deserializer.deserialize_u128();
        deserializer.decrease_container_depth();
        return builder.build();
    }

    public static MemberRevokeEvent bcsDeserialize(byte[] input) throws com.novi.serde.DeserializationError {
        if (input == null) {
             throw new com.novi.serde.DeserializationError("Cannot deserialize null array");
        }
        com.novi.serde.Deserializer deserializer = new com.novi.bcs.BcsDeserializer(input);
        MemberRevokeEvent value = deserialize(deserializer);
        if (deserializer.get_buffer_offset() < input.length) {
             throw new com.novi.serde.DeserializationError("Some input bytes were not read");
        }
        return value;
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        MemberRevokeEvent other = (MemberRevokeEvent) obj;
        if (!java.util.Objects.equals(this.dao_id, other.dao_id)) { return false; }
        if (!java.util.Objects.equals(this.member_id, other.member_id)) { return false; }
        if (!java.util.Objects.equals(this.address, other.address)) { return false; }
        if (!java.util.Objects.equals(this.sbt, other.sbt)) { return false; }
        return true;
    }

    public int hashCode() {
        int value = 7;
        value = 31 * value + (this.dao_id != null ? this.dao_id.hashCode() : 0);
        value = 31 * value + (this.member_id != null ? this.member_id.hashCode() : 0);
        value = 31 * value + (this.address != null ? this.address.hashCode() : 0);
        value = 31 * value + (this.sbt != null ? this.sbt.hashCode() : 0);
        return value;
    }

    public static final class Builder {
        public @com.novi.serde.Unsigned Long dao_id;
        public @com.novi.serde.Unsigned Long member_id;
        public AccountAddress address;
        public java.math.@com.novi.serde.Unsigned @com.novi.serde.Int128 BigInteger sbt;

        public MemberRevokeEvent build() {
            return new MemberRevokeEvent(
                dao_id,
                member_id,
                address,
                sbt
            );
        }
    }
}
