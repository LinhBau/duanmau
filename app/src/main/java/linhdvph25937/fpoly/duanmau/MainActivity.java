package linhdvph25937.fpoly.duanmau;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.navigation.NavigationView;

import linhdvph25937.fpoly.duanmau.DAO.SachDAO;
import linhdvph25937.fpoly.duanmau.Fragment.DoanhThuFragment;
import linhdvph25937.fpoly.duanmau.Fragment.DoiMatKhauFragment;
import linhdvph25937.fpoly.duanmau.Fragment.LoaiSachFragment;
import linhdvph25937.fpoly.duanmau.Fragment.PhieuMuonFragment;
import linhdvph25937.fpoly.duanmau.Fragment.SachFragment;
import linhdvph25937.fpoly.duanmau.Fragment.ThanhVienFragment;
import linhdvph25937.fpoly.duanmau.Fragment.ThemNguoiDungFragment;
import linhdvph25937.fpoly.duanmau.Fragment.Top10Fragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AnhXa();
        toolbar.setTitle("");
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, 0,0);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

//        Intent intent = getIntent();
//        String user = intent.getStringExtra("nameUser");
//        if (user.equals("admin")){
//            navigationView.getMenu().findItem(R.id.id_top_10_sach).setVisible(true);
//            navigationView.getMenu().findItem(R.id.id_doanh_thu).setVisible(true);
//            navigationView.getMenu().findItem(R.id.id_them_nguoi_dung).setVisible(true);
//        }

        ChooseFragment(SachFragment.newInstance());
        toolbar.setTitle("Sách");
    }

    private void AnhXa(){
        drawerLayout = findViewById(R.id.drawerLayout);
        toolbar = findViewById(R.id.toolBar);
        navigationView = findViewById(R.id.navigationView);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.id_quan_ly_phieu_muon:
                ChooseFragment(PhieuMuonFragment.newInstance());
                toolbar.setTitle("Phiếu mượn");
                break;
            case R.id.id_quan_ly_loai_sach:
                ChooseFragment(LoaiSachFragment.newInstance());
                toolbar.setTitle("Loại sách");
                break;
            case R.id.id_quan_ly_sach:
                ChooseFragment(SachFragment.newInstance());
                toolbar.setTitle("Sách");
                break;
            case R.id.id_quan_ly_thanh_vien:
                ChooseFragment(ThanhVienFragment.newInstance());
                toolbar.setTitle("Thành viên");
                break;
            case R.id.id_top_10_sach:
                ChooseFragment(Top10Fragment.newInstance());
                toolbar.setTitle("Top 10 sách");
                break;
            case R.id.id_doanh_thu:
                ChooseFragment(DoanhThuFragment.newInstance());
                toolbar.setTitle("Doanh thu");
                break;
            case R.id.id_them_nguoi_dung:
                ChooseFragment(ThemNguoiDungFragment.newInstance());
                toolbar.setTitle("Thêm người dùng");
                break;
            case R.id.id_doi_mat_khau:
                ChooseFragment(DoiMatKhauFragment.newInstance());
                toolbar.setTitle("Đổi mật khẩu");
                break;
            case R.id.id_exit:
                Exit();
                break;
        }
        drawerLayout.closeDrawer(navigationView);
        return true;
    }




    public void Exit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Exit Program");
        builder.setIcon(R.drawable.ic_baseline_exit_to_app_24);
        builder.setMessage("Are you sure?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(getApplicationContext(), ManHinhDangNhapActivity.class));
                finish();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.show();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(navigationView)) {
            drawerLayout.closeDrawer(navigationView);
        }
        super.onBackPressed();
    }

    private void ChooseFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.addToBackStack("prrrrrrrrrrrrrrr");
        transaction.commit();
    }
}