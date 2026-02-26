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
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;


@Entity
public class City implements Serializable {

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


    private String name;
    
    public String getName() {
        return name;
    }
    

      
    public void setName(String name) {
        String oldName = this.name;
        this.name = name;
        changeSupport.firePropertyChange("name", oldName, name);
    }
    
    private Long weatherId;

    public Long getWeatherId() {
        return weatherId;
    }
    
    public void setWeatherId(Long weatherId) {
        Long oldWeatherId = this.weatherId;
        this.weatherId = weatherId;
        changeSupport.firePropertyChange("weatherId", oldWeatherId, weatherId);
    }
    
    @OneToMany(mappedBy = "city")
    private Set<WeatherSnapshot> weatherSnapshots = new HashSet<WeatherSnapshot>(
			0);
    
    public Set<WeatherSnapshot> getWeatherSnapshots() {
            return this.weatherSnapshots;
    }
    
    public void setWeatherSnapshots(Set<WeatherSnapshot> weatherSnapshots) {
            this.weatherSnapshots = weatherSnapshots;
    }

    @OneToMany(mappedBy = "city")
    private Set<WeatherForecast> weatherForecasts = new HashSet<WeatherForecast>(
			0);
    
    public Set<WeatherForecast> getWeatherForecasts() {
            return this.weatherForecasts;
    }
    
    public void setWeatherForecasts(Set<WeatherForecast> weatherForecasts) {
            this.weatherForecasts = weatherForecasts;
    }

    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof City)) {
            return false;
        }
        City other = (City) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
