package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "meal_plan")
public class MealPlan
{
    //Sammensatte nøgler er FYFY!
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meal_plan_id", nullable = false)
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
    @Lob
    @Column(name = "meal_type", nullable = false)
    private MealType type;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    public MealPlan()
    {
    }

    public MealPlan(Integer id, User userName, Recipe recipe, MealType type, LocalDate date)
    {
        this.id = id;
        this.userName = userName;
        this.recipe = recipe;
        this.type = type;
        this.date = date;
    }


    public MealPlan(User userName, Recipe recipe, MealType type, LocalDate date)
    {
        this.userName = userName;
        this.recipe = recipe;
        this.type = type;
        this.date = date;
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



    public LocalDate getDate() {
        return date;
    }

    public MealType getType()
    {
        return type;
    }

    public void setType(MealType type)
    {
        this.type = type;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString()
    {
        return "MealPlan{" +
                "id=" + id +
                ", userName=" + userName +
                ", recipe=" + recipe +
                ", type=" + type +
                ", date=" + date +
                '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MealPlan mealPlan = (MealPlan) o;
        return Objects.equals(id, mealPlan.id);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id);
    }

    public enum MealType {
        BREAKFAST,
        LUNCH,
        DINNER
    }

}