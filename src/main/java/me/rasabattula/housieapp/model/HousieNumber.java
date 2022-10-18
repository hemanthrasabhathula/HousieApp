package me.rasabattula.housieapp.model;

public class HousieNumber {

    public int number;
    public boolean state;

    public HousieNumber(int number, boolean state) {
        this.number = number;
        this.state = state;
    }

    public HousieNumber() {
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
