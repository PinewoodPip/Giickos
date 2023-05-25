package edu.ub.pis.giickos.resources.dao.firebase;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import edu.ub.pis.giickos.model.garden.Bamboo;
import edu.ub.pis.giickos.model.user.User;
import edu.ub.pis.giickos.resources.dao.CachedGardenDAO;

public class GardenDAO extends CachedGardenDAO {

    private String userEmail;

    private boolean dataLoaded = false;
    private FirebaseFirestore db;
    public GardenDAO() {
        super();
        this.db = FirebaseFirestore.getInstance();
    }
    // Methods for getting documents using paths ----------------------------------
    private DocumentReference getBamboosDocument(String uniqueID)
    {
        return db.document(String.format("userdata/%s/bamboos/%s", userEmail, uniqueID));
    }
    private DocumentReference getGardenDocument(String uniqueID) {
        return db.document(String.format("userdata/%s/bambooGarden/%s", userEmail, uniqueID));
    }
    private DocumentReference getStorageDocument(String uniqueID)
    {
        return db.document(String.format("userdata/%s/bambooStorage/%s", userEmail, uniqueID));
    }
    // ----------------------------------------------------------------------------

    //This method, create/updates a bamboo in a list of bamboos using the uniqueID as the key
    private void updateBamboosEntry(Bamboo bamboo) {

        // We save the bamboo in the bamboos collection
        DocumentReference bamboosDocument = getBamboosDocument(bamboo.getUniqueID());
        Map<String, Object> entry = new HashMap<>();
        entry.put("uniqueID", bamboo.getUniqueID());
        entry.put("slot", bamboo.getSlot());
        entry.put("title", bamboo.getTitle());
        entry.put("answers", new HashMap<String,String>(bamboo.getAnswers()));
        entry.put("growth", bamboo.getGrowth());
        entry.put("totalGrowth", bamboo.getTotalGrowth());
        entry.put("lastTimeWatered", bamboo.getLastTimeWatered());
        entry.put("creationDate", bamboo.getCreationDate());
        entry.put("storedDate", bamboo.getStoredDate());

        bamboosDocument.set(entry);
    }



    //This method, creates/updates a slot and saves the reference to the bamboo in the bamboos collection using the uniqueID as the key
    private void updateGardenEntry(Bamboo bamboo) {
        // We save the bamboo in the garden using the slot as the key, and we save the reference to the bamboo in the bamboos collection
        DocumentReference bamboosDocument = getGardenDocument(bamboo.getUniqueID());
        Map<String, Object> entry = new HashMap<>();
        entry.put("uniqueID", bamboo.getUniqueID());
        bamboosDocument.set(entry);

    }

    //This method, creates/updates a bamboo in the storage using the uniqueID as the key
    private void updateStorageEntry(Bamboo bamboo) {
        // We save the bamboo in the garden using the slot as the key, and we save the reference to the bamboo in the bamboos collection
        DocumentReference bamboosDocument = getStorageDocument(bamboo.getUniqueID());
        Map<String, Object> entry = new HashMap<>();
        entry.put("uniqueID", bamboo.getUniqueID());
        bamboosDocument.set(entry);
    }


    @Override
    public boolean plantBamboo(Bamboo bamboo) {
        boolean success = super.plantBamboo(bamboo);

        // Write to firebase
        if (success && dataLoaded) {
            updateBamboosEntry(bamboo);
            updateGardenEntry(bamboo);
        }

        return success;
    }

    @Override
    public boolean updatePlantedBamboo(Bamboo bamboo) {
        boolean success = super.updatePlantedBamboo(bamboo);

        if (success && dataLoaded) {
            updateBamboosEntry(bamboo);
        }

        return success;
    }

    @Override
    public boolean deletePlantedBamboo(int slotID) {
        Bamboo bamboo = getPlantedBamboo(slotID);
        boolean succes = super.deletePlantedBamboo(slotID);


        if (succes && dataLoaded) {
            DocumentReference bambooSlot = getGardenDocument(bamboo.getUniqueID());
            bambooSlot.delete();

            DocumentReference bambooRef = getBamboosDocument(bamboo.getUniqueID());
            bambooRef.delete();
        }

        return succes;
    }

    @Override
    public boolean saveBambooToStorage(Bamboo bamboo) {
        boolean success = super.addStoredBamboo(bamboo) && super.deletePlantedBamboo(bamboo.getSlot());

        // Write to firebase
        if (success && dataLoaded) {
            // Add the key to the bamboo in the storage, and remove it from the garden keys
            updateStorageEntry(bamboo);
            DocumentReference bambooRef = getGardenDocument(bamboo.getUniqueID());
            bambooRef.delete();
        }

        return success;
    }

    @Override
    public boolean deleteStoredBamboo(Bamboo bamboo) {
        boolean success = super.deleteStoredBamboo(bamboo);

        // Write to firebase
        if (success && dataLoaded) {
            // Delete the bamboo from the storage and from the list of bamboos
            DocumentReference storageRef = getStorageDocument(bamboo.getUniqueID());
            storageRef.delete();
            DocumentReference bambooRef = getBamboosDocument(bamboo.getUniqueID());
            bambooRef.delete();
        }

        return success;
    }


    @Override
    public void loadDataForUser(User user, DataLoadedListener listener)
    {
        userEmail = user.getEmail();
        dataLoaded = false;

        // Obtenir les referncies de firebase.
        CollectionReference bamboosRef = db.collection(String.format("userdata/%s/bamboos", userEmail));
        CollectionReference gardenRef = db.collection(String.format("userdata/%s/bambooGarden", userEmail));
        CollectionReference storageRef = db.collection(String.format("userdata/%s/bambooStorage", userEmail));

        // Netegem les dades locals
        clearData();

        // Obtenim les dades de firebase

        gardenRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DocumentSnapshot doc : task.getResult()) {

                    String bambooUniqueID = (String) doc.getData().get("uniqueID");
                    DocumentReference bambooDoc = bamboosRef.document(bambooUniqueID);
                    bambooDoc.get().addOnCompleteListener(stored -> {
                        if (stored.isSuccessful()) {
                            DocumentSnapshot bambooData = stored.getResult();
                            long slot = (long) bambooData.getData().get("slot");
                            plantedBamboos.put((int)slot, getBambooFromDataBase(bambooData, bambooUniqueID));
                        }
                        else
                        {
                            dataLoaded = false;
                            listener.onLoad(false);
                        }
                    });

                }
                dataLoaded = true;
                listener.onLoad(true);
            }
            else
            {
                dataLoaded = false;
                listener.onLoad(false);
            }

        });

        storageRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DocumentSnapshot doc : task.getResult()) {

                    String bambooUniqueID = (String) doc.getData().get("uniqueID");
                    DocumentReference bambooDoc = bamboosRef.document(bambooUniqueID);
                    bambooDoc.get().addOnCompleteListener(stored -> {
                        if (stored.isSuccessful()) {
                            DocumentSnapshot bambooData = stored.getResult();
                            storedBamboos.add(getBambooFromDataBase(bambooData, bambooUniqueID));
                        }
                        else
                        {
                            dataLoaded = false;
                            listener.onLoad(false);
                        }
                    });
                }
                dataLoaded = true;
                listener.onLoad(true);
            }
            else
            {
                dataLoaded = false;
                listener.onLoad(false);
            }
        });


    }

    private Bamboo getBambooFromDataBase(DocumentSnapshot bambooData, String bambooUniqueID)
    {
        String title = (String) bambooData.getData().get("title");
        Map<String,String> answers = (Map<String,String> ) bambooData.getData().get("answers");

        long slot = (long) bambooData.getData().get("slot");
        long growth = (long) bambooData.getData().get("growth");
        long totalGrowth = (long) bambooData.getData().get("totalGrowth");
        long lastWatered = (long) bambooData.getData().get("lastTimeWatered");

        Bamboo bamboo = new Bamboo((int) slot, title, answers, (int) growth, (int) totalGrowth, bambooUniqueID);
        bamboo.setLastWatered(lastWatered);

        if (bambooData.getData().containsKey("creationDate") && bambooData.getData().containsKey("storedDate"))
        {
            long creationDate = (long) bambooData.getData().get("creationDate");
            long storedDate = (long) bambooData.getData().get("lastTimeWatered");

            // A 0 indicates no date, by default is 0
            if (creationDate != 0)
                bamboo.setCreationDate(LocalDate.ofEpochDay(creationDate));
            if (storedDate != 0)
                bamboo.setStoredDate(LocalDate.ofEpochDay(storedDate));
        }

        return bamboo;
    }





}
