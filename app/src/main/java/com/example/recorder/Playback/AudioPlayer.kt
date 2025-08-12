package com.example.recorder.Playback

import java.io.File

interface AudioPlayer {
    fun playFile(file: File)

    fun stop()

}