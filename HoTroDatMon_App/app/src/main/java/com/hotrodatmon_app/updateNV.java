package com.hotrodatmon_app;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.hotrodatmon_app.DAL.QL_NhanVien;


public class updateNV extends AppCompatActivity {
    EditText edtMaNV, edtTen, edtSDT, edtCCCD, edtUser, edtPass,edtConfirm;
    Button btnSave, btnThoat;
    QL_NhanVien ql_nhanVien;
    Toolbar toolbar;
    boolean passVisible;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updatenv);
        addControls();
        toolbar= findViewById(R.id.backToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String maNV = getIntent().getStringExtra("MaNV");
        edtMaNV.setText(maNV);
        String ten=getIntent().getStringExtra("Ten");
        edtTen.setText(ten);
        String sdt = getIntent().getStringExtra("Sdt");
        String cccd = getIntent().getStringExtra("cccd");
        String tk = getIntent().getStringExtra("TK");
        String mk = getIntent().getStringExtra("Pass");
        edtCCCD.setText(cccd);
        edtSDT.setText(sdt);
        edtUser.setText(tk);
        edtPass.setText(mk);
        edtConfirm.setText(mk);
        ql_nhanVien=new QL_NhanVien(this);
        edtPass.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int right=2;
                if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                    if(motionEvent.getRawX()>=edtPass.getRight()-edtPass.getCompoundDrawables()[right].getBounds().width()){
                        int selection=edtPass.getSelectionEnd();
                        if(passVisible){
                            edtPass.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.baseline_visibility_off_24,0);
                            edtPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passVisible=false;
                        }else {
                            edtPass.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.baseline_visibility_24,0);
                            edtPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passVisible=true;

                        }
                        edtPass.setSelection(selection);
                        return true;

                    }
                }
                return false;
            }
        });
        edtConfirm.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int right=2;
                if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                    if(motionEvent.getRawX()>=edtConfirm.getRight()-edtConfirm.getCompoundDrawables()[right].getBounds().width()){
                        int selection=edtConfirm.getSelectionEnd();
                        if(passVisible){
                            edtConfirm.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.baseline_visibility_off_24,0);
                            edtConfirm.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passVisible=false;
                        }else {
                            edtConfirm.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.baseline_visibility_24,0);
                            edtConfirm.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passVisible=true;

                        }
                        edtConfirm.setSelection(selection);
                        return true;

                    }
                }
                return false;
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String maNV = edtMaNV.getText().toString();

                String ten = edtTen.getText().toString();
                String sdt = edtSDT.getText().toString();
                String cccd = edtCCCD.getText().toString();
                String tk = edtUser.getText().toString();
                String mk = edtPass.getText().toString();
                String confirmmk=edtConfirm.getText().toString();
                    // cập nhật thông tin nhân viên
                if (isEditTextEmpty(edtTen)) {
                    showErrorAndFocus(edtTen, "Vui lòng nhập tên nhân viên");

                    return;
                }
                if (sdt.length()!=10) {
                    showErrorAndFocus(edtSDT, "Số Điện Thoại Có 11 Số!");
                    return;
                }
                if (cccd.length()!=10) {
                    showErrorAndFocus(edtCCCD, "CCCD Có 11 Số!");
                    return;
                }
                if (isEditTextEmpty(edtUser)) {
                    showErrorAndFocus(edtUser, "Vui Lòng Nhập User!");

                    return;
                }
                if (isEditTextEmpty(edtPass)) {
                    showErrorAndFocus(edtPass, "Vui Lòng Nhập Pass!");

                    return;
                }
                if(!mk.equals(confirmmk)){
                    showErrorAndFocus(edtConfirm,"Mật Khẩu phải trùng khớp!");

                }else{
                    boolean result = ql_nhanVien.updateNV(maNV, ten, sdt, cccd, tk, mk);
                    if (result) {
                        Toast.makeText(getApplicationContext(), "Cập nhật thông tin nhân viên thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(updateNV.this, NhanSuActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Cập nhật thông tin nhân viên không thành công", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    private boolean isEditTextEmpty(EditText editText) {
        return editText.getText().toString().trim().isEmpty();
    }
    private void showErrorAndFocus(EditText editText, String errorMessage) {
        Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
        editText.setError(errorMessage);
        editText.requestFocus();
    }
    private void addControls(){
        edtConfirm=(EditText) findViewById(R.id.edtConfirm);
        edtMaNV=(EditText) findViewById(R.id.edtMaNV);
        edtTen=(EditText) findViewById(R.id.edtTenNV);
        edtSDT=(EditText) findViewById(R.id.edtSDT);
        edtCCCD=(EditText) findViewById(R.id.edtCCCD);
        edtUser=(EditText) findViewById(R.id.edtUser);
        edtPass=(EditText) findViewById(R.id.edtPass);

        btnSave=(Button) findViewById(R.id.btnSave);
    }
}
