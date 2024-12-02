package com.samiode.disiplinku
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HistoryViolationActivity : AppCompatActivity() {

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_violation)

        val txtNIS: TextView = findViewById(R.id.txtNIS)
        val txtNama: TextView = findViewById(R.id.txtNama)
        val txtKelas: TextView = findViewById(R.id.txtKelas)
        val txtJenisKelamin: TextView = findViewById(R.id.txtJenisKelamin)
        val txtJenisPelanggaran: TextView = findViewById(R.id.txtJenisPelanggaran)
        val txtTanggalPelanggaran: TextView = findViewById(R.id.txtTanggalPelanggaran)

        dbRef = FirebaseDatabase.getInstance().getReference("ViolationRecords")
        val violationId = intent.getStringExtra("violationId") ?: return

        dbRef.child(violationId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val nis = snapshot.child("nis").getValue(String::class.java)
                    val nama = snapshot.child("nama").getValue(String::class.java)
                    val kelas = snapshot.child("kelas").getValue(String::class.java)
                    val jenisKelamin = snapshot.child("jenisKelamin").getValue(String::class.java)
                    val jenisPelanggaran = snapshot.child("jenisPelanggaran").getValue(String::class.java)
                    val tanggalPelanggaran = snapshot.child("tanggalPelanggaran").getValue(String::class.java)

                    txtNIS.text = "NIS: $nis"
                    txtNama.text = "Nama: $nama"
                    txtKelas.text = "Kelas: $kelas"
                    txtJenisKelamin.text = "Jenis Kelamin: $jenisKelamin"
                    txtJenisPelanggaran.text = "Jenis Pelanggaran: $jenisPelanggaran"
                    txtTanggalPelanggaran.text = "Tanggal Pelanggaran: $tanggalPelanggaran"
                } else {
                    Toast.makeText(this@HistoryViolationActivity, "Data tidak ditemukan", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@HistoryViolationActivity, "Gagal mengambil data", Toast.LENGTH_SHORT).show()
            }
        })
    }
}