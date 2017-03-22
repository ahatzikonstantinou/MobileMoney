package ahat.mobilemoney;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by antonis on 21/3/2017.
 */

public class AddBankActivityListAdapter extends BaseAdapter
{
    private final List<BanksActivityListItem> banksActivityListItems;
    private final     Context                     context;

    public AddBankActivityListAdapter( Context context, List<BanksActivityListItem> banksActivityListItems ) {
        this.banksActivityListItems = banksActivityListItems;
        this.context = context;
    }

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount()
    {
        return banksActivityListItems.size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public Object getItem( int position )
    {
        return banksActivityListItems.get( position );
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId( int position )
    {
        return banksActivityListItems.indexOf( getItem( position ) );
    }

    private class  ViewHolder
    {
        ImageView bankLogo;
        TextView  bankName;
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView( int position, View convertView, ViewGroup parent )
    {
        AddBankActivityListAdapter.ViewHolder viewHolder = null;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Activity.LAYOUT_INFLATER_SERVICE );
        if( convertView == null )
        {
            convertView = inflater.inflate( R.layout.add_bank_list_item, null );
            viewHolder = new AddBankActivityListAdapter.ViewHolder();
            viewHolder.bankLogo = (ImageView) convertView.findViewById( R.id.activity_add_bank_list_bank_logo );
            viewHolder.bankName = (TextView) convertView.findViewById( R.id.activity_add_bank_list_bank_name );

            BanksActivityListItem item = banksActivityListItems.get( position );
            viewHolder.bankName.setText( item.getBankName() );
            viewHolder.bankLogo.setImageResource( item.getBankLogo() );
        }
        return convertView;
    }
}
