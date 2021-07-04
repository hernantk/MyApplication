package br.hernan.myapplication.ui.newmemory

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.view.drawToBitmap
import br.hernan.myapplication.MainActivity
import br.hernan.myapplication.R
import br.hernan.myapplication.databinding.ActivityNewMemoryBinding
import br.hernan.myapplication.domain.dto.RegisteMemoryDto
import br.hernan.myapplication.ui.selectMap.ActivityMapsSelection
import org.koin.android.ext.android.inject
import java.io.ByteArrayOutputStream
import java.io.File
import java.time.LocalDate


class ActivityNewMemory : AppCompatActivity() {

    private val binding : ActivityNewMemoryBinding by lazy {
        ActivityNewMemoryBinding.inflate(layoutInflater)
    }
    private val viewModel : ViewModelNewMemory by inject()

    private lateinit var pictureUri: Uri



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupEvents()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.btnConfirm){
            saveMemory()
            startActivity(MainActivity.newIntent(this))
            finish()
            return true
        }
        return false
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_confirm,menu)
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == RESULT_OK){
        binding.imgCameraNewMemory.setImageURI(data?.data)
        }
        if (requestCode == 2 && resultCode == RESULT_OK){
            binding.tvLatitude.text = data?.extras?.get("LATITUDE").toString()
            binding.tvLongitude.text = data?.extras?.get("LONGITUDE").toString()
        }
    }

    private fun setupEvents(){
        binding.btnGoCamera.setOnClickListener{takePicture()}
        binding.btnGoGalery.setOnClickListener{selectImageInAlbum()}
        binding.btnGoOtherLocation.setOnClickListener{selectLocation()}
        binding.tvLatitude.text = getLatitude()
        binding.tvLongitude.text = getLongitude()

    }



    private fun saveMemory() {
        viewModel.save(RegisteMemoryDto(
            LocalDate.now().toString(),
            binding.etCity.text.toString(),
            binding.etDescription.text.toString(),
            binding.tvLatitude.text.toString(),
            binding.tvLongitude.text.toString()
            ,convertImage()))
    }

    private fun convertImage(): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        val image = binding.imgCameraNewMemory.drawToBitmap()
        image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        return Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT)

    }


    private fun getLatitude():String {
        return (intent.extras?.getDouble("LATITUDE") ?: 0.0).toString()
    }
    private fun getLongitude():String{
        return (intent.extras?.getDouble("LONGITUDE") ?: 0.0).toString()
    }

    private fun selectImageInAlbum() {
        val intent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent,1)
    }

    private fun selectLocation() {
        val intent = Intent(this,ActivityMapsSelection::class.java)
        startActivityForResult(intent,2)
    }

    private fun takePicture() {
        createPictureFile()
        takePictureLauncher.launch(pictureUri)

    }
    private fun createPictureFile() {
        val pictureDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val pictureFile = File(pictureDir,"pic1")

        this.pictureUri =
            FileProvider.getUriForFile(this, "br.edu.unisep.myapplication.fileprovider", pictureFile)


    }
    private val takePictureLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) { pictureTaken ->
        if (pictureTaken) {
            binding.imgCameraNewMemory.setImageURI(this.pictureUri)

        } else {
            binding.imgCameraNewMemory.setImageURI(null)
        }
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, ActivityNewMemory::class.java)
    }

}