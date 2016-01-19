package com.zk.wechat.util;

/**
 * com.zk.wechat.util.Button
 *
 * @author: Administrator
 * @Date: 2016/1/16
 * @Time: 20:18
 */
public class Button {

    private String type;
    private String name;
    private String key;
    private String url;
    private Button[] sub_button;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Button[] getSub_button() {
        return sub_button;
    }

    public void setSub_button(Button[] sub_button) {
        this.sub_button = sub_button;
    }
}
