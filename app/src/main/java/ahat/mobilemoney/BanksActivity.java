package ahat.mobilemoney;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ahat.mobilemoney.Banking.BankDTO;
import ahat.mobilemoney.Banking.BankService;

public class BanksActivity extends AppCompatActivity implements AdapterView.OnItemClickListener
{
    ListView listView;
    List<BanksActivityListItem> banksActivityListItems;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_banks );
        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

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

        setupBanksList();
    }

    public void setupBanksList()
    {
        banksActivityListItems = Utils.GetBanksActivityListItems( this );
        listView = (ListView) findViewById( R.id.activity_banks_list);
        listView.setAdapter( new BanksActivityListAdapter( this, banksActivityListItems, listView ) );
        listView.setOnItemClickListener( this );
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onItemClick( AdapterView<?> parent, View view, int position, long id )
    {
        editBank( banksActivityListItems.get( position ).getBankDTO() );
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu )
    {
        getMenuInflater().inflate( R.menu.activity_banks_menu, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( final MenuItem item )
    {
        switch( item.getItemId() )
        {
            case android.R.id.home:
                finish();
                break;
//            case R.id.action_delete_bank:
//                AlertDialog.Builder builder = new AlertDialog.Builder( this );
//                builder.setMessage(R.string.delete_bank_dialog_msg)
//                       .setTitle(R.string.delete_bank_dialog_title)
//                       .setPositiveButton( android.R.string.ok, new DialogInterface.OnClickListener() {
//                                               @Override
//                                               public void onClick( DialogInterface dialog, int which )
//                                               {
//                                                   // TODO: do the bank delete
//                                                   SparseBooleanArray checked = listView.getCheckedItemPositions();
//                                                   String bankNames = "";
//                                                   for( int i = 0 ; i < listView.getAdapter().getCount() ; i++ )
//                                                   {
//                                                       if( checked.get( i ) )
//                                                       {
//                                                           bankNames = getResources().getStringArray( R.array.bank_names )[item.getOrder()] + ", ";
//                                                       }
//                                                   }
//                                                   Toast.makeText( getApplicationContext(), "Deleting banks " + bankNames.substring( 0, bankNames.length()-2 ), Toast.LENGTH_LONG ).show();
//                                               }
//                                           }
//                       )
//                       .setNegativeButton( android.R.string.cancel, null )
//                       .setIcon( R.drawable.ic_warning_black_24dp )
//                       .create().show();
//                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    public void editBank( BankDTO bankDTO )
    {
        Intent intent = new Intent( this, EditBankActivity.class );
        intent.putExtra( "bankDTO", bankDTO );
        startActivity( intent );
//        Toast.makeText( this, "Edit bank " + bankDTO.getName(), Toast.LENGTH_LONG ).show();
    }
}
