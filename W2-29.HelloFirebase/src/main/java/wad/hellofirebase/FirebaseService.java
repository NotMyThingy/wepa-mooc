package wad.hellofirebase;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class FirebaseService {

    private Map<String, FirebaseItem> items;
    private final String url;

    public FirebaseService() {
        this.url = "https://blinding-torch-827.firebaseio.com/items/";
        this.items = new HashMap<>();

        new Firebase(this.url).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot data) {
                for (DataSnapshot snapshot : data.getChildren()) {
                    FirebaseItem item = snapshot.getValue(FirebaseItem.class);
                    items.put(item.getIdentifier(), item);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }

    public void add(FirebaseItem item) {
        if (item == null || item.getName() == null || item.getIdentifier() == null) {
            return;
        }

        new Firebase(this.url).child(item.getIdentifier()).setValue(item);
        this.items.put(item.getIdentifier(), item);
    }

    public void delete(String key) {                
        new Firebase(this.url).child(key).removeValue();
        this.items.remove(key);
    }

    public FirebaseItem get(String key) {
        return this.items.get(key);
    }

    public List<FirebaseItem> list() {
        return this.items.values().stream()
                .filter(o -> o.getName() != null)
                .sorted((o1, o2) -> o1.getName().compareTo(o2.getName()))
                .collect(Collectors.toList());
    }

}
