package com.example.a212.project.object;

/**
 * Created by 213 on 2017-07-04.
 */

public class Bbs {
    String Content;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getBbsAvailable() {
        return bbsAvailable;
    }

    public void setBbsAvailable(String bbsAvailable) {
        this.bbsAvailable = bbsAvailable;
    }

    String bbsAvailable;
    public Bbs(int bbsID, String bbsTitle, String userID, String bbsDate, String Content , String bbsAvailable) {
        this.bbsID = bbsID;
        this.bbsTitle = bbsTitle;
        this.userID = userID;
        this.bbsDate = bbsDate;
        this.Content = Content;
        this.bbsAvailable=bbsAvailable;
    }

    int bbsID;

    public int getBbsID() {
        return bbsID;
    }

    public void setBbsID(int bbsID) {
        this.bbsID = bbsID;
    }

    public String getBbsTitle() {
        return bbsTitle;
    }

    public void setBbsTitle(String bbsTitle) {
        this.bbsTitle = bbsTitle;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getBbsDate() {
        return bbsDate;
    }

    public void setBbsDate(String bbsDate) {
        this.bbsDate = bbsDate;
    }

    String bbsTitle;
    String userID;
    String bbsDate;

}
