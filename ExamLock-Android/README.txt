EXAMLOCK ANDROID

CARA MEMBUKA
1. Ekstrak zip, buka folder ExamLock-Android di Android Studio, tunggu Gradle sync.
2. Build > Build APK(s). Pasang APK di HP siswa.

DUA TINGKAT PENGUNCIAN
A. HP pribadi (tanpa persiapan): saat siswa menekan Mulai Ujian, Android menampilkan
   konfirmasi "Sematkan layar". Siswa harus menekan Mulai. Selama tersemat, Home dan
   Recent tidak berfungsi. Siswa masih bisa keluar dengan menahan Back + Recent, tetapi
   keluar dari ujian akan tercatat sebagai pelanggaran.
B. HP sekolah (kios penuh): pada HP yang baru direset atau belum ada akun Google, aktifkan
   USB debugging lalu jalankan:
   adb shell dpm set-device-owner id.sekolah.examlock/.AdminReceiver
   Setelah itu penguncian berjalan tanpa konfirmasi dan siswa tidak bisa keluar.
   Untuk melepas: tekan Selesai di aplikasi, atau reset HP.

AKUN CONTOH: siswa 1001 / 1234, admin admin / admin123 (ganti sebelum dipakai).
Data masih disimpan di HP itu saja. Untuk monitoring antar-HP perlu database (Firebase).
