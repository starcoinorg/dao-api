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

