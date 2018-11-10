package project.smd.studysync;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
public class User {
    String username;
    String password;
    String email;
    ArrayList <String> classes= new ArrayList<>();

    //Parameterized Constructor
    public User(String name, String pass, String em, ArrayList<String> classs) {
        username= name;
        password=pass;
        email=em;
        classes= classs;
    }

    //Empty constructor
    public User() {
        username= "";
        password="";
        email="";
    }
    public ArrayList<String> getClasses() {
        return classes;
    }
    public void setClasses(ArrayList<String> classes) {
        this.classes = classes;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
