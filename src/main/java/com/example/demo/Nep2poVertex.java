package com.example.demo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigInteger;

@Entity
@Table(name = "nep_2po_vertex")
public class Nep2poVertex {

    @Id
    Integer id;

    @Column
    Integer clazz;

    @Column
    BigInteger osm_id;

    @Column
    String osm_name;

    @Column
    Integer ref_count;

    @Column
    String restrictions;

    @Column
    String geom_vertex;

    public Nep2poVertex() {  }

    public Nep2poVertex(Integer id, Integer clazz, BigInteger osm_id, String osm_name, Integer ref_count, String restrictions, String geom_vertex) {
        this.id = id;
        this.clazz = clazz;
        this.osm_id = osm_id;
        this.osm_name = osm_name;
        this.ref_count = ref_count;
        this.restrictions = restrictions;
        this.geom_vertex = geom_vertex;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClazz() {
        return clazz;
    }

    public void setClazz(Integer clazz) {
        this.clazz = clazz;
    }

    public BigInteger getOsm_id() {
        return osm_id;
    }

    public void setOsm_id(BigInteger osm_id) {
        this.osm_id = osm_id;
    }

    public String getOsm_name() {
        return osm_name;
    }

    public void setOsm_name(String osm_name) {
        this.osm_name = osm_name;
    }

    public Integer getRef_count() {
        return ref_count;
    }

    public void setRef_count(Integer ref_count) {
        this.ref_count = ref_count;
    }

    public String getRestrictions() {
        return restrictions;
    }

    public void setRestrictions(String restrictions) {
        this.restrictions = restrictions;
    }

    public String getGeom_vertex() {
        return geom_vertex;
    }

    public void setGeom_vertex(String geom_vertex) {
        this.geom_vertex = geom_vertex;
    }
}
