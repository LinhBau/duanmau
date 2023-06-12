package linhdvph25937.fpoly.duanmau;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import linhdvph25937.fpoly.duanmau.DAO.ThuThuDAO;

public class ManHinhDangNhapActivity extends AppCompatActivity {
    private TextInputEditText edUser, edPass;
    private CheckBox chkCheck;
    private Button btnLogin, btnCancel;
    private ThuThuDAO dao;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_dang_nhap);

        AnhXa();
        dao = new ThuThuDAO(getApplicationContext());
        sharedPreferences = getSharedPreferences("FILE", MODE_PRIVATE);
        edUser.setText(sharedPreferences.getString("USER", ""));
        edPass.setText(sharedPreferences.getString("PASS", ""));
        chkCheck.setChecked(sharedPreferences.getBoolean("CHECK", false));

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edUser.setText("");
                edPass.setText("");
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckLogin();
            }
        });
    }

    private void CheckLogin(){
        String user = edUser.getText().toString();
        String pass = edPass.getText().toString();
        if (user.isEmpty() || pass.isEmpty()){
            Toast.makeText(this, "Không được để trống thông tin", Toast.LENGTH_SHORT).show();
        }else if (dao.CheckLogin(user, pass) > 0 || (user.equals("admin") && pass.equals("admin"))){
            Toast.makeText(this, "Login successfully", Toast.LENGTH_SHORT).show();
            Remember(user, pass, chkCheck.isChecked());
            Intent intent = new Intent(ManHinhDangNhapActivity.this, MainActivity.class);
            intent.putExtra("nameUser", user);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(this, "Thông tin không đúng", Toast.LENGTH_SHORT).show();
        }

    }

    public void Remember(String user, String pass, boolean status){
        editor = sharedPreferences.edit();
        if (status){
            editor.putString("USER", user);
            editor.putString("PASS", pass);
            editor.putBoolean("CHECK", status);
        }else{
            editor.remove("PASS");
            editor.remove("CHECK");
        }
        editor.commit();
    }

    private void AnhXa(){
        edUser = findViewById(R.id.id_text_input_username);
        edPass = findViewById(R.id.id_text_input_password);
        chkCheck = findViewById(R.id.id_chk_save_input);
        btnLogin= findViewById(R.id.id_btn_dang_nhap);
        btnCancel = findViewById(R.id.id_btn_huy);
    }
}