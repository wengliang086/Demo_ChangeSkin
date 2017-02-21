package com.demo.changeskin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class MenuLeftFragment extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_menu, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.id_rl_innerchange01).setOnClickListener(this);
        view.findViewById(R.id.id_rl_innerchange02).setOnClickListener(this);
        view.findViewById(R.id.id_restore).setOnClickListener(this);
        view.findViewById(R.id.id_changeskin).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_rl_innerchange01:
                Toast.makeText(getActivity(), "id_rl_innerchange01", Toast.LENGTH_SHORT).show();
                break;
            case R.id.id_rl_innerchange02:
                Toast.makeText(getActivity(), "id_rl_innerchange02", Toast.LENGTH_SHORT).show();
                break;
            case R.id.id_restore:
                Toast.makeText(getActivity(), "id_restore", Toast.LENGTH_SHORT).show();
                break;
            case R.id.id_changeskin:
                Toast.makeText(getActivity(), "id_changeskin", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
