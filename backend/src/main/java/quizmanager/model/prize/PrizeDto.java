package quizmanager.model.prize;


import java.util.List;

public record PrizeDto (String name, String description, List<PrizeTypeDto> prizeTypes){}

