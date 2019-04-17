package com.example.vanda.agrivolution;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.heatmaps.Gradient;
import com.google.maps.android.heatmaps.HeatmapTileProvider;


import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Scanner;

/**
 * create a colored map overlay that visualises many points of weighted importance/intensity, with
 * different colors representing areas of high and low concentration/combined intensity of points.
 */
public class HeatmapActivity extends FragmentActivity implements OnMapReadyCallback {

    private static GoogleMap mMap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        setUpMap();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMap();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        if (mMap != null) {
            return;
        }
        mMap = map;
        startDemo();
    }

    private void setUpMap() {
        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);
    }

    /**
     * Run the demo-specific code.
     */

    protected GoogleMap getMap() {
        return mMap;
    }
    /**
     * Alternative radius for convolution
     */
    private static final int ALT_HEATMAP_RADIUS = 50;

    /**
     * Alternative opacity of heatmap overlay
     */
    private static final double ALT_HEATMAP_OPACITY = 0.4;

    /**
     * Alternative heatmap gradient (blue -> red)
     * Copied from Javascript version
     */
    private static final int[] ALT_HEATMAP_GRADIENT_COLORS = {
            Color.argb(0, 0, 255, 255),// transparent
            Color.argb(255 / 3 * 2, 0, 255, 255),
            Color.rgb(0, 191, 255),
            Color.rgb(0, 0, 127),
            Color.rgb(255, 0, 0)
    };

    public static final float[] ALT_HEATMAP_GRADIENT_START_POINTS = {
            0.0f, 0.10f, 0.20f, 0.60f, 1.0f
    };

    public static final Gradient ALT_HEATMAP_GRADIENT = new Gradient(ALT_HEATMAP_GRADIENT_COLORS,
            ALT_HEATMAP_GRADIENT_START_POINTS);

    private HeatmapTileProvider mProvider;
    private TileOverlay mOverlay;

    private boolean mDefaultGradient = true;
    private boolean mDefaultRadius = true;
    private boolean mDefaultOpacity = true;

    /**
     * Maps name of data set to data (list of LatLngs)
     * Also maps to the URL of the data set for attribution
     */
    private HashMap<String, DataSet> mListsS = new HashMap<String, DataSet>();
    private HashMap<String, DataSet> mListsYr = new HashMap<String, DataSet>();
    private HashMap<String, DataSet> mLists = new HashMap<String, DataSet>();

    protected int getLayoutId() {
        return R.layout.activity_maps;
    }

    protected void startDemo() {
        getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(41.30, -72.92), 4));

        // Set up the season spinner/dropdown list
        Spinner spinnerS = (Spinner) findViewById(R.id.spinner_s);
        ArrayAdapter<CharSequence> adapterS = ArrayAdapter.createFromResource(this,
                R.array.heatmaps_datasets_array_season, android.R.layout.simple_spinner_item);
        adapterS.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerS.setAdapter(adapterS);
//        spinnerS.setOnItemSelectedListener(new SpinnerActivity());

        try {
            mListsS.put(getString(R.string.spring), new DataSet(readItems(R.raw.yr2019springwhiteflies),
                    getString(R.string.spring_url)));
            mListsS.put(getString(R.string.summer), new DataSet(readItems(R.raw.yr2019summerwhiteflies),
                    getString(R.string.summer_url)));
            mListsS.put(getString(R.string.fall), new DataSet(readItems(R.raw.yr2019fallwhiteflies),
                    getString(R.string.fall_url)));
            mListsS.put(getString(R.string.winter), new DataSet(readItems(R.raw.yr2019winterwhiteflies),
                    getString(R.string.winter_url)));
        } catch (JSONException e) {
            Toast.makeText(this, "Problem reading list of markers.", Toast.LENGTH_LONG).show();
        }

        // Set up the year spinner/dropdown list
        Spinner spinnerYr = (Spinner) findViewById(R.id.spinner_yr);
        ArrayAdapter<CharSequence> adapterYr = ArrayAdapter.createFromResource(this,
                R.array.heatmaps_datasets_array_yr, android.R.layout.simple_spinner_item);
        adapterYr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerYr.setAdapter(adapterYr);
//        spinnerYr.setOnItemSelectedListener(new SpinnerActivity());

        try {
            mListsYr.put(getString(R.string.yr2019), new DataSet(readItems(R.raw.yr2019springwhiteflies),
                    getString(R.string.yr2019_url)));
            mListsYr.put(getString(R.string.yr2018), new DataSet(readItems(R.raw.yr2018springwhiteflies),
                    getString(R.string.yr2018_url)));
            mListsYr.put(getString(R.string.yr2017), new DataSet(readItems(R.raw.yr2017springwhiteflies),
                    getString(R.string.yr2017_url)));
            mListsYr.put(getString(R.string.yr2016), new DataSet(readItems(R.raw.yr2016springwhiteflies),
                    getString(R.string.yr2016_url)));
            mListsYr.put(getString(R.string.yr2015), new DataSet(readItems(R.raw.yr2015springwhiteflies),
                    getString(R.string.yr2015_url)));
        } catch (JSONException e) {
            Toast.makeText(this, "Problem reading list of markers.", Toast.LENGTH_LONG).show();
        }

        // Set up the pest spinner/dropdown list
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.heatmaps_datasets_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new SpinnerActivity());

        try {
            mLists.put(getString(R.string.whiteflies), new DataSet(readItems(R.raw.yr2019springwhiteflies),
                    getString(R.string.whiteflies_url)));
            mLists.put(getString(R.string.brown_stink_bug), new DataSet(readItems(R.raw.yr2019springbrown_stink_bug),
                    getString(R.string.brown_Stink_Bug_url)));
        } catch (JSONException e) {
            Toast.makeText(this, "Problem reading list of markers.", Toast.LENGTH_LONG).show();
        }
    }

    public void changeRadius(View view) {
        if (mDefaultRadius) {
            mProvider.setRadius(ALT_HEATMAP_RADIUS);
        } else {
            mProvider.setRadius(HeatmapTileProvider.DEFAULT_RADIUS);
        }
        mOverlay.clearTileCache();
        mDefaultRadius = !mDefaultRadius;
    }

    public void changeGradient(View view) {
        if (mDefaultGradient) {
            mProvider.setGradient(ALT_HEATMAP_GRADIENT);
        } else {
            mProvider.setGradient(HeatmapTileProvider.DEFAULT_GRADIENT);
        }
        mOverlay.clearTileCache();
        mDefaultGradient = !mDefaultGradient;
    }

    public void changeOpacity(View view) {
        if (mDefaultOpacity) {
            mProvider.setOpacity(ALT_HEATMAP_OPACITY);
        } else {
            mProvider.setOpacity(HeatmapTileProvider.DEFAULT_OPACITY);
        }
        mOverlay.clearTileCache();
        mDefaultOpacity = !mDefaultOpacity;
    }

    // Dealing with spinner choices
    public class SpinnerActivity implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int pos, long id) {
            String dataset = parent.getItemAtPosition(pos).toString();

            TextView attribution = ((TextView) findViewById(R.id.attribution));

            // Check if need to instantiate (avoid setData etc twice)
            if (mProvider == null) {
                mProvider = new HeatmapTileProvider.Builder().data(
                        mLists.get(getString(R.string.whiteflies)).getData()).build();
                mOverlay = getMap().addTileOverlay(new TileOverlayOptions().tileProvider(mProvider));
                // Render links
                attribution.setMovementMethod(LinkMovementMethod.getInstance());
            } else {
                mProvider.setData(mLists.get(dataset).getData());
                mOverlay.clearTileCache();
            }
            // Update attribution
            attribution.setText(Html.fromHtml(String.format(getString(R.string.attrib_format),
                    mLists.get(dataset).getUrl())));

        }

        public void onNothingSelected(AdapterView<?> parent) {
            // Another interface callback
        }
    }

    // testing data set
    private ArrayList<LatLng> readItems(int resource) throws JSONException {
        ArrayList<LatLng> list = new ArrayList<LatLng>();
        InputStream inputStream = getResources().openRawResource(resource);
        String json = new Scanner(inputStream).useDelimiter("\\A").next();
        JSONArray array = new JSONArray(json);
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            double lat = object.getDouble("lat");
            double lng = object.getDouble("lng");
            list.add(new LatLng(lat, lng));
        }
        return list;
    }

    /**
     * Helper class - stores data sets and sources.
     */
    private class DataSet {
        private ArrayList<LatLng> mDataset;
        private String mUrl;

        public DataSet(ArrayList<LatLng> dataSet, String url) {
            this.mDataset = dataSet;
            this.mUrl = url;
        }

        public ArrayList<LatLng> getData() {
            return mDataset;
        }

        public String getUrl() {
            return mUrl;
        }
    }

}
