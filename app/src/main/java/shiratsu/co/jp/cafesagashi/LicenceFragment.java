package shiratsu.co.jp.cafesagashi;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.SupportMapFragment;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LicenceFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LicenceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LicenceFragment extends Fragment {

    private SupportMapFragment fragment;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LicenceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LicenceFragment newInstance() {
        LicenceFragment fragment = new LicenceFragment();

        return fragment;
    }

    public LicenceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_licence, container, false);
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


}
