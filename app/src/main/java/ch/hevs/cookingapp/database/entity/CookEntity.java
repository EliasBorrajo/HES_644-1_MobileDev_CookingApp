package ch.hevs.cookingapp.database.entity;

import androidx.annotation.NonNull;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;


/**
 * Entity class for the Cook table
 * Relation with the Recipe table : One to Many (One Cook can have many Recipes).
 * Cook <-1----*-> Recipe
 */
public class CookEntity
{
    private String email;   // Est utilisé pour AuthentificationFirebase, mais aussi présent car variable que on afficherait dans l'application
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String password;
    // Password ira dans AuthentificationFirebase
    private byte[] image; // TODO : Ira dans le StorageFirebase, mais en attendant en B64 dans la DB

    // C O N S T R U C T E U R
    public CookEntity()
    {
    }


    public CookEntity(String email, String firstName, String lastName, String phoneNumber, byte[] image)
    {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.image = image;
    }

    // G E T T E R S   S E T T E R S
    @Exclude
    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Exclude
    public String getEmail()
    {
        return email;
    }

    public void setEmail(@NonNull String email)
    {
        this.email = email;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    // O V E R R I D E
    @Override
    public String toString()
    {
        return "CookEntity{" +
                "email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

    // F I R E B A S E
    /**
     * Used to convert the object to a Map for Firebase Database purposes (pushing to the database)
     * and vice versa (retrieving from the database).
     * @return : a Map with the object's attributes as keys and their values as values.
     */
    @Exclude
    public Map<String, Object> toMap()
    {
        HashMap<String, Object> result = new HashMap<>();
        result.put("email", email);
        result.put("firstName", firstName);
        result.put("lastName", lastName);
        result.put("phoneNumber", phoneNumber);
        result.put("image", image);
        return result;
    }
}
