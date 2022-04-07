package space.frogurtik.slova_hard_version;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.TreeSet;

public class MainActivity extends AppCompatActivity {
    EditText ET_count_of_gamers, ET_names_of_gamers;
    Button BTN_to_start_game, BTN_to_rules;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ET_count_of_gamers = findViewById(R.id.count_of_players);
        ET_names_of_gamers = findViewById(R.id.names_of_players);
        BTN_to_start_game = findViewById(R.id.btn_to_start_game);
        BTN_to_rules = findViewById(R.id.btn_to_rules);

        BTN_to_start_game.setOnClickListener(view -> game_classic());
        BTN_to_rules.setOnClickListener(view -> to_rules());

    }

    void to_rules(){
        dialog = new Dialog(MainActivity.this);
        dialog.setTitle("Заголовок диалога");
        dialog.setContentView(R.layout.dialog_view);
        dialog.show();
    }

    void game_classic(){
        String count_of_gamers = ET_count_of_gamers.getText().toString();
        String s = ET_names_of_gamers.getText().toString();

        if (TextUtils.isEmpty(count_of_gamers) || TextUtils.isEmpty(s)){
            Toast.makeText(getApplicationContext(), "Не все необходиые данные заполнены", Toast.LENGTH_SHORT).show();
        }else{
            TreeSet<String> names_of_gamers = new TreeSet<>();
            String[] arr = s.split(" ");
            for (int i = 0; i < arr.length; i++) {
                names_of_gamers.add(arr[i]);
            }
            if (names_of_gamers.size() != Integer.parseInt(count_of_gamers)){
                Toast.makeText(getApplicationContext(), "Данные не сходятся", Toast.LENGTH_SHORT).show();
            }else{
                int i = Integer.parseInt(count_of_gamers);
                if (i == 1){
                    Toast.makeText(getApplicationContext(), "К сожалению, нельзя играть ни с кем :( ", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(MainActivity.this, GameActivity.class);
                    intent.putExtra("count_people", count_of_gamers);
                    intent.putExtra("spisok_names", arr);
                    startActivity(intent);
                }
            }
        }
    }

}