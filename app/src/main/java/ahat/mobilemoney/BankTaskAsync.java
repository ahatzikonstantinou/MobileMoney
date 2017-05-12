package ahat.mobilemoney;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.Tab;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ButtonBarLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import ahat.mobilemoney.Banking.BankTaskRunner;
import ahat.mobilemoney.Banking.StepBuilder;
import ahat.mobilemoney.Banking.Task;
import ahat.mobilemoney.Banking.TaskBuilder;
import ahat.mobilemoney.Banking.TaskDefinition;

/**
 * Created by antonis on 23/3/2017.
 */

public class BankTaskAsync extends AsyncTask<Void, StepExecutionStatus, Boolean>
{
    private View                         dialogView;
    private AppCompatActivity            parentActivity;
    private Task                         task;
    private String                       title;
    private AlertDialog                  dialog;
//    private StepsFragment     stepsFragment;
    private TaskExecuteDialogListAdapter listAdapter;
    private ListView                     listView;
    private WebView                      webView;
    private CheckBox                     askContinueCbx;
    private Boolean                      continueExecution;
    private Boolean                      askContinue;
    StepExecutionStatus[]                results;
    final Boolean[] stepExecutionFinished = { true };
    private BankTaskRunner bankTaskRunner;

    private void setupListView( View view, TaskExecuteDialogListAdapter listAdapter )
    {
        listView = (ListView) view.findViewById( R.id.task_execution_listview);
        listView.setAdapter( listAdapter );
    }

    private void setupWebView( View view )
    {
        webView = (WebView) view.findViewById( R.id.task_execution_web );
    }

    public void MarkRunningStep( StepExecutionStatus[] progress )
    {
        listAdapter.setRunningStep( listAdapter.getRunningStep() + 1 );
        listAdapter.setResults( progress );
        listAdapter.setTask( task );
        setupListView( dialogView, listAdapter );
    }

    public static class StepsFragment extends Fragment
    {
        private View                         view;
        private TaskExecuteDialogListAdapter listAdapter;
        private ListView                     listView;
        private Task                         task;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
//            this.task = (TaskDefinition) savedInstanceState.getSerializable( "task" );
            view = (View) inflater.inflate(R.layout.task_execution_list, container, false);
//            this.listAdapter = new TaskExecuteDialogListAdapter( this.getContext(), task, -1 );
//            setupListView( view, listAdapter );
            return view;
        }

        private void setupListView( View view, TaskExecuteDialogListAdapter listAdapter )
        {
            listView = (ListView) view.findViewById( R.id.task_execution_listview);
            listView.setAdapter( listAdapter );
        }

        public void MarkRunningStep( StepExecutionStatus[] progress )
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

    public BankTaskAsync( AppCompatActivity activity, Task task, String title, BankTaskRunner bankTaskRunner )
    {
        this.parentActivity = activity;
        this.task = task;
        this.title = title;
        this.bankTaskRunner = bankTaskRunner;

        this.continueExecution = true;
        this.askContinue = true;
        this.results = new StepExecutionStatus[task.getSteps().size()];
        for( int r = 0 ; r < results.length ; r++ ) { results[r] = StepExecutionStatus.NOT_EXECUTED; }
        dialog = BuildTaskDialog();
    }


    public AlertDialog BuildTaskDialog()
    {
        LayoutInflater inflater = parentActivity.getLayoutInflater();
        dialogView = inflater.inflate( R.layout.task_execution_tabs, null);

        askContinueCbx = (CheckBox) dialogView.findViewById( R.id.task_ask_continue_cbx );
        askContinueCbx.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v )
            {
                askContinue = askContinueCbx.isChecked();
                continueExecution = !askContinue;
                dialog.getButton( AlertDialog.BUTTON_NEUTRAL ).setVisibility( askContinue ? View.VISIBLE : View.GONE );
            }
        } );
        askContinue = askContinueCbx.isChecked();
        continueExecution = !askContinue;

        // tabs added according to http://www.truiton.com/2015/06/android-tabs-example-fragments-viewpager/
        TabLayout tabs = (TabLayout) dialogView.findViewById( R.id.tabs );
        tabs.addTab( tabs.newTab().setText( R.string.tab_task_steps ).setIcon( R.drawable.ic_view_list_black_24dp ) );
        tabs.addTab( tabs.newTab().setText( R.string.tab_task_webview ).setIcon( R.drawable.ic_language_black_24dp ) );

        listAdapter = new TaskExecuteDialogListAdapter( parentActivity, task, -1 );
        listAdapter.setResults( results );
        setupListView( dialogView, listAdapter );

        setupWebView( dialogView );

// ahat: the following does not seem to work. Therefore I placed a webview in the dialog and I
// manipulate it directly
//        ViewPager pager = (ViewPager) dialogView.findViewById( R.id.pager);
//        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener( tabs ));
//
//        pager.setAdapter( new FragmentPagerAdapter( parentActivity.getSupportFragmentManager() ) {
//            @Override
//            public Fragment getItem( int position )
//            {
//                // getItem is called to instantiate the fragment for the given page.
//                if( 0 == position )
//                {
//                    // get an instance of FragmentTransaction from your Activity
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable( "task", task );
//                    stepsFragment = new StepsFragment() ;
//                    stepsFragment.setArguments( bundle );
//                    return stepsFragment;
//                }
//                return new WebFragment();
//            }
//
//            @Override
//            public int getCount()
//            {
//                return 2;
//            }
//        } );
//        tabs.setupWithViewPager(pager);

        tabs.addOnTabSelectedListener( new TabLayout.OnTabSelectedListener()
        {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                ListView lv = (ListView) dialogView.findViewById( R.id.task_execution_listview );
                WebView wv = (WebView) dialogView.findViewById( R.id.task_execution_web );
                if( 0 == tab.getPosition() )
                {
                    lv.setVisibility( View.VISIBLE );
                    wv.setVisibility( View.GONE );
                }
                else
                {
                    lv.setVisibility( View.GONE );
                    wv.setVisibility( View.VISIBLE );
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab)
            {
                ListView lv = (ListView) dialogView.findViewById( R.id.task_execution_listview );
                WebView wv = (WebView) dialogView.findViewById( R.id.task_execution_web );
                if( 0 == tab.getPosition() )
                {
                    lv.setVisibility( View.GONE );
                    wv.setVisibility( View.VISIBLE );
                }
                else
                {
                    lv.setVisibility( View.VISIBLE );
                    wv.setVisibility( View.GONE );
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab)
            {
                onTabSelected( tab );
            }
        });

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
            setNeutralButton( R.string.task_continue_button_txt, null).
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
        dialog.getButton( AlertDialog.BUTTON_NEUTRAL ).setVisibility( askContinue ? View.VISIBLE : View.GONE );
        // ahat: the setOnClickListener must be done AFTER the dialog.show() in order for the button to NOT dismiss the dialog
        // see http://stackoverflow.com/questions/6142308/android-dialog-keep-dialog-open-when-button-is-pressed
        dialog.getButton( AlertDialog.BUTTON_NEUTRAL ).setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View v ) { continueExecution = true; }
        });

        bankTaskRunner.init( webView, task, new OnStepResult() {
            @Override
            public void onFail()
            {
                stepExecutionFinished[0] = true;
                results[ bankTaskRunner.getCurrentStepIndex() ] = StepExecutionStatus.FAIL;
                publishProgress( results );
            }

            @Override
            public void onSuccess()
            {
                stepExecutionFinished[0] = true;
                results[ bankTaskRunner.getCurrentStepIndex() ] = StepExecutionStatus.SUCCESS;
                publishProgress( results );
            }
        } );
    }

    @Override
    protected void onPostExecute( Boolean result )
    {
        if( dialog.isShowing() )
        {
            dialog.getButton( AlertDialog.BUTTON_NEGATIVE ).setVisibility( View. GONE );
            askContinueCbx.setEnabled( false );
            dialog.getButton( AlertDialog.BUTTON_NEUTRAL ).setVisibility( View. GONE );
            dialog.getButton( AlertDialog.BUTTON_POSITIVE ).setVisibility( View.VISIBLE );
        }
        Toast.makeText( parentActivity, new String("Task " + title + " finished."), Toast.LENGTH_LONG ).show();
        super.onPostExecute(result);
    }

    @Override
    protected void onProgressUpdate( StepExecutionStatus... progress)
    {
        super.onProgressUpdate(progress);
//        Toast.makeText( parentActivity, new String("Progress: " + progress[0]), Toast.LENGTH_LONG ).show();
        markRunningStep( progress );

        try
        {
            if( stepExecutionFinished[0] && StepExecutionStatus.EXECUTING == results[ bankTaskRunner.getCurrentStepIndex() ] )
            {
                stepExecutionFinished[0] = false;
                bankTaskRunner.run();
            }
        }
        catch( Exception e )
        {
//            e.printStackTrace();
            Toast.makeText( parentActivity, new String("Task execution failed. Details: " + e.getLocalizedMessage()), Toast.LENGTH_LONG ).show();
        }
    }

    private void markRunningStep( StepExecutionStatus[] progress )
    {
//        stepsFragment.MarkRunningStep( progress );
        MarkRunningStep( progress );
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
//        for( int i = 0 ; i < task.getSteps().size() ; i++ )
        while( !bankTaskRunner.finished() && !bankTaskRunner.isExplicitlyFinished() )
        {
            if( stepExecutionFinished[0] )
            {
//            int stepIndex = i;
                int stepIndex = bankTaskRunner.getCurrentStepIndex();
                results[stepIndex] = StepExecutionStatus.PENDING_CONFIRMATION;
                publishProgress( results );
                while( !continueExecution )
                {
                    SystemClock.sleep( 1000 );
                }
                continueExecution = !askContinue;

                results[stepIndex] = StepExecutionStatus.EXECUTING;
                publishProgress( results );



//                //TODO: replace SystemClock.sleep( 5000 ); with actual work
//                SystemClock.sleep( 5000 );
//                results[stepIndex] = i % 2 == 0 ? StepExecutionStatus.SUCCESS : StepExecutionStatus.FAIL;
//                publishProgress( results );
            }
        }
//        publishProgress( results );
//        SystemClock.sleep( 3000 );
        return true;
    }
}
