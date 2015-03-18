package shiratsu.co.jp.cafesagashi.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by shiratsu on 15/03/14.
 */
@Table(name = "cafes")
public class Cafes extends Model {


    @Column(name = "id")
    public String cafeid;

    @Column(name = "store_name")
    public String store_name;

    @Column(name = "log_image")
    public String log_image;

    @Column(name = "address")
    public String address;

    @Column(name = "lat")
    public String lat;

    @Column(name = "lng")
    public String lng;

    @Column(name = "url")
    public String url;

    @Column(name = "created_at")
    public String created_at;

    @Column(name = "updated_at")
    public String updated_at;

    public String getCafeid() {
        return cafeid;
    }

    public void setCafeid(String cafeid) {
        this.cafeid = cafeid;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getLog_image() {
        return log_image;
    }

    public void setLog_image(String log_image) {
        this.log_image = log_image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public Cafes() {
        super();

    }

    public static List<Cafes> getCafesFromMap(float fromlat, float fromlng, float tolat, float tolng) {
        return new Select()
                .from(Cafes.class)
                .where("lat >= "+fromlat+" and lat <= "+tolat+" and lng >= "+fromlng+" and lng <="+tolng)
                .execute();
    }
}