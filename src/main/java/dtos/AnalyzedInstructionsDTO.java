package dtos;

import java.util.List;

public class AnalyzedInstructionsDTO
{
    String name;
    List<StepsDTO> steps;

    public AnalyzedInstructionsDTO(String name, List<StepsDTO> steps)
    {
        this.name = name;
        this.steps = steps;
    }

    @Override
    public String toString()
    {
        return "{" +
                "\"name\":\"" + name + "\"" +
                ", \"steps\":" + steps +
                '}';
    }
}
