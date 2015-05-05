package com.application.yaroslav.searchprogm;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Yaroslav on 02.05.2015.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Food {

    @JsonProperty("id_food_tc")
    private String idFood;

    private String name;

    private String photo;

    private Double rating;

    private Double protein;

    private Double fats;

    private Double carbs;

    private Double kcal;

    private String ingredients;

    @JsonProperty("count_calculate")
    private int countCalculate;

    public String getIdFood() {
        return idFood;
    }

    public void setIdFood(String idFood) {
        this.idFood = idFood;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Double getProtein() {
        return protein;
    }

    public void setProtein(Double protein) {
        this.protein = protein;
    }

    public Double getFats() {
        return fats;
    }

    public void setFats(Double fats) {
        this.fats = fats;
    }

    public Double getCarbs() {
        return carbs;
    }

    public void setCarbs(Double carbs) {
        this.carbs = carbs;
    }

    public Double getKcal() {
        return kcal;
    }

    public void setKcal(Double kcal) {
        this.kcal = kcal;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public int getCountCalculate() {
        return countCalculate;
    }

    public void setCountCalculate(int countCalculate) {
        this.countCalculate = countCalculate;
    }
}
