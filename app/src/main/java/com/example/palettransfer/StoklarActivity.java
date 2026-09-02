package com.example.palettransfer;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StoklarActivity extends AppCompatActivity {

    private RecyclerView rvStoklar;
    private StoklarAdapter adapter;
    private List<StokModel> stokList;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView tvDepoInfo;

    private String COMPANY = "";
    private String PLANT = "";
    private String WAREHOUSE = "";
    private String STOCKPLACE = "";
    private String PLANTTXT = "";

    private ExecutorService executorService;
    private Handler mainThreadHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stoklar);

        executorService = Executors.newSingleThreadExecutor();
        mainThreadHandler = new Handler(Looper.getMainLooper());

        rvStoklar = findViewById(R.id.rv_stoklar);
        progressBar = findViewById(R.id.progressBar);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        tvDepoInfo = findViewById(R.id.tv_depo_info);

        ImageButton btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> finish());

        stokList = new ArrayList<>();
        adapter = new StoklarAdapter(stokList);
        rvStoklar.setLayoutManager(new LinearLayoutManager(this));
        rvStoklar.setAdapter(adapter);

        Bundle g = getIntent().getExtras();
        if (g != null) {
            COMPANY = g.getString("COMPANY", "01");
            PLANT = g.getString("PLANT", "");
            PLANTTXT = g.getString("PLANTTXT", "");
            WAREHOUSE = g.getString("WAREHOUSE", "");
            STOCKPLACE = g.getString("STOCKPLACE", "");
        }

        tvDepoInfo.setText("Tesis: " + PLANTTXT + "\nDepo: " + WAREHOUSE + " / " + STOCKPLACE);

        swipeRefreshLayout.setOnRefreshListener(this::fetchData);

        fetchData();
    }

    private void fetchData() {
        progressBar.setVisibility(View.VISIBLE);
        
        executorService.execute(() -> {
            List<StokModel> tempList = new ArrayList<>();
            boolean success = false;
            String errorMsg = "";

            try {
                String apiUrl = "https://webservis.tiresutkoop.org/tsws/TireSutWS.asmx/DepoStokDurumu" +
                        "?token=67d55f1f766b9e4d59db80d54e9c29c3ed8cf2d3fd4d6ea813ef52086cee33bc&cli=00" +
                        "&comp=" + COMPANY +
                        "&pla=" + PLANT +
                        "&wh=" + WAREHOUSE +
                        "&sp=" + STOCKPLACE;

                URL url = new URL(apiUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(10000);
                conn.setReadTimeout(15000);

                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream is = conn.getInputStream();
                    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                    factory.setNamespaceAware(true);
                    XmlPullParser parser = factory.newPullParser();
                    parser.setInput(is, null);

                    int eventType = parser.getEventType();
                    String material = "";
                    String stext = "";
                    double stok = 0;
                    String currentTag = "";

                    while (eventType != XmlPullParser.END_DOCUMENT) {
                        if (eventType == XmlPullParser.START_TAG) {
                            currentTag = parser.getName();
                        } else if (eventType == XmlPullParser.TEXT) {
                            if (currentTag.equals("MATERIAL")) {
                                material = parser.getText();
                            } else if (currentTag.equals("STEXT")) {
                                stext = parser.getText();
                            } else if (currentTag.equals("STOK")) {
                                try {
                                    stok = Double.parseDouble(parser.getText());
                                } catch (NumberFormatException e) {
                                    stok = 0;
                                }
                            }
                        } else if (eventType == XmlPullParser.END_TAG) {
                            if (parser.getName().equals("Table")) {
                                tempList.add(new StokModel(material, stext, stok));
                                material = "";
                                stext = "";
                                stok = 0;
                            }
                            currentTag = "";
                        }
                        eventType = parser.next();
                    }
                    is.close();
                    success = true;
                } else {
                    errorMsg = "HTTP Hatası: " + conn.getResponseCode();
                }

            } catch (Exception e) {
                Log.e("StoklarActivity", "API Error", e);
                errorMsg = e.getMessage();
            }

            final boolean finalSuccess = success;
            final String finalErrorMsg = errorMsg;

            mainThreadHandler.post(() -> {
                progressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                if (finalSuccess) {
                    stokList.clear();
                    stokList.addAll(tempList);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(StoklarActivity.this, "Veri alınamadı: " + finalErrorMsg, Toast.LENGTH_LONG).show();
                }
            });
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (executorService != null) {
            executorService.shutdown();
        }
    }
}
