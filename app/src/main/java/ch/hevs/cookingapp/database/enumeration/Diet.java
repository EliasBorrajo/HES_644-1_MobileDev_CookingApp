package ch.hevs.cookingapp.database.enumeration;

/**
 * Enumeration of all the Diets options we want as a checkbox
 */
public enum Diet
{
    VEGAN() {
        @Override
        public String toString() {
            return "Vegan";
        }
    },
    MEAT() {
        @Override
        public String toString() {
            return "Meat";
        }
    },
    FISH() {
        @Override
        public String toString() {
            return "Fish";
        }
    },
    VEGETARIAN() {
        @Override
        public String toString() {
            return "Vegetarian";
        }
    };
}
