package com.proatcoding.pets.models;

import java.io.Serializable;

/**
 * @author Yalantis
 */
public class ImageGrid implements Serializable {
    private int avatar;

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }
}
