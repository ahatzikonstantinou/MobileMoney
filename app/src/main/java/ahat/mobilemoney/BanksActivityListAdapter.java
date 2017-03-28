package ahat.mobilemoney;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by antonis on 14/3/2017.
 */

public class BanksActivityListAdapter extends BaseAdapter
{
    private List<BanksActivityListItem> banksActivityListItems;
    private final Context context;

    public BanksActivityListAdapter( Context context, List<BanksActivityListItem> banksActivityListItems )
    {
        this.context = context;
        this.banksActivityListItems = banksActivityListItems;
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
                    Utils.AskDeleteBank( context, item.getBankDTO(), BanksActivity.class );
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
