package com.mandeep.officialcode.Utils;

/**
 * Created by MANDEEP on 17-12-2017.
 */

public class User {
    public String name;
    public String datsmeId;
    public String email;
    public int age;
    public double lattitude;
    public double longitude;
    public String photoUrl;

    public User() {
    }

    public User(String name, String datsmeId, String email, int age, double lattitude, double longitude,String photoUrl) {
        this.name = name;
        this.datsmeId = datsmeId;
        this.email = email;
        this.age=age;
        this.lattitude = lattitude;
        this.longitude = longitude;
        this.photoUrl = photoUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDatsmeId() {
        return datsmeId;
    }

    public void setDatsmeId(String datsmeId) {
        datsmeId = datsmeId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }


    public double getLattitude() {
        return lattitude;
    }

    public void setLattitude(double lattitude) {
        this.lattitude = lattitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", DatsmeId='" + datsmeId + '\'' +
                ", email='" + email + '\'' +
                ", age='" + age + '\'' +
                ", lattitude=" + lattitude +
                ", longitude=" + longitude +
                ", photoUrl='" + photoUrl + '\'' +
                '}';
    }
}
