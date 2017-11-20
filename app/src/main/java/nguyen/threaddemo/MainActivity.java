package nguyen.threaddemo;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //reasonsible for updating the UI
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //update the UI
            TextView mWelcomeTV = (TextView) findViewById(R.id.welcomeTV);
            mWelcomeTV.setText("Downloaded");
        }
    };

    public void clickMyButton(View view) {

        //second thread that runs in the background
        Runnable r = new Runnable() {
            @Override
            public void run() {
                long futureTime = System.currentTimeMillis() + 10000;

                while(System.currentTimeMillis() < futureTime) {
                    synchronized (this) {
                        try {
                            wait(futureTime - System.currentTimeMillis());
                        } catch(Exception e) {

                        }
                    }
                } //end while

                //call the handler
                mHandler.sendEmptyMessage(0);
            } //end run
        }; //end runnable

        //put all your code in the thread and start it
        Thread mThread = new Thread(r);
        mThread.start();
    }
}
