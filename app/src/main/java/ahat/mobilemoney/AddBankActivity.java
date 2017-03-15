package ahat.mobilemoney;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
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
        AlertDialog.Builder builder = new AlertDialog.Builder( this );
        builder.setMessage(R.string.test_bank_dialog_msg)
            .setTitle(R.string.test_bank_dialog_title)
            .setPositiveButton( android.R.string.ok, new DialogInterface.OnClickListener() {
                                   @Override
                                   public void onClick( DialogInterface dialog, int which )
                                   {
                                       // TODO: do the credentials test
                                   }
                               }
            )
            .setNegativeButton( android.R.string.cancel, null )
            .setIcon( R.drawable.ic_warning_black_24dp )
            .create().show();
    }
}
