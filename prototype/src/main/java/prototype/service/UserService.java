package prototype.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import com.google.api.core.ApiFuture;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import prototype.Quartier.Quartier;
import prototype.Users.Intervenant;
import prototype.Users.Resident;

public class UserService {

    public static String saveResidentToFirebase(String name, String lastName, String birthday, String address,
            String email, String phone, String password) {
        try {
            // Validate birthday
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate birthDate = LocalDate.parse(birthday, formatter);
            LocalDate currentDate = LocalDate.now();

            if (birthDate.isAfter(currentDate)) {
                throw new IllegalArgumentException("Birthday cannot be in the future.");
            }

            long age = ChronoUnit.YEARS.between(birthDate, currentDate);
            if (age < 16) {
                throw new IllegalArgumentException("You must be at least 16 years old.");
            }

            // Register user with Firebase Authentication
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            ApiFuture<UserRecord> future = firebaseAuth.createUserAsync(new UserRecord.CreateRequest()
                    .setEmail(email)
                    .setPassword(password)
                    .setDisplayName(name + " " + lastName));

            UserRecord userRecord = future.get(); // Block until operation completes
            String uid = userRecord.getUid();

            // Save user to Firebase Database
            FirebaseDatabase database = FirebaseDatabase
                    .getInstance("https://maville-18aa2-default-rtdb.firebaseio.com/");
            DatabaseReference userFolderRef = database.getReference("residents").child(uid);

            Quartier quartier = new Quartier(address);

            Resident resident = new Resident(name, lastName, birthday, address, email, phone, password,
                    quartier.getName());
            userFolderRef.setValueAsync(resident);

            return uid; // Return UID after saving the resident

        } catch (Exception e) {
            e.printStackTrace();
            return null; // Return null if something goes wrong
        }
    }

    public static String saveIntervenantToFirebase(String name, String lastName, String birthday, String address,
            String email, String phone, String password, String cityID) {
        try {
            // Register user with Firebase Authentication
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            ApiFuture<UserRecord> future = firebaseAuth.createUserAsync(new UserRecord.CreateRequest()
                    .setEmail(email)
                    .setPassword(password)
                    .setDisplayName(name + " " + lastName));

            UserRecord userRecord = future.get(); // Block until operation completes
            String uid = userRecord.getUid(); // Get the unique UID of the user

            // Save Intervenant to Firebase Database
            FirebaseDatabase database = FirebaseDatabase
                    .getInstance("https://maville-18aa2-default-rtdb.firebaseio.com/");
            DatabaseReference userFolderRef = database.getReference("intervenants").child(uid);

            // Create a new Intervenant object
            Intervenant intervenant = new Intervenant(name, lastName, password, birthday, phone, address, email,
                    cityID);

            // Save the Intervenant data under the UID folder
            userFolderRef.setValueAsync(intervenant);

            return uid; // Return UID after saving the intervenant

        } catch (Exception e) {
            e.printStackTrace();
            return null; // Return null if something goes wrong
        }
    }
}