package hacathon2k16.drugdealer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hacathon2k16.drugdealer.model.ActiveSubstance;
import hacathon2k16.drugdealer.model.Drug;
import hacathon2k16.drugdealer.model.DrugKind;
import hacathon2k16.drugdealer.model.Store;


/**
 * Created by vlastachu on 19/11/16.
 */

public class MainActivity extends Activity {
    private RecyclerView recyclerView;
    public static final Map<Integer, Store> stores = new HashMap<>();
    public static final Map<Integer, ActiveSubstance> substances = new HashMap<>();
    public static final Map<Integer, DrugKind> drugKinds = new HashMap<>();
    public static final Map<String, Integer> problems = new HashMap<>();
    public static final List<Drug> drugs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Intent intent = new Intent(this, MapsActivity.class);

        findViewById(R.id.maps_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final Intent itemIntent = new Intent(this, MapsActivity.class);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                itemIntent.putExtra("problem_id", problems.get(((TextView)view.findViewById(R.id.tip_textview)).getText()));
                startActivity(itemIntent);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
        loadJSON();
    }

    private void loadJSON() {
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try (Reader reader = new BufferedReader(new InputStreamReader(getResources().openRawResource(R.raw.model), "UTF-8"))){
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } catch (Exception e) {
            Log.d("uuuu", "ahaha", e);
        }

        String jsonString = writer.toString();
        try {
            JSONObject jObject = new JSONObject(jsonString);
            JSONArray array = jObject.getJSONArray("store");
            for (int i = 0; i < array.length(); i++) {
                JSONObject o = (JSONObject) array.get(i);
                int id = o.getInt("id");
                String name = o.getString("drugstorename");
                double longitude = o.getDouble("longitude");
                double latitude = o.getDouble("latitude");
                String phone = o.getString("phone");
                String workrange = o.getString("workrange");
                stores.put(id, new Store(name, longitude, latitude, phone, workrange));
            }

            array = jObject.getJSONArray("substance");
            for (int i = 0; i < array.length(); i++) {
                JSONObject o = (JSONObject) array.get(i);
                int id = o.getInt("id");
                String name = o.getString("substancename");
                String description = o.getString("substancedescription");
                substances.put(id, new ActiveSubstance(name,  description));
            }

            array = jObject.getJSONArray("drugKind");
            for (int i = 0; i < array.length(); i++) {
                JSONObject o = (JSONObject) array.get(i);
                int id = o.getInt("id");
                String name = o.getString("name");
                String container = o.getString("container");
                String description = o.getString("description");
                ActiveSubstance substance = substances.get(o.getInt("substanceid"));
                drugKinds.put(id, new DrugKind(name,  description, container, substance));
            }

            array = jObject.getJSONArray("drug");
            for (int i = 0; i < array.length(); i++) {
                JSONObject o = (JSONObject) array.get(i);
                long price = (long) Math.floor(o.getDouble("price") * 100);
                DrugKind drugKind = drugKinds.get(o.getInt("drugkindid"));
                Store store = stores.get(o.getInt("storeid"));
                drugs.add(new Drug(drugKind, store, price));
            }

            array = jObject.getJSONArray("problems");
            for (int i = 0; i < array.length(); i++) {
                JSONObject o = (JSONObject) array.get(i);
                problems.put(o.getString("name"), o.getInt("substanceid"));

            }
            recyclerView.setAdapter(new RecyclerViewAdapter(this, new ArrayList<>(problems.keySet())));

        } catch (Exception e) {
            Log.d("uuuu", "bwahaha", e);
        }

    }
}
