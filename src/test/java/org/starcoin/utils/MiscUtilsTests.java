package org.starcoin.utils;

import org.junit.jupiter.api.Test;
import org.starcoin.types.StructTag;

public class MiscUtilsTests {

    @Test
    void parseStructTag() {
        String tag = "0x00000000000000000000000000000001::IdentifierNFT::IdentifierNFT<0x6bfb460477adf9dd0455d3de2fc7f211::SBTModule::DaoMember<0x6bfb460477adf9dd0455d3de2fc7f211::SBTModule::SbtTestDAO2>, " +
                "0x6bfb460477adf9dd0455d3de2fc7f211::SBTModule::DaoMemberBody<0x6bfb460477adf9dd0455d3de2fc7f211::SBTModule::SbtTestDAO2>>";
        StructTag structTag = MiscUtils.parseStructTag(tag);
        System.out.println(structTag);
    }
}
