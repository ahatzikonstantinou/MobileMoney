package ahat.mobilemoney;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddBankActivity extends AppCompatActivity
{

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_add_bank );
    }

    public void addBank( View view )
    {
        Spinner bankSpinner = (Spinner) findViewById( R.id.spinner );
        String text = bankSpinner.getSelectedItem().toString();

        Toast.makeText( this.getApplication().getApplicationContext(), text, Toast.LENGTH_LONG ).show();
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

                                           AlertDialog.Builder alert = new AlertDialog.Builder( AddBankActivity.this );
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
}
