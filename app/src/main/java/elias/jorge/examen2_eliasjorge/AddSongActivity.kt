package elias.jorge.examen2_eliasjorge

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AddSongActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_song)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val etTitle = findViewById<EditText>(R.id.etTitle)
        val etArtist = findViewById<EditText>(R.id.etArtist)
        val etAlbum = findViewById<EditText>(R.id.etAlbum)
        val etLength = findViewById<EditText>(R.id.etLength)
        val btnAdd = findViewById<Button>(R.id.btnAdd)

        btnAdd.setOnClickListener {
            val title = etTitle.text.toString()
            val artist = etArtist.text.toString()
            val album = etAlbum.text.toString()
            val length = etLength.text.toString().toIntOrNull() ?: 0

            if (title.isNotEmpty() && artist.isNotEmpty()) {
                val intent = Intent().apply {
                    putExtra("title", title)
                    putExtra("artist", artist)
                    putExtra("album", album)
                    putExtra("length", length)
                }
                setResult(RESULT_OK, intent)
                finish()
            }
        }

    }
}