package dtos;

import entities.User;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link entities.Recipe} entity
 */
public class RecipeDTO implements Serializable
{
    private final Integer id;
    @Size(max = 1073741824)
    @NotNull
    private final String recipeJson;
    @Size(max = 1073741824)
    private  String ingredientsJson;
    private  Set<Integer> innerBookmarkIds;
    private  Set<User> innerBookmarkInnerBookmarkUserNames;
    private  Set<RatingDTO> innerRating;

    public RecipeDTO(Integer id, String recipeJson, String ingredientsJson, Set<Integer> innerBookmarkIds, Set<User> innerBookmarkInnerBookmarkUserNames, Set<RatingDTO> innerRating)
    {
        this.id = id;
        this.recipeJson = recipeJson;
        this.ingredientsJson = ingredientsJson;
        this.innerBookmarkIds = innerBookmarkIds;
        this.innerBookmarkInnerBookmarkUserNames = innerBookmarkInnerBookmarkUserNames;
        this.innerRating = innerRating;
    }

    public RecipeDTO(Integer id, String recipeJson, String ingredientsJson)
    {
        this.id = id;
        this.recipeJson = recipeJson;
        this.ingredientsJson = ingredientsJson;
    }

    public RecipeDTO(Integer id, String recipeJson)
    {
        this.id = id;
        this.recipeJson = recipeJson;
    }

    public Integer getId()
    {
        return id;
    }

    public String getRecipeJson()
    {
        return recipeJson;
    }

    public String getIngredientsJson()
    {
        return ingredientsJson;
    }

    public Set<Integer> getInnerBookmarkIds()
    {
        return innerBookmarkIds;
    }

    public Set<User> getInnerBookmarkInnerBookmarkUserNames()
    {
        return innerBookmarkInnerBookmarkUserNames;
    }

    public Set<RatingDTO> getInnerRating()
    {
        return innerRating;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeDTO entity = (RecipeDTO) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.recipeJson, entity.recipeJson) &&
                Objects.equals(this.ingredientsJson, entity.ingredientsJson) &&
                Objects.equals(this.innerBookmarkIds, entity.innerBookmarkIds) &&
                Objects.equals(this.innerBookmarkInnerBookmarkUserNames, entity.innerBookmarkInnerBookmarkUserNames) &&
                Objects.equals(this.innerRating, entity.innerRating);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, recipeJson, ingredientsJson, innerBookmarkIds, innerBookmarkInnerBookmarkUserNames, innerRating);
    }

    @Override
    public String toString()
    {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "recipeJson = " + recipeJson + ", " +
                "ingredientsJson = " + ingredientsJson + ", " +
                "innerBookmarkIds = " + innerBookmarkIds + ", " +
                "innerBookmarkInnerBookmarkUserNames = " + innerBookmarkInnerBookmarkUserNames + ", " +
                "innerRating = " + innerRating + ")";
    }

    /**
     * A DTO for the {@link entities.Rating} entity
     */
    public static class RatingDTO implements Serializable
    {
        private final Integer id;
        @NotNull
        private final String innerRatingUserNameUserName;
        @NotNull
        private final Integer rating;

        public RatingDTO(Integer id, String innerRatingUserNameUserName, Integer rating)
        {
            this.id = id;
            this.innerRatingUserNameUserName = innerRatingUserNameUserName;
            this.rating = rating;
        }

        public Integer getId()
        {
            return id;
        }

        public String getInnerRatingUserNameUserName()
        {
            return innerRatingUserNameUserName;
        }

        public Integer getRating()
        {
            return rating;
        }

        @Override
        public boolean equals(Object o)
        {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            RatingDTO entity = (RatingDTO) o;
            return Objects.equals(this.id, entity.id) &&
                    Objects.equals(this.innerRatingUserNameUserName, entity.innerRatingUserNameUserName) &&
                    Objects.equals(this.rating, entity.rating);
        }

        @Override
        public int hashCode()
        {
            return Objects.hash(id, innerRatingUserNameUserName, rating);
        }

        @Override
        public String toString()
        {
            return getClass().getSimpleName() + "(" +
                    "id = " + id + ", " +
                    "innerRatingUserNameUserName = " + innerRatingUserNameUserName + ", " +
                    "rating = " + rating + ")";
        }
    }
}