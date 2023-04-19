package ru.startandroid.develop.asynctaskrotate

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private var mt: MyTask? = null
    var tv: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("qwe", "create MainActivity: ${this.hashCode()}")

        tv = findViewById<View>(R.id.tv) as TextView

        mt = lastNonConfigurationInstance as MyTask?
        if (mt == null) {
            mt = MyTask()
            mt!!.execute()
        }
        mt!!.link(this)
        Log.d("qwe", "create MyTask: ${mt.hashCode()}")
    }

    override fun onRetainCustomNonConfigurationInstance(): Any {
        mt!!.unLink()
        return mt!!
    }

    internal inner class MyTask : AsyncTask<String?, Int?, Unit?>() {

        private var activity: MainActivity? = null

        fun link(act: MainActivity?) {
            activity = act
        }

        fun unLink() {
            activity = null
        }

        override fun doInBackground(vararg params: String?): Unit? {
            try {
                for (i in 1..10) {
                    Thread.sleep(1000)
                    publishProgress(i)
                    Log.d("qwe", "i = $i," +
                            "MyTask: ${this.hashCode()} " +
                            "MainActivity: ${activity.hashCode()}")
                }
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            return null
        }

        override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)
            activity!!.tv!!.text = "i = ${values[0]}"
        }
    }
}