package org.starcoin.dao.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class DaoPurpose {
    @Id
    @Column(length = 100)
    private String purposeId;

    public String getPurposeId() {
        return purposeId;
    }

    public void setPurposeId(String purposeId) {
        this.purposeId = purposeId;
    }
}
