package com.example.quakereport;

public class WordsList
{
    double magnitude;
    String url;
    String city;
    private long mTimeInMilliseconds;
    public WordsList(double magnitude, String city, long timeinMilliseconds,String url)
    {
        this.magnitude = magnitude;
        this.mTimeInMilliseconds=timeinMilliseconds;
        this.city = city;
        this.url=url;
    }

    public long getmTimeInMilliseconds() {
        return mTimeInMilliseconds;
    }

    public void setmTimeInMilliseconds(long mTimeInMilliseconds) {
        this.mTimeInMilliseconds = mTimeInMilliseconds;
    }

    public double getMagnitude()
    {
        return magnitude;
    }

    public void setMagnitude(double magnitude)
    {
        this.magnitude = magnitude;
    }
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
