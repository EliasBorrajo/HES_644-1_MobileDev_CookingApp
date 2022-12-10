package ch.hevs.cookingapp.database.entity;

import androidx.annotation.NonNull;

import com.google.firebase.database.Exclude;


/**
 * Entity class for the Cook table
 * Relation with the Recipe table : One to Many (One Cook can have many Recipes).
 * Cook <-1----*-> Recipe
 */
public class CookEntity
{
    private String email;   // On va l'utiliser comme foreing KEY
    private String firstName;
    private String lastName;
    private String phoneNumber;
    // Password ira dans AuthentificationFirebase
    @Exclude
    private byte[] image; // Ira dans le StorageFirebase

    // C O N S T R U C T E U R
    @Ignore                 //On ne le veut pas dans la DB & on veut utiliser l'autre constructeur qui contient les data
    public CookEntity()
    {
    }


    public CookEntity(@NonNull String email, String firstName, String lastName, String password, String phoneNumber, byte[] image)
    {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.image = image;
    }

    // G E T T E R S   S E T T E R S


    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @NonNull
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

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
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
}
