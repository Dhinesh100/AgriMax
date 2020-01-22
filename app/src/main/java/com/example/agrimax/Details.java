package com.example.agrimax;

public class Details {

    String dist;
    String yr;
    String ssn;
    String crop;
    String area;

    public Details(String dist, String yr, String ssn, String crop, String area){
        this.dist=dist;
        this.yr=yr;
        this.ssn=ssn;
        this.crop=crop;
        this.area=area;
    }

    public String getDist(){
        return dist;
    }

    public String getYr(){
        return yr;
    }

    public String getSsn(){
        return ssn;
    }

    public String getCrop(){
        return crop;
    }

    public String getArea(){
        return area;
    }
}
