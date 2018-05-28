package bw.com.donyin;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import bw.com.donyin.view.MovieRecorderView;

public class Main3Activity extends AppCompatActivity implements View.OnClickListener {

    private MovieRecorderView moive_rv;
    private Button start_btn;
    private Button stop_btn;
    private RelativeLayout record_rl;
    private SurfaceView playView;
    private Button play_btn;
    private Button pause_btn;
    private RelativeLayout play_rl;
    int position;
    private MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        initView();
        init();
    }

    private void initView() {
        moive_rv = (MovieRecorderView) findViewById(R.id.moive_rv);
        start_btn = (Button) findViewById(R.id.start_btn);
        stop_btn = (Button) findViewById(R.id.stop_btn);
        record_rl = (RelativeLayout) findViewById(R.id.record_rl);
        playView = (SurfaceView) findViewById(R.id.play_surfaceV);
        play_btn = (Button) findViewById(R.id.play_btn);
        pause_btn = (Button) findViewById(R.id.pause_btn);
        play_rl = (RelativeLayout) findViewById(R.id.play_rl);

        start_btn.setOnClickListener(this);
        stop_btn.setOnClickListener(this);
        play_btn.setOnClickListener(this);
        pause_btn.setOnClickListener(this);
    }
    private void init()
    {
        player = new MediaPlayer();

        //设置SurfaceView自己不管理的缓冲区
        playView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        playView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (position>0) {
                    try {
                        //开始播放
                        play();
                        //并直接从指定位置开始播放
                        player.seekTo(position);
                        position=0;
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                }
            }
            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                       int height) {

            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_btn:
                moive_rv.record(new MovieRecorderView.OnRecordFinishListener() {
                    @Override
                    public void onRecordFinish() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Main3Activity.this,"录制成功！",Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });
                break;
            case R.id.stop_btn:
                moive_rv.stop();
                break;
            case R.id.play_btn:
                play();
                break;
            case R.id.pause_btn:
                if(player.isPlaying())
                {
                    player.pause();
                }
                else
                {
                    player.start();
                }
                break;
        }
    }

    @Override
    protected void onPause() {
        //先判断是否正在播放

        if (player.isPlaying()&&player!=null) {
            //如果正在播放我们就先保存这个播放位置
            position=player.getCurrentPosition()
            ;
            player.stop();
        }
        super.onPause();
    }
    private void play()
    {
        try {
            Log.d("play:","");
            player.reset();
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            //设置需要播放的视频
            String path=moive_rv.getmVecordFile().getAbsolutePath();
            player.setDataSource(path);
            Log.d("play:",path);
            //把视频画面输出到SurfaceView
            player.setDisplay(playView.getHolder());
            player.prepare();
            //播放
            player.start();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
