package kr.co.company.it_maverick;

public class UserProfile {
    private String username;
    private String gender;
    private String profileImageUrl;

    public UserProfile() {

    }

    public UserProfile(String username, String gender, String profileImageUrl) {
        this.username = username;
        this.gender = gender;
        this.profileImageUrl = profileImageUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}
