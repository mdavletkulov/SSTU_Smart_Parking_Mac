package com.example.smartParking.model.domain;

import org.hibernate.annotations.Nationalized;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "subdivision")
public class Subdivision {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @ManyToOne
    @JoinColumn(name = "division_id")
    private Division division;
    @Nationalized
    @NotBlank(message = "Название кафедры не может быть пустым")
    @Length(max = 100, message = "Название кафедры слишком длинное")
    private String name;
    @Enumerated(EnumType.STRING)
    private TypeJobPosition typeJobPosition;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Division getDivision() {
        return division;
    }

    public void setDivision(Division division) {
        this.division = division;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
