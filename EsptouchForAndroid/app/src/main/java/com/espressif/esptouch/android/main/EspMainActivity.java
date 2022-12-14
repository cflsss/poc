package com.espressif.esptouch.android.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.espressif.esptouch.android.Device;
import com.espressif.esptouch.android.ListDevicesAdapter;
import com.espressif.esptouch.android.R;
import com.espressif.esptouch.android.v1.EspTouchActivity;
import com.espressif.esptouch.android.v2.EspTouch2Activity;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class EspMainActivity extends AppCompatActivity {
    private static final String[] ITEMS = {
            "EspTouch",
            "EspTouch V2"
    };

    List<Device> devices;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.main_title);

        try {
            init();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



       /* Button armDisarmBtn = (Button) findViewById(R.id.armDisarmBtn);
        armDisarmBtn.setOnClickListener(view -> {
            //enviar al server el comando armar
            //new RequestTask().execute("1");
            if (armDisarmBtn.getText() == "ARMAR"){
                new RequestTask().execute("186.57.143.30:613","ArmDisarm","state","1");
                armDisarmBtn.setText("DESARMAR");
            }else{
                new RequestTask().execute("186.57.143.30:613","ArmDisarm","state","0");
                armDisarmBtn.setText("ARMAR");
            }
            setTitle(R.string.main_title + " - Armada");

        });*/

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, OrientationHelper.VERTICAL));
        recyclerView.setAdapter(new ItemAdapter());
    }

    private class ItemHolder extends RecyclerView.ViewHolder {
        int position;
        TextView label;

        ItemHolder(@NonNull View itemView) {
            super(itemView);

            label = itemView.findViewById(R.id.label);

            itemView.setOnClickListener(v -> {
                switch (position) {
                    case 0:
                        startActivity(new Intent(EspMainActivity.this, EspTouchActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(EspMainActivity.this, EspTouch2Activity.class));
                        break;
                }
            });
        }
    }

    private class ItemAdapter extends RecyclerView.Adapter<ItemHolder> {

        @NonNull
        @Override
        public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = getLayoutInflater().inflate(R.layout.activity_main_item, parent, false);
            return new ItemHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
            String item = ITEMS[position];
            holder.position = position;
            holder.label.setText(item);
        }

        @Override
        public int getItemCount() {
            return ITEMS.length;
        }
    }

    private void init() throws ExecutionException, InterruptedException {
        devices = new ArrayList<>();

        ObtainDevicesTask obtainDevicesTask = new ObtainDevicesTask();
        Device[] response = obtainDevicesTask.execute("cflsss.cf","ESP/obtenerips.php").get();

        for(Device device: response) {
            RequestTask requestTask = new RequestTask();
            String enLinea = requestTask.execute(device.getIP() + ":613", "isOnline").get();

            String deviceEnLinea = enLinea.equals("ONLINE")?"EN LINEA":"DESCONECTADO";

            devices.add(new Device("CASA", "DESARMADA",deviceEnLinea , "", device.getIP(), ""));
        }

        ListDevicesAdapter listDevicesAdapter = new ListDevicesAdapter(devices, this);
        RecyclerView recyclerView= findViewById(R.id.listRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listDevicesAdapter);
    }
}
