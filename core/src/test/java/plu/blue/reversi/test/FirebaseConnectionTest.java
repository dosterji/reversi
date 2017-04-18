package plu.blue.reversi.test;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import plu.blue.reversi.client.firebase.FirebaseConnection;

public class FirebaseConnectionTest {

    private static FirebaseConnection connection;

    @BeforeClass
    public static void init() {
        connection = new FirebaseConnection();
    }

    @Test
    public void getProjectNameTest() {
        DatabaseReference ref = connection.getDatabase().getReference("test/project");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String projectName = dataSnapshot.getValue(String.class);
                Assert.assertTrue(projectName.equals("Reversi"));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
    }

}
