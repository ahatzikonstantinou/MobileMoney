package ahat.mobilemoney;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import ahat.mobilemoney.Banking.BankDTO;
import ahat.mobilemoney.Banking.BankService;
import ahat.mobilemoney.Banking.BankShort;
import ahat.mobilemoney.Storage.StorageProxy;

/**
 * Created by antonis on 19/3/2017.
 * This class handles communicating with the platform to detemrine if new banks should be added to the supported banks list,
 * and whether existing banks should be deleted or updated.
 */

public class UpdateBanks extends AsyncTask<Void, Void, Long >
{
    private Context context;
    private TextView msgTextView;

    public UpdateBanks( Context context, TextView msgTextView )
    {
        this.context = context;
        this.msgTextView = msgTextView;
    }

    /**
     * Override this method to perform a computation on a background thread. The
     * specified parameters are the parameters passed to {@link #execute}
     * by the caller of this task.
     * <p>
     * This method can call {@link #publishProgress} to publish updates
     * on the UI thread.
     *
     * @param params The parameters of the task.
     * @return A result, defined by the subclass of this task.
     * @see #onPreExecute()
     * @see #onPostExecute
     * @see #publishProgress
     */
    @Override
    protected Long doInBackground( Void... params )
    {
        List<BankShort> existing = getExistingBanks();
        List<BankDTO> newBanks = Platform.GetBankList( existing );
        try
        {
            Save( newBanks, existing );
        }
        catch( Exception e )
        {
            Log.e( "ERROR", e.getLocalizedMessage() );
        }
        return new Long( newBanks.size() );
    }

    private List<BankShort> getExistingBanks()
    {
        StorageProxy storageProxy = new StorageProxy( context );
        return storageProxy.GetAllBanksShort();
    }

    private void Save( List<BankDTO> newBanks, List<BankShort> existing ) throws IOException
    {
        StorageProxy storageProxy = new StorageProxy( context );
        for( final BankDTO newBank : newBanks )
        {
            // add new banks
            boolean exists = false;
            for( BankShort e : existing )
            {
                if( e.getCode().equals( newBank.getCode() ) )
                {
                    exists = true;
                    storageProxy.Update( BankService.UpdateFromPlatform( context, e, newBank ) );
                    break;
                }
            }
            if( !exists )
            {
                storageProxy.SaveNew( newBank );
            }
        }
    }

    // This is called each time you call publishProgress()
    protected void onProgressUpdate(Integer... progress) {
        msgTextView.setText(progress[0]);
    }

    // This is called when doInBackground() is finished
    protected void onPostExecute(Long result) {
        msgTextView.setText("Downloaded " + result + " banks");
    }

}
