package com.espressif.esptouch.android;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.espressif.esptouch.android.main.RequestTask;

import java.util.List;

public  class ListDevicesAdapter extends RecyclerView.Adapter<ListDevicesAdapter.ViewHolder> {

    private List<Device> mData;
    private LayoutInflater mInflater;
    private Context context;

    public ListDevicesAdapter(List<Device> devicesList, Context context){
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = devicesList;
    }

    //@Override
    //public void onBindViewHolder(@NonNull ListDevicesAdapter.ViewHolder holder, int position, @NonNull List<Object> payloads) {
        //super.onBindViewHolder(holder, position, payloads);
    //    holder.bindData(mData.get(position));
    //}

    @NonNull
    @Override
    public ListDevicesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_devices, null);
        return new ListDevicesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iconImage;
        TextView nombre, estado, enLinea, ip;

        ViewHolder(View itemView){
            super(itemView);
            iconImage = itemView.findViewById(R.id.iconImageView);
            nombre = itemView.findViewById(R.id.nombreTextView);
            estado = itemView.findViewById(R.id.estadoTextView);
            enLinea = itemView.findViewById(R.id.enLineaTextView);
            ip = itemView.findViewById(R.id.ipTextView);

            itemView.setOnClickListener(view -> {
                //enviar al server el comando armar
                //new RequestTask().execute("1");
                if (estado.getText() == "DESARMADA"){
                    Context context = itemView.getContext();
                    //Device[] devices = context.getAssets();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        context.getApplicationContext().getParams();
                    }
                    ApplicationInfo appInfo = context.getApplicationInfo();
//                    CharSequence txt = context.getText(0);

                    new RequestTask().execute(ip.getText()+":613","ArmDisarm","state","1");
                    estado.setText("ARMADA");
                    iconImage.setColorFilter(Color.parseColor("#cb4335"), PorterDuff.Mode.SRC_IN);
                    iconImage.setImageResource(R.drawable.ic_baseline_lock_48);

                }else{
                    new RequestTask().execute(ip.getText()+":613","ArmDisarm","state","0");
                    estado.setText("DESARMADA");
                    iconImage.setColorFilter(Color.parseColor("#2ecc71"), PorterDuff.Mode.SRC_IN);
                    iconImage.setImageResource(R.drawable.ic_baseline_lock_open_48);
                }

            });
        }

        void bindData(final Device item){

            iconImage.setColorFilter(Color.parseColor(item.getEstado() == "ARMADA" ? "#cb4335": "#2ecc71"), PorterDuff.Mode.SRC_IN);
            nombre.setText(item.getNombre());
            estado.setText(item.getEstado());
            enLinea.setText(item.getEnLinea());
            enLinea.setTextColor(Color.parseColor(item.getEnLinea() == "EN LINEA" ?"#2ecc71": "#cb4335" ));
            ip.setText(item.IP);
        }

    }
}
