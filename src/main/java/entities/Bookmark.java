package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "bookmarks")
@NamedQuery(name = "Bookmark.deleteAllRows", query = "DELETE from Bookmark")
public class Bookmark
{
    //Sammensatte nøgler er FYFY!
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookmark_id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_name", nullable = false)
    private User userName;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    public Bookmark(User userName, Recipe recipe)
    {
        this.userName = userName;
        this.recipe = recipe;
    }

    public Bookmark()
    {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUserName() {
        return userName;
    }

    public void setUserName(User userName) {
        this.userName = userName;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

}