package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ratings")
@NamedQuery(name = "Rating.deleteAllRows", query = "DELETE from Rating")
public class Rating
{
    //Sammensatte nøgler er FYFY!
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rating_id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_name", nullable = false)
    private User userName;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    @NotNull
    @Column(name = "rating", nullable = false)
    private Integer rating;

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

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

}