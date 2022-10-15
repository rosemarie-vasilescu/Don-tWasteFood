package com.example.dontwastefood.Models;

import java.util.ArrayList;

public class IngredientsResponse {

        private String name;
        private String image;
        private int id;
        private String aisle;
        private ArrayList<String> possibleUnits;
        public String toString(){
        return "name: " + name + " aisle: " + aisle +" id: " + String.valueOf(id);

}

        public String getName() {
                return name;
        }

        public String getImage() {
                return image;
        }

        public int getId() {
                return id;
        }

        public String getAisle() {
                return aisle;
        }

        public ArrayList<String> getPossibleUnits() {
                return possibleUnits;
        }
}
