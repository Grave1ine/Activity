package otus.gpb.homework.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

class Contract: ActivityResultContract<Transit, Transit?>() {

    override fun createIntent(context: Context, input: Transit): Intent {
        val intent = Intent(context, FillFormActivity::class.java)
        intent.putExtra("AAA", input)
        return intent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Transit? {
        if (intent == null) return null
        if (resultCode != Activity.RESULT_OK) return null
        return intent.extras?.getParcelable("AAA")

    }
}