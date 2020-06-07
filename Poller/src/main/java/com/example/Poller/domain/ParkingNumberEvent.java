package com.example.Poller.domain;

import java.util.List;

public class ParkingNumberEvent {

    private Integer placeNum;
    private List<String> numbers;
    private String fileName;

    public ParkingNumberEvent(Integer placeNum, List<String> numbers, String fileName) {
        this.placeNum = placeNum;
        this.numbers = numbers;
        this.fileName = fileName;
    }

    public Integer getPlaceNum() {
        return placeNum;
    }

    public void setParkingId(Integer placeNum) {
        this.placeNum = placeNum;
    }

    public List<String> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<String> numbers) {
        this.numbers = numbers;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
