package xiren.soft.com.mydialog.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xiren.soft.com.mydialog.R;
import xiren.soft.com.mydialog.util.InputMethodUtil;
import xiren.soft.com.mydialog.util.StringUtils;

/**
 * description:	输入对话框，内容为空的时候不能够输入
 * User: shaobing
 * Date: 2016/6/27
 * Time: 18:09
 */
public class EditDialog extends Dialog implements View.OnClickListener {


    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.et_context)
    EditText etContext;
    @Bind(R.id.cancel_button)
    TextView cancelButton;
    @Bind(R.id.confirm_button)
    TextView confirmButton;
    @Bind(R.id.container)
    LinearLayout container;

    private String titleText;
    private String confirmText;
    private String cancelText;
    private Context mContext;

    private OnDialogListener mOnDialogListener;

    public EditDialog(Context context, OnDialogListener mOnDialogListener) {
        super(context, R.style.CustomDialog);
        this.mContext = context;
        this.mOnDialogListener = mOnDialogListener;
    }

    public EditDialog(Context context, int alertType) {
        super(context, R.style.CustomDialog);
        setCancelable(true);
        setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_edit);
        ButterKnife.bind(this);
        /**************************************************************************************/
        this.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失 true消失  false不消失
        this.setCancelable(true);              //设置点击返回按钮消失   true 消失   false 不消失
        Window dialogWindow = this.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialogWindow.setAttributes(lp);
        dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.TOP);
        setOwnerActivity((Activity) mContext);
        dialogWindow.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        dialogWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        dialogWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        initView();
        initData();
        initEvent();
    }

    private void initView() {

    }

    private void initData() {
        setTitleText(titleText);
        setCancelText(cancelText);
        setConfirmText(confirmText);
        etContext.requestFocus();
        InputMethodUtil.showSoftInput(etContext);
        if(StringUtils.isEmpty(etContext.getText().toString())){
            handleView(0);
        }
        enterAnima();
    }

    private void initEvent() {
        etContext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String suggestion = etContext.getText().toString().trim();
                if (suggestion.length() > 0) {
                    handleView(1);
                } else {
                    handleView(0);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void  handleView(int state){
        switch (state){
            case 0:
                confirmButton.setClickable(false);
                confirmButton.setEnabled(false);
                confirmButton.setBackgroundResource(R.drawable.shape_dialog_delete);
                break;
            case 1:
                confirmButton.setClickable(true);
                confirmButton.setEnabled(true);
                confirmButton.setBackgroundResource(R.drawable.shape_dialog_ok);
                break;
        }
    }


    /**
     * @param confirmText 设置确定按钮内容
     */
    public EditDialog setConfirmText(String confirmText) {
        this.confirmText = confirmText;
        if (confirmButton != null && confirmText != null) {
            confirmButton.setText(confirmText);
        }
        return this;
    }

    /**
     * @param cancelText 设置取消按钮内容
     */
    public EditDialog setCancelText(String cancelText) {
        this.cancelText = cancelText;
        if (cancelButton != null && cancelText != null) {
            cancelButton.setText(cancelText);
        }
        return this;
    }

    /**
     * @param titleText 设置标题
     */
    public EditDialog setTitleText(String titleText) {
        this.titleText = titleText;
        if (tvTitle != null && titleText != null) {
            tvTitle.setText(titleText);
        }
        return this;
    }

    /**
     * 设置编辑框内容
     *
     * @param s 传入的内容
     */
    public void setEditText(String s) {
        if (StringUtils.isEmpty(s) && etContext != null) {
            etContext.setText(s);
            etContext.setSelection(s.length());
        }
    }
    /**
     * 进入动画
     */
    private void enterAnima() {
        container.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.dialog_scale_vertical_line_in));
    }
    /**
     * 退出动画
     */
    private void exitAnima() {
        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.dialog_scale_vertical_line_out);
        container.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }
            @Override
            public void onAnimationEnd(Animation animation) {
                EditDialog.this.dismiss();
            }
            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
    @OnClick({R.id.cancel_button, R.id.confirm_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel_button:
                if (mOnDialogListener != null) {
                    mOnDialogListener.onDialogCancel(etContext.getText().toString());
                }
                InputMethodUtil.hideSoftInput(etContext);
                exitAnima();
                break;
            case R.id.confirm_button:
                if (mOnDialogListener != null) {
                    mOnDialogListener.onDialogOK(etContext.getText().toString());
                }
                InputMethodUtil.hideSoftInput(etContext);
                exitAnima();
                break;
        }
    }

    public interface OnDialogListener {
        //确定按钮返回值
        void onDialogOK(String message);

        //取消按钮返回值
        void onDialogCancel(String message);
    }

}



