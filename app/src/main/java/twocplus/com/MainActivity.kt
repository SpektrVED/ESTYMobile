package twocplus.com

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import java.io.IOError
import java.io.IOException
import java.io.InputStream

class MainActivity : AppCompatActivity() {
    var mydownloading : Long = 0
    var arr = arrayListOf<String>()
    var type = arrayListOf<String>()
    var contact = arrayListOf<String>()
    var contrag = arrayListOf<String>()
    var status = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val json_list = findViewById<ListView>(R.id.json_list)
        val textjson = findViewById<TextView>(R.id.textjson)
        val btnMade = findViewById<Button>(R.id.btnMade)
        val DownloadFile = findViewById<Button>(R.id.DownloadFile)

        btnMade.setOnClickListener {
            var json : String? = null

            try {
                val inputStream: InputStream = assets.open("First.json")
                json = inputStream.bufferedReader().use { it.readText() }
                textjson.text = json

                var jsonarr = JSONArray(json)
                for (i in 0..jsonarr.length()-1)
                {
                    var jsonobj = jsonarr.getJSONObject(i)
                    arr.add(jsonobj.getString("name"))
                    type.add(jsonobj.getString("type"))
                    contact.add(jsonobj.getString("contact"))
                    contrag.add(jsonobj.getString("contrag"))
                    status.add(jsonobj.getString("status"))
                }

                var adpt = ArrayAdapter(this, android.R.layout.simple_list_item_1, arr)
                json_list.adapter = adpt

                json_list.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
                    Toast.makeText(applicationContext, type[position] + "\n" + contact[position] + "\n" + contrag[position] + "\n" + status[position], Toast.LENGTH_LONG).show()
                }
            }
            catch (e : IOException) {
                textjson.text = "Что то пошло не так"
                //ReadFiliJSON()
            }
        }
        DownloadFile.setOnClickListener {
            Download()
        }
    }

    private fun ReadFiliJSON() {

    }
    private fun Download(){
        val url = "https://iasbh.tmgrup.com.tr/5af99e/0/0/0/0/0/0?u=http://i.sabah.com.tr/sb/fotohaber/ekonomi/dunyanin-en-degerli-100-markasi/6.jpg&mw=752&mh=700"
        val request = DownloadManager.Request(Uri.parse(url))
            .setTitle("title")
            .setDescription("Dis")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setAllowedOverMetered(true)
        val dm = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        dm.enqueue(request)
    }

}
