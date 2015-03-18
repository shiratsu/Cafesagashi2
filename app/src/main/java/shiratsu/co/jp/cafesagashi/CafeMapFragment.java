package shiratsu.co.jp.cafesagashi;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link } interface
 * to handle interaction events.
 * Use the {@link CafeMapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CafeMapFragment extends Fragment
        implements GoogleMap.OnMyLocationChangeListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {


    //private OnFragmentInteractionListener mListener;
    private SupportMapFragment fragment;
    /*object of google map*/
    public GoogleMap mGoogleMap;
    private String textLog = "start\n";
    private Location location;

    GoogleMap.OnMyLocationChangeListener listener;

    private LocationListener lolistner;

    private FusedLocationProviderApi fusedLocationProviderApi = LocationServices.FusedLocationApi;
    private GoogleApiClient mLocationClient = null;
    private static final LocationRequest locationRequest = LocationRequest.create()
            .setInterval(5000)
            .setFastestInterval(16)
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

    private boolean mResolvingError = false;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CafeMapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CafeMapFragment newInstance() {
        CafeMapFragment fragment = new CafeMapFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public CafeMapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {

        if (mGoogleMap == null) {
            mGoogleMap = fragment.getMap();
            setUpMap();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        listener = (GoogleMap.OnMyLocationChangeListener) this;

        mLocationClient = new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        if(mLocationClient != null){
            mLocationClient.connect();
        }


        return inflater.inflate(R.layout.fragment_cafe_map, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        FragmentManager fm = getChildFragmentManager();
        fragment = (SupportMapFragment) fm.findFragmentById(R.id.fragmentMap);
        if (fragment == null) {
            fragment = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(R.id.fragmentMap, fragment).commit();
        }

    }

    //　とりあえず適当にピンたててみたり。
    private void setUpMap() {

        // 現在位置表示の有効化
        mGoogleMap.setMyLocationEnabled(true);
        // 設定の取得
        UiSettings settings = mGoogleMap.getUiSettings();
        // コンパスの有効化
        settings.setCompassEnabled(true);
        // 現在位置に移動するボタンの有効化
        settings.setMyLocationButtonEnabled(true);
        // ズームイン・アウトボタンの有効化
        settings.setZoomControlsEnabled(true);
        // 回転ジェスチャーの有効化
        settings.setRotateGesturesEnabled(false);
        // スクロールジェスチャーの有効化
        settings.setScrollGesturesEnabled(true);
        // Tlitジェスチャー(立体表示)の有効化
        settings.setTiltGesturesEnabled(false);
        // ズームジェスチャー(ピンチイン・アウト)の有効化
        settings.setZoomGesturesEnabled(true);
        mGoogleMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition position) {
                Log.d("TEST", "zoop:" + position.zoom);
            }
        });

        mGoogleMap.setOnMyLocationChangeListener(listener);
    }

    public void onDestroyView() {
        super.onDestroyView();


        try{

            FragmentTransaction ft = getChildFragmentManager().beginTransaction();
            ft.remove(fragment);
            ft.commit();
        }catch(Exception e){
        }
    }


    @Override
    public void onMyLocationChange(Location loc) {
        Log.d("TEST", "lat:" + loc.getLatitude()+ " lng:"+loc.getLongitude());
        LatLng curr = new LatLng(loc.getLatitude(), loc.getLongitude());
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLng(curr));
    }

    @Override
    public void onConnected(Bundle bundle) {

        Location currentLocation = fusedLocationProviderApi.getLastLocation(mLocationClient
        );
        if (currentLocation != null && currentLocation.getTime() > 20000) {
            location = currentLocation;

            textLog += "----------\n";
            textLog += "Latitude="+ String.valueOf(location.getLatitude())+"\n";
            textLog += "Longitude="+ String.valueOf(location.getLongitude())+"\n";
            textLog += "Accuracy="+ String.valueOf(location.getAccuracy())+"\n";
            textLog += "Altitude="+ String.valueOf(location.getAltitude())+"\n";
            textLog += "Time="+ String.valueOf(location.getTime())+"\n";
            textLog += "Speed="+ String.valueOf(location.getSpeed())+"\n";
            textLog += "Bearing="+ String.valueOf(location.getBearing())+"\n";
            Log.d("TEST", textLog);

        } else {
            fusedLocationProviderApi.requestLocationUpdates(mLocationClient, locationRequest, this);
            // Schedule a Thread to unregister location listeners
            Executors.newScheduledThreadPool(1).schedule(new Runnable() {
                @Override
                public void run() {
                    fusedLocationProviderApi.removeLocationUpdates(mLocationClient, lolistner);
                }
            }, 60000, TimeUnit.MILLISECONDS);

            textLog += "onConnected(), requestLocationUpdates \n";
            Log.d("TEST", textLog);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {
        textLog += "onConnectionSuspended()\n";
        Log.d("TEST", textLog);
    }

    @Override
    public void onLocationChanged(Location location) {

        textLog += "----------\n";
        textLog += "Latitude="+ String.valueOf(location.getLatitude())+"\n";
        textLog += "Longitude="+ String.valueOf(location.getLongitude())+"\n";
        textLog += "Accuracy="+ String.valueOf(location.getAccuracy())+"\n";
        textLog += "Altitude="+ String.valueOf(location.getAltitude())+"\n";
        textLog += "Time="+ String.valueOf(location.getTime())+"\n";
        textLog += "Speed="+ String.valueOf(location.getSpeed())+"\n";
        textLog += "Bearing="+ String.valueOf(location.getBearing())+"\n";

        Log.d("TEST", textLog);

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        textLog += "onConnectionFailed()\n";
        Log.d("TEST", textLog);

        if (mResolvingError) {
            // Already attempting to resolve an error.
            Log.d("","Already attempting to resolve an error");

            return;
        } else if (connectionResult.hasResolution()) {

        } else {
            mResolvingError = true;
        }
    }
}