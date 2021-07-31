package ru.samsung.itschool.mdev.threads;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView text;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = findViewById(R.id.text1);
        // Handler позволяет отправлять сообщения в другие потоки
        // Looper - запускает цикл обработки сообщений
        // getMainLooper() - цикл в главном потоке (UI)
        handler = new Handler(Looper.getMainLooper())  { // Создание хэндлера
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                text.setText(text.getText().toString() + msg.what);
                text.invalidate();
            }
        };
        AnotherThread t = new AnotherThread();// Создание потока
        t.start();// Запуск потока
    }

    class AnotherThread extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 20; i++) {
                try {
                    Thread.sleep(1000); // Приостанавливает поток на 1 секунду
                } catch (InterruptedException e) {
                }
                handler.sendEmptyMessage(1);  // Отправка сообщения хендлеру
            }
        }
    }
}