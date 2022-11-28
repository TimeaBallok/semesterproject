package dtos;

import java.util.List;

public class RecipesDTO
{

    List<RecipeDTO> results;

    public RecipesDTO(List<RecipeDTO> results)
    {
        this.results = results;
    }

    public List<RecipeDTO> getResults()
    {
        return results;
    }

    public void setResults(List<RecipeDTO> results)
    {
        this.results = results;
    }
}
