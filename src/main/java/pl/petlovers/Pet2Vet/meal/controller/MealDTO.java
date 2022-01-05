package pl.petlovers.Pet2Vet.meal.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.petlovers.Pet2Vet.meal.Meal;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MealDTO {

    private Long id;
    private String food;
    private double foodAmount;
    private LocalDateTime expectedFeedingTime;
    private LocalDateTime actualFeedingTime;

    public static MealDTO of(Meal meal) {
        return MealDTO.builder()
                .id(meal.getId())
                .food(meal.getFood())
                .foodAmount(meal.getFoodAmount())
                .expectedFeedingTime(meal.getExpectedFeedingTime())
                .actualFeedingTime(meal.getActualFeedingTime())
                .build();
    }

    public Meal toMeal() {
        return Meal.builder()
                .id(id)
                .food(food)
                .foodAmount(foodAmount)
                .expectedFeedingTime(expectedFeedingTime)
                .actualFeedingTime(actualFeedingTime)
                .build();
    }
}
