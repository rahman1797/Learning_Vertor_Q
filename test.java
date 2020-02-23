private static void test()
{
 /* kita akan memberikan sebuah contoh LVQ yaitu 
  * menggunakan 2 parameter dengan data 14            
  */
 double[,] sample = new double[,]{
       {7, 8},
       {8, 8},
       {9, 8},
       {8, 7},
       {8, 9},
       {1, 3},
       {3, 1},
       {5, 3},
       {3, 5},
       {2, 13},
       {3, 13},
       {4, 13},
       {3, 12},
       {3, 14}



    };
 Program.Print(sample);
 /* LVQ hanya support untuk 1 target paramater saja!
  * dan dimulai dari 1 sampai nilai tertentu!
  */
 int[,] target = new int[,]{
     {1},
     {1},
     {1},
     {1},
     {1},
     {2},
     {2},
     {2},
     {2},
     {3},
     {3},
     {3},
     {3},
     {3}

    };
 int jumlahTarget = 3;
 int jumlahParamater = 2;
 double[,] bobot = LVQ.initRandomBobot(jumlahTarget,jumlahParamater); //biar mudah, kita menggunakan bobot random saja!
 

 double[] minmax = DataNorm.MinMax(sample); //juga kita butuh normalisasi data!
            //dengan class DataNorm untuk 
            //menghitung nilai maksimal dan minimal sebuah data!

 LVQ lvqPelatihan = new LVQ(); //panggil class LVQ

 lvqPelatihan.CreateSample(DataNorm.Normalisasi(sample, minmax[0], minmax[1])); //masukan data sample nya!
 lvqPelatihan.TARGET = target; //setting target
 lvqPelatihan.MAX_EPOCH = 1000; //setting ulangan
 lvqPelatihan.BOBOT = bobot; //setting bobot
 lvqPelatihan.TARGET_ERROR = 0.0001; //nilai MSE minimum square error nya!
 lvqPelatihan.MOMENTUM = true; //pakai momentum!
 lvqPelatihan.Training(); //proses pelatihan dimulai!!!
    
 /* untuk mengetahui LVQ berjalan dengan baik!
  * maka kita bisa cek nilai error nya!
  * jika nilai error nya = 0
  * maka itu tandanya proses pelatihan 100% sukses
  * kalau tidak! maka mungkin ada yang tidak sesuai antara
  * target dan result nya
  */
 Console.WriteLine("Cek nilai error : " + lvqPelatihan.CurrentError); //cek nilai error nya!
 //nilai error itu dihitung dengan cara MSE antara target dan result nya!
 //jika nilai error != 0, maka ulangi saja proses training! dikarenakan juga inisiasi bobot itu dengan random
 //terkadang bisa berhasil - juga tidak!
 Console.WriteLine("Dicapai pada epoch : " + lvqPelatihan.EPOCH); //cek nilai epoch nya!
 Console.WriteLine("Cek result nya : ");            
 Program.Print(lvqPelatihan.RESULT);
 Console.WriteLine("Hasil Akhir bobot nya : ");
 Program.Print(lvqPelatihan.BOBOT);
 
 if(lvqPelatihan.CurrentError!=0)
 {
  Console.WriteLine("proses pelatihan harus diulangi lagi!, karena error tidak mencapai 0");
  return;
 }

 /* kita akan mencoba melakukan proses pengujian!
  * kita ambil saja 2 baris saja!
  */
 double[,] sample2 = new double[,]{
       {7, 8},
       {8, 8}
 };

 LVQ lvqPengujian = new LVQ(); //panggil class LVQ
 
 int[,] prediksi = lvqPengujian.Testing(DataNorm.Normalisasi(sample2, minmax[0], minmax[1]),bobot);

 Console.WriteLine("Cek prediksi nya : ");
 Program.Print(prediksi);




}
