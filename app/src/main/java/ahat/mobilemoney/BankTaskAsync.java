package ahat.mobilemoney;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.Tab;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import ahat.mobilemoney.Banking.TaskDefinition;

/**
 * Created by antonis on 23/3/2017.
 */

public class BankTaskAsync extends AsyncTask<Void, Boolean, Boolean>
{
    private View                         dialogView;
    private AppCompatActivity            parentActivity;
    private TaskDefinition               task;
    private String                       title;
    private AlertDialog                  dialog;
    private StepsFragment                stepsFragment;

    public static class StepsFragment extends Fragment
    {
        private View                         view;
        private TaskExecuteDialogListAdapter listAdapter;
        private ListView                     listView;
        private TaskDefinition               task;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            this.task = (TaskDefinition) savedInstanceState.getSerializable( "task" );
            view = (View) inflater.inflate(R.layout.task_execution_list, container, false);
            this.listAdapter = new TaskExecuteDialogListAdapter( this.getContext(), task, -1 );
            setupListView( view, listAdapter );
            return view;
        }

        private void setupListView( View view, TaskExecuteDialogListAdapter listAdapter )
        {
            listView = (ListView) view.findViewById( R.id.task_execution_listview);
            listView.setAdapter( listAdapter );
        }

        public void MarkRunningStep( Boolean[] progress )
        {
            listAdapter.setRunningStep( listAdapter.getRunningStep() + 1 );
            listAdapter.setResults( progress );
            listAdapter.setTask( task );
            setupListView( view, listAdapter );
        }
    }

    public static class WebFragment extends Fragment
    {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.task_execution_web, container, false);
        }
    }

    public BankTaskAsync( AppCompatActivity activity, TaskDefinition task, String title )
    {
        this.parentActivity = activity;
        this.task = task;
        this.title = title;
        dialog = BuildTaskDialog();
    }

    public AlertDialog BuildTaskDialog()
    {
        LayoutInflater inflater = parentActivity.getLayoutInflater();
        dialogView = inflater.inflate( R.layout.task_execution_tabs, null);

        TabLayout tabs = (TabLayout) dialogView.findViewById( R.id.tabs );
        tabs.addTab( tabs.newTab().setText( R.string.tab_task_steps ).setIcon( R.drawable.ic_view_list_black_24dp ) );
        tabs.addTab( tabs.newTab().setText( R.string.tab_task_webview ).setIcon( R.drawable.ic_language_black_24dp ) );

        ViewPager pager = (ViewPager) dialogView.findViewById( R.id.pager);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener( tabs ));

        pager.setAdapter( new FragmentPagerAdapter( parentActivity.getSupportFragmentManager() ) {
            @Override
            public Fragment getItem( int position )
            {
                // getItem is called to instantiate the fragment for the given page.
                if( 0 == position )
                {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable( "task", task );
                    stepsFragment = new StepsFragment() ;
                    stepsFragment.setArguments( bundle );
                    return stepsFragment;
                }
                return new WebFragment();
            }

            @Override
            public int getCount()
            {
                return 2;
            }
        } );
        tabs.setupWithViewPager(pager);
        /*
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
         */

        return new AlertDialog.Builder( parentActivity ).
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
//        Toast.makeText( parentActivity, new String("Task " + title + " finished."), Toast.LENGTH_LONG ).show();
        super.onPostExecute(result);
    }

    @Override
    protected void onProgressUpdate( Boolean... progress)
    {
        super.onProgressUpdate(progress);
//        Toast.makeText( parentActivity, new String("Progress: " + progress[0]), Toast.LENGTH_LONG ).show();
        markRunningStep( progress );
    }

    private void markRunningStep( Boolean[] progress )
    {
        stepsFragment.MarkRunningStep( progress );
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
        Boolean[] results = new Boolean[task.getSteps().size()];
        for( int i = 0 ; i < task.getSteps().size() ; i++ )
        {
            results[i] = i%2 == 0;
            publishProgress( results );

            //TODO: replaace SystemClock.sleep( 5000 ); with actual work
            SystemClock.sleep( 5000 );
        }
        publishProgress( results );
//        SystemClock.sleep( 3000 );
        return true;
    }
}
