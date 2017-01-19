package xiren.soft.com.mydialog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xiren.soft.com.mydialog.ui.dialog.EditDialog;
import xiren.soft.com.mydialog.ui.dialog.OkDialog;
import xiren.soft.com.mydialog.util.LogUtil;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.btn_dialog1)
    Button btnDialog1;
    @Bind(R.id.btn_dialog2)
    Button btnDialog2;
    @Bind(R.id.btn_dialog3)
    Button btnDialog3;
    @Bind(R.id.btn_dialog4)
    Button btnDialog4;
    @Bind(R.id.btn_dialog5)
    Button btnDialog5;
    @Bind(R.id.btn_dialog6)
    Button btnDialog6;
    @Bind(R.id.btn_dialog7)
    Button btnDialog7;
    @Bind(R.id.btn_dialog8)
    Button btnDialog8;
    @Bind(R.id.activity_main)
    LinearLayout activityMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.btn_dialog1, R.id.btn_dialog2, R.id.btn_dialog3, R.id.btn_dialog4, R.id.btn_dialog5, R.id.btn_dialog6, R.id.btn_dialog7, R.id.btn_dialog8})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_dialog1:
                OkDialog okDialog = new OkDialog(this, "确认是否执行操作？", new OkDialog.OnDialogListener() {
                    @Override
                    public void onDialogOK(OkDialog okDialog) {

                    }

                    @Override
                    public void onDialogCancel(OkDialog okDialog) {

                    }
                });
                okDialog.show();
                LogUtil.d("btn_dialog1");
                break;
            case R.id.btn_dialog2:
                EditDialog editDialog = new EditDialog(this, new EditDialog.OnDialogListener() {
                    @Override
                    public void onDialogOK(String message) {

                    }

                    @Override
                    public void onDialogCancel(String message) {

                    }
                });
                editDialog.show();
                break;
            case R.id.btn_dialog3:
                break;
            case R.id.btn_dialog4:
                break;
            case R.id.btn_dialog5:
                break;
            case R.id.btn_dialog6:
                break;
            case R.id.btn_dialog7:
                break;
            case R.id.btn_dialog8:
                break;
        }
    }
}
