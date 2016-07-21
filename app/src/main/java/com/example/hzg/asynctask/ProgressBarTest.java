package com.example.hzg.asynctask;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ProgressBar;

/**
 * Created by hzg on 2016/7/21.
 */
public class ProgressBarTest extends Activity {

    private ProgressBar pb;
    private MyAsynTask mTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progressbar);
        pb = (ProgressBar) findViewById(R.id.pg);
        mTask=new MyAsynTask();
        mTask.execute();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mTask != null && mTask.getStatus() == AsyncTask.Status.RUNNING) {
            //cancel方法只是将对应的AsyncTask标记为cancel状态，并不是真正的取消线程的执行
            mTask.cancel(true);
        }
    }

    class MyAsynTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            for (int i = 0; i < 100; i++) {
                if (isCancelled()) {
                    //AsyncTask状态为cance的，取消线程的执行
                    break;
                }
                publishProgress(i);
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            //获取进度更新值
            pb.setProgress(values[0]);
        }
    }

}
