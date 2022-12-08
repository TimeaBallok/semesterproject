package dtos;

import entities.Bookmark;
import entities.MealPlan;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;

public class BookmarkDTO
{
    private String userName;

    private Integer recipeId;

    public BookmarkDTO(String username, Integer recipeId)
    {
        this.userName = username;
        this.recipeId = recipeId;
    }

    public static List<BookmarkDTO> getBookMarkDtos(List<Bookmark> rms){
        List<BookmarkDTO> bmdtos = new ArrayList();
        rms.forEach(bm->bmdtos.add(new BookmarkDTO(bm.getUserName().getUserName(),bm.getRecipe().getId())));
        return bmdtos;
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
