# README

## Generating event type BCS serialization/deserialization code

```shell
mvn exec:java -Dexec.mainClass="org.starcoin.serde.format.cli.SerdeGenJava" -X -Dexec.args="-w {PATH-TO}/starcoinorg/dao-api --onlyRetainDependenciesOfLast 1 --targetSourceDirectoryPath ./src/main/java {PATH-TO}/starcoinorg/starcoin/etc/starcoin_types.yml:org.starcoin.types {PATH-TO}/starcoinorg/starcoin/etc/onchain_events.yml:org.starcoin.types.event {PATH-TO}/starcoinorg/dao-api/src/main/resources/serde-format/dao_types_event.yaml:org.starcoin.dao.types.event"
```
