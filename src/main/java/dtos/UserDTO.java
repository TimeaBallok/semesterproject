package dtos;

import entities.User;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link entities.User} entity
 */
public class UserDTO implements Serializable
{
    @NotNull
    private final String userName;
    @NotNull
    @Size(min = 1, max = 255)
    private final String userPass;

    public UserDTO(String userName, String userPass)
    {
        this.userName = userName;
        this.userPass = userPass;
    }

    public UserDTO(User user)
    {
        this.userName = user.getUserName();
        this.userPass = user.getUserPass();
    }

    public String getUserName()
    {
        return userName;
    }

    public String getUserPass()
    {
        return userPass;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO entity = (UserDTO) o;
        return Objects.equals(this.userName, entity.userName) &&
                Objects.equals(this.userPass, entity.userPass);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(userName, userPass);
    }

    @Override
    public String toString()
    {
        return getClass().getSimpleName() + "(" +
                "userName = " + userName + ", " +
                "userPass = " + userPass + ")";
    }
}
