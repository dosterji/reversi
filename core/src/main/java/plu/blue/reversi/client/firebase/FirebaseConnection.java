package plu.blue.reversi.client.firebase;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseCredentials;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class FirebaseConnection {

    private final FirebaseDatabase database;

    public FirebaseConnection() {
        try {
            FileInputStream serviceAccount = new FileInputStream("../serviceAccountCredentials.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredential(FirebaseCredentials.fromCertificate(serviceAccount))
                    .setDatabaseUrl("https://reversi-7f75f.firebaseio.com")
                    .build();

            FirebaseApp.initializeApp(options);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        database = FirebaseDatabase.getInstance();
    }

    // +---------+
    // | Getters |
    // +---------+

    public FirebaseDatabase getDatabase() {
        return database;
    }

}
