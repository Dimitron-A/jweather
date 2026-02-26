/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import org.joda.time.DateTime;


@Entity
public class WeatherSnapshot implements Serializable {

    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        Long oldId = this.id;
        this.id = id;
        changeSupport.firePropertyChange("id", oldId, id);
    }

    Date dt;
    
    public Date getDt() {
        return dt;
    }
    
    public void setDt(Date dt) {
        Date oldDt = this.dt;
        this.dt = dt;
        changeSupport.firePropertyChange("dt", oldDt, dt);
    }

    
    @ManyToOne
    private City city;

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        City oldCity = this.city;
        this.city = city;
        changeSupport.firePropertyChange("city", oldCity, city);
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        float oldTemperature = this.temperature;
        this.temperature = temperature;
        changeSupport.firePropertyChange("temperature", oldTemperature, temperature);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        String oldDescription = this.description;
        this.description = description;
        changeSupport.firePropertyChange("description", oldDescription, description);
    }

    public int getClouds() {
        return clouds;
    }

    public void setClouds(int clouds) {
        int oldClouds = this.clouds;
        this.clouds = clouds;
        changeSupport.firePropertyChange("clouds", oldClouds, clouds);
    }

    public float getWind_speed() {
        return wind_speed;
    }

    public void setWind_speed(float wind_speed) {
        float oldWind_speed = this.wind_speed;
        this.wind_speed = wind_speed;
        changeSupport.firePropertyChange("wind_speed", oldWind_speed, wind_speed);
    }

    public float getRain_mm() {
        return rain_mm;
    }

    public void setRain_mm(float rain_mm) {
        float oldRain_mm = this.rain_mm;
        this.rain_mm = rain_mm;
        changeSupport.firePropertyChange("rain_mm", oldRain_mm, rain_mm);
    }

    public float getSnow_mm() {
        return snow_mm;
    }

    public void setSnow_mm(float snow_mm) {
        float oldSnow_mm = this.snow_mm;
        this.snow_mm = snow_mm;
        changeSupport.firePropertyChange("snow_mm", oldSnow_mm, snow_mm);
    }

    private float temperature;
    private String description;
    private int clouds;
    private float wind_speed;
    private float rain_mm;
    private float snow_mm;
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WeatherSnapshot)) {
            return false;
        }
        WeatherSnapshot other = (WeatherSnapshot) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.WeatherSnapshot[ id=" + id + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
