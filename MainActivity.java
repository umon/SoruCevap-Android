import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    Button secenekA, secenekB, secenekC, secenekD, soruSil, yeniSoruEkle;
    TextView soru, cevapA, cevapB, cevapC, cevapD;
    JSONArray veritabani = new JSONArray();
    JSONObject soruNesnesi;
    int sorusirasi = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        secenekA = (Button) findViewById(R.id.button);
        secenekB = (Button) findViewById(R.id.button2);
        secenekC = (Button) findViewById(R.id.button3);
        secenekD = (Button) findViewById(R.id.button4);
        soruSil = (Button) findViewById(R.id.button5);
        yeniSoruEkle = (Button) findViewById(R.id.button6);
        soru = (TextView) findViewById(R.id.textView1);
        cevapA = (TextView) findViewById(R.id.textView2);
        cevapB = (TextView) findViewById(R.id.textView3);
        cevapC = (TextView) findViewById(R.id.textView4);
        cevapD = (TextView) findViewById(R.id.textView5);
        IzinKontrolu();
        soruGoster();
        secenekA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cevapla("A");
            }
        });
        secenekB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cevapla("B");
            }
        });
        secenekC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cevapla("C");
            }
        });
        secenekD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cevapla("D");
            }
        });
        yeniSoruEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SoruEkle.class);
                i.putExtra("veritabani", veritabani.toString());
                startActivity(i);
                finish();
            }
        });
        soruSil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    sorusirasi--;
                    veritabani.remove(sorusirasi);
                    File dosya = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/sorular.json");
                    FileOutputStream fileOutputStream = new FileOutputStream(dosya);
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
                    outputStreamWriter.write(veritabani.toString());
                    outputStreamWriter.close();
                    fileOutputStream.close();
                    sorusirasi--;
                    soruGoster();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public void soruGoster() {
        if (veritabani.isNull(sorusirasi)) {
            new AlertDialog.Builder(this)
                    .setTitle("HATA !!!")
                    .setMessage("Veri Tabanınızda Başka Soru Kalmamış.. Yeni Soru Eklemelisiniz.")
                    .setPositiveButton("Yeni Soru Ekle", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent(MainActivity.this, SoruEkle.class);
                            i.putExtra("veritabani", veritabani.toString());
                            startActivity(i);
                            finish();
                        }
                    })
                    .setNegativeButton("Iptal", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .show();
        } else {
            try {
                soruNesnesi = veritabani.getJSONObject(sorusirasi);
                soru.setText(soruNesnesi.getString("Soru"));
                cevapA.setText(soruNesnesi.getString("A"));
                cevapB.setText(soruNesnesi.getString("B"));
                cevapC.setText(soruNesnesi.getString("C"));
                cevapD.setText(soruNesnesi.getString("D"));
                sorusirasi++;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void cevapla(String cevap) {
        try {
            if (soruNesnesi.getString("Cevap").equals(cevap)) {
                Toast.makeText(this, "Tebrikler! Bildin..", Toast.LENGTH_SHORT).show();
                soruGoster();
            } else {
                Toast.makeText(this, "Yanlış Cevabı Seçtiniz", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //---------- JSON Dosyasındaki verileri burada uygulamaya yüklüyoruz.
    public void VeritabaniYukle() {
        File dosya = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/sorular.json");
        try {
            if (!dosya.exists()) {
                FileOutputStream fileOutputStream = new FileOutputStream(dosya);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
                outputStreamWriter.write(veritabani.toString());
                outputStreamWriter.close();
                fileOutputStream.close();
            } else {
                FileInputStream fileInputStream = new FileInputStream(dosya);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                //--- Dosyadaki verileri bir JSON Nesnesine aktarıyoruz.
                veritabani = new JSONArray(bufferedReader.readLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //------- Burada Uygulamada Dosya yazma-okuma işlemleri için gerekli izni alıyoruz.
    public void IzinKontrolu() {
        String[] perms = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE};
        int permsRequestCode = 67;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                VeritabaniYukle();
            } else {
                requestPermissions(perms, permsRequestCode);
                IzinKontrolu();
            }
        } else {
            VeritabaniYukle();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case 67:
                boolean izin = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
    }
}
