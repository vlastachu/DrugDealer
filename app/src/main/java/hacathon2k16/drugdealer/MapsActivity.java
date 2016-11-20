package hacathon2k16.drugdealer;

import android.*;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import hacathon2k16.drugdealer.model.ActiveSubstance;
import hacathon2k16.drugdealer.model.Drug;
import hacathon2k16.drugdealer.model.DrugKind;
import hacathon2k16.drugdealer.model.Store;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LocationManager locationManager;
    private boolean showedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    }

    private void addStores() {
        int problem = getIntent().getIntExtra("problem_id", -1);
        if (problem != -1) {
            ActiveSubstance substance = MainActivity.substances.get(problem);
            List<Drug> drugs = new ArrayList<>();
            for(Drug drug: MainActivity.drugs) {
                if(drug.getDrugKind().getActiveSubstance().equals(substance)) {
                    drugs.add(drug);
                }
            }
            for(Drug drug: drugs) {

                LatLng pos = new LatLng(drug.getStore().getLongitude(), drug.getStore().getLatitude());
                mMap.addMarker(new MarkerOptions().position(pos).title(drug.getDrugKind().getName() + " по цене " + formatPrice(drug.getPrice()) + " \u20BD"));
            }
        } else {
            for (Store store : MainActivity.stores.values()) {
                LatLng pos = new LatLng(store.getLongitude(), store.getLatitude());
                mMap.addMarker(new MarkerOptions().position(pos).title(store.getName() + ", Номер телефона " + store.getPhone()
                        + ", Часы работы: " + store.getWorkRange()));
            }
        }

    }

    private String formatPrice(long price) {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(price / 100.0);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(Objects.equals(permissions[0], Manifest.permission.ACCESS_FINE_LOCATION))
            autoUpdateLocation();
    }

    private void autoUpdateLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10,
                10, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        if (!showedOnce) {
                            LatLng pos = new LatLng(location.getLatitude(), location.getLongitude());
                            //mMap.addMarker(new MarkerOptions().position(pos).title("Marker in Sydney"));
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pos, 12.0f));
                            mMap.addCircle(new CircleOptions().center(pos).fillColor(0xff00ff00).radius(90));
                            showedOnce = true;
                        }
                    }

                    @Override
                    public void onStatusChanged(String s, int i, Bundle bundle) {
                    }

                    @Override
                    public void onProviderEnabled(String s) {
                    }

                    @Override
                    public void onProviderDisabled(String s) {
                    }
                });
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        addStores();
        autoUpdateLocation();
    }
}
