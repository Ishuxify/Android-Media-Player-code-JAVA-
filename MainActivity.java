package com.example.mediaplayerpractice;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mediaplayerpractice.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    SeekBar seekBar;
    int currentTrackIndex = 0;

    // Playlist of audio files
    List<Integer> playlist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button playButton = findViewById(R.id.playButton);
        Button pauseButton = findViewById(R.id.pauseButton);

        Button nextButton = findViewById(R.id.nextButton);
        seekBar = findViewById(R.id.seekBar);

        // Add audio files to the playlist
        playlist.add(R.raw.music);
        playlist.add(R.raw.music1);
        playlist.add(R.raw.music2);

        // Initialize MediaPlayer with the first track
        mediaPlayer = MediaPlayer.create(this, playlist.get(currentTrackIndex));

        // Play button
        playButton.setOnClickListener(v -> mediaPlayer.start());

        // Pause button
        pauseButton.setOnClickListener(v -> mediaPlayer.pause());



        // Next button
        nextButton.setOnClickListener(v -> playNextTrack());

        // Update SeekBar
        mediaPlayer.setOnPreparedListener(mp -> seekBar.setMax(mediaPlayer.getDuration()));
        mediaPlayer.setOnCompletionListener(mp -> playNextTrack());

        // SeekBar listener (optional for progress tracking)
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    private void playNextTrack() {
        // Stop current track
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        mediaPlayer.reset();

        // Move to the next track
        currentTrackIndex = (currentTrackIndex + 1) % playlist.size();

        // Load and play the next track
        mediaPlayer = MediaPlayer.create(this, playlist.get(currentTrackIndex));
        mediaPlayer.start();

        // Update SeekBar
        mediaPlayer.setOnPreparedListener(mp -> seekBar.setMax(mediaPlayer.getDuration()));
        mediaPlayer.setOnCompletionListener(mp -> playNextTrack());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }
}
