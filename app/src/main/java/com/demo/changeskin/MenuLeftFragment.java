package com.demo.changeskin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class MenuLeftFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_menu, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.id_btn_default).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "default", Toast.LENGTH_SHORT).show();
                ((MainActivity) getActivity()).resetDefault();
            }
        });
        view.findViewById(R.id.id_btn_inside).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "inside", Toast.LENGTH_SHORT).show();
            }
        });
        view.findViewById(R.id.id_btn_plugin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "plugin", Toast.LENGTH_SHORT).show();
                ((MainActivity) getActivity()).loadPlagin();
            }
        });
    }
}
