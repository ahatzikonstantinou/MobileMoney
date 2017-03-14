package ahat.mobilemoney;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class BanksActivity extends AppCompatActivity implements AdapterView.OnItemClickListener
{
    ListView listView;
    List<BanksActivityListItem> banksActivityListItems;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_banks );
//        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
//        setSupportActionBar( toolbar );

        FloatingActionButton fab = (FloatingActionButton) findViewById( R.id.fab );
        fab.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
//                Snackbar.make( view, "Replace with your own action", Snackbar.LENGTH_LONG )
//                        .setAction( "Action", null ).show();
                Intent intent = new Intent( view.getContext(), AddBankActivity.class );
                startActivity( intent );
            }
        } );

        String[] names = getResources().getStringArray( R.array.bank_names );
        TypedArray logos = getResources().obtainTypedArray( R.array.bank_logos );
        banksActivityListItems = new ArrayList<BanksActivityListItem>();
        for( int i = 0 ; i < names.length ; i++ )
        {
            banksActivityListItems.add( new BanksActivityListItem( names[i], logos.getResourceId(i, -1) ) );
        }
        listView = (ListView) findViewById( R.id.activity_banks_list);
        listView.setAdapter( new BanksActivityListAdapter( this, banksActivityListItems ) );
        listView.setOnItemClickListener( this );
    }

    @Override
    public void onItemClick( AdapterView<?> parent, View view, int position, long id )
    {
        String bankName = banksActivityListItems.get( position ).getBankName();
        Toast.makeText( getApplicationContext(), "" + bankName, Toast.LENGTH_LONG ).show();
    }
}
