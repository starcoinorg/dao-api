package org.starcoin.bcs.sab;

import com.novi.bcs.BcsDeserializer;
import com.novi.serde.DeserializationError;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.starcoin.utils.HexUtils;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.starcoin.bcs.sab.PrintContentHandler.toObjectForPrint;

public class BCSTraverserTests {
    @Test
    void testOptionalBytesVectorTraverse() throws DeserializationError, ParseException {
        VectorU8Traverser bytesTraverser = new VectorU8Traverser();
        OptionalTraverser optionalTraverser = new OptionalTraverser(bytesTraverser);
        VectorTraverser vectorTraverser = new VectorTraverser(optionalTraverser);
        System.out.println(vectorTraverser);
        byte[] bytes = HexUtils.hexToByteArray("0x020001200f30a41872208c6324fa842889315b14f9be6f3dd0d5050686317adfdd0cda60");
        printTraverse(vectorTraverser, bytes);

        BcsDeserializer deserializer_0 = new BcsDeserializer(bytes);
        GetValueByIndexesHandler valueGetter_0 = new GetValueByIndexesHandler(new int[]{0});
        vectorTraverser.traverse(deserializer_0, valueGetter_0);
        System.out.println(toObjectForPrint(valueGetter_0.getTargetValue()));
        Assertions.assertEquals(Optional.empty(), valueGetter_0.getTargetValue());

        // ////////////////////////////////////////////////
        Object v_0 = BCSPath.select(bytes, "vector<option<bytes>>[0]");
        Assertions.assertEquals(Optional.empty(), v_0);
        // ////////////////////////////////////////////////

        BcsDeserializer deserializer_1 = new BcsDeserializer(bytes);
        GetValueByIndexesHandler valueGetter_1 = new GetValueByIndexesHandler(new int[]{1});
        vectorTraverser.traverse(deserializer_1, valueGetter_1);
        System.out.println(toObjectForPrint(valueGetter_1.getTargetValue()));
        byte[] expectedBytes = new byte[]{15, 48, -92, 24, 114, 32, -116, 99, 36, -6, -124, 40, -119, 49, 91, 20, -7, -66, 111, 61, -48, -43, 5, 6, -122, 49, 122, -33, -35, 12, -38, 96};
        Assertions.assertTrue(valueGetter_1.getTargetValue() instanceof Optional<?>);
        Assertions.assertArrayEquals(expectedBytes, ((Optional<byte[]>) valueGetter_1.getTargetValue()).get());

        // ////////////////////////////////////////////////
        Object v_1 = BCSPath.select(bytes, "vector<option<bytes>>[1]");
        Assertions.assertArrayEquals(expectedBytes, ((Optional<byte[]>) v_1).get());
    }

    @Test
    void testStructTraverse_1() throws DeserializationError, ParseException {
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
        // ////////////////////////////////////////////////
        Object v_0 = BCSPath.select(bytes, "{u64,{{u128}},u128}[0]");
        Assertions.assertEquals(250L, v_0);
        // ////////////////////////////////////////////////

        BcsDeserializer deserializer_1 = new BcsDeserializer(bytes);
        GetValueByIndexesHandler valueGetter_1 = new GetValueByIndexesHandler(new int[]{1, 0, 0});
        structTraverser.traverse(deserializer_1, valueGetter_1);
        System.out.println(toObjectForPrint(valueGetter_1.getTargetValue()));
        Assertions.assertEquals(new BigInteger("2902046449728"), valueGetter_1.getTargetValue());
        // ////////////////////////////////////////////////
        Object v_1 = BCSPath.select(bytes, "{u64,{{u128}},u128}[1][0][0]");
        Assertions.assertEquals(new BigInteger("2902046449728"), v_1);
        // ////////////////////////////////////////////////

        BcsDeserializer deserializer_2 = new BcsDeserializer(bytes);
        GetValueByIndexesHandler valueGetter_2 = new GetValueByIndexesHandler(new int[]{2});
        structTraverser.traverse(deserializer_2, valueGetter_2);
        System.out.println(toObjectForPrint(valueGetter_2.getTargetValue()));
        Assertions.assertEquals(new BigInteger("0"), valueGetter_2.getTargetValue());
        // ////////////////////////////////////////////////
        Object v_2 = BCSPath.select(bytes, "{u64,{{u128}},u128}[2]");
        Assertions.assertEquals(new BigInteger("0"), v_2);
        // ////////////////////////////////////////////////

    }

    @Test
    void testStructTraverse_2() throws DeserializationError, ParseException {
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

        // ////////////////////////////////////////////////
        Object v_1 = BCSPath.select(bytes, "{{{u128}}}[0][0][0]");
        Assertions.assertEquals(new BigInteger("0"), v_1);
        // ////////////////////////////////////////////////
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

    @SuppressWarnings("unchecked")
    @Test
    void testEnumTraverse_1() throws DeserializationError, ParseException {
        byte[] bytes = HexUtils.hexToByteArray("07e498d62f5d1f469d2f72eb3e9dc8f2301143726f7373436861696e4d616e616765720f43726f7373436861696e4576656e7400");
        EnumTraverser enumTraverser = new EnumTraverser(Arrays.asList(
                //pub enum TypeTag {
                NoOperationTraverser.INSTANCE,//Bool,
                NoOperationTraverser.INSTANCE,//U8,
                NoOperationTraverser.INSTANCE,//U64,
                NoOperationTraverser.INSTANCE,//U128,
                NoOperationTraverser.INSTANCE,//Address,
                NoOperationTraverser.INSTANCE,//Signer,
                UnsupportedOperationTraverser.INSTANCE, //Vector(Box<TypeTag>),
                new StructTraverser(Arrays.asList( //Struct(StructTag),
                        //pub struct StructTag {
                        //    pub address: AccountAddress,
                        //    pub module: Identifier,
                        //    pub name: Identifier,
                        //    pub type_params: Vec<TypeTag>,
                        //}
                        AccountAddressTraverser.INSTANCE,
                        StringTraverser.INSTANCE,
                        StringTraverser.INSTANCE,
                        new VectorTraverser(UnsupportedOperationTraverser.INSTANCE)
                ))
                //}
        ));
        printTraverse(enumTraverser, bytes);

        BcsDeserializer deserializer = new BcsDeserializer(bytes);
        GetValueByIndexesHandler valueGetter = new GetValueByIndexesHandler(GetValueByIndexesHandler.ROOT_INDEXES);
        enumTraverser.traverse(deserializer, valueGetter);
        System.out.println(toObjectForPrint(valueGetter.getTargetValue()));
        BCSTraverser.Enum enumObj_0 = (BCSTraverser.Enum) valueGetter.getTargetValue();
        Assertions.assertEquals(7, enumObj_0.getVariantIndex());
        Assertions.assertTrue(enumObj_0.getValue() instanceof List);
        List<Object> listObj_0 = (List<Object>) enumObj_0.getValue();
        Assertions.assertEquals("CrossChainManager", listObj_0.get(1));

        // ////////////////////////////////////////////////
        BCSTraverser.Enum enumObj = (BCSTraverser.Enum) BCSPath.select(bytes,
                "enum{_, _, _, _, _, _, _, {address, string, string, vector<_>}}");
        Assertions.assertEquals(7, enumObj.getVariantIndex());
        Assertions.assertTrue(enumObj.getValue() instanceof List);
        List<Object> listObj = (List<Object>) enumObj.getValue();
        Assertions.assertEquals("CrossChainManager", listObj.get(1));
        // ////////////////////////////////////////////////
    }


    private void printTraverse(BCSTraverser traverser, byte[] bytes) throws DeserializationError {
        BcsDeserializer deserializer = new BcsDeserializer(bytes);
        ContentHandler printContentHandler = new PrintContentHandler();
        traverser.traverse(deserializer, printContentHandler);
    }

}
