package dtos;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.MealPlan;
import entities.Recipe;
import utils.Utility;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the {@link entities.Recipe} entity
 */
public class SingleMealPlanDTO implements Serializable
{
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    @Size(max = 1073741824)
    @NotNull
    private SingleRecipeDTO recipeJson;

    private String type;

    public SingleMealPlanDTO() {
    }

    public SingleMealPlanDTO(String badJSON, String type){
        String temp = Utility.fixDiets(badJSON);
        this.recipeJson = GSON.fromJson(temp, SingleRecipeDTO.class);
        this.type = type;
    }

    public SingleMealPlanDTO(SingleRecipeDTO recipeJson, String type) {
        this.recipeJson = recipeJson;
        this.type = type;
    }

    public SingleRecipeDTO getRecipeJson() {
        return recipeJson;
    }

    public void setRecipeJson(SingleRecipeDTO recipeJson) {
        this.recipeJson = recipeJson;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static List<SingleMealPlanDTO> getDtos(List<Recipe> recipes, List<MealPlan> mealPlans){
        List<SingleMealPlanDTO> smpDTOs = new ArrayList();
        for (int i = 0; i < recipes.size(); i++) {
            smpDTOs.add(new SingleMealPlanDTO(recipes.get(i).getRecipeJson(), mealPlans.get(i).getType().getMeal()));
        }
//        stupidthing.forEach(mp->smpDTOs.add(new MealPlanDTO(mp.getUserName().getUserName(),mp.getRecipe().getId(),mp.getType().getMeal(),mp.getDate())));
        return smpDTOs;
    }



    @Override
    public String toString() {
        return "SingleMealPlanDTO{" +
                "recipeJson='" + recipeJson + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}