package dtos;

import java.util.List;

public class RecipeNutritionDTO
{
    List<NutrientsDTO> nutrients;

    public RecipeNutritionDTO(List<NutrientsDTO> nutrients)
    {
        this.nutrients = nutrients;
    }
}
