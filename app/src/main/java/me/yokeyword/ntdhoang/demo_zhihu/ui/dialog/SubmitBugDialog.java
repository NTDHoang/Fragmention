package me.yokeyword.ntdhoang.demo_zhihu.ui.dialog;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import me.yokeyword.ntdhoang.R;


public class SubmitBugDialog extends DABaseDialog {

    public interface ConfirmDialogListener {
        void onSelect(int indexButton, int mode);
    }

    private int mTitleId, mMessageId, mConfirmTextId, mCancelTextId;
    private String mMessageString;
    private String mTitleString;
    private int mConfirmBtnResId;
    private long score;
    protected View mView;


    public boolean isChangeLayout() {
        return isChangeLayout;
    }

    public void setChangeLayout(boolean changeLayout) {
        isChangeLayout = changeLayout;
    }

    private boolean isChangeLayout;

    public int getTitleId() {
        return mTitleId;
    }

    public void setTitleId(int titleId) {
        this.mTitleId = titleId;
    }

    public int getMessageId() {
        return mMessageId;
    }

    public void setMessageId(int messageId) {
        this.mMessageId = messageId;
    }

    public int getConfirmTextId() {
        return mConfirmTextId;
    }

    public void setConfirmTextId(int confirmTextId) {
        this.mConfirmTextId = confirmTextId;
    }

    public int getCancelTextId() {
        return mCancelTextId;
    }

    public void setCancelTextId(int cancelTextId) {
        this.mCancelTextId = cancelTextId;
    }

    public String getMessageString() {
        return mMessageString;
    }

    public void setMessageString(String messageString) {
        this.mMessageString = messageString;
    }

    public String getTitleString() {
        return mTitleString;
    }

    public void setTitleString(String titleString) {
        this.mTitleString = titleString;
    }

    public int getConfirmBtnResId() {
        return mConfirmBtnResId;
    }

    public void setConfirmBtnResId(int confirmBtnResId) {
        this.mConfirmBtnResId = confirmBtnResId;
    }

    public SubmitBugDialog(Context context, int themeResId) {
        super(context);
    }


    public SubmitBugDialog(Context context) {
        super(context);
        this.mContext = context;
    }


    private Context mContext;

    public SubmitBugDialog(Context context, Bundle mBundle) {
        super(context);
        this.mContext = context;
        this.mBundle = mBundle;
    }

    private Bundle mBundle;
    private int mode;



    public void setupViewWithTitle(int titleId, int messageId, int confirmText, int cancelText, final ConfirmDialogListener listener) {

        this.mMessageId = messageId;
        this.mTitleId = titleId;
        this.mConfirmTextId = confirmText;
        this.mCancelTextId = cancelText;
        this.mTitleString = "";
        this.mMessageString = "";
    }

    @Override
    protected void onStart() {
        super.onStart();


      /*  mSpinner.setAdapter(new CountriesListAdapter(getContext(), getContext().getResources().getStringArray(R.array.CountryCodes)));

        mSpinner.setSelected(true);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Log.d(TAG, "Ranking type: onItemSelected() called with: " + "position = [" + position + "], id = [" + id + "]");
                mSpinner.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });*/

        btnActionCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubmitBugDialog.this.dismiss();
            }
        });


        mRightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent send = new Intent(Intent.ACTION_SENDTO);
                String uriText = "mailto:" + Uri.encode("@gmail.com") +
                        "?subject=" + Uri.encode("[ProdoStudios] S8RoundScreen report bugs") +
                        "&body=" + Uri.encode(mEditTextName.getText()+"");
                Uri uri = Uri.parse(uriText);

                send.setData(uri);
                mContext.startActivity(Intent.createChooser(send, "Send mail..."));
            }
        });

    }

    ;

    private LinearLayout layoutCountry;
    private TextView lblTextMessage;
    private Button btnActionCancel;
    private Button mRightButton;
    private ImageView mImageFlag;
    private EditText mEditTextName;
    private TextView mTitleText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mConfirmBtnResId > 0) mRightButton.setBackgroundResource(mConfirmBtnResId);

        setContentView(R.layout.dialog_status_confirm);

        lblTextMessage = (TextView) findViewById(R.id.lblMessage);
        btnActionCancel = (Button) findViewById(R.id.btnActionCancel);
        mRightButton = (Button) findViewById(R.id.btnSettle);
        mEditTextName = (EditText) findViewById(R.id.eTxtName);
        mTitleText = (TextView) findViewById(R.id.lblTitle);
        mTitleText = (TextView) findViewById(R.id.lblTitle);
        mTitleText.setText(mContext.getString(R.string.str_rating_content6));

        lblTextMessage.setText(mContext.getString(R.string.str_rating_content5));
    }



}
