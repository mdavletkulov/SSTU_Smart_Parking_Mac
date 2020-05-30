package com.example.smartParking.model.response;

import java.util.List;

public class CarNumberResponse {

    public List<String> numbers;

    public List<String> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<String> number) {
        this.numbers = number;
    }
}
