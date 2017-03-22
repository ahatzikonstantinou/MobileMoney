package ahat.mobilemoney;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import ahat.mobilemoney.Banking.Bank;
import ahat.mobilemoney.Banking.BankDTO;
import ahat.mobilemoney.Banking.BankService;

public class EditBankActivity extends AppCompatActivity
{
    private BankDTO bankDTO;
    EditText passwordTV;
    EditText usernameTV;
    Button storeCredentialsButton;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_edit_bank );

        bankDTO = ( BankDTO ) getIntent().getSerializableExtra( "bankDTO" );

        ImageView bankLogo = (ImageView) findViewById( R.id.activity_edit_bank_bank_logo );
        bankLogo.setImageResource( BankService.GetBankLogo( this, bankDTO ) );

        storeCredentialsButton = (Button) findViewById( R.id.edit_bank_credentials_store_button );

        TextView bankName = (TextView) findViewById( R.id.activity_edit_bank_bank_name );
        bankName.setText( bankDTO.getName() );

        Bank bank = BankService.GetBank( this, bankDTO );
        passwordTV = (EditText) findViewById( R.id.editTextPassword );
        passwordTV.setText( bank.getPassword() );
        passwordTV.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged( CharSequence s, int start, int count, int after )
            {

            }

            @Override
            public void onTextChanged( CharSequence s, int start, int before, int count )
            {
                storeCredentialsButton.setEnabled( 0 < s.toString().trim().length() );
            }

            @Override
            public void afterTextChanged( Editable s )
            {

            }
        } );
        CheckBox showPasswordCheckBox = (CheckBox) findViewById( R.id.showPasswordCheckBox );
        showPasswordCheckBox.setOnCheckedChangeListener(
            new CompoundButton.OnCheckedChangeListener()
            {
                 @Override
                 public void onCheckedChanged( CompoundButton buttonView, boolean isChecked )
                 {
                    passwordTV.setTransformationMethod( ( isChecked ? null : new PasswordTransformationMethod() ) );
                 }
            }

        );

        usernameTV = (EditText) findViewById( R.id.editTextUsername );
        usernameTV.setText( bank.getUsername() );
        usernameTV.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged( CharSequence s, int start, int count, int after )
            {

            }

            @Override
            public void onTextChanged( CharSequence s, int start, int before, int count )
            {
                storeCredentialsButton.setEnabled( 0 < s.toString().trim().length() );
            }

            @Override
            public void afterTextChanged( Editable s )
            {

            }
        } );

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
}
