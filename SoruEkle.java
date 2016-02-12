import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by umut on 11.02.2016.
 */
public class SoruEkle extends AppCompatActivity {
    Button kaydet, vazgec;
    EditText soru, secenekA, secenekB, secenekC, secenekD;
    RadioButton a, b, c, d;
    RadioGroup cevaplar;
    JSONArray veritabani;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.soru_ekle);
        kaydet = (Button) findViewById(R.id.button8);
        vazgec = (Button) findViewById(R.id.button7);
        soru = (EditText) findViewById(R.id.editText);
        secenekA = (EditText) findViewById(R.id.editText2);
        secenekB = (EditText) findViewById(R.id.editText3);
        secenekC = (EditText) findViewById(R.id.editText4);
        secenekD = (EditText) findViewById(R.id.editText5);
        a = (RadioButton) findViewById(R.id.radioButton);
        b = (RadioButton) findViewById(R.id.radioButton2);
        c = (RadioButton) findViewById(R.id.radioButton3);
        d = (RadioButton) findViewById(R.id.radioButton4);
        cevaplar = (RadioGroup) findViewById(R.id.radioGroup);
        try {
            veritabani = new JSONArray(getIntent().getSerializableExtra("veritabani").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        vazgec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SoruEkle.this, MainActivity.class));
                finish();
            }
        });

        kaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!soru.getText().toString().equals("") && !secenekA.getText().toString().equals("")
                        && !secenekB.getText().toString().equals("") && !secenekD.getText().toString().equals("")) {
                    JSONObject yeniSoru = new JSONObject();
                    try {
                        if (!cevapKontrolu().equals("")) {

                            //-- Yeni bir JSON Nesnesi oluşturuyoruz
                            yeniSoru.put("Soru", soru.getText().toString());
                            yeniSoru.put("A", secenekA.getText().toString());
                            yeniSoru.put("B", secenekB.getText().toString());
                            yeniSoru.put("C", secenekC.getText().toString());
                            yeniSoru.put("D", secenekD.getText().toString());
                            yeniSoru.put("Cevap", cevapKontrolu());

                            //-- oluşturduğumuz JSON Nesnesini veritabani ismindeki JSON Dizisine ekliyoruz.
                            veritabani.put(yeniSoru);

                            //-- veritabani'nin son halini JSON dosyasına kaydediyoruz.
                            File dosya = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/sorular.json");
                            FileOutputStream fileOutputStream = new FileOutputStream(dosya);
                            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
                            outputStreamWriter.write(veritabani.toString());
                            outputStreamWriter.close();
                            fileOutputStream.close();
                            Toast.makeText(SoruEkle.this,"Sorunu Eklenmiştir.",Toast.LENGTH_SHORT).show();
                        } else {
                            new AlertDialog.Builder(SoruEkle.this)
                                    .setTitle("HATA !!!")
                                    .setMessage("Doğru Cevabı Seçmediniz")
                                    .setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    })
                                    .show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    new AlertDialog.Builder(SoruEkle.this)
                            .setTitle("HATA !!!")
                            .setMessage("Tüm Alanları Doldurmalısınız.")
                            .setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                }
            }
        });
    }

    public String cevapKontrolu() {
        if (a.isChecked()) {
            return "A";
        } else if (b.isChecked()) {
            return "B";
        } else if (c.isChecked()) {
            return "C";
        } else if (d.isChecked()) {
            return "D";
        } else {
            return "";
        }
    }
}
