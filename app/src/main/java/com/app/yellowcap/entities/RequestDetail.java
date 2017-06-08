package com.app.yellowcap.entities;

import java.util.ArrayList;

/**
 * Created by saeedhyder on 6/7/2017.
 */

public class RequestDetail {

    int id;
    int service_id;
    String discription;
    String address;
    String full_address;
    String date;
    String time;
    String payment_type;
    int status;
    String estimate_from;
    String estimate_to;
    String total;
    String user_id;
    String message;
    String parent_id;
    String created_at;
    String updated_at;
    ArrayList<serviceList> servics_list;
    ServiceDetail service_detail;
    UserDetail user_detail;
    ArrayList<RequestDetail> sub_request;

    private ArrayList<ImageDetailEnt> image_detail;

    public int getId() {
        return id;
    }


    public ArrayList<ImageDetailEnt> getImage_detail() {
        return image_detail;
    }

    public void setImage_detail(ArrayList<ImageDetailEnt> image_detail) {
        this.image_detail = image_detail;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getService_id() {
        return service_id;
    }

    public void setService_id(int service_id) {
        this.service_id = service_id;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFull_address() {
        return full_address;
    }

    public void setFull_address(String full_address) {
        this.full_address = full_address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getEstimate_from() {
        return estimate_from;
    }

    public void setEstimate_from(String estimate_from) {
        this.estimate_from = estimate_from;
    }

    public String getEstimate_to() {
        return estimate_to;
    }

    public void setEstimate_to(String estimate_to) {
        this.estimate_to = estimate_to;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
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

    public ArrayList<serviceList> getServics_list() {
        return servics_list;
    }

    public ServiceDetail getService_detail() {
        return service_detail;
    }

    public void setService_detail(ServiceDetail service_detail) {
        this.service_detail = service_detail;
    }

    public void setServics_list(ArrayList<serviceList> servics_list) {
        this.servics_list = servics_list;
    }

    public UserDetail getUser_detail() {
        return user_detail;
    }

    public void setUser_detail(UserDetail user_detail) {
        this.user_detail = user_detail;
    }

    public ArrayList<RequestDetail> getSub_request() {
        return sub_request;
    }

    public void setSub_request(ArrayList<RequestDetail> sub_request) {
        this.sub_request = sub_request;
    }
}
