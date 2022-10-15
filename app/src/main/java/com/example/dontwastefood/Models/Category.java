package com.example.dontwastefood.Models;

import java.util.List;

public class Category {
    private List<MyPantry> nestedList;
    private String itemText;
    private boolean isExpandable;

    public Category(List<MyPantry> nestedList, String itemText){
        this.nestedList = nestedList;
        this.itemText = itemText;
        isExpandable = false;
    }

    public void setExpandable(boolean expandable){
        isExpandable = expandable;
    }

    public List<MyPantry> getNestedList(){
        return nestedList;
    }

    public String getItemText(){
        return itemText;
    }

    public boolean isExpandable(){
        return isExpandable;
    }
    public String toString(){
        return nestedList.toString() ;
    }
}
