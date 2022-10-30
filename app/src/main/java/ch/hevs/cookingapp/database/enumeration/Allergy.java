package ch.hevs.cookingapp.database.enumeration;

public enum Allergy
{
   LACTOSE() {
        @Override
        public String toString() {
            return "Lactose";
        }
    },
    GLUTEN() {
        @Override
        public String toString() {
            return "Gluten";
        }
    },
    NUT() {
        @Override
        public String toString() {
            return "Noix";
        }
    },
    PEANUT() {
        @Override
        public String toString() {
            return "Arachide";
        }
    };
}
