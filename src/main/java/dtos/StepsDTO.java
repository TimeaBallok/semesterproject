package dtos;

public class StepsDTO
{
    Integer number;
    String step;

    public StepsDTO(Integer number, String step)
    {
        this.number = number;
        this.step = step;
    }

    @Override
    public String toString()
    {
        return "{" +
                "\"number\":" + number +
                ", \"step\":\"" + step + "\"" +
                '}';
    }
}
