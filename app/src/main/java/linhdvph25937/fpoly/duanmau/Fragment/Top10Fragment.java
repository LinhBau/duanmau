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

import java.util.ArrayList;

import linhdvph25937.fpoly.duanmau.Adapter.TopAdapter;
import linhdvph25937.fpoly.duanmau.DAO.ThongKeDAO;
import linhdvph25937.fpoly.duanmau.DTO.Top;
import linhdvph25937.fpoly.duanmau.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Top10Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Top10Fragment extends Fragment {
    private RecyclerView lvDs;
    private TopAdapter adapter;
    private ThongKeDAO dao;
    private ArrayList<Top> list;

    public Top10Fragment() {
        // Required empty public constructor
    }

    public static Top10Fragment newInstance() {
        Top10Fragment fragment = new Top10Fragment();
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
        return inflater.inflate(R.layout.fragment_top10, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lvDs = view.findViewById(R.id.lv_top_10);
        dao = new ThongKeDAO(getActivity());
        list = (ArrayList<Top>) dao.getTop();
        adapter = new TopAdapter(getActivity(), dao);
        adapter.setData(list);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        lvDs.setLayoutManager(manager);
        lvDs.setAdapter(adapter);
    }
}