package ahat.mobilemoney;

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
}
