package ch.hevs.cookingapp.database.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;

@Entity(tableName = "cook", primaryKeys = {"email"})
public class CookEntity implements Comparable
{
    @NonNull
    private String email;   // On va l'utiliser comme foreing KEY
    @ColumnInfo(name = "first_name") // Nom dans la database
    private String firstName;
    @ColumnInfo(name = "last_name")
    private String lastName;
    private String password;
    private String phoneNumber;
    // TODO : Oublié de rajouter LISTE DE RECETTES ?
    // TODO : private Iterable <RecipeEntity> recipes;

    // C O N S T R U C T E U R
    @Ignore                 //On ne le veut pas dans la DB & on veut utiliser l'autre constructeur qui contient les data
    public CookEntity()
    {
    }

    public CookEntity(@NonNull String email, String firstName, String lastName, String password, String phoneNumber)
    {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    // G E T T E R S   S E T T E R S
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
    // Ces 3 methodes sont apellés dans les RECYCLER list
    @Override
    public boolean equals (Object obj)
    {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof CookEntity)) return false;

        // Dans les autres cas, on compare l'email qui est la Primary Key
        CookEntity o = (CookEntity) obj;
        return o.getEmail().equals(this.getEmail());
    }

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

    @Override
    public int compareTo(@NonNull Object o)
    {
        return toString().compareTo(o.toString());
    }
}
