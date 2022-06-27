package org.starcoin.bcs.sab;

import com.novi.bcs.BcsDeserializer;
import com.novi.serde.DeserializationError;

public class AccountAddressTraverser extends AbstractTraverser {
    public static AccountAddressTraverser INSTANCE = new AccountAddressTraverser();

    public AccountAddressTraverser() {
    }

    @Override
    protected Object doTraverse(BcsDeserializer deserializer) throws DeserializationError {
        byte[] bytes = new byte[16];
        for (int i = 0; i < 16; i++) {
            bytes[i] = deserializer.deserialize_u8();
        }
        return bytes;
    }

    @Override
    public String type() {
        return TYPE_ACCOUNT_ADDRESS;
    }

    @Override
    public String toString() {
        return "AccountAddressTraverser{" +
                '}';
    }
}
