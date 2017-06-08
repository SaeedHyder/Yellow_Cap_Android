package com.app.yellowcap.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import static com.app.yellowcap.helpers.DateHelper.getLocalTimeDate;

/**
 * Created on 6/3/2017.
 */

public class ServiceEnt {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("parent_id")
    @Expose
    private Integer parentId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("deleted_at")
    @Expose
    private String deletedAt;
    @SerializedName("service_image")
    @Expose
    private String serviceImage;
    public ServiceEnt(Integer id, String title, String image, Integer parentId, String createdAt, String updatedAt, String deletedAt, String serviceImage) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.parentId = parentId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.serviceImage = serviceImage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = getLocalTimeDate(createdAt);
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = getLocalTimeDate(updatedAt);
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = getLocalTimeDate(deletedAt);
    }

    public String getServiceImage() {
        return serviceImage;
    }

    public void setServiceImage(String serviceImage) {
        this.serviceImage = serviceImage;
    }

    @Override
    public boolean equals(Object object) {
        boolean isEqual = false;

        if (object != null && object instanceof ServiceEnt) {
            isEqual = (this.id == ((ServiceEnt) object).id);
        }

        return isEqual;
    }

    @Override
    public int hashCode() {
        return this.id;
    }
}
