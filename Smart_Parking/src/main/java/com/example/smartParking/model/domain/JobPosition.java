package com.example.smartParking.model.domain;

import org.hibernate.annotations.Nationalized;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "job_position")
public class JobPosition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Enumerated(EnumType.STRING)
    private TypeJobPosition typeJobPosition;
    @Nationalized
    @NotBlank(message = "Название должности не может быть пустой")
    @Length(max = 60, message = "Значение должности слишком длинное")
    private String namePosition;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTypeJobPosition() {
        if (typeJobPosition.equals(TypeJobPosition.AUP))
            return "АУП";
        else if (typeJobPosition.equals(TypeJobPosition.PPS))
            return "ППС";
        return null;
    }

    public TypeJobPosition getTypeJobPositionEn() {
        return typeJobPosition;
    }

    public void setTypeJobPosition(TypeJobPosition typeJobPosition) {
        this.typeJobPosition = typeJobPosition;
    }

    public String getNamePosition() {
        return namePosition;
    }

    public void setNamePosition(String namePosition) {
        this.namePosition = namePosition;
    }
}
