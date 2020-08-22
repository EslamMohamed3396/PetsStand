package com.proatcoding.pets.models;

import java.io.Serializable;
import java.util.ArrayList;

public class OffersDTO implements Serializable {

    String id = "";
    String title = "";
    String description = "";
    String image = "";
    String c_id = "";
    String discount = "";
    String end_date = "";
    String p_name = "";
    String p_pet_type = "";
    String p_type = "";
    String start_date = "";
    String status = "";
    String p_id = "";

    public ArrayList<Products> products;

    public ArrayList<Products> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Products> products) {
        this.products = products;
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

    public String getP_id() {
        return p_id;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getC_id() {
        return c_id;
    }

    public void setC_id(String c_id) {
        this.c_id = c_id;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public String getP_pet_type() {
        return p_pet_type;
    }

    public void setP_pet_type(String p_pet_type) {
        this.p_pet_type = p_pet_type;
    }

    public String getP_type() {
        return p_type;
    }

    public void setP_type(String p_type) {
        this.p_type = p_type;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public class Products implements Serializable {
        String p_id = "";
        String p_code = "";
        String cat_id = "";
        String p_name = "";
        String p_description = "";
        String p_type = "";
        String p_pet_type = "";
        String p_cat_type = "";
        String p_rate = "";
        String p_rate_sale = "";
        String quantity = "";
        String gross_amt = "";
        String discount = "";
        String net_amt = "";
        String shipping_cost = "";
        String country = "";
        String img_path = "";
        String color = "";
        String size = "";
        String weight = "";
        String deleted = "";
        String status = "";
        String product_rating = "";
        String c_id = "";
        String mandatory = "";
        String created_at = "";
        String updated_at = "";
        String c_name = "";

        public String getP_id() {
            return p_id;
        }

        public void setP_id(String p_id) {
            this.p_id = p_id;
        }

        public String getP_code() {
            return p_code;
        }

        public void setP_code(String p_code) {
            this.p_code = p_code;
        }

        public String getCat_id() {
            return cat_id;
        }

        public void setCat_id(String cat_id) {
            this.cat_id = cat_id;
        }

        public String getP_name() {
            return p_name;
        }

        public void setP_name(String p_name) {
            this.p_name = p_name;
        }

        public String getP_description() {
            return p_description;
        }

        public void setP_description(String p_description) {
            this.p_description = p_description;
        }

        public String getP_type() {
            return p_type;
        }

        public void setP_type(String p_type) {
            this.p_type = p_type;
        }

        public String getP_pet_type() {
            return p_pet_type;
        }

        public void setP_pet_type(String p_pet_type) {
            this.p_pet_type = p_pet_type;
        }

        public String getP_cat_type() {
            return p_cat_type;
        }

        public void setP_cat_type(String p_cat_type) {
            this.p_cat_type = p_cat_type;
        }

        public String getP_rate() {
            return p_rate;
        }

        public void setP_rate(String p_rate) {
            this.p_rate = p_rate;
        }

        public String getP_rate_sale() {
            return p_rate_sale;
        }

        public void setP_rate_sale(String p_rate_sale) {
            this.p_rate_sale = p_rate_sale;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getGross_amt() {
            return gross_amt;
        }

        public void setGross_amt(String gross_amt) {
            this.gross_amt = gross_amt;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getNet_amt() {
            return net_amt;
        }

        public void setNet_amt(String net_amt) {
            this.net_amt = net_amt;
        }

        public String getShipping_cost() {
            return shipping_cost;
        }

        public void setShipping_cost(String shipping_cost) {
            this.shipping_cost = shipping_cost;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getImg_path() {
            return img_path;
        }

        public void setImg_path(String img_path) {
            this.img_path = img_path;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getDeleted() {
            return deleted;
        }

        public void setDeleted(String deleted) {
            this.deleted = deleted;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getProduct_rating() {
            return product_rating;
        }

        public void setProduct_rating(String product_rating) {
            this.product_rating = product_rating;
        }

        public String getC_id() {
            return c_id;
        }

        public void setC_id(String c_id) {
            this.c_id = c_id;
        }

        public String getMandatory() {
            return mandatory;
        }

        public void setMandatory(String mandatory) {
            this.mandatory = mandatory;
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

        public String getC_name() {
            return c_name;
        }

        public void setC_name(String c_name) {
            this.c_name = c_name;
        }
    }
}

