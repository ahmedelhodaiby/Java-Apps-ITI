/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication2;

/**
 *
 * @author El-Hodaiby
 */
public class Pyramid {
    
    private double height;
    private String modern_name;
    private String pharaoh;
    private String site;
    
    public Pyramid(String pharaoh, String modern_name, String site, double height) {
        this.height = height;
        this.modern_name = modern_name;
        this.pharaoh = pharaoh;
        this.site = site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setModern_name(String modern_name) {
        this.modern_name = modern_name;
    }

    public void setPharaoh(String pharaoh) {
        this.pharaoh = pharaoh;
    }

    public double getHeight() {
        return height;
    }

    public String getModern_name() {
        return modern_name;
    }

    public String getPharaoh() {
        return pharaoh;
    }

    public String getSite() {
        return site;
    }

    @Override
    public String toString() {
        return "Pyramid " + modern_name  + " has a height of " + String.valueOf(height) + " meters and the body of the pharaoh " + pharaoh + " is at " + site;
    }
    
}
