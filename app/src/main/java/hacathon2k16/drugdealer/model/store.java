package hacathon2k16.drugdealer.model;

/**
 * Created by vlastachu on 19/11/16.
 */

public class Store {
    private String name;
    private double longitude;
    private double latitude;
    private String phone;
    private String workRange;

    public Store(String name, double longitude, double latitude, String phone, String workRange) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.phone = phone;
        this.workRange = workRange;
    }

    public String getName() {
        return name;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getPhone() {
        return phone;
    }

    public String getWorkRange() {
        return workRange;
    }
}
