package dtos;

import java.util.List;

public class RecipeNutritionDTO
{
    List<NutrientsDTO> nutrients;

    public RecipeNutritionDTO(List<NutrientsDTO> nutrients)
    {
        this.nutrients = nutrients;
    }

    @Override
    public String toString()
    {
        return "RecipeNutritionDTO{" +
                "nutrients=" + nutrients +
                '}';
    }
}
