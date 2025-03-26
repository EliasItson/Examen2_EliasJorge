package elias.jorge.examen2_eliasjorge

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private val songList = mutableListOf<Song>()
    private lateinit var adapter: SongAdapter
    private val REQUEST_AGREGAR_CANCION = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewCanciones)
        val btnAgregar: Button = findViewById(R.id.btnAgregar)

        adapter = SongAdapter(songList){ song ->
            openSongDetails(song)
        }

        recyclerView.layoutManager = GridLayoutManager(this, 3)
        recyclerView.adapter = adapter



        btnAgregar.setOnClickListener {
            val intent = Intent(this, AddSongActivity::class.java)
            startActivityForResult(intent, REQUEST_AGREGAR_CANCION)
        }
    }

    fun openSongDetails(song: Song) {

        val intent = Intent(this, SongDetailsActivity::class.java).apply {
            putExtra("title", song.title)
            putExtra("artist", song.artist)
            putExtra("length", song.length)
            putExtra("album", song.album)
        }
        startActivity(intent)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_AGREGAR_CANCION && resultCode == RESULT_OK) {
            val title = data?.getStringExtra("title") ?: return
            val artist = data.getStringExtra("artist") ?: return
            val length = data.getIntExtra("length", 0)
            val album = data.getStringExtra("album") ?: return

            val newSong = Song(title, artist, length, album)
            songList.add(newSong)
            adapter.notifyDataSetChanged()
        }
    }

    fun addDefaultSongs() {

        val newSong1 = Song("Radioactive1", "Imagine Dragons", 300, "nose")
        val newSong2 = Song("Radioactive2", "Imagine Dragons", 300, "nose")
        val newSong3 = Song("Radioactive3", "Imagine Dragons", 300, "nose")
        val newSong4 = Song("Radioactive4", "Imagine Dragons", 300, "nose")
        val newSong5 = Song("Radioactive5", "Imagine Dragons", 300, "nose")
        val newSong6 = Song("Radioactive6", "Imagine Dragons", 300, "nose")

        songList.add(newSong1)
        songList.add(newSong2)
        songList.add(newSong3)
        songList.add(newSong4)
        songList.add(newSong5)
        songList.add(newSong6)


    }



    class SongAdapter(private val songs: List<Song>, private val onClick: (Song) -> Unit) :
        RecyclerView.Adapter<SongAdapter.SongViewHolder>() {

        private val colors = listOf("#FFCDD2", "#C8E6C9", "#BBDEFB", "#FFECB3", "#D1C4E9")
        private val colorsMap = mutableMapOf<String, String>()

        init {
            songs.forEach { song ->
                colorsMap[song.title] = colors.random()
            }
        }

        class SongViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val title: TextView = view.findViewById(R.id.nombreCancion)
            val artist: TextView = view.findViewById(R.id.artistaCancion)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_song, parent, false)
            return SongViewHolder(view)
        }

        override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
            val song = songs[position]
            holder.title.text = song.title
            holder.artist.text = song.artist
            holder.itemView.setBackgroundColor(Color.parseColor(colorsMap[song.title]))
            holder.itemView.setOnClickListener {
                onClick(song)
            }
        }

        override fun getItemCount() = songs.size
    }
}