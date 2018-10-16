package buildtools.service;

public class SQLProfile {
    private long id;
    private String userName;
    private String city;
    private String country;
    private long mobile;

    public SQLProfile(long id, String userName, String city, String country, long mobile) {
        this.id = id;
        this.userName = userName;
        this.city = city;
        this.country = country;
        this.mobile = mobile;
    }

    public long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public long getMobile() {
        return mobile;
    }

    @Override
    public String toString() {
        return "SQLProfile{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", mobile=" + mobile +
                '}';
    }
}
