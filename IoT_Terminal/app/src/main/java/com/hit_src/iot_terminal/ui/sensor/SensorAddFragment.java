package com.hit_src.iot_terminal.ui.sensor;

import android.os.Bundle;
import android.os.RemoteException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.hit_src.iot_terminal.GlobalVar;
import com.hit_src.iot_terminal.MainActivity;
import com.hit_src.iot_terminal.MainApplication;
import com.hit_src.iot_terminal.R;
import com.hit_src.iot_terminal.object.sensortype.SensorType;

import java.util.HashSet;
import java.util.Set;

class SensorAddFragment extends Fragment {
    private Spinner typeSpinner;
    private EditText loraAddrEditText;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sensoradd, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart(){
        super.onStart();

        View view=getView();
        assert view != null;
        typeSpinner=view.findViewById(R.id.Sensor_Type_Spinner);
        loraAddrEditText=view.findViewById(R.id.Sensor_Add_LoraAddr_EditText);
        Button confirmButton=view.findViewById(R.id.Sensor_Add_Button);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String loraAddr=loraAddrEditText.getText().toString();
                if(loraAddr.isEmpty()){
                    Toast.makeText(getContext(),"LoRa地址不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    String datatype= (String) typeSpinner.getSelectedItem();
                    for(int i: GlobalVar.sensorTypeMap.keySet()){
                        SensorType now=GlobalVar.sensorTypeMap.get(i);
                        assert now != null;
                        if(datatype.equals(now.name)){
                            MainApplication.dbService.addSensor(i,Integer.parseInt(loraAddrEditText.getText().toString()));
                            break;
                        }
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                FragmentManager manager=getParentFragmentManager();
                FragmentTransaction transaction=manager.beginTransaction();
                transaction.remove(SensorAddFragment.this);
                transaction.commit();
            }
        });
        Set<String> set=new HashSet<>();
        for(int i: GlobalVar.sensorTypeMap.keySet()){
            SensorType sensorType=GlobalVar.sensorTypeMap.get(i);
            assert sensorType != null;
            set.add(sensorType.name);
        }
        Object[] tmp=set.toArray();
        String[] data=new String[tmp.length];
        for(int i=0;i<data.length;i++){
            data[i]= (String) tmp[i];
        }
        final ArrayAdapter<String> spinnerAdapter= new ArrayAdapter<>(MainApplication.self, R.layout.spinner_display_style, data);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_style);
        MainActivity.self.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                typeSpinner.setAdapter(spinnerAdapter);
                typeSpinner.setSelection(0);
            }
        });

    }
}
