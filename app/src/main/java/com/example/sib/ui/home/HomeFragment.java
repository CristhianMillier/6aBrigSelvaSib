package com.example.sib.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.sib.EscudoBrig;
import com.example.sib.GaleriaBrig;
import com.example.sib.HimnoBrig;
import com.example.sib.HistoriaBrig;
import com.example.sib.MainActivity;
import com.example.sib.R;
import com.example.sib.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private ImageButton fac, ig, yt, tik;
    private Button atras, himno, historia, escudo, comandos;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        fac = root.findViewById(R.id.facebook);
        fac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri url = Uri.parse("https://www.facebook.com/SEXTABRIGADADESELVA");
                Intent nuevo = new Intent(Intent.ACTION_VIEW, url);
                startActivity(nuevo);
            }
        });
        ig = root.findViewById(R.id.instagram);
        ig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri url = Uri.parse("https://instagram.com/sexta.brigada.de.selva?igshid=YmMyMTA2M2Y=");
                Intent nuevo = new Intent(Intent.ACTION_VIEW, url);
                startActivity(nuevo);
            }
        });
        yt = root.findViewById(R.id.youtube);
        yt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri url = Uri.parse("https://www.youtube.com/@SextaBrigadaSelva");
                Intent nuevo = new Intent(Intent.ACTION_VIEW, url);
                startActivity(nuevo);
            }
        });
        tik = root.findViewById(R.id.tiktok);
        tik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri url = Uri.parse("https://www.tiktok.com/@6a_brigsva?_t=8eQIrqLD810&_r=1");
                Intent nuevo = new Intent(Intent.ACTION_VIEW, url);
                startActivity(nuevo);
            }
        });
        atras = root.findViewById(R.id.atras);
        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nuevo = new Intent(getContext(), MainActivity.class);
                startActivity(nuevo);
            }
        });
        himno = root.findViewById(R.id.himnoBtn);
        himno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nuevo = new Intent(getContext(), HimnoBrig.class);
                startActivity(nuevo);
            }
        });
        historia = root.findViewById(R.id.historiaBtn);
        historia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nuevo = new Intent(getContext(), HistoriaBrig.class);
                startActivity(nuevo);
            }
        });
        escudo = root.findViewById(R.id.escudoBtn);
        escudo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nuevo = new Intent(getContext(), EscudoBrig.class);
                startActivity(nuevo);
            }
        });
        comandos = root.findViewById(R.id.galeriaBtn);
        comandos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nuevo = new Intent(getContext(), GaleriaBrig.class);
                startActivity(nuevo);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}