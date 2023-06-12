package linhdvph25937.fpoly.duanmau.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import linhdvph25937.fpoly.duanmau.Adapter.PhieuMuonAdapter;
import linhdvph25937.fpoly.duanmau.DAO.PhieuMuonDAO;
import linhdvph25937.fpoly.duanmau.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PhieuMuonFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PhieuMuonFragment extends Fragment {
    private RecyclerView recyclerView;
    private FloatingActionButton btnAdd;
    private PhieuMuonAdapter adapter;
    private PhieuMuonDAO dao;
    public PhieuMuonFragment() {
        // Required empty public constructor
    }
    public static PhieuMuonFragment newInstance() {
        PhieuMuonFragment fragment = new PhieuMuonFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_phieu_muon, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.dsPhieuMuon);
        btnAdd = view.findViewById(R.id.btnAddPhieuMuon);
        dao = new PhieuMuonDAO(getActivity());
        adapter = new PhieuMuonAdapter(getActivity(), dao);
        adapter.setData(dao.getAll());
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.ShowAddDialog(getActivity());
            }
        });
    }
}