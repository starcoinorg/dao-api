/*
 * Copyright 2002-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.starcoin.utils;


import org.bouncycastle.jcajce.provider.digest.SHA3;
import org.starcoin.types.AccountAddress;
import org.starcoin.types.Ed25519PublicKey;
import org.starcoin.types.MultiEd25519PublicKey;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.starcoin.types.Constant.SCHEME_ID_ED25519;
import static org.starcoin.types.Constant.SCHEME_ID_MULTI_ED25519;


public class AccountAddressUtils {
    public static int ACCOUNT_ADDRESS_LENGTH = 16;

    public static AccountAddress create(byte[] bytes) {
        if (bytes.length != ACCOUNT_ADDRESS_LENGTH) {
            throw new IllegalArgumentException(
                    String.format("account address bytes length must be {}", ACCOUNT_ADDRESS_LENGTH));
        }
        List<Byte> address = new ArrayList<Byte>();
        for (int i = 0; i < ACCOUNT_ADDRESS_LENGTH; i++) {
            address.add(Byte.valueOf(bytes[i]));
        }
        return new AccountAddress(address);
    }

    public static AccountAddress create(String address) {
        if (address.startsWith("0x")) {
            return create(HexUtils.decode(address.substring("0x".length())));
        }
        return create(HexUtils.decode(address));
    }

    /**
     * 从十六进制字符串转AccountAddress，支持短地址
     *
     * @param hexLiteral
     * @return
     */
    public static AccountAddress from_hex_literal(String hexLiteral) {
        if (hexLiteral.toLowerCase().startsWith("0x")) {
            hexLiteral = hexLiteral.substring(2);
        }
        String hexStr = hexLiteral;
        int hexLen = hexLiteral.length();
        if (hexLen < AccountAddress.LENGTH * 2) {
            StringBuilder builder = new StringBuilder();
            int missing = AccountAddress.LENGTH * 2 - hexLen;
            for (int i = 0; i < missing; i++) {
                builder.append('0');
            }
            builder.append(hexLiteral);
            hexStr = builder.toString();
        }
        return AccountAddress.valueOf(HexUtils.decode(hexStr));
    }

    public static String hex(AccountAddress address) {
        return HexUtils.encode(address.value);
    }

    public static byte[] bytes(AccountAddress address) {
        byte[] ret = new byte[ACCOUNT_ADDRESS_LENGTH];
        for (int i = 0; i < ACCOUNT_ADDRESS_LENGTH; i++) {
            ret[i] = address.value.get(i);
        }
        return ret;
    }

    public static AccountAddress getFromPublicKey(Ed25519PublicKey publicKey) {
        return getFromPublicKey(publicKey.value.content(), SCHEME_ID_ED25519);
    }

    public static AccountAddress getFromMultiPublicKey(MultiEd25519PublicKey publicKey) {
        return getFromPublicKey(publicKey.value.content(), SCHEME_ID_MULTI_ED25519);
    }

    private static AccountAddress getFromPublicKey(byte[] keyBytes, byte schemeId) {
        byte[] rawBytes = com.google.common.primitives.Bytes.concat(keyBytes, new byte[]{schemeId});
        byte[] digestedBytes = new SHA3.Digest256().digest(rawBytes);

        byte[] addressBytes = Arrays.copyOfRange(digestedBytes, digestedBytes.length - ACCOUNT_ADDRESS_LENGTH, digestedBytes.length);
        return create(addressBytes);
    }

    public static String getHexFromPublicKey(Ed25519PublicKey publicKey) {
        return hex(getFromPublicKey(publicKey));
    }
}
