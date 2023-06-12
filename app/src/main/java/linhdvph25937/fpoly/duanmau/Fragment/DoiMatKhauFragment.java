package linhdvph25937.fpoly.duanmau.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import linhdvph25937.fpoly.duanmau.DAO.ThuThuDAO;
import linhdvph25937.fpoly.duanmau.DTO.ThuThu;
import linhdvph25937.fpoly.duanmau.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DoiMatKhauFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DoiMatKhauFragment extends Fragment {
    private TextInputEditText edOldPass, edPass, edRePass;
    private Button btnSave, btnCancel;
    private ThuThuDAO dao;
    private SharedPreferences sharedPreferences;
    public DoiMatKhauFragment() {
        // Required empty public constructor
    }

    public static DoiMatKhauFragment newInstance() {
        DoiMatKhauFragment fragment = new DoiMatKhauFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doi_mat_khau, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edOldPass = view.findViewById(R.id.ed_mat_khau_cu);
        edPass = view.findViewById(R.id.ed_mat_khau_moi);
        edRePass = view.findViewById(R.id.ed_mat_khau_moi_re);
        dao = new ThuThuDAO(getActivity());
        btnSave = view.findViewById(R.id.btn_save_doi_mat_khau);
        btnCancel = view.findViewById(R.id.btn_cancel_doi_mat_khau);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edOldPass.setText("");
                edPass.setText("");
                edRePass.setText("");
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangePassword();
            }
        });
    }

    private void ChangePassword() {
        if (Validate() > 0){
            sharedPreferences = getActivity().getSharedPreferences("FILE", Context.MODE_PRIVATE);
            String user = sharedPreferences.getString("USER", "");
            ThuThu obj = dao.getId(user);
            obj.setMatKhau(edPass.getText().toString());
            if (dao.update(obj) > 0){
                Toast.makeText(getActivity(), "Sửa thành công", Toast.LENGTH_SHORT).show();
                edOldPass.setText("");
                edPass.setText("");
                edRePass.setText("");
            }else {
                Toast.makeText(getActivity(), "Sửa thất bại", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private int Validate(){
        int check = 1;
        sharedPreferences = getActivity().getSharedPreferences("FILE", Context.MODE_PRIVATE);
        String passCheck = sharedPreferences.getString("PASS", "");

        String oldPass = edOldPass.getText().toString();
        String pass = edPass.getText().toString();
        String rePass = edRePass.getText().toString();
        if (oldPass.isEmpty() || pass.isEmpty() || rePass.isEmpty()){
            Toast.makeText(getActivity(), "Không được để trống thông tin", Toast.LENGTH_SHORT).show();
            check = -1;
        }else if (!oldPass.equals(passCheck)){
            Toast.makeText(getActivity(), "Mật khẩu cũ không đúng", Toast.LENGTH_SHORT).show();
            check = -1;
        }else if (!rePass.equals(pass)){
            Toast.makeText(getActivity(), "Mật khẩu nhập lại không đúng", Toast.LENGTH_SHORT).show();
            check = -1;
        }

        return check;
    }
}