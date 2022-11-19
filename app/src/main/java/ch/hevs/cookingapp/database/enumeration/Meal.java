package ch.hevs.cookingapp.database.enumeration;

/**
 * Enumeration of all the Meals options we want as a checkbox
 */
public enum Meal
{
    BREAKFAST() {
        @Override
        public String toString() {
            return "Breakfast";
        }
    },
    LUNCH() {
        @Override
        public String toString() {
            return "Lunch";
        }
    },
    DINNER() {
        @Override
        public String toString() {
            return "Dinner";
        }
    };
}
