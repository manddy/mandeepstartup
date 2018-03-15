package com.mandeep.officialcode;

/**
 * Created by MANDEEP on 17-12-2017.
 */

public class Userinformation {
    public String name;
    public String DatsmeId;
    public String email;
    public String age;
    public String password;
    public double lattitude;
    public double longitude;

    public Userinformation() {
    }

    Userinformation(String name, String DatsmeId, String email,String age, String password, double lattitude, double longitude) {
        this.name = name;
        this.DatsmeId = DatsmeId;
        this.email = email;
        this.age=age;
        this.password = password;
        this.lattitude = lattitude;
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "Userinformation{" +
                "name='" + name + '\'' +
                ", DatsmeId='" + DatsmeId + '\'' +
                ", email='" + email + '\'' +
                ", age='" + age + '\'' +
                ", password='" + password + '\'' +
                ", lattitude=" + lattitude +
                ", longitude=" + longitude +
                '}';
    }
}
