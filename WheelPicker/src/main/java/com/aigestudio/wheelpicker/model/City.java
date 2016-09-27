package com.aigestudio.wheelpicker.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/9/14 0014.
 */
public class City implements Serializable {
    public String name;
    public List<String> area;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getArea() {
        return area;
    }

    public void setArea(List<String> area) {
        this.area = area;
    }
}
