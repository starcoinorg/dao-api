package org.starcoin.bcs;

import com.novi.bcs.BcsDeserializer;
import com.novi.serde.DeserializationError;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.starcoin.utils.HexUtils;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.starcoin.bcs.PrintContentHandler.toObjectForPrint;

public class BCSTraverserTests {
    @Test
    void testOptionalBytesVectorTraverse() throws DeserializationError {
        BytesTraverser bytesTraverser = new VectorU8Traverser();
        OptionalTraverser optionalTraverser = new OptionalTraverser(bytesTraverser, bytesTraverser.type());
        VectorTraverser vectorTraverser = new VectorTraverser(optionalTraverser, optionalTraverser.type());
        System.out.println(vectorTraverser);
        byte[] bytes = HexUtils.hexToByteArray("0x020001200f30a41872208c6324fa842889315b14f9be6f3dd0d5050686317adfdd0cda60");
        printTraverse(vectorTraverser, bytes);

        BcsDeserializer deserializer_0 = new BcsDeserializer(bytes);
        GetValueByIndexesHandler valueGetter_0 = new GetValueByIndexesHandler(new int[]{0});
        vectorTraverser.traverse(deserializer_0, valueGetter_0);
        System.out.println(toObjectForPrint(valueGetter_0.getTargetValue()));
        Assertions.assertEquals(Optional.empty(), valueGetter_0.getTargetValue());

        BcsDeserializer deserializer_1 = new BcsDeserializer(bytes);
        GetValueByIndexesHandler valueGetter_1 = new GetValueByIndexesHandler(new int[]{1});
        vectorTraverser.traverse(deserializer_1, valueGetter_1);
        System.out.println(toObjectForPrint(valueGetter_1.getTargetValue()));
        byte[] expectedBytes = new byte[]{15, 48, -92, 24, 114, 32, -116, 99, 36, -6, -124, 40, -119, 49, 91, 20, -7, -66, 111, 61, -48, -43, 5, 6, -122, 49, 122, -33, -35, 12, -38, 96};
        Assertions.assertTrue(valueGetter_1.getTargetValue() instanceof Optional<?>);
        Assertions.assertArrayEquals(expectedBytes, ((Optional<byte[]>)valueGetter_1.getTargetValue()).get());
    }

    @Test
    void testStructTraverse_1() throws DeserializationError {
        // 0x8c109349c6bd91411d6bc962e080c4a3::TokenSwapFarmBoost::UserInfo<0x00000000000000000000000000000001::STC::STC, 0x8c109349c6bd91411d6bc962e080c4a3::STAR::STAR>
        // {"json":{"boost_factor":250,"locked_vetoken":{"token":{"value":2902046449728}},"user_amount":0},"raw":"0xfa0000000000000040a072afa3020000000000000000000000000000000000000000000000000000"}
        // {u64, {{u128}}, u128}
        StructTraverser structTraverser = new StructTraverser(Arrays.asList(
                U64Traverser.INSTANCE,
                new StructTraverser(Collections.singletonList(
                        new StructTraverser(Collections.singletonList(
                                U128Traverser.INSTANCE))
                )),
                U128Traverser.INSTANCE
        ));
        System.out.println(structTraverser);
        byte[] bytes = HexUtils.hexToByteArray("0xfa0000000000000040a072afa3020000000000000000000000000000000000000000000000000000");
        printTraverse(structTraverser, bytes);

        BcsDeserializer deserializer_0 = new BcsDeserializer(bytes);
        GetValueByIndexesHandler valueGetter_0 = new GetValueByIndexesHandler(new int[]{0});
        structTraverser.traverse(deserializer_0, valueGetter_0);
        System.out.println(toObjectForPrint(valueGetter_0.getTargetValue()));
        Assertions.assertEquals(250L, valueGetter_0.getTargetValue());

        BcsDeserializer deserializer_1 = new BcsDeserializer(bytes);
        GetValueByIndexesHandler valueGetter_1 = new GetValueByIndexesHandler(new int[]{1, 0, 0});
        structTraverser.traverse(deserializer_1, valueGetter_1);
        System.out.println(toObjectForPrint(valueGetter_1.getTargetValue()));
        Assertions.assertEquals(new BigInteger("2902046449728"), valueGetter_1.getTargetValue());

        BcsDeserializer deserializer_2 = new BcsDeserializer(bytes);
        GetValueByIndexesHandler valueGetter_2 = new GetValueByIndexesHandler(new int[]{2});
        structTraverser.traverse(deserializer_2, valueGetter_2);
        System.out.println(toObjectForPrint(valueGetter_2.getTargetValue()));
        Assertions.assertEquals(new BigInteger("0"), valueGetter_2.getTargetValue());
    }

    @Test
    void testStructTraverse_2() throws DeserializationError {
        // 0x8c109349c6bd91411d6bc962e080c4a3::TokenSwapVestarMinter::Treasury
        // {"json":{"vtoken":{"token":{"value":0}}},"raw":"0x00000000000000000000000000000000"}
        // {{{u128}}}
        StructTraverser structTraverser = new StructTraverser(Collections.singletonList(
                new StructTraverser(Collections.singletonList(
                        new StructTraverser(Collections.singletonList(
                                U128Traverser.INSTANCE))
                ))
        ));
        System.out.println(structTraverser);
        byte[] bytes = HexUtils.hexToByteArray("0x00000000000000000000000000000000");
        printTraverse(structTraverser, bytes);

        BcsDeserializer deserializer = new BcsDeserializer(bytes);
        GetValueByIndexesHandler valueGetter = new GetValueByIndexesHandler(new int[]{0, 0, 0});
        structTraverser.traverse(deserializer, valueGetter);
        System.out.println(toObjectForPrint(valueGetter.getTargetValue()));
        Assertions.assertEquals(new BigInteger("0"), valueGetter.getTargetValue());
    }

    @Test
    void testAccountAddressTraverse() throws DeserializationError {
        byte[] bytes = HexUtils.hexToByteArray("0x8c109349c6bd91411d6bc962e080c4a3");
        printTraverse(AccountAddressTraverser.INSTANCE, bytes);

        BcsDeserializer deserializer = new BcsDeserializer(bytes);
        GetValueByIndexesHandler valueGetter = new GetValueByIndexesHandler(GetValueByIndexesHandler.ROOT_INDEXES);
        AccountAddressTraverser.INSTANCE.traverse(deserializer, valueGetter);
        System.out.println(toObjectForPrint(valueGetter.getTargetValue()));
        Assertions.assertArrayEquals(bytes, (byte[]) valueGetter.getTargetValue());
    }


    private void printTraverse(BCSTraverser traverser, byte[] bytes) throws DeserializationError {
        BcsDeserializer deserializer = new BcsDeserializer(bytes);
        ContentHandler printContentHandler = new PrintContentHandler();
        traverser.traverse(deserializer, printContentHandler);
    }

}
