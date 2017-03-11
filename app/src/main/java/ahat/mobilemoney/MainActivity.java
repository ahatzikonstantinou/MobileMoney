package ahat.mobilemoney;

import android.app.Activity;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.GridView;

//public class MainActivity extends AppCompatActivity
public class MainActivity extends Activity
{
    private StorageDBHelper       storageDBHelper;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

//        Toolbar myToolbar = (Toolbar) findViewById( R.id.my_toolbar);
//        setSupportActionBar(myToolbar);

//        Intent intent = new Intent( MainActivity.this, AddBankActivity.class );
//        startActivity( intent );
    }
}
