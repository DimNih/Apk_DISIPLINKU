package com.samiode.disiplinku

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RecordViolationActivity : AppCompatActivity() {

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record_violation)

        val inputNIS: EditText = findViewById(R.id.inputNIS)
        val inputNama: EditText = findViewById(R.id.inputNama)
        val inputKelas: EditText = findViewById(R.id.inputKelas)
        val inputJenisKelamin: EditText = findViewById(R.id.inputJenisKelamin)
        val inputJenisPelanggaran: EditText = findViewById(R.id.inputJenisPelanggaran)
        val inputTanggalPelanggaran: EditText = findViewById(R.id.inputTanggalPelanggaran)
        val btnSimpan: Button = findViewById(R.id.btnSimpan)

        dbRef = FirebaseDatabase.getInstance().getReference("ViolationRecords")

        btnSimpan.setOnClickListener {
            val nis = inputNIS.text.toString()
            val nama = inputNama.text.toString()
            val kelas = inputKelas.text.toString()
            val jenisKelamin = inputJenisKelamin.text.toString()
            val jenisPelanggaran = inputJenisPelanggaran.text.toString()
            val tanggalPelanggaran = inputTanggalPelanggaran.text.toString()

            if (nis.isEmpty() || nama.isEmpty() || kelas.isEmpty() || jenisKelamin.isEmpty() || jenisPelanggaran.isEmpty() || tanggalPelanggaran.isEmpty()) {
                Toast.makeText(this, "Harap isi semua data", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val violationId = dbRef.push().key!!
            val violation = ViolationRecord(nis, nama, kelas, jenisKelamin, jenisPelanggaran, tanggalPelanggaran)

            dbRef.child(violationId).setValue(violation)
                .addOnCompleteListener {
                    Toast.makeText(this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show()
                    clearFields()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Gagal menyimpan data", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun clearFields() {
        findViewById<EditText>(R.id.inputNIS).text.clear()
        findViewById<EditText>(R.id.inputNama).text.clear()
        findViewById<EditText>(R.id.inputKelas).text.clear()
        findViewById<EditText>(R.id.inputJenisKelamin).text.clear()
        findViewById<EditText>(R.id.inputJenisPelanggaran).text.clear()
        findViewById<EditText>(R.id.inputTanggalPelanggaran).text.clear()
    }

    data class ViolationRecord(
        val nis: String? = null,
        val nama: String? = null,
        val kelas: String? = null,
        val jenisKelamin: String? = null,
        val jenisPelanggaran: String? = null,
        val tanggalPelanggaran: String? = null
    )
}