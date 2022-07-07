INSERT INTO `dao`
(`dao_id`,
`community_links_discord`,
`community_links_telegram`,
`community_links_twitter`,
`description`,
`long_description`,
`name`,
`purpose_id`,
`tags`)
VALUES
('test_dao_id', 'http://test_dao_community_links_discord', 'http://test_dao_community_links_telegram', 'http://test_dao_community_links_twitter', 'test_dao_description', NULL, 'test_dao_name', 'test_dao_purpose_id', 'test_dao_tags,blockchain,dao'
);

INSERT INTO `proposal`
(`dao_id`,
`proposal_number`,
`block_state_root`,
`block_height`,
`category_id`,
`description`,
`discussion`,
`title`,
`voting_period_end`,
`voting_period_start`,
`voting_type`)
VALUES
('test_dao_id', '1', '0x99163c0fc319b62c3897ada8f97881e396e33b30383f47e23d93aaed07d6806d', '19999', 'category1', 'description1', 'https://github.com/starcoinorg/discussion1', 'title1', '1655728732763', '1655123932763', 'SINGLE_CHOICE'
),
('test_dao_id', '2', '0x99163c0fc319b62c3897ada8f97881e396e33b30383f47e23d93aaed07d6806d', '19999', 'category2', 'description2', 'https://github.com/starcoinorg/discussion2', 'title2', '1655728732815', '1655123932815', 'YES_NO'
);

INSERT INTO `proposal_voting_choice`
(`dao_id`,
`proposal_number`,
`sequence_id`,
`title`)
VALUES
('test_dao_id','1','0','YES'
),
('test_dao_id','1','1','NO'
);

INSERT INTO `dao_voting_resource`
(`dao_id`,
`sequence_id`,
`resource_struct_tag`,
`voting_power_bcs_path`)
VALUES
('test_dao_id','1','0x8c109349c6bd91411d6bc962e080c4a3::TokenSwapFarmBoost::UserInfo<0x00000000000000000000000000000001::STC::STC, 0x8c109349c6bd91411d6bc962e080c4a3::STAR::STAR>',''
);


INSERT INTO `account_vote`
(`account_address`,
`dao_id`,
`proposal_number`,
`choice_sequence_id`,
`voting_power`)
VALUES
('0x8c109349c6bd91411d6bc962e080c4a3','test_dao_id','1','0','11111'),
('0xa7cdbbd23a489acac81b07fdecbacc25','test_dao_id','1','0','21111')
;


-- ----------------------- 2022-06-17 -----------------------
delete from dao_voting_resource where dao_id = 'test_dao_id' and (sequence_id = '1' or sequence_id = '2');

INSERT INTO `dao_voting_resource`
(`dao_id`,
`sequence_id`,
`resource_struct_tag`,
`voting_power_bcs_path`)
VALUES
('test_dao_id','1','0x8c109349c6bd91411d6bc962e080c4a3::TokenSwapVestarMinter::Treasury','{{{u128<-}}}'
),
('test_dao_id','2','0x8c109349c6bd91411d6bc962e080c4a3::TokenSwapFarmBoost::UserInfo<0x00000000000000000000000000000001::STC::STC, 0x8c109349c6bd91411d6bc962e080c4a3::STAR::STAR>','{u128,{{u128<-}},u128}'
);

UPDATE `proposal` SET `block_state_root` = '0x5ee196ac92839743e79db7e6a7d75acdd4afe02b3c89c036e498f66df996c0cf' WHERE (`dao_id` = 'test_dao_id') and (`proposal_number` = '1');
UPDATE `proposal` SET `block_state_root` = '0x5ee196ac92839743e79db7e6a7d75acdd4afe02b3c89c036e498f66df996c0cf' WHERE (`dao_id` = 'test_dao_id') and (`proposal_number` = '2');

-- ---------------- 2022/06/30 ----------------- --

UPDATE `dao_voting_resource` SET `voting_power_bcs_path` = '{{{u128}}}[0][0][0]' WHERE (`dao_id` = 'test_dao_id') and (`sequence_id` = '1');
UPDATE `dao_voting_resource` SET `voting_power_bcs_path` = '{u64,{{u128}},u128}[1][0][0]' WHERE (`dao_id` = 'test_dao_id') and (`sequence_id` = '2');

UPDATE `proposal` SET `block_state_root` = '0xffa6e0c0d4aa79020ee0f799ff4919637237e153accc30d782f652fc56f1a005', `block_height` = '6545885' WHERE (`dao_id` = 'test_dao_id') and (`proposal_number` = '1');
UPDATE `proposal` SET `block_state_root` = '0xffa6e0c0d4aa79020ee0f799ff4919637237e153accc30d782f652fc56f1a005', `block_height` = '6545885' WHERE (`dao_id` = 'test_dao_id') and (`proposal_number` = '2');

UPDATE `proposal` SET `voting_period_start` = '1956583272665' WHERE (`dao_id` = 'test_dao_id') and (`proposal_number` = '1');
UPDATE `proposal` SET `voting_period_start` = '1956583272720' WHERE (`dao_id` = 'test_dao_id') and (`proposal_number` = '2');

-- SELECT
--    FROM_UNIXTIME(p.voting_period_end / 1000), p.*
-- FROM
--    dao_api.proposal p;

INSERT INTO `dao`
(`dao_id`,
`community_links_discord`,
`community_links_telegram`,
`community_links_twitter`,
`description`,
`long_description`,
`name`,
`purpose_id`,
`tags`)
VALUES
('test_dao_id2', 'http://test_dao2_community_links_discord', 'http://test_dao2_community_links_telegram', 'http://test_dao2_community_links_twitter', 'test_dao2_description', NULL, 'test_dao2_name', 'test_dao_purpose_id', 'test_dao_tags,blockchain,dao'
);


INSERT INTO `dao_voting_resource`
(`dao_id`,
`sequence_id`,
`resource_struct_tag`,
`voting_power_bcs_path`)
VALUES
('test_dao_id','3','0x8c109349c6bd91411d6bc962e080c4a3::TokenSwapFarmBoost::UserInfo<0x00000000000000000000000000000001::STC::STC, 0x4ffcc98f43ce74668264a0cf6eebe42b::FAI::FAI>','{u64,{{u128}},u128}[1][0][0]'
),
('test_dao_id','4','0x8c109349c6bd91411d6bc962e080c4a3::TokenSwapFarmBoost::UserInfo<0x00000000000000000000000000000001::STC::STC, 0xbf60b00855c92fe725296a436101c8c6::WEN::WEN>','{u64,{{u128}},u128}[1][0][0]'
);



-- ----------------------- 2022-07-06 -----------------------

-- Insert or update DAOs:
INSERT INTO `dao`
(`dao_id`,
`community_links_discord`,
`community_links_telegram`,
`community_links_twitter`,
`description`,
`long_description`,
`name`,
`purpose_id`,
`tags`,
`sequence_id`,
`dao_type_tag`)
VALUES
('starcoin_dao',
'https://discord.gg/CJhpNfW8',
'https://t.me/Starcoin_STC',
'https://twitter.com/StarcoinSTC',
'Starcoin DAO',
'Starcoin DAO',
'Starcoin DAO',
null,
'Starcoin,STC,blockchain,dao',
1,
'0x8c109349c6bd91411d6bc962e080c4a3::TestStarcoinDaoModule::StarcoinDao'
);

UPDATE `dao` SET `sequence_id` = '11' WHERE (`dao_id` = 'test_dao_id');
UPDATE `dao` SET `sequence_id` = '12' WHERE (`dao_id` = 'test_dao_id2');


-- Insert dao_voting_resource:
INSERT INTO `dao_voting_resource`
(`dao_id`,
`sequence_id`,
`resource_struct_tag`,
`voting_power_bcs_path`)
VALUES
('starcoin_dao',
'1',
'0x00000000000000000000000000000001::Account::Balance<0x00000000000000000000000000000001::STC::STC>',
'{{u128}}[0][0]'
);

/*
curl --location --request POST 'https://main-seed.starcoin.org' --header 'Content-Type: application/json' --data-raw '{
 "id":101,
 "jsonrpc":"2.0",
 "method":"chain.info",
 "params":[]
}'
*/

-- Insert proposal:

INSERT INTO `proposal`
(`dao_id`,
`proposal_number`,
`block_state_root`,
`block_height`,
`category_id`,
`description`,
`discussion`,
`title`,
`voting_period_end`,
`voting_period_start`,
`voting_type`)
VALUES
('starcoin_dao',
'1',
'0xa49034c16844103645331b6b61b0c9af7c19b7a003c0c4eb6169b8aa45087bc6',
'6649322',
null,
'This is a TEST proposal#1',
'https://github.com/starcoinorg/starcoin-cookbook/issues/137',
'This is a TEST proposal#1',
(UNIX_TIMESTAMP() + 365 * 24 * 60 * 60) * 1000, -- one year from now
unix_timestamp() * 1000,
'STANDARD'
);

-- ----------------------- 2022-07-07 -----------------------

-- Insert or update DAOs:
INSERT INTO `dao`
(`dao_id`,
`community_links_discord`,
`community_links_telegram`,
`community_links_twitter`,
`description`,
`long_description`,
`name`,
`purpose_id`,
`tags`,
`sequence_id`,
`dao_type_tag`)
VALUES
('starswap_dao',
'https://discord.com/channels/822159062475997194/922700202891153428',
'https://t.me/StarswapEN',
'https://twitter.com/StarswapEN',
'Starswap DAO',
'Starswap DAO',
'Starswap DAO',
null,
'DEX,AMM,Starcoin,STC,swap,dao',
1,
'0x8c109349c6bd91411d6bc962e080c4a3::TestStarcoinDaoModule::StarswapDao'
);

INSERT INTO `dao_strategy` (`dao_id`, `strategy_id`, `sequence_id`) VALUES ('starcoin_dao', 'RESOURCES', '0');
INSERT INTO `dao_strategy` (`dao_id`, `strategy_id`, `sequence_id`) VALUES ('starswap_dao', 'RESOURCES', '0');
INSERT INTO `dao_strategy` (`dao_id`, `strategy_id`, `sequence_id`) VALUES ('test_dao_id2', 'SBT', '0');
INSERT INTO `dao_strategy` (`dao_id`, `strategy_id`, `sequence_id`) VALUES ('test_dao_id', 'RESOURCES', '0');

INSERT INTO `dao_voting_resource` (`dao_id`, `sequence_id`, `resource_struct_tag`, `voting_power_bcs_path`)
  VALUES ('starswap_dao', '1', '0x8c109349c6bd91411d6bc962e080c4a3::TokenSwapVestarMinter::Treasury', '{{{u128}}}[0][0][0]');
INSERT INTO `dao_voting_resource` (`dao_id`, `sequence_id`, `resource_struct_tag`, `voting_power_bcs_path`)
  VALUES ('starswap_dao', '2', '0x8c109349c6bd91411d6bc962e080c4a3::TokenSwapFarmBoost::UserInfo<0x00000000000000000000000000000001::STC::STC, 0x8c109349c6bd91411d6bc962e080c4a3::STAR::STAR>', '{u64,{{u128}},u128}[1][0][0]');
INSERT INTO `dao_voting_resource` (`dao_id`, `sequence_id`, `resource_struct_tag`, `voting_power_bcs_path`)
  VALUES ('starswap_dao', '3', '0x8c109349c6bd91411d6bc962e080c4a3::TokenSwapFarmBoost::UserInfo<0x00000000000000000000000000000001::STC::STC, 0x4ffcc98f43ce74668264a0cf6eebe42b::FAI::FAI>', '{u64,{{u128}},u128}[1][0][0]');
INSERT INTO `dao_voting_resource` (`dao_id`, `sequence_id`, `resource_struct_tag`, `voting_power_bcs_path`)
  VALUES ('starswap_dao', '4', '0x8c109349c6bd91411d6bc962e080c4a3::TokenSwapFarmBoost::UserInfo<0x00000000000000000000000000000001::STC::STC, 0xbf60b00855c92fe725296a436101c8c6::WEN::WEN>', '{u64,{{u128}},u128}[1][0][0]');


INSERT INTO `proposal` (
`dao_id`, `proposal_number`, `block_state_root`, `block_height`, `category_id`, `description`,
`discussion`, `title`, `voting_period_end`, `voting_period_start`, `voting_type`
) VALUES (
'test_dao_id2', '1', '0xffa6e0c0d4aa79020ee0f799ff4919637237e153accc30d782f652fc56f1a005', '6545885', 'category1', 'description1',
'https://github.com/starcoinorg/discussion2', 'A Test proposal', '1657188072720', '1856583272720', 'YES_NO'
);


UPDATE `dao` SET `dao_type_tag` = '0x8c109349c6bd91411d6bc962e080c4a3::TestStarcoinDaoModule::TestDao2'
  WHERE (`dao_id` = 'test_dao_id2');

INSERT INTO `proposal`(
`dao_id`, `proposal_number`, `block_state_root`, `block_height`,
`description`, `discussion`, `title`,
`voting_period_end`, `voting_period_start`, `voting_type`
) VALUES (
'starswap_dao', '1', '0xa49034c16844103645331b6b61b0c9af7c19b7a003c0c4eb6169b8aa45087bc6', '6649322',
'This is a TEST Starswap proposal#1', 'https://github.com/starcoinorg/starcoin-cookbook/issues/137', 'This is a TEST Starswap proposal#1',
'1688645961000', '1657109961000', 'STANDARD'
);

UPDATE `proposal` SET
`block_state_root` = '0xffa6e0c0d4aa79020ee0f799ff4919637237e153accc30d782f652fc56f1a005',
`block_height` = '6545885'
WHERE (`dao_id` = 'starswap_dao') and (`proposal_number` = '1');


