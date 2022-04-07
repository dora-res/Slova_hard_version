package space.frogurtik.slova_hard_version;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class GameActivity extends AppCompatActivity {

    TextView TV_count_of_player, TV_names_of_players, TV_Player_smb_you_hod, TV_spisok_slov,
            TV_slovo_na_bukvu, TV_timer, TV_text_for_proigravshi;
    Button BTN_exit, BTN_hod_ready;
    EditText ET_new_word_fron_player;
    String[] spisok_of_gamers;
    String all_words_for_TV = "";
    int count_of_gamers;
    int count_all_words = 0;
    String na_kakuyi_bukvu_slovo = "А";
    ArrayList<String> list_of_all_words = new ArrayList<>();
    Dialog dialog_for_proigravshi;
    CountDownTimer countDownTimer = new CountDownTimer(1000*60*2, 1000){

        @Override
        public void onTick(long l) {
            long seconds = (1000*60*2-l)/1000;
            TV_timer.setText("Время: " + seconds/60 + " мин. " + seconds%60 + " сек. ");
            if (seconds == 60) Toast.makeText(getApplicationContext(), "Половина времени!", Toast.LENGTH_LONG).show();
            if (seconds == 90) Toast.makeText(getApplicationContext(), "Осталось полминуты! " +
                    "Поторопись!", Toast.LENGTH_LONG).show();
            if (seconds == 110) Toast.makeText(getApplicationContext(), "10 СЕКУНД!", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onFinish() {
            TV_timer.setText("Время закончилось!");
            if (ET_new_word_fron_player.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(),
                        "Игрок " + spisok_of_gamers[count_all_words%count_of_gamers] + " проиграл!",
                        Toast.LENGTH_LONG).show();
                startActivity(new Intent(GameActivity.this, MainActivity.class));

            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        TV_count_of_player = findViewById(R.id.TW_count_people);
        TV_names_of_players = findViewById(R.id.TW_spisok_people);
        TV_Player_smb_you_hod = findViewById(R.id.Player_smb_you_hod);
        TV_spisok_slov = findViewById(R.id.spisok_slov);
        BTN_exit = findViewById(R.id.exit);
        BTN_hod_ready = findViewById(R.id.hod_ready);
        TV_slovo_na_bukvu = findViewById(R.id.slovo_na_bukvu);
        ET_new_word_fron_player = findViewById(R.id.new_word_from_player);
        TV_timer = findViewById(R.id.timer);

        preparing_to_game();

        BTN_hod_ready.setOnClickListener(view -> game_start());

        BTN_exit.setOnClickListener(view -> exit_ot_game());

    }

    void preparing_to_game(){
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            spisok_of_gamers = extras.getStringArray("spisok_names");
            count_of_gamers = Integer.parseInt((String) extras.get("count_people"));
        }
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < spisok_of_gamers.length-1; i++) {
            s.append(spisok_of_gamers[i]).append(", ");
        }
        s.append(spisok_of_gamers[spisok_of_gamers.length - 1]);
        TV_names_of_players.setText("Имена игроков: " + s);
        TV_count_of_player.setText("Кол-во игроков: \n" + count_of_gamers);

        TV_Player_smb_you_hod.setText("Игрок " + spisok_of_gamers[0] + ", ваш ход");

        list_of_all_words.add(" . ");
    }

    void game_start(){

        String new_word = ET_new_word_fron_player.getText().toString();
        boolean bad_chars_in_word = new_word.contains(",") || new_word.contains("`") || new_word.contains("~")
                || new_word.contains("/") || new_word.contains("!") || new_word.contains("@") || new_word.contains("#")
                || new_word.contains("№") || new_word.contains("$") || new_word.contains("%") || new_word.contains("^")
                || new_word.contains("&") || new_word.contains("*") || new_word.contains("(") || new_word.contains(")")
                || new_word.contains("\\") || new_word.contains("|") || new_word.contains(" ") || new_word.contains("1")
                || new_word.contains("2") || new_word.contains("3") || new_word.contains("4") || new_word.contains("5")
                || new_word.contains("6") || new_word.contains("7") || new_word.contains("8") || new_word.contains("9")
                || new_word.contains("0");
        if (new_word.equals(" ")){
            Toast.makeText(getApplicationContext(), "Вы не ввели слово", Toast.LENGTH_SHORT).show();
        }else if (bad_chars_in_word){
            Toast.makeText(this, "В слове есть запрещенные символы. Нельзя использовать следующие символы: " +
                    ", ~ ` @ ! # № $ % ^ & * ; : ( ) \\ / | пробел и цифры", Toast.LENGTH_LONG).show();
        }
        else if (list_of_all_words.contains(new_word.toUpperCase())) {
            Toast.makeText(this, "Это слово уже было!", Toast.LENGTH_SHORT).show();
        }else{
            countDownTimer.cancel();
            String[] toChar_new_word = new_word.split("");
            if (toChar_new_word[toChar_new_word.length-1].toUpperCase().equals(na_kakuyi_bukvu_slovo)){
                count_all_words++;
                all_words_for_TV += new_word.toUpperCase() + " - ";
                TV_spisok_slov.setText(all_words_for_TV);
                TV_Player_smb_you_hod.setText("Игрок " + spisok_of_gamers[count_all_words%count_of_gamers] + ", ваш ход");
                na_kakuyi_bukvu_slovo = toChar_new_word[0].toUpperCase();
                TV_slovo_na_bukvu.setText("Введите слово с буквой " + na_kakuyi_bukvu_slovo + " на конце");
                ET_new_word_fron_player.setText("");
                list_of_all_words.add(new_word.toUpperCase());
                countDownTimer.start();
            }else{
                Toast.makeText(getApplicationContext(), "Слово не на ту букву", Toast.LENGTH_SHORT).show();
            }
        }
    }

    void exit_ot_game(){
        startActivity(new Intent(GameActivity.this, MainActivity.class));
    }


}