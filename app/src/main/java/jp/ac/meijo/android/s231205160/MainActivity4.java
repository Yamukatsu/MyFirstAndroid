package jp.ac.meijo.android.s231205160;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultCaller;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Optional;

import jp.ac.meijo.android.s231205160.databinding.ActivityMain4Binding;
import jp.ac.meijo.android.s231205160.databinding.ActivityMainBinding;

public class MainActivity4 extends AppCompatActivity {

    private ActivityMain4Binding binding;
    private final ActivityResultLauncher<Intent> getActivityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                switch (result.getResultCode()) {
                    case RESULT_OK -> {
                        Optional.ofNullable(result.getData())
                                .map(data -> data.getStringExtra("ret"))
                                .map(ret -> "Result: " + ret)
                                .ifPresent(text ->binding.text.setText(text));
                    }
                    case RESULT_CANCELED -> {
                        binding.text.setText("Result: Canceled");
                    }
                    default -> {
                        binding.text.setText("Result: Unknown(" + result.getResultCode() + ")");
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityMain4Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.button1.setOnClickListener(view -> {
            var intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        binding.button2.setOnClickListener(view -> {
            var intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://www.yahoo.co.jp"));
            startActivity(intent);
        });

        //send
        binding.button3.setOnClickListener(view -> {
            var intent = new Intent(this, MainActivity.class);
            intent.putExtra("text", binding.editText.getText().toString());
            startActivity(intent);
        });

        //run
        binding.button4.setOnClickListener(view -> {
            var intent = new Intent(this, MainActivity.class);
            intent.putExtra("text", binding.editText.getText().toString());
            getActivityResult.launch(intent);
        });
    }

    protected void onStart() {
        super.onStart();
        binding.editText.setText("");
    }
}