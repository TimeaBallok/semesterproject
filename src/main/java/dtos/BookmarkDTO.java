package dtos;

public class BookmarkDTO
{
    private String userName;

    private Integer recipeId;

    public BookmarkDTO(String username, Integer recipeId)
    {
        this.userName = username;
        this.recipeId = recipeId;
    }

    public String getUsername()
    {
        return userName;
    }

    public void setUsername(String username)
    {
        this.userName = username;
    }

    public Integer getRecipeId()
    {
        return recipeId;
    }

    public void setRecipeId(Integer recipeId)
    {
        this.recipeId = recipeId;
    }
}
