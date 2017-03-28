package ahat.mobilemoney;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import ahat.mobilemoney.Banking.BankDTO;
import ahat.mobilemoney.Banking.BankService;

public class AddBankActivity extends AppCompatActivity implements AdapterView.OnItemClickListener
{

    ListView listView;
    List<BanksActivityListItem> banksActivityListItems;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_add_bank );
        setListview();
    }

    private void setListview()
    {
        banksActivityListItems = Utils.GetAddBanksActivityListItems( this );
        listView = (ListView) findViewById( R.id.activity_add_bank_list);
        listView.setAdapter( new AddBankActivityListAdapter( this, banksActivityListItems ) );
        listView.setOnItemClickListener( this );
    }

    /**
     * Callback method to be invoked when an item in this AdapterView has
     * been clicked.
     * <p>
     * Implementers can call getItemAtPosition(position) if they need
     * to access the data associated with the selected item.
     *
     * @param parent   The AdapterView where the click happened.
     * @param view     The view within the AdapterView that was clicked (this
     *                 will be a view provided by the adapter)
     * @param position The position of the view in the adapter.
     * @param id       The row id of the item that was clicked.
     */
    @Override
    public void onItemClick( AdapterView<?> parent, View view, int position, long id )
    {
        BankDTO bankDTO = banksActivityListItems.get( position ).getBankDTO();
        BankService.UserAddBank( this, bankDTO );
        Intent intent = new Intent( this, EditBankActivity.class );
        intent.putExtra( "bankDTO", bankDTO );
        startActivity( intent );
        String msg = getResources().getString( R.string.bankAdded );
        msg = msg.replace( "{}", bankDTO.getName() );

        Toast.makeText( this, msg, Toast.LENGTH_LONG ).show();
    }

    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        //Refresh your stuff here
        setListview();
    }
}
