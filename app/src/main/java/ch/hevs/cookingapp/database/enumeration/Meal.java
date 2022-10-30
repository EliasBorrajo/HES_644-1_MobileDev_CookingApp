package ch.hevs.cookingapp.database.enumeration;

public enum Meal
{
    BREAKFAST() {
        @Override
        public String toString() {
            return "Déjeuné";
        }
    },
    LUNCH() {
        @Override
        public String toString() {
            return "Dîné";
        }
    },
    DINNER() {
        @Override
        public String toString() {
            return "Soupé";
        }
    };
}
