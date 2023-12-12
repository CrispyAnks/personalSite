package com.sei.gamerknew.entities;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class Game {
    @TableId
    private String gameid;
    private String name;
    private Date reldate;
    private String infoid;
    private String tagid;
    @TableField(exist = false)
    private double rating;

    @TableField(exist = false)
    private int platform;
    @TableField(exist = false)
    private String producer;
    @TableField(exist = false)
    private String mainstaff;
    @TableField(exist = false)
    private String intro;
    @TableField(exist = false)
    private String imgsrc;
    @TableField(exist = false)
    private int tag01;
    @TableField(exist = false)
    private int tag02;
    @TableField(exist = false)
    private int tag03;

    public Game() {
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }



    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    @Override
    public String toString() {
        return "Game{" +
                "gameid='" + gameid + '\'' +
                ", name='" + name + '\'' +
                ", reldate=" + reldate +
                ", infoid='" + infoid + '\'' +
                ", tagid='" + tagid + '\'' +
                ", rating=" + rating +
                ", platform=" + platform +
                ", producer='" + producer + '\'' +
                ", mainstaff='" + mainstaff + '\'' +
                ", intro='" + intro + '\'' +
                ", imgsrc='" + imgsrc + '\'' +
                ", tag01=" + tag01 +
                ", tag02=" + tag02 +
                ", tag03=" + tag03 +
                '}';
    }

    public void complete(Info info, Tag tag,Double rating){
        this.setIntro(info.getIntro());
        this.setImgsrc(info.getImgsrc());
        this.setPlatform(info.getPlatform());
        this.setProducer(info.getProducer());
        this.setMainstaff(info.getMainstaff());
        this.setTag01(tag.getTag01());
        this.setTag02(tag.getTag02());
        this.setTag03(tag.getTag03());
        this.rating = rating;
    }
}
