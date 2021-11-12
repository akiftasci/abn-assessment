package com.abn.recipes.utils;

import com.abn.recipes.dto.IngredientDto;
import com.abn.recipes.dto.RecipeDto;
import com.abn.recipes.model.Ingredient;
import com.abn.recipes.model.Recipe;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class Util {
    public static String formatDate(final LocalDateTime created) {
        String formattedDate;

        final int day = created.getDayOfMonth();
        final int month = created.getMonthValue();
        final int year = created.getYear();
        final int hour = created.getHour();
        final int minute = created.getMinute();

        formattedDate = day +"-"+ month +"-"+ year +" "+ hour+":"+minute;

        return formattedDate;
    }

    public static Recipe modelMapper(final RecipeDto recipeDto) {
        Recipe recipe = new Recipe();

        recipe.setName(recipeDto.getName());
        recipe.setPortion(recipeDto.getPortion());
        recipe.setVegetarian(recipeDto.isVegetarian());
        recipe.setInstructions(recipeDto.getInstructions());

        recipe.setIngredients(recipeDto.getIngredients().stream().map(n -> {
            final Ingredient ingredient = new Ingredient();

            ingredient.setName(n.getName());
            ingredient.setAmount(n.getAmount());
            ingredient.setRecipe(recipe);

            return ingredient;
        }).collect(Collectors.toList()));

        return recipe;
    }

    public static RecipeDto convertRecipeDto(final Recipe save) {
        RecipeDto recipeDto = new RecipeDto();

        recipeDto.setId(save.getId());
        recipeDto.setDate(Util.formatDate(save.getCreated()));

        return recipeDto;
    }

    public static List<RecipeDto> entityToDto(final List<Recipe> all) {
        return all.stream().map(n -> {

            final RecipeDto recipeDto = new RecipeDto();

            recipeDto.setId(n.getId());
            recipeDto.setDate(Util.formatDate(n.getCreated()));
            recipeDto.setName(n.getName());
            recipeDto.setVegetarian(n.isVegetarian());
            recipeDto.setPortion(n.getPortion());
            recipeDto.setInstructions(n.getInstructions());

            recipeDto.setIngredients(n.getIngredients().stream().map(a -> {
                IngredientDto ingredient = new IngredientDto();

                ingredient.setName(a.getName());
                ingredient.setAmount(a.getAmount());

                return ingredient;
            }).collect(Collectors.toList()));

            return recipeDto;
        }).collect(Collectors.toList());

    }
}
