package ch.hevs.cookingapp.database.enumeration;

public enum Diet
{
    VEGAN() {
        @Override
        public String toString() {
            return "Végane";
        }
    },
    MEAT() {
        @Override
        public String toString() {
            return "Viande";
        }
    },
    FISH() {
        @Override
        public String toString() {
            return "Poisson";
        }
    },
    VEGETARIAN() {
        @Override
        public String toString() {
            return "Végétarien";
        }
    };
}
