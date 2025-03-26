package elias.jorge.examen2_eliasjorge

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SongDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_song_details)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val title = intent.getStringExtra("title") ?: "Desconocido"
        val artist = intent.getStringExtra("artist") ?: "Desconocido"
        val length = intent.getIntExtra("length", 0)
        val album = intent.getStringExtra("album") ?: "Desconocido"

        val minutes = length/60
        val seconds = length%60

        findViewById<TextView>(R.id.txtTitle).text = "$title"
        findViewById<TextView>(R.id.txtArtist).text = "$artist"
        findViewById<TextView>(R.id.txtMinutes).text = "$minutes"
        findViewById<TextView>(R.id.txtSeconds).text = "$seconds"
        findViewById<TextView>(R.id.txtAlbum).text = "$album"

        val btnPlay = findViewById<Button>(R.id.btnPlay)
        btnPlay.text = "Play $title"

        btnPlay.setOnClickListener {
            val intent = Intent(this, PlaySongActivity::class.java).apply {
                putExtra("title", title)
            }
            startActivity(intent)
        }
    }
}