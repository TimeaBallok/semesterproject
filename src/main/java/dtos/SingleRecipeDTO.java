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
    String instructions;

    public SingleRecipeDTO(List<ExtendedIngredientsDTO> extendedIngredients, Integer id, String title, Integer readyInMinutes, Integer servings, String image, String summary, String instructions)
    {
        this.extendedIngredients = extendedIngredients;
        this.id = id;
        this.title = title;
        this.readyInMinutes = readyInMinutes;
        this.servings = servings;
        this.image = image;
        this.summary = summary;
        this.instructions = instructions;
    }


}
