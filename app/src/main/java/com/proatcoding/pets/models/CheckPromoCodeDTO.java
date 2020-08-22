package com.proatcoding.pets.models;

import java.io.Serializable;

public class CheckPromoCodeDTO implements Serializable {
    String promo_code_id = "";
    String title = "";
    String description = "";
    String promo_code = "";
    String image = "";
    String figure = "";
    String start_date = "";
    String end_date = "";
    String promo_code_type = "";
    String type = "";
    String status = "";
    String created_at = "";
    String updated_at = "";
    String final_price = "";
    String discount = "";


    public String getPromo_code_id() {
        return promo_code_id;
    }

    public void setPromo_code_id(String promo_code_id) {
        this.promo_code_id = promo_code_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPromo_code() {
        return promo_code;
    }

    public void setPromo_code(String promo_code) {
        this.promo_code = promo_code;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFigure() {
        return figure;
    }

    public void setFigure(String figure) {
        this.figure = figure;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getPromo_code_type() {
        return promo_code_type;
    }

    public void setPromo_code_type(String promo_code_type) {
        this.promo_code_type = promo_code_type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getFinal_price() {
        return final_price;
    }

    public void setFinal_price(String final_price) {
        this.final_price = final_price;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }
}
