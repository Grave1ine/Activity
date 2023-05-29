package otus.gpb.homework.activities

import android.Manifest
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class EditProfileActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        imageView = findViewById(R.id.imageview_photo)
        imageView.setOnClickListener {
            createDialog()
        }

        findViewById<Toolbar>(R.id.toolbar).apply {
            inflateMenu(R.menu.menu)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.send_item -> {
                        openSenderApp()
                        true
                    }
                    else -> false
                }
            }
        }
    }


    /**
     * Используйте этот метод чтобы отобразить картинку полученную из медиатеки в ImageView
     */
    private fun populateImage(uri: Uri) {
        val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
        imageView.setImageBitmap(bitmap)
    }

    private fun openSenderApp() {
        TODO("В качестве реализации метода отправьте неявный Intent чтобы поделиться профилем. В качестве extras передайте заполненные строки и картинку")
    }

    private fun createDialog() {

        val alertDialog = AlertDialog.Builder(this)                                          //создание AlertDialog
        var selectedIndex: Int = -1
        alertDialog.setTitle("шапка")                                                               //заголовок
        alertDialog.setSingleChoiceItems(R.array.alert_item, selectedIndex) { dialog, i ->          //только один выбор
            (dialog as AlertDialog).getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = true
            selectedIndex = i                                                                       //переписывает переменную со значением равному позицией строки из списка выбора
        }

        alertDialog.setPositiveButton("Применить") { _, _ ->                                    //кнопка применить
            when (selectedIndex) {
                0 -> runtimePermissionCamera()
                1 -> resultImageContent.launch("image/*")
                else -> Toast.makeText(this, "ничего не выбрано", Toast.LENGTH_SHORT).show()
            }
        }

        alertDialog.setNegativeButton("Выход") { dialog, _ ->                                   //скипаем меню выбора
            dialog.cancel()
        }

        alertDialog.show()
    }


    private val resultImageContent = registerForActivityResult(ActivityResultContracts.GetContent()) { result ->    //открывает галерею
        val uri = result ?: return@registerForActivityResult
        populateImage(uri)
    }

    private val permissionCamera = registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
        when {
            granted -> {
                //запрос на использование камеры дан. Ставим фотку как просят в задании
                imageView.setImageBitmap(BitmapFactory.decodeResource(resources, R.drawable.cat))
                //imageView.setImageResource(R.drawable.cat)
            }
            //разрешение не дано тогда -> permissionSetting()
            !shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> permissionSetting()             //если -> то?????
            /*{
            //нажата кнопка не спрашивать больше
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                data = Uri.fromParts("package", packageName, null)
            }
            startActivity(intent)
        }*/
            else -> {
                Toast.makeText(this, "???", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun permissionSetting() {                                                                                //запрос после первого отказа
        MaterialAlertDialogBuilder(this)
            .setMessage("нужно разрешение на использование камеры")
            .setPositiveButton("настройки") { _, _ ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", packageName, null)
                }
                startActivity(intent)
            }.show()
    }

    private fun runtimePermissionCamera() {                                                                        //как это все работает в этом блоке????
        // повторный запрет на использвование камеры
        if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                MaterialAlertDialogBuilder(this)
                    .setMessage("нужно разрешение на использование камеры")
                    .setNegativeButton("Отмена") { dialog, _ ->
                        Toast.makeText(this, "???", Toast.LENGTH_LONG).show()
                        dialog.cancel()
                    }
                    .setPositiveButton("Дать доступ") { _, _ ->
                        permissionCamera.launch(Manifest.permission.CAMERA)
                    }.show()
            }
        } else permissionCamera.launch(Manifest.permission.CAMERA)
    }

// работает точно так же как и resultImageContent только понятней
/*
    private val contractCameraStart = registerForActivityResult(ActivityResultContracts.GetContent()){ result ->
        findViewById<ImageView>(R.id.imageview_photo).setImageURI(result)
    }
*/

    /* alertDialog.setMultiChoiceItems(R.array.alert_item, null) { _, _, _ ->                    //можно много галок ставить
    }*/
    /*private fun createDialog(){

    val alertDialog = AlertDialog.Builder(this)                                          //создание AlertDialog
    alertDialog.setTitle("потому что")                                                          //заголовок
    alertDialog.setMessage("выберите способ")                                                   //текст

    alertDialog.setNeutralButton("Сделать фото"){ _, _ ->                                  //кнопки

    }
    alertDialog.setNegativeButton("Выбрать фото"){ _, _ ->

    }
    alertDialog.show()
}*/

}