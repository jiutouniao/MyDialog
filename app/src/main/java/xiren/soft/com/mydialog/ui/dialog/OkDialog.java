package xiren.soft.com.mydialog.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xiren.soft.com.mydialog.R;

/**
 * description:	确认对话框
 * User: shaobing
 * Date: 2016/6/27
 * Time: 18:09
 */
public class OkDialog extends Dialog {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.btn_delete)
    TextView btnDelete;
    @Bind(R.id.btn_ok)
    TextView btnOk;
    @Bind(R.id.llyt_delete)
    LinearLayout llytDelete;
    @Bind(R.id.rlyt_dialog)
    RelativeLayout rlytDialog;
    //上下文
    private Context mContext;
    //接口回调
    private OnDialogListener mListener;
    //标题
    private String mText;
    //取消
    private String mCancelText;
    //确认
    private String mConfirmText;
    /**
     * @param mContext  上下文
     * @param title     标题
     * @param mListener 回调
     */
    public OkDialog(Context mContext, String title, OnDialogListener mListener) {
        super(mContext, R.style.CustomDialog);
        this.mContext = mContext;
        this.mListener = mListener;
        mText = title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_ok);
        ButterKnife.bind(this);
        /**************************************************************************************/
        this.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失 true消失  false不消失
        this.setCancelable(true);              //设置点击返回按钮消失   true 消失   false 不消失
        Window dialogWindow = this.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialogWindow.setAttributes(lp);
        dialogWindow.setGravity(Gravity.CENTER);
        setOwnerActivity((Activity) mContext);
        initView();
        initData();
        initEvent();
    }

    private void initView() {
    }

    private void initData() {
        enterAnima();
        setCancelText(mCancelText);
        setConfirmText(mConfirmText);
        setContentText(mText);
    }

    /**
     * 设置取消数值
     *
     * @param cancelText 取消按钮文字
     * @return dialog
     */
    public OkDialog setCancelText(String cancelText) {
        this.mCancelText = cancelText;
        if (null != btnDelete && mCancelText != null) {
            btnDelete.setText(mCancelText);
        }
        return this;
    }

    /**
     * 设置确定按钮
     *
     * @param confirmText 设置按钮文字
     * @return dialog
     */
    public OkDialog setConfirmText(String confirmText) {
        this.mConfirmText = confirmText;
        if (null != btnOk && mConfirmText != null) {
            btnOk.setText(mConfirmText);
        }
        return this;
    }

    /**
     * @param mText 设置标题
     * @return dialog
     */
    public OkDialog setContentText(String mText) {
        this.mText = mText;
        if (null != mText && null != mText) {
            tvTitle.setText(mText);
        }
        return this;
    }

    private void initEvent() {
    }

    public void hide() {
        this.dismiss();
    }
    /**
     * 进入动画
     */
    private void enterAnima() {
        rlytDialog.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.dialog_scale_vertical_line_in));
    }
    /**
     * 退出动画
     */
    private void exitAnima() {
        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.dialog_scale_vertical_line_out);
        rlytDialog.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }
            @Override
            public void onAnimationEnd(Animation animation) {
                OkDialog.this.dismiss();
            }
            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @OnClick({R.id.btn_delete, R.id.btn_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_delete:
                exitAnima();
                if (mListener != null) {
                    mListener.onDialogCancel(OkDialog.this);
                }
                break;
            case R.id.btn_ok:
                exitAnima();
                if (mListener != null) {
                    mListener.onDialogOK(OkDialog.this);
                }
                break;
        }
    }

    /**
     * 对话框回调接口
     */
    public interface OnDialogListener {
        //确定按钮返回值
        void onDialogOK(OkDialog okDialog);
        //取消按钮返回值
        void onDialogCancel(OkDialog okDialog);

    }
}