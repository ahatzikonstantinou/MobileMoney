package ahat.mobilemoney;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import ahat.mobilemoney.Banking.Step;
import ahat.mobilemoney.Banking.Task;

/**
 * Created by antonis on 23/3/2017.
 */

public class BankTaskAsync extends AsyncTask<Void, Integer, Boolean>
{
    private TaskExecuteDialogListAdapter listAdapter;
    private ListView listView;
    private View dialogView;
    private Activity parentActivity;
    private Task     task;
    private String   title;
    private AlertDialog dialog;

    public BankTaskAsync( Activity activity, Task task, String title )
    {
        this.parentActivity = activity;
        this.task = task;
        this.title = title;
        this.listAdapter = new TaskExecuteDialogListAdapter( parentActivity, task, -1 );
        dialog = BuildTaskDialog();
    }

    private void setupListView( View dialogView, TaskExecuteDialogListAdapter listAdapter )
    {
        listView = (ListView) dialogView.findViewById( R.id.task_execution_listview);
        listView.setAdapter( listAdapter );
    }

    public AlertDialog BuildTaskDialog()
    {
        LayoutInflater inflater = parentActivity.getLayoutInflater();
        dialogView = inflater.inflate( R.layout.task_execution_list, null);
        setupListView( dialogView, listAdapter );

        AlertDialog d = new AlertDialog.Builder( parentActivity ).
             setTitle( title ).
             setView( dialogView ).
             setNegativeButton( android.R.string.cancel, new DialogInterface.OnClickListener()
             {
                 @Override
                 public void onClick( DialogInterface dialog, int which )
                 {
                     cancel( true );
                 }
             } ).
            setPositiveButton( android.R.string.ok, new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick( DialogInterface dialog, int which )
                {
                    dialog.dismiss();
                }
            }).
                create();


        return d;
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
        dialog.show();
        dialog.getButton( AlertDialog.BUTTON_POSITIVE ).setVisibility( View.GONE );
    }

    @Override
    protected void onPostExecute( Boolean result )
    {
        if( dialog.isShowing() )
        {
            dialog.getButton( AlertDialog.BUTTON_NEGATIVE ).setVisibility( View.GONE );
            dialog.getButton( AlertDialog.BUTTON_POSITIVE ).setVisibility( View.VISIBLE );
        }
        Toast.makeText( parentActivity, new String("Task " + title + " finished."), Toast.LENGTH_LONG ).show();
        super.onPostExecute(result);
    }

    @Override
    protected void onProgressUpdate( Integer...progress)
    {
        super.onProgressUpdate(progress);
        Toast.makeText( parentActivity, new String("Progress: " + progress[0]), Toast.LENGTH_LONG ).show();
        markRunningStep( progress[0] );
    }

    private void markRunningStep( int progress )
    {
        listAdapter.setRunningStep( progress );
        listAdapter.setTask( task );
        setupListView( dialogView, listAdapter );
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
    protected Boolean doInBackground( Void... params )
    {
        for( int i = 0 ; i < task.getSteps().size() ; i++ )
        {
            publishProgress( i );

            //TODO: replaace SystemClock.sleep( 5000 ); with actual work
            SystemClock.sleep( 5000 );
        }
        publishProgress( task.getSteps().size() );
//        SystemClock.sleep( 3000 );
        return true;
    }
}
