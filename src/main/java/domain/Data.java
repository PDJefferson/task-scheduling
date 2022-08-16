package domain;


import java.util.ArrayList;


public class Data {
    private ArrayList<String[]> data;

    public Data(ArrayList<String[]> data) {
        this.data = data;
    }

    public Data() {

    }

    public ArrayList<String[]> getData() {
        return data;
    }

    public void setData(ArrayList<String[]> data) {
        this.data = data;
    }
}
