package com.example.smartParking.model.domain;

import org.hibernate.annotations.Nationalized;

public enum TypeJobPosition {
    PPS("ППС"), AUP("АУП");

    public String type;

    TypeJobPosition(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
