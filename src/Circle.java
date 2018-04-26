/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author steekam
 */
public class Circle {
    
    private double radius;
    private String color;
    
    //First constructor
    
    public Circle(){
        radius = 1.0;
        color ="red";
    }
    
    //Seconf constructor
    
    public Circle(double r){
        radius = r;
        color = "red";
    }
    
    public double getRadius(){
        return radius;
    }
    
    public double getArea(){
        return radius*radius*Math.PI;
    }
}
