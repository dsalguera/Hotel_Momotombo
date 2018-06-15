/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_hotel.clases;

import javafx.scene.image.Image;

/**
 *
 * @author David Salguera
 */
public class CustomThing {
    private Image image;
        private String name;
        private int price;
        
        public Image getImage() {
            return image;
        }
                
        public String getName() {
            return name;
        }
        
        public int getPrice() {
            return price;
        }
        public CustomThing(Image image, String name, int price) {
            super();
            this.image = image;
            this.name = name;
            this.price = price;
        }
}
