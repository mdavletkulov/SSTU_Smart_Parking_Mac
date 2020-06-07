package com.example.Poller.domain;

import org.hibernate.annotations.Nationalized;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "automobile")
public class Automobile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Nationalized
    @NotBlank(message = "Номер машины не может быть пустым")
    @Length(max = 12, message = "Значение номера машины слишком длинное")
    private String number;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id")
    private Person person;
    @Nationalized
    @Length(max = 50, message = "Значение модели машины слишком длинное")
    private String model;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "color_id")
    private Color color;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getInfo() {
        return model + "; Цвет: " + color.getName();
    }
}
