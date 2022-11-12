package ch.hevs.cookingapp.database.enumeration;

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
