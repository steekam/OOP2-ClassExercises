/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author steekam
 */
public class TestCircle {
    public static void main (String[] args){
        //Declaring an object of Circle with the first construnctor
        Circle c1 = new Circle();
        
        //Invoking methods in the object using the dot operator
        System.out.println("The circle has a radius of " + c1.getRadius() + " and area of " + c1.getArea());
        
        //Declaring an object with the second constructor
        Circle c2 = new Circle(2.0);
        System.out.println("The circle has a radius of " + c2.getRadius() + " and an area of " + c2.getArea());
        
    }
}
