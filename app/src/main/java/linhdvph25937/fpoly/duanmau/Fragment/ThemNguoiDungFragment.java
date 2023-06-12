package linhdvph25937.fpoly.duanmau.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import linhdvph25937.fpoly.duanmau.DAO.ThuThuDAO;
import linhdvph25937.fpoly.duanmau.DTO.ThuThu;
import linhdvph25937.fpoly.duanmau.DsNguoiDungActivity;
import linhdvph25937.fpoly.duanmau.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThemNguoiDungFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThemNguoiDungFragment extends Fragment {
    private TextInputEditText edUser, edFullName, edPass, edRePass;
    private Button btnSave, btnCancel;
    private ThuThuDAO dao;
    private ImageView imgLogin;

    public ThemNguoiDungFragment() {
        // Required empty public constructor
    }


    public static ThemNguoiDungFragment newInstance() {
        ThemNguoiDungFragment fragment = new ThemNguoiDungFragment();
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
        return inflater.inflate(R.layout.fragment_them_nguoi_dung, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imgLogin = view.findViewById(R.id.imgLoginNguoiDung);
        edUser = view.findViewById(R.id.ed_ten_dang_nhap_them_nguoi_dung);
        edFullName = view.findViewById(R.id.ed_ho_ten_them_nguoi_dung);
        edPass = view.findViewById(R.id.ed_mat_khau_them_nguoi_dung);
        edRePass = view.findViewById(R.id.ed_mat_khau_re_them_nguoi_dung);
        btnSave = view.findViewById(R.id.btn_save_them_nguoi_dung);
        btnCancel = view.findViewById(R.id.btn_cancel_them_nguoi_dung);
        dao = new ThuThuDAO(getActivity());

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edUser.setText("");
                edFullName.setText("");
                edPass.setText("");
                edRePass.setText("");
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddUser();
            }
        });

        imgLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), DsNguoiDungActivity.class));
            }
        });
    }

    private void AddUser() {
        if (Validate() > 0){
            ThuThu obj = new ThuThu();
            obj.setMaTT(edUser.getText().toString());
            obj.setHoTen(edFullName.getText().toString());
            obj.setMatKhau(edPass.getText().toString());
            long res = dao.insert(obj);
            if (res>0){
                Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                edUser.setText("");
                edFullName.setText("");
                edPass.setText("");
                edRePass.setText("");
            }else{
                Toast.makeText(getActivity(), "Không thêm được", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private int Validate(){
        int check = 1;
        String user = edUser.getText().toString();
        String fullName = edFullName.getText().toString();
        String pass = edPass.getText().toString();
        String rePass = edRePass.getText().toString();

        if (user.isEmpty() || fullName.isEmpty() || pass.isEmpty() || rePass.isEmpty()){
            Toast.makeText(getActivity(), "Không được để trống thông tin", Toast.LENGTH_SHORT).show();
            check = -1;
        }else if (!pass.equals(rePass)){
            Toast.makeText(getActivity(), "Mật khẩu nhập lại không trùng khớp", Toast.LENGTH_SHORT).show();
            check = -1;
        }
        return check;
    }
}