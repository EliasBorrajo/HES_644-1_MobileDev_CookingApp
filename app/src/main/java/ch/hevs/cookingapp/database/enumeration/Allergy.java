package ch.hevs.cookingapp.database.enumeration;

/**
 * Enumeration of all the allergies options we want as a checkbox
 */
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
            return "Nuts";
        }
    },
    PEANUT() {
        @Override
        public String toString() {
            return "Peanut";
        }
    };
}
