package org.starcoin.dao.types.event;


import org.starcoin.types.AccountAddress;

public final class MemberJoinEvent {
    public final @com.novi.serde.Unsigned Long id;
    public final AccountAddress address;
    public final java.math.@com.novi.serde.Unsigned @com.novi.serde.Int128 BigInteger sbt;

    public MemberJoinEvent(@com.novi.serde.Unsigned Long id, AccountAddress address, java.math.@com.novi.serde.Unsigned @com.novi.serde.Int128 BigInteger sbt) {
        java.util.Objects.requireNonNull(id, "id must not be null");
        java.util.Objects.requireNonNull(address, "address must not be null");
        java.util.Objects.requireNonNull(sbt, "sbt must not be null");
        this.id = id;
        this.address = address;
        this.sbt = sbt;
    }

    public void serialize(com.novi.serde.Serializer serializer) throws com.novi.serde.SerializationError {
        serializer.increase_container_depth();
        serializer.serialize_u64(id);
        address.serialize(serializer);
        serializer.serialize_u128(sbt);
        serializer.decrease_container_depth();
    }

    public byte[] bcsSerialize() throws com.novi.serde.SerializationError {
        com.novi.serde.Serializer serializer = new com.novi.bcs.BcsSerializer();
        serialize(serializer);
        return serializer.get_bytes();
    }

    public static MemberJoinEvent deserialize(com.novi.serde.Deserializer deserializer) throws com.novi.serde.DeserializationError {
        deserializer.increase_container_depth();
        Builder builder = new Builder();
        builder.id = deserializer.deserialize_u64();
        builder.address = AccountAddress.deserialize(deserializer);
        builder.sbt = deserializer.deserialize_u128();
        deserializer.decrease_container_depth();
        return builder.build();
    }

    public static MemberJoinEvent bcsDeserialize(byte[] input) throws com.novi.serde.DeserializationError {
        if (input == null) {
             throw new com.novi.serde.DeserializationError("Cannot deserialize null array");
        }
        com.novi.serde.Deserializer deserializer = new com.novi.bcs.BcsDeserializer(input);
        MemberJoinEvent value = deserialize(deserializer);
        if (deserializer.get_buffer_offset() < input.length) {
             throw new com.novi.serde.DeserializationError("Some input bytes were not read");
        }
        return value;
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        MemberJoinEvent other = (MemberJoinEvent) obj;
        if (!java.util.Objects.equals(this.id, other.id)) { return false; }
        if (!java.util.Objects.equals(this.address, other.address)) { return false; }
        if (!java.util.Objects.equals(this.sbt, other.sbt)) { return false; }
        return true;
    }

    public int hashCode() {
        int value = 7;
        value = 31 * value + (this.id != null ? this.id.hashCode() : 0);
        value = 31 * value + (this.address != null ? this.address.hashCode() : 0);
        value = 31 * value + (this.sbt != null ? this.sbt.hashCode() : 0);
        return value;
    }

    public static final class Builder {
        public @com.novi.serde.Unsigned Long id;
        public AccountAddress address;
        public java.math.@com.novi.serde.Unsigned @com.novi.serde.Int128 BigInteger sbt;

        public MemberJoinEvent build() {
            return new MemberJoinEvent(
                id,
                address,
                sbt
            );
        }
    }
}
