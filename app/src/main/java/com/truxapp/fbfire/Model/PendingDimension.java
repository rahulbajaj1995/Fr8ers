package com.truxapp.fbfire.Model;

import java.io.Serializable;

/**
 * Created by hp on 6/10/17.
 */

public class PendingDimension implements Serializable {
    private int length;
    private int breadth;
    private int height;
    private int no_of_box;

    public int getNo_of_box() {
        return no_of_box;
    }

    public void setNo_of_box(int no_of_box) {
        this.no_of_box = no_of_box;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getBreadth() {
        return breadth;
    }

    public void setBreadth(int breadth) {
        this.breadth = breadth;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
