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

-- ----------- 2020-07-08 -----------
UPDATE `proposal` SET `description` = 'This is a TEST proposal#1', `discussion` = 'https://github.com/starcoinorg/starcoin-cookbook/issues/137', `title` = 'A Test proposal#1', `voting_period_end` = '1657246438000', `voting_period_start` = '1659838422000', `submitted_at` = '1657246438000', `submitted_by` = '0xd117638e105403784bf6A92AA1276Ec1' WHERE (`dao_id` = 'test_dao_id') and (`proposal_number` = '1');
UPDATE `proposal` SET `description` = 'This is a TEST proposal#1', `discussion` = 'https://github.com/starcoinorg/starcoin-cookbook/issues/137', `title` = 'A Test proposal#1', `voting_period_end` = '1657246438000', `voting_period_start` = '1659838422000', `submitted_at` = '1657246438000', `submitted_by` = '0xd117638e105403784bf6A92AA1276Ec1' WHERE (`dao_id` = 'test_dao_id2') and (`proposal_number` = '1');
UPDATE `proposal` SET `description` = 'This is a TEST proposal#2', `discussion` = 'https://github.com/starcoinorg/starcoin-cookbook/issues/137', `title` = 'A Test proposal#2', `voting_period_end` = '1657246438000', `voting_period_start` = '1659838422000', `submitted_at` = '1657246438000', `submitted_by` = '0xd117638e105403784bf6A92AA1276Ec1' WHERE (`dao_id` = 'test_dao_id') and (`proposal_number` = '2');
UPDATE `proposal` SET `submitted_at` = '1657246438000', `submitted_by` = '0xd117638e105403784bf6A92AA1276Ec1' WHERE (`dao_id` = 'starcoin_dao') and (`proposal_number` = '1');
UPDATE `proposal` SET `submitted_at` = '1657246438000', `submitted_by` = '0xd117638e105403784bf6A92AA1276Ec1' WHERE (`dao_id` = 'starswap_dao') and (`proposal_number` = '1');

-- update proposal block_state_root and block_height
UPDATE `proposal` SET `block_state_root` = '0xcc190378418d98388e0e3bb674a884ad33318e972a51b147e4367aa91a987286', `block_height` = '6750388' WHERE (`dao_id` = 'starcoin_dao') and (`proposal_number` = '1');
UPDATE `proposal` SET `block_state_root` = '0xcc190378418d98388e0e3bb674a884ad33318e972a51b147e4367aa91a987286', `block_height` = '6750388' WHERE (`dao_id` = 'starswap_dao') and (`proposal_number` = '1');
UPDATE `proposal` SET `block_state_root` = '0xcc190378418d98388e0e3bb674a884ad33318e972a51b147e4367aa91a987286', `block_height` = '6750388' WHERE (`dao_id` = 'test_dao_id') and (`proposal_number` = '1');
UPDATE `proposal` SET `block_state_root` = '0xcc190378418d98388e0e3bb674a884ad33318e972a51b147e4367aa91a987286', `block_height` = '6750388' WHERE (`dao_id` = 'test_dao_id') and (`proposal_number` = '2');
UPDATE `proposal` SET `block_state_root` = '0xcc190378418d98388e0e3bb674a884ad33318e972a51b147e4367aa91a987286', `block_height` = '6750388' WHERE (`dao_id` = 'test_dao_id2') and (`proposal_number` = '1');

-- ----------- 2020-07-18 -----------

INSERT INTO `dao_voting_resource`
(
  `dao_id`, `sequence_id`, `resource_struct_tag`, `voting_power_bcs_path`
) VALUES (
  'starswap_dao', '5', '0x8c109349c6bd91411d6bc962e080c4a3::TokenSwapFarmBoost::UserInfo<0x00000000000000000000000000000001::STC::STC, 0xe52552637c5897a2d499fbf08216f73e::XUSDT::XUSDT>', '{u64,{{u128}},u128}[1][0][0]'
);

UPDATE `proposal` SET `description` = 'We\'re proposing an update to the #Starswap farm rewards:  - Increase rewards to key farms  | Pair     | Current Multiplier | Proposed New Multiplier | Change | | -------- | ------------------ | ----------------------- | ------ | | STC/STAR | 30                 | 50                      | +20    |',
  `discussion` = ''
  WHERE (`dao_id` = 'starswap_dao') and (`proposal_number` = '1');

UPDATE `proposal` SET `title` = 'Starswap Farm Weighting Adjustments',
  `voting_type` = 'SINGLE_CHOICE',
  `voting_method` = 'OFF_CHAIN'
  WHERE (`dao_id` = 'starswap_dao') and (`proposal_number` = '1');


INSERT INTO `proposal_voting_choice` (`dao_id`, `proposal_number`, `sequence_id`, `title`)
  VALUES ('starswap_dao', '1', '0', 'Yes');

INSERT INTO `proposal_voting_choice` (`dao_id`, `proposal_number`, `sequence_id`, `title`)
  VALUES ('starswap_dao', '1', '1', 'No');

INSERT INTO `proposal_voting_choice` (`dao_id`, `proposal_number`, `sequence_id`, `title`)
  VALUES ('starswap_dao', '1', '2', 'Abstain');

UPDATE `proposal` SET
  `block_state_root` = '0x15d2ba9603fd3163fa0f986bbcdec50922b5044b7ef9ea289501d2e358433865',
  `block_height` = '6852206'
  WHERE (`dao_id` = 'starcoin_dao') and (`proposal_number` = '1');

UPDATE `proposal` SET
  `block_state_root` = '0x15d2ba9603fd3163fa0f986bbcdec50922b5044b7ef9ea289501d2e358433865',
  `block_height` = '6852206'
  WHERE (`dao_id` = 'starswap_dao') and (`proposal_number` = '1');


UPDATE `dao_strategy` SET `voting_power_decimals` = '9'
  WHERE (`dao_id` = 'starcoin_dao') and (`strategy_id` = 'RESOURCES');

UPDATE `dao_strategy` SET `voting_power_decimals` = '9'
  WHERE (`dao_id` = 'starswap_dao') and (`strategy_id` = 'RESOURCES');

UPDATE `proposal` SET
  `description` = 'We\'re proposing an update to the #Starswap farm rewards:\n\n- Increase rewards to key farms\n\n| Pair     | Current Multiplier | Proposed New Multiplier | Change |\n| -------- | ------------------ | ----------------------- | ------ |\n| STC/STAR | 30                 | 50                      | +20    |\n\n'
  WHERE (`dao_id` = 'starswap_dao') and (`proposal_number` = '1');

UPDATE `dao_strategy` SET `voting_power_name` = 'STC'
  WHERE (`dao_id` = 'starcoin_dao') and (`strategy_id` = 'RESOURCES');

UPDATE `dao_strategy` SET `voting_power_name` = 'veStar'
  WHERE (`dao_id` = 'starswap_dao') and (`strategy_id` = 'RESOURCES');


-- --------------------- delete test data -----------------------

DELETE FROM `dao` WHERE (`dao_id` = 'test_dao_id');
DELETE FROM `dao` WHERE (`dao_id` = 'test_dao_id2');
DELETE FROM `dao` WHERE (`dao_id` = 'test_dao_id3');

DELETE FROM `dao_strategy` WHERE (`dao_id` = 'test_dao_id') and (`strategy_id` = 'RESOURCES');
DELETE FROM `dao_strategy` WHERE (`dao_id` = 'test_dao_id2') and (`strategy_id` = 'SBT');
DELETE FROM `dao_strategy` WHERE (`dao_id` = 'test_dao_id3') and (`strategy_id` = 'SBT');

DELETE FROM `dao_voting_resource` WHERE (`dao_id` = 'test_dao_id') and (`sequence_id` = '1');
DELETE FROM `dao_voting_resource` WHERE (`dao_id` = 'test_dao_id') and (`sequence_id` = '2');
DELETE FROM `dao_voting_resource` WHERE (`dao_id` = 'test_dao_id') and (`sequence_id` = '3');
DELETE FROM `dao_voting_resource` WHERE (`dao_id` = 'test_dao_id') and (`sequence_id` = '4');
DELETE FROM `dao_voting_resource` WHERE (`dao_id` = 'test_dao_id3') and (`sequence_id` = '1');

DELETE FROM `proposal` WHERE (`dao_id` = 'test_dao_id') and (`proposal_number` = '1');
DELETE FROM `proposal` WHERE (`dao_id` = 'test_dao_id') and (`proposal_number` = '2');
DELETE FROM `proposal` WHERE (`dao_id` = 'test_dao_id2') and (`proposal_number` = '1');
DELETE FROM `proposal` WHERE (`dao_id` = 'test_dao_id3') and (`proposal_number` = '1');

DELETE FROM `proposal_voting_choice` WHERE (`dao_id` = 'test_dao_id') and (`proposal_number` = '1') and (`sequence_id` = '0');
DELETE FROM `proposal_voting_choice` WHERE (`dao_id` = 'test_dao_id') and (`proposal_number` = '1') and (`sequence_id` = '1');
DELETE FROM `proposal_voting_choice` WHERE (`dao_id` = 'test_dao_id3') and (`proposal_number` = '1') and (`sequence_id` = '1');
DELETE FROM `proposal_voting_choice` WHERE (`dao_id` = 'test_dao_id3') and (`proposal_number` = '1') and (`sequence_id` = '2');


-- --------------------- update proposal voting end time -----------------------

UPDATE proposal SET voting_period_end = UNIX_TIMESTAMP('2022-07-25 18:00:00') * 1000 WHERE dao_id = 'starcoin_dao' AND proposal_number = 1;
UPDATE proposal SET voting_period_end = UNIX_TIMESTAMP('2022-07-25 18:00:00') * 1000 WHERE dao_id = 'starswap_dao' AND proposal_number = 1;
-- SELECT FROM_UNIXTIME(p.voting_period_end / 1000), p.* FROM proposal p;


-- --------------------- 2022-07-20 -----------------------

ALTER TABLE `proposal`
  CHANGE COLUMN `description` `description` VARCHAR(2000) NULL DEFAULT NULL ;

UPDATE `proposal` SET `description` = 'Starswap is a decentralized exchange (DEX) that is deployed in a smart contract network on the Starcoin blcokchain. The goal is to be the most feature-rich DEX on Starcoin network and Move ecosystem, with security and stability of assets.\n\nThe Grant will be used entirely for Starswap ecological construction, and it is planned to continuously buyback STAR in 6~12 months. The bought-back STAR will be used for ecological incentives and project construction through the governance of DAO to decide the usage. When there is no suitable ecological direction to invest in the case also does not exclude the direct burn.\n\nNote that as hosted by the DAO, no one has permission to misappropriate the grant, and the DAO is controlled by on-chain contracts and made public. To explain, before the DAO tools are perfected, a small amount of funds may be operated by manual strategies, but these operations will be communicated and confirmed by the community in advance.\n\nFor the sake of fairness and security, there is a 48-hour publicity period after the proposal is passed. This proposal will be implemented only if no proposals disputing this proposal are put on the voting process during the publicity period.\n\nThe grant\'s application link: https://grant.starcoin.org/#/vote?gid=45\n',
  `discussion` = 'https://grant.starcoin.org/#/vote?gid=45',
  `title` = 'Starswap Grant for ecological Construction',
  `voting_type` = 'YES_NO_ABSTAIN', `voting_method` = 'OFF_CHAIN'
  WHERE (`dao_id` = 'starcoin_dao') and (`proposal_number` = '1');

UPDATE `proposal` SET
  `circulating_voting_power` = '282837606000000000',
  `voting_turnout_threshold` = '2828376060000000'
  WHERE (`dao_id` = 'starcoin_dao') and (`proposal_number` = '1');

UPDATE `proposal` SET
  `circulating_voting_power` = '383351685143768',
  `voting_turnout_threshold` = '38335168514376'
  WHERE (`dao_id` = 'starswap_dao') and (`proposal_number` = '1');

UPDATE `proposal` SET `block_state_root` = '0x561dd037d47b6de49f913440dcb6d321f9740a7a3d006671504f3a8c74a0b9ca', `block_height` = '6888260' WHERE (`dao_id` = 'starcoin_dao') and (`proposal_number` = '1');
UPDATE `proposal` SET `block_state_root` = '0x561dd037d47b6de49f913440dcb6d321f9740a7a3d006671504f3a8c74a0b9ca', `block_height` = '6888260' WHERE (`dao_id` = 'starswap_dao') and (`proposal_number` = '1');

UPDATE `proposal_voting_choice` SET `title` = 'New Multiplier should be x30' WHERE (`dao_id` = 'starswap_dao') and (`proposal_number` = '1') and (`sequence_id` = '0');
UPDATE `proposal_voting_choice` SET `title` = 'New Multiplier should be x40' WHERE (`dao_id` = 'starswap_dao') and (`proposal_number` = '1') and (`sequence_id` = '1');
UPDATE `proposal_voting_choice` SET `title` = 'New Multiplier should be x50' WHERE (`dao_id` = 'starswap_dao') and (`proposal_number` = '1') and (`sequence_id` = '2');

UPDATE `proposal` SET `title` = 'Starswap Grant for Ecological Construction' WHERE (`dao_id` = 'starcoin_dao') and (`proposal_number` = '1');

UPDATE `proposal` SET `description` = 'We\'re proposing an update to the STC/STAR farm multiplier, increase reward to this #Starswap key farm.\n\n| Choice   | Current Multiplier | Proposed New Multiplier | Change |\n| -------- | ------------------ | ----------------------- | ------ |\n| Choice 1 | x30                | x30                     | +0     |\n| Choice 2 | x30                | x40                     | +10    |\n| Choice 3 | x30                | x50                     | +20    |\n\nAfter farm weighting adjustment, predict APR for each farm pool as follows. \n\nNote that the predicted APR and the actual APR may differ.\n\n**STC-STAR Multiplier** x30 (not changed) current APR(when submitting the proposal): \n\n| Token Pair | Farm TVL  | Multiplier | Farm APR         |\n| ---------- | --------- | ---------- | ---------------- |\n| STAR-STC   | 238308.50 | x30        | 55.02% ~ 137.56% |\n| FAI-STC    | 236235.83 | x10        | 17.70% ~ 44.25%  |\n| WEN-STC    | 64349.45  | x5         | 41.62% ~ 104.05% |\n| STC-USDT   | 84917.13  | x10        | 84.23% ~ 210.58% |\n\n **STC-STAR Multiplier**  x40  predicted APR: \n\n| Token Pair | Farm TVL  | New Multiplier | Predict Farm APR |\n| ---------- | --------- | -------------- | ---------------- |\n| STAR-STC   | 238308.50 | x40            | 62.06% ~ 155.16% |\n| FAI-STC    | 236235.83 | x10            | 14.97% ~ 37.44%  |\n| WEN-STC    | 64349.45  | x5             | 35.21% ~ 88.03%  |\n| STC-USDT   | 84917.13  | x10            | 71.26% ~ 178.15% |\n\n **STC-STAR Multiplier** x50 predicted APR：\n\n| Token Pair | Farm TVL  | New Multiplier | Predict Farm APR |\n| ---------- | --------- | -------------- | ---------------- |\n| STAR-STC   | 238308.50 | x50            | 67.34% ~ 168.36% |\n| FAI-STC    | 236235.83 | x10            | 12.94% ~ 32.35%  |\n| WEN-STC    | 64349.45  | x5             | 30.63% ~ 76.58%  |\n| STC-USDT   | 84917.13  | x10            | 61.57% ~ 153.93% |\n\n' WHERE (`dao_id` = 'starswap_dao') and (`proposal_number` = '1');

UPDATE `proposal` SET `submitted_at` = voting_period_start WHERE (`dao_id` = 'starcoin_dao') and (`proposal_number` = '1');
UPDATE `proposal` SET `submitted_at` = voting_period_start WHERE (`dao_id` = 'starswap_dao') and (`proposal_number` = '1');

UPDATE `proposal` SET `voting_period_end` = '1658664000000' WHERE (`dao_id` = 'starcoin_dao') and (`proposal_number` = '1');
UPDATE `proposal` SET `voting_period_end` = '1658664000000' WHERE (`dao_id` = 'starswap_dao') and (`proposal_number` = '1');

UPDATE `proposal_voting_choice` SET `title` = 'Multiplier should be x30' WHERE (`dao_id` = 'starswap_dao') and (`proposal_number` = '1') and (`sequence_id` = '0');


-- ---------------- insert test data into pulling_event_task ------------------
INSERT INTO `pulling_event_task` (
    `from_block_number`,
    `to_block_number`,
    `version`,
    `created_at`,
    `created_by`,
    `updated_at`,
    `updated_by`,
    `status`
) VALUES (
    '31709',
    '31709',
    '0',
    UNIX_TIMESTAMP()*1000,
    'admin',
    UNIX_TIMESTAMP()*1000,
    'admin',
    'CREATED'
);


-- ----------------- insert test proposal ------------------
INSERT INTO `proposal`
(`dao_id`,
`proposal_number`,
`block_state_root`,
`block_height`,
`description`,
`title`,
`voting_period_end`,
`voting_period_start`,
`voting_type`,
`submitted_at`,
`submitted_by`,
`voting_method`,
`circulating_voting_power`,
`voting_turnout_threshold`
) VALUES (
'starswap_dao',
'2',
'0x561dd037d47b6de49f913440dcb6d321f9740a7a3d006671504f3a8c74a0b9ca',
'6888260',
'When Starswap initially applied for the STAR buyback Grant, the plan was to set the 1 million STC buyback period to one year. In recent community discussions, some community users proposed to adjust the buyback period.\n\nThe buyback period and the daily cumulative bought-back STARs are the two most important indicators in the STAR buyback process. The final buyback period of 1 million STC Grant will be decided by this voting.\n\n| Choice   | Buyback Period | Bought-back STARs per Day(equivalent to STC) |\n| -------- | -------------- | -------------------------------------------- |\n| Choice 1 | 365 days       | 2741                                         |\n| Choice 2 | 270 days       | 3703                                         |\n| Choice 3 | 180 days       | 5555                                         |\n\n',
'Proposal for Starswap STAR Buyback Period',
'1656676800000',
'1658318400000',
'SINGLE_CHOICE',
'1657109961000',
'0xd117638e105403784bf6A92AA1276Ec1',
'OFF_CHAIN',
'383351685143768',
'38335168514376'
);

UPDATE `proposal`
SET
  `block_state_root` = '0x5bd8f8934ea0510a0a68dd82a1164fbe6570a153251653e1a05a1342195c1605',
  `block_height` = '7246693'
WHERE
  (`dao_id` = 'starswap_dao') and (`proposal_number` = '2');


UPDATE `proposal`
SET
    `submitted_at` = UNIX_TIMESTAMP() * 1000,
    `voting_period_start` = UNIX_TIMESTAMP() * 1000,
    `voting_period_end` = UNIX_TIMESTAMP('2022-08-15 04:00:00') * 1000
WHERE
    (`dao_id` = 'starswap_dao')
        AND (`proposal_number` = '2');
        
INSERT INTO `proposal_voting_choice` (`dao_id`, `proposal_number`, `sequence_id`, `title`) VALUES ('starswap_dao', '2', '0', 'Buyback Period 365 days');
INSERT INTO `proposal_voting_choice` (`dao_id`, `proposal_number`, `sequence_id`, `title`) VALUES ('starswap_dao', '2', '1', 'Buyback Period 270 days');
INSERT INTO `proposal_voting_choice` (`dao_id`, `proposal_number`, `sequence_id`, `title`) VALUES ('starswap_dao', '2', '2', 'Buyback Period 180 days');

UPDATE `proposal`
SET
  `circulating_voting_power` = '495054294041587',
  `voting_turnout_threshold` = '148516288212476'
WHERE
  (`dao_id` = 'starswap_dao') and (`proposal_number` = '2');


-- ----------------- insert test proposal ------------------

INSERT INTO `proposal` (`dao_id`, `proposal_number`, `block_state_root`, `block_height`, `description`,
  `title`, `voting_period_end`, `voting_period_start`,
  `voting_type`, `submitted_at`,
  `submitted_by`, `voting_method`,
  `circulating_voting_power`, `voting_turnout_threshold`
  ) VALUES (
  'starswap_dao', '3',
  '0x5bd8f8934ea0510a0a68dd82a1164fbe6570a153251653e1a05a1342195c1605',
  '7246693',
  'The community has voted to buyback STARs using a 1 million STC grant for a period of 270 days. The configuration will take effect when the buyback contract goes live.\n\nThe community now needs to vote on the usage of the bought-back STARs to maximize their value.\n\n| Choice   | Buyback Usage                         |\n| -------- | ------------------------------------- |\n| Choice 1 | Stake, locked for 90 days             |\n| Choice 2 | Increase STC/STAR swap pool liquidity |\n| Choice 3 | Burn                                  |\n| Choice 4 | Return to Starcoin DAO Treasury       |\n\n',
  'Proposal for Starswap STAR Buyback Usage',
  '1660482000000', '1660222800000',
  'SINGLE_CHOICE', '1660120055000',
  '0xd117638e105403784bf6A92AA1276Ec1',
  'OFF_CHAIN',
  '495054294041587',
  '148516288212476'
  );

-- ----------------- update proposal block info. ------------------
UPDATE `proposal`
SET
  `block_state_root` = '0x5dbcfee69ac97324430f3730891da82968932193a8b9e8c0f916fb78c03e9879',
  `block_height` = '7368907'
WHERE
  (`dao_id` = 'starswap_dao') and (`proposal_number` = '3');

-- ----------------- update proposal timestamps ------------------
UPDATE `proposal`
SET
    `submitted_at` = UNIX_TIMESTAMP() * 1000,
    `voting_period_start` = UNIX_TIMESTAMP() * 1000,
    `voting_period_end` = UNIX_TIMESTAMP('2022-08-22 04:00:00') * 1000
WHERE
    (`dao_id` = 'starswap_dao')
        AND (`proposal_number` = '3');

-- ----------------- update proposal voting info. ------------------
UPDATE `proposal`
SET
  `circulating_voting_power` = '540000000000000',
  `voting_turnout_threshold` = '162000000000000'
WHERE
  (`dao_id` = 'starswap_dao') and (`proposal_number` = '3');

INSERT INTO `proposal_voting_choice` (`dao_id`, `proposal_number`, `sequence_id`, `title`) VALUES ('starswap_dao', '3', '0', 'Stake, locked for 90 days');
INSERT INTO `proposal_voting_choice` (`dao_id`, `proposal_number`, `sequence_id`, `title`) VALUES ('starswap_dao', '3', '1', 'Increase STC/STAR swap pool liquidity');
INSERT INTO `proposal_voting_choice` (`dao_id`, `proposal_number`, `sequence_id`, `title`) VALUES ('starswap_dao', '3', '2', 'Burn');
INSERT INTO `proposal_voting_choice` (`dao_id`, `proposal_number`, `sequence_id`, `title`) VALUES ('starswap_dao', '3', '3', 'Return to Starcoin DAO Treasury');

