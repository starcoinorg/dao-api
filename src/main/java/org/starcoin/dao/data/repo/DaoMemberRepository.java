package org.starcoin.dao.data.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.starcoin.dao.data.model.DaoMember;
import org.starcoin.dao.data.model.DaoMemberId;

public interface DaoMemberRepository extends JpaRepository<DaoMember, DaoMemberId> {


}
