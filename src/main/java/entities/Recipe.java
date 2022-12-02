package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "recipes")
public class Recipe
{
    @Id
    @Column(name = "recipe_id", nullable = false)
    private Integer id;

    @Size(max = 1073741824)
    @NotNull
    @Column(name = "recipe_json", nullable = false, length = 1073741824)
    private String recipeJson;

    @Size(max = 1073741824)
    @Column(name = "ingredients_json", length = 1073741824)
    private String ingredientsJson;

    @OneToMany(mappedBy = "recipe")
    private Set<Bookmark> bookmarks = new LinkedHashSet<>();

    @OneToMany(mappedBy = "recipe")
    private Set<Rating> ratings = new LinkedHashSet<>();

    public Recipe()
    {
    }

    public Recipe(Integer id, String recipeJson, String ingredientsJson, Set<Bookmark> bookmarks, Set<Rating> ratings)
    {
        this.id = id;
        this.recipeJson = recipeJson;
        this.ingredientsJson = ingredientsJson;
        this.bookmarks = bookmarks;
        this.ratings = ratings;
    }

    public Recipe(Integer id, String recipeJson)
    {
        this.id = id;
        this.recipeJson = recipeJson;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRecipeJson() {
        return recipeJson;
    }

    public void setRecipeJson(String recipeJson) {
        this.recipeJson = recipeJson;
    }

    public String getIngredientsJson() {
        return ingredientsJson;
    }

    public void setIngredientsJson(String ingredientsJson) {
        this.ingredientsJson = ingredientsJson;
    }

    public Set<Bookmark> getBookmarks() {
        return bookmarks;
    }

    public void setBookmarks(Set<Bookmark> bookmarks) {
        this.bookmarks = bookmarks;
    }

    public Set<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(Set<Rating> ratings) {
        this.ratings = ratings;
    }

}