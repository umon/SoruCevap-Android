# SoruCevap-Android
http://www.umutonur.com/json-kullanimi-android-icin/ adresinde anlatılan SoruCevap uygulaması örneğinin kaynak kodları..

Kodların Kullanımı : 
- Ilk olarak bir Android Projesi oluşturuyoruz.
- activity_main.xml dosyasındaki kodları proje içindeki activity_main.xml dosyasında <RelativeLaoyut> .. </RelativeLayout> tagları arasına yerleştiriyoruz.
- daha sonra projemiz içerisinde layout klasörüne sağ tıklayarak; RelativeLayout tipinde soru_ekle.xml adında yeni bir Layout (Layout Resourse File) oluşturuyoruz.
- (burada) soru_ekle.xml dosyası içinde bulunan kodları, proje içinde yeni oluşturduğumuz dosyada içerisine <RelativeLaoyut> .. </RelativeLayout> tagları arasına yerleştiriyoruz.
- MainActivity.java içerisinde bulunan kodları; projemizdeki MainActivity.java içine yerleştiriyoruz.
- Projemizdeki java klasörüne sağ tıklayarak SoruEkle.java adında yeni bir java sınıfı oluşturuyoruz.
- (burada) SoruEkle.java içerisinde bulunan kodları projemizde yeni oluşturduğumuz dosya içine yerleştiriyoruz..
- Son olarak ta Manifest dosyamızın içine aşağıdaki izinleri ekliyoruz.

 
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />


Detaylı bilgi ve iletişim için http://www.umutonur.com/ adresini ziyaret edebilirsiniz. 
