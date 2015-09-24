/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peternerd;

/**
 *
 * @author Pedro
 */
public class Image {
    private String name;
    private String path;
    
    
    public Image(String url){
        this.path = url;
    }

    public String getPath(){
        return this.path;
    }
    
    public void setPath(String path) {
        this.path = path;
    }
    
    
    public void setName(String name){
        this.name = name;
    }
    
    public String getName(){
        return this.name;
    }
    
    
}
