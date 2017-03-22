package ahat.mobilemoney;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ahat.mobilemoney.Banking.BankService;
import ahat.mobilemoney.Storage.StorageProxy;

/**
 * Created by antonis on 14/3/2017.
 */

public class BanksActivityListAdapter extends BaseAdapter
{
    private List<BanksActivityListItem> banksActivityListItems;
    private ListView listView;
    private final Context context;

    public BanksActivityListAdapter( Context context, List<BanksActivityListItem> banksActivityListItems, ListView listView )
    {
        this.context = context;
        this.banksActivityListItems = banksActivityListItems;
        this.listView = listView;
    }
    @Override
    public int getCount()
    {
        return banksActivityListItems.size();
    }

    @Override
    public Object getItem( int position )
    {
        return banksActivityListItems.get( position );
    }

    @Override
    public long getItemId( int position )
    {
        return banksActivityListItems.indexOf( getItem( position ) );
    }


    private class  ViewHolder
    {
        ImageView bankLogo;
        TextView bankName;
    }

    @Override
    public View getView( final int position, View convertView, ViewGroup parent )
    {
        ViewHolder viewHolder = null;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Activity.LAYOUT_INFLATER_SERVICE );
        if( convertView == null )
        {
            convertView = inflater.inflate( R.layout.content_banks_list_item, null );
            viewHolder = new ViewHolder();
            viewHolder.bankLogo = (ImageView) convertView.findViewById( R.id.activity_banks_list_bank_logo );
//            viewHolder.bankLogo.setOnClickListener(
//                    new View.OnClickListener() {
//                        @Override
//                        public void onClick( View v )
//                        {
//                            ListView lv = (ListView) v.getParent().getParent();
//                            lv.setItemChecked(position, !lv.isItemChecked(position));
//                        }
//                    }
//            );
            viewHolder.bankName = (TextView) convertView.findViewById( R.id.activity_banks_list_bank_name );

            BanksActivityListItem item = banksActivityListItems.get( position );
            viewHolder.bankName.setText( item.getBankName() );
            viewHolder.bankLogo.setImageResource( item.getBankLogo() );

            Button deleteButton = (Button) convertView.findViewById( R.id.activity_banks_list_delete );
            deleteButton.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick( View v )
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder( context );
                    builder.setMessage(R.string.delete_bank_dialog_msg)
                           .setTitle(R.string.delete_bank_dialog_title)
                           .setPositiveButton( android.R.string.ok, new DialogInterface.OnClickListener() {
                                                   @Override
                                                   public void onClick( DialogInterface dialog, int which )
                                                   {
                                                       BankService.UserDeleteBank( context, item.getBankDTO() );
                                                       Toast.makeText( context, "Deleted bank " + item.getBankName(), Toast.LENGTH_LONG ).show();
                                                       banksActivityListItems.clear();
                                                       banksActivityListItems = Utils.GetBanksActivityListItems( context );
                                                       // the next lines do not update the listview. Must expliciltly call the setupBanksList method
//                                                       notifyDataSetChanged();
//                                                       listView.invalidateViews();
                                                       ( (BanksActivity ) context ).setupBanksList();
                                                   }
                                               }
                           )
                           .setNegativeButton( android.R.string.cancel, null )
                           .setIcon( R.drawable.ic_warning_black_24dp )
                           .create().show();
                }
            } );

            Button editButton = (Button) convertView.findViewById( R.id.activity_banks_list_edit );
            editButton.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick( View v )
                {
                    ( (BanksActivity ) context ).editBank( item.getBankDTO() );
                }
            } );
        }

        return convertView;
    }
}
