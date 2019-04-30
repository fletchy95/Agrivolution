package com.example.vanda.agrivolution;

public class Comments {
    private String Comment;
    private String PostedBy;
    private String UserId;


    public Comments(){

    }
    public Comments(String Comment, String PostedBy, String Id){
        this.Comment = Comment;
        this.PostedBy = PostedBy;
        this.UserId = Id;
    }
    public String getComment() {
        return Comment;
    }

    public void setComment(String Comment) {
        this.Comment = Comment;
    }

    public String getPostedBy() {
        return PostedBy;
    }

    public void setPostedBy(String PostedBy) {
        this.PostedBy = PostedBy;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String Id) {
        this.UserId = Id;
    }

}
