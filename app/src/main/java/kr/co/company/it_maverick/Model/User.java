package kr.co.company.it_maverick.Model;

public class User {

    private String userid;
    private String pw;
    private String username;
    private String imageurl;
    private String sex;
    private String category;



    public User(String userid, String username, String imageurl, String sex, String category,
                String pw) {
        this.userid = userid;
        this.pw = pw;
        this.username = username;
        this.imageurl = imageurl;
        this.sex = sex;
        this.category = category;
    }

    public User() {

    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


}

