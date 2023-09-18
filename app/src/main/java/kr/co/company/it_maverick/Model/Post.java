package kr.co.company.it_maverick.Model;

public class Post {
    private String postid;
    private String username;
    private String userimage;



    private String postimage;
    private String description;
    private String publisher;
    private String category;




    public Post(String username, String userimage, String postid, String postimage, String description, String publisher, String category) {
        this.postid = postid;
        this.username = username;
        this.postimage = postimage;
        this.description = description;
        this.publisher = publisher;
        this.category = category;
        this.userimage = userimage;
    }

    public Post() {
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getUserimage() {
        return userimage;
    }

    public void setUserimage(String userimage) {
        this.userimage = userimage;
    }
    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getPostimage() {
        return postimage;
    }

    public void setPostimage(String postimage) {
        this.postimage = postimage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
