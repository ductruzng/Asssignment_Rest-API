package trungndph39729.fpoly.assignment.Domain;

import java.io.Serializable;

public class CategoryDomain implements Serializable {
    private String title;
    private String _id;
    private String picUrl;

    public CategoryDomain() {
    }

    public CategoryDomain(String _id, String picUrl,String title) {
        this.title = title;
        this._id = _id;
        this.picUrl = picUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String get_Id() {
        return _id;
    }

    public void set_Id(String _id) {
        this._id = _id;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
}
