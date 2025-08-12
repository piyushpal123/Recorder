package com.example.recorder

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import com.example.recorder.Playback.AndroidAudioPlayer
import com.example.recorder.record.AndroidAudioRecorder
import com.example.recorder.ui.theme.RecorderTheme
import java.io.File

class MainActivity : ComponentActivity() {

    private val recorder by lazy {
        AndroidAudioRecorder(applicationContext)

    }
    private val player by lazy {
        AndroidAudioPlayer(applicationContext)
    }
    private var audioFile : File? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Use Activity Result API for requesting permission
        val requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (!isGranted) {
                Toast.makeText(this, "Audio recording permission is required", Toast.LENGTH_LONG).show()
                finish()
            }
        }
        requestPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)

        setContent {
            RecorderTheme {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Button(onClick = {
                        File(cacheDir,"audio.mp3").also{
                            recorder.start(it)
                            audioFile = it
                        }
                    }){
                        Text(text = "Start Recording")
                    }
                    Button(onClick = {
                       recorder.stop()

                    }){
                        Text(text = "Stop Recording")
                    }

                    Button(onClick = {
                       player.playFile(audioFile?:return@Button)

                    }){
                        Text(text = "Play")
                    }
                    Button(onClick = {
                       player.stop()

                    }){
                        Text(text = "Stop Playing")
                    }
                }

            }
        }
    }
}
