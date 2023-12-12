package com.sei.gamerknew.entities;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class Mark {
    @TableId
    private String markid;
    private int status;
    private double rating;
    private String comment;

    private String userid;
    private String gameid;

    public Date getComment_date() {
        return comment_date;
    }

    public void setComment_date(Date comment_date) {
        this.comment_date = comment_date;
    }

    private Date comment_date;




    public Mark() {
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


    @Override
    public String toString() {
        return "Mark{" +
                "markid='" + markid + '\'' +
                ", status=" + status +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", userid=" + userid +
                ", gameid=" + gameid +
                ", comment_date=" + comment_date +
                '}';
    }
}
