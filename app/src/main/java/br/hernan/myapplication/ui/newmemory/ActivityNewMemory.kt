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
import br.hernan.myapplication.domain.dto.RegisterMemoryDto
import br.hernan.myapplication.ui.selectMap.ActivityMapsSelection
import com.google.android.gms.maps.model.LatLng
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



    private fun setupEvents(){
        binding.btnGoCamera.setOnClickListener{takePicture()}
        binding.btnGoGalery.setOnClickListener{selectImageInAlbum()}
        binding.btnGoOtherLocation.setOnClickListener{selectLocation()}
        binding.tvLatitude.text = getLatitude()
        binding.tvLongitude.text = getLongitude()

    }



    private fun saveMemory() {
        viewModel.save(getRegisterMemoryDto())
    }

    private fun getRegisterMemoryDto():RegisterMemoryDto{
        return RegisterMemoryDto(
            LocalDate.now().toString(),
            binding.etCity.text.toString(),
            binding.etDescription.text.toString(),
            binding.tvLatitude.text.toString().toDouble(),
            binding.tvLongitude.text.toString().toDouble()
            ,convertImage())
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
        startActivityForResult(Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI),1)
    }
    private fun selectLocation() {
        startActivityForResult(Intent(this,ActivityMapsSelection::class.java),2)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == RESULT_OK){
            binding.imgCameraNewMemory.setImageURI(data?.data)
        }
        if (requestCode == 2 && resultCode == RESULT_OK){
            val point : LatLng = data?.extras?.get("POINT") as LatLng
            binding.tvLatitude.text = point.latitude.toString()
            binding.tvLongitude.text = point.longitude.toString()
        }
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

        }
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, ActivityNewMemory::class.java)
    }

}