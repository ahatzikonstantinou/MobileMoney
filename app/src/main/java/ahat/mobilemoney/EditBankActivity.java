package ahat.mobilemoney;

import android.Manifest;
import android.app.KeyguardManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.samsung.android.sdk.SsdkUnsupportedException;
import com.samsung.android.sdk.pass.Spass;
import com.samsung.android.sdk.pass.SpassFingerprint;

import ahat.mobilemoney.Banking.Bank;
import ahat.mobilemoney.Banking.BankDTO;
import ahat.mobilemoney.Banking.BankService;
import ahat.mobilemoney.Banking.Task;

public class EditBankActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_USE_FINGERPRINT = 1024;
    private BankDTO bankDTO;
    private Bank bank;
    EditText passwordTV;
    EditText usernameTV;
    Button storeCredentialsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_bank);

        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        bankDTO = (BankDTO) getIntent().getSerializableExtra("bankDTO");

        ImageView bankLogo = (ImageView) findViewById(R.id.activity_edit_bank_bank_logo);
        bankLogo.setImageResource(BankService.GetBankLogo(this, bankDTO));

        storeCredentialsButton = (Button) findViewById(R.id.edit_bank_credentials_store_button);

        TextView bankName = (TextView) findViewById(R.id.activity_edit_bank_bank_name);
        bankName.setText(bankDTO.getName());

        try
        {
            bank = BankService.GetBank(this, bankDTO);
        }
        catch( Exception e )
        {
            Toast.makeText( this, "Could not get bank " + bankDTO.getName(), Toast.LENGTH_SHORT ).show();
            EnableStoreCredentialsButton( false );
            return ;
        }
        passwordTV = (EditText) findViewById(R.id.editTextPassword);
        passwordTV.setText(bank.getPassword());
        passwordTV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkDisableStoreCredentialsButton();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        CheckBox showPasswordCheckBox = (CheckBox) findViewById(R.id.showPasswordCheckBox);
        showPasswordCheckBox.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        passwordTV.setTransformationMethod((isChecked ? null : new PasswordTransformationMethod()));
                    }
                }

        );

        usernameTV = (EditText) findViewById(R.id.editTextUsername);
        usernameTV.setText(bank.getUsername());
        usernameTV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkDisableStoreCredentialsButton();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        checkDisableStoreCredentialsButton();

//        checkFingerprintAvailability();

    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults )
//    {
//        switch( requestCode )
//        {
//            case MY_PERMISSIONS_REQUEST_USE_FINGERPRINT:
//                // If request is cancelled, the result arrays are empty.
//                enableFingerprint( grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED );
//        }
//    }

//    private void enableFingerprint(boolean enable)
//    {
//        ImageView imageViewFingeprint = (ImageView) findViewById( R.id.imageViewFingeprint);
//        CheckBox checkBoxUseFingerprint = (CheckBox) findViewById( R.id.checkBoxUseFingerprint );
//        imageViewFingeprint.setAlpha( enable ? 255: 38 );
//        checkBoxUseFingerprint.setEnabled( enable );
//    }

//    private void checkFingerprintAvailability()
//    {
//        KeyguardManager keyguardManager = (KeyguardManager ) getSystemService(Context.KEYGUARD_SERVICE);
//        if( !keyguardManager.isKeyguardSecure() )
//        {
//            // Show a message that the user hasn't set up a fingerprint or lock screen.
//            enableFingerprint( false );
//            new AlertDialog.Builder( this ).
//                    setMessage( R.string.setupLockScreen ).
//                    setTitle( R.string.info ).
//                    setIcon( android.R.drawable.ic_dialog_info ).
//                    setPositiveButton( android.R.string.ok, null ).
//                    create().
//                    show();
//            return;
//        }
//
//        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
//        {
//            FingerprintManager fingerprintManager = (FingerprintManager) getSystemService( Context.FINGERPRINT_SERVICE );
//
//            if( ActivityCompat.checkSelfPermission( this, Manifest.permission.USE_FINGERPRINT ) != PackageManager.PERMISSION_GRANTED )
//            {
//                enableFingerprint( false );
//
//                //from https://developer.android.com/training/permissions/requesting.html
//                if( ActivityCompat.shouldShowRequestPermissionRationale( this, Manifest.permission.USE_FINGERPRINT ) )
//                {
//                    // show an explanation
//                    Toast.makeText( this, R.string.needFingerprintPermission, Toast.LENGTH_LONG ).show();
//                }
//                else
//                {
//                    ActivityCompat.requestPermissions( this, new String[]{ Manifest.permission.USE_FINGERPRINT }, MY_PERMISSIONS_REQUEST_USE_FINGERPRINT );
//                }
//                return;
//            }
//            if( !fingerprintManager.isHardwareDetected() )
//            {
//                // Device doesn't support fingerprint authentication
//                enableFingerprint( false );
//            }
//            else if( !fingerprintManager.hasEnrolledFingerprints() )
//            {
//                // User hasn't enrolled any fingerprints to authenticate with
//                enableFingerprint( false );
//                new AlertDialog.Builder( this ).
//                                                       setMessage( R.string.enrollFingeprints ).
//                                                       setTitle( R.string.info ).
//                                                       setIcon( android.R.drawable.ic_dialog_info ).
//                                                       setPositiveButton( android.R.string.ok, null ).
//                                                       create().
//                                                       show();
//            }
//            else
//            {
//                // Everything is ready for fingerprint authentication
//                enableFingerprint( true );
//            }
//        }
//        else if( Build.BRAND.toLowerCase().equals( "samsung" ) )
//        {
//            Spass mSpass = new Spass();
//            try
//            {
//                mSpass.initialize(this);
//            }
//            catch (SsdkUnsupportedException e)
//            {
//                Log.e( "Fingerprint", e.getLocalizedMessage() );
//            }
//            catch (UnsupportedOperationException e)
//            {
//                Log.e( "Fingerprint", e.getLocalizedMessage() );
//            }
//            boolean isFeatureEnabled = mSpass.isFeatureEnabled(Spass.DEVICE_FINGERPRINT);
//            if(isFeatureEnabled)
//            {
//                SpassFingerprint mSpassFingerprint = new SpassFingerprint( this);
//                boolean mHasRegisteredFinger = mSpassFingerprint.hasRegisteredFinger();
//                Log.d( "Fingerprint", "mHasRegisteredFinger: " + mHasRegisteredFinger );
//            }
//            else
//            {
//                Log.d( "Fingerprint", "Fingerprint Service is not supported in the device.");
//            }
//        }
//    }

    private void checkDisableStoreCredentialsButton()
    {
        boolean enable = 0 < usernameTV.getText().toString().trim().length() && 0 < passwordTV.getText().toString().trim().length();
        EnableStoreCredentialsButton( enable );
    }

    private void EnableStoreCredentialsButton( boolean enable )
    {
        storeCredentialsButton.setEnabled( enable );
        storeCredentialsButton.setClickable( enable );
        Drawable icon = storeCredentialsButton.getCompoundDrawables()[1];
        icon.setAlpha( enable ? 255 : 38 ); //255: fully opaque, 0: fully transparent
    }

    public void testBankCredentials( View view )
    {

        TextView usernameTV = (TextView) findViewById( R.id.editTextUsername );
        final String username = usernameTV.getText().toString();
        TextView passwordTV = (TextView) findViewById( R.id.editTextPassword );
        final String password = passwordTV.getText().toString();

        Toast.makeText( this, username + " : " + password, Toast.LENGTH_LONG ).show();

        AlertDialog.Builder builder = new AlertDialog.Builder( this );
        builder.setMessage( R.string.test_bank_dialog_msg )
               .setTitle( R.string.test_bank_dialog_title )
               .setPositiveButton( android.R.string.ok, new DialogInterface.OnClickListener()
                                   {
                                       @Override
                                       public void onClick( DialogInterface dialog, int which )
                                       {
                                           // TODO: do the credentials test
                                           dialog.dismiss();
                                           Toast.makeText( getApplicationContext(), "Will test", Toast.LENGTH_LONG ).show();

                                           AlertDialog.Builder alert = new AlertDialog.Builder( EditBankActivity.this );
                                           alert.setTitle( "Testing Bank" );

                                           WebView wv = new WebView( getApplicationContext() );
                                           wv.loadUrl( "https://mobile.winbank.gr/login.aspx?lg=en" );
                                           wv.getSettings().setUseWideViewPort( true );
                                           wv.getSettings().setLoadWithOverviewMode( true );
                                           wv.setInitialScale( 70 );

//        wv.setWebChromeClient(new WebChromeClient() {
//            public void onProgressChanged(WebView view, int progress) {
//                alert.setTitle("Loading... " + (progress * 100) + "%");
//                if(progress == 100)
//                    alert.setTitle("Title here");
//            }
//        });
                                           wv.getSettings().setJavaScriptEnabled( true );
                                           wv.getSettings().setDomStorageEnabled( true );
                                           //danger! if you unconditionally load a page at the onPageFinished loading will repeat forever
//                                           wv.setWebViewClient( new WebViewClient()
//                                           {
//                                               @Override
//                                               public void onPageFinished( WebView view, String url )
//                                               {
//                                                   super.onPageFinished( view, url );
//
////                                                   final String password = "423hh[23";
////                                                   final String username = "ahatz1k0nstant1n0u";
//
//                                                   final String js = "javascript:{" +
//                                                                     "document.getElementsByName('UserID')[0].value='" + username + "'; " +
//                                                                     "document.getElementsByName('Pin')[0].value='" + password + "'; " +
//                                                                     "document.getElementsByName('LoginCommand')[0].click();" +
//                                                                     "}";
//
//
//                                                   if( Build.VERSION.SDK_INT >= 19 )
//                                                   {
//                                                       view.evaluateJavascript( js, new ValueCallback<String>()
//                                                       {
//                                                           @Override
//                                                           public void onReceiveValue( String s )
//                                                           {
//
//                                                           }
//                                                       } );
//                                                   }
//                                                   else
//                                                   {
//                                                       view.loadUrl( js );
//                                                   }
//                                               }
//
//                                           } );
                                           alert.setView( wv );
                                           alert.setNegativeButton( "Close", new DialogInterface.OnClickListener()
                                           {
                                               @Override
                                               public void onClick( DialogInterface dialog, int id )
                                               {
                                                   dialog.dismiss();
                                               }
                                           } );
                                           alert.create().show();
                                       }
                                   }
               )
               .setNegativeButton( android.R.string.cancel, null )
               .setIcon( R.drawable.ic_warning_black_24dp )
               .create()
               .show();
    }

    public void clearCredentials( View view )
    {
        BankService.UserClearCredentials( this, bankDTO );
        usernameTV.setText( "" );
        passwordTV.setText( "" );
    }

    public void storeCredentials( View view )
    {
        BankService.UserStoreCredentials( this, bankDTO, usernameTV.getText().toString(), passwordTV.getText().toString() );
    }

    @Override
    public boolean onSupportNavigateUp()
    {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu )
    {
        getMenuInflater().inflate( R.menu.activity_edit_bank_menu, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( final MenuItem item )
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_delete_bank:
                askDeleteBank();
                break;
            case R.id.action_import_accounts:
                importAccounts();
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void importAccounts()
    {
        String taskName = BankService.GetTaskName( this, Task.Code.ImportAccounts );
        Task task = BankService.GetTaskOfBank( bank, Task.Code.ImportAccounts );
        if( null == task )
        {
            new AlertDialog.Builder( this ).
                setTitle( "Task " + taskName ).
                    setIcon( android.R.drawable.ic_dialog_alert ).
                    setMessage( "There is no task \"" + taskName + "\" for bank " + bankDTO.getName() ).
                    setPositiveButton( android.R.string.ok, null ).
                    create().
                    show();
            return;
        }

        Utils.RunTask( this, task, taskName );
    }

    private void askDeleteBank()
    {
        Utils.AskDeleteBank( this, bankDTO, BanksActivity.class );
    }
}
