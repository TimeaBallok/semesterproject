package dtos;

import java.util.List;

public class SingleRecipeDTO
{
    List<ExtendedIngredientsDTO> extendedIngredients;
    Integer id;
    String title;
    Integer readyInMinutes;
    Integer servings;
    String image;
    String summary;
    List<String> diets;
    List<AnalyzedInstructionsDTO> analyzedInstructions;
    RecipeNutritionDTO nutrition;

//    public SingleRecipeDTO(List<ExtendedIngredientsDTO> extendedIngredients, Integer id, String title, Integer readyInMinutes, Integer servings, String image, String summary, String instructions)
//    {
//        this.extendedIngredients = extendedIngredients;
//        this.id = id;
//        this.title = title;
//        this.readyInMinutes = readyInMinutes;
//        this.servings = servings;
//        this.image = image;
//        this.summary = summary;
//        this.instructions = instructions;
//    }


    public SingleRecipeDTO(List<ExtendedIngredientsDTO> extendedIngredients, Integer id, String title, Integer readyInMinutes, Integer servings, String image, String summary, List<AnalyzedInstructionsDTO> analyzedInstructions, RecipeNutritionDTO nutrition, List<String> diets)
    {
        this.extendedIngredients = extendedIngredients;
        this.id = id;
        this.title = title;
        this.readyInMinutes = readyInMinutes;
        this.servings = servings;
        this.image = image;
        this.summary = summary;
        this.diets = diets;
        this.analyzedInstructions = analyzedInstructions;
        this.nutrition = nutrition;
    }

    public Integer getId()
    {
        return id;
    }

    @Override
    public String toString()
    {
        return "{" +
                "extendedIngredients=" + extendedIngredients +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", readyInMinutes=" + readyInMinutes +
                ", servings=" + servings +
                ", image='" + image + '\'' +
                ", summary='" + summary + '\'' +
                ", diets=" + diets +
                ", analyzedInstructions=" + analyzedInstructions +
                ", nutrition=" + nutrition +
                '}';
    }
}
