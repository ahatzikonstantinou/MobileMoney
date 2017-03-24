package ahat.mobilemoney;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import ahat.mobilemoney.Banking.Step;
import ahat.mobilemoney.Banking.Task;

/**
 * Created by antonis on 23/3/2017.
 */

public class TaskExecuteDialogListAdapter extends BaseAdapter
{
    private Context context;
    private Task task;
    private int runningStep;
    private Boolean[] results;

    public void setRunningStep( int value ) { runningStep = value; }
    public int getRunningStep(){ return runningStep; }

    public void setTask( Task task ){ this.task = task; }
    public Task getTask(){ return task; }

    public Boolean[] getResults(){ return results; }
    public void setResults( Boolean[] results ){ this.results = results; }

    public TaskExecuteDialogListAdapter( Context context, Task task, int runningStep )
    {
        this.context = context;
        this.task = task;
        this.runningStep = runningStep;
    }

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount()
    {
        return task.getSteps().size();
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
        return task.getSteps().get( position );
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
        return task.getSteps().indexOf( getItem( position ) );
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
        LayoutInflater inflater = LayoutInflater.from( context );
        if( convertView == null )
        {
            Step s = task.getSteps().get( position );
            convertView = inflater.inflate( R.layout.task_execution_step, null );
            TextView stepTV = (TextView) convertView.findViewById( R.id.textViewStep );
            stepTV.setText( s.getName() );

            ProgressBar pb = (ProgressBar) convertView.findViewById( R.id.progressBar );
            ImageView ivsf = (ImageView) convertView.findViewById( R.id.imageViewStepFail );
            ImageView ivss = (ImageView) convertView.findViewById( R.id.imageViewStepSuccess );
            if( position > runningStep )
            {
                //Step has not run yet
                stepTV.setAlpha( 0.5f );
                ivss.setVisibility( View.INVISIBLE );
                ivsf.setVisibility( View.INVISIBLE );
                pb.setVisibility( View.INVISIBLE );
            }
            else if( runningStep == position )
            {
                stepTV.setAlpha( 1f );
                ivss.setVisibility( View.INVISIBLE );
                ivsf.setVisibility( View.INVISIBLE );
                pb.setVisibility( View.VISIBLE );
            }
            else
            {
                // step has already executed
                stepTV.setAlpha( 1f );
                if( results[position] ) //success
                {
                    ivss.setVisibility( View.VISIBLE );
                    ivsf.setVisibility( View.INVISIBLE );
                }
                else
                {
                    ivss.setVisibility( View.INVISIBLE );
                    ivsf.setVisibility( View.VISIBLE );
                }
                pb.setVisibility( View.INVISIBLE );
            }
        }
        return convertView;
    }

}
