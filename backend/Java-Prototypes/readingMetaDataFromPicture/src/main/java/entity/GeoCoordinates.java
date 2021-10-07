package entity;

public class GeoCoordinates {

    private String latitude;
    private String longitude;

    public GeoCoordinates() {
    }

    public GeoCoordinates(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "Längengrad: " + longitude
                + "\n" +
                "Breitengrad: " + latitude;
    }
}
