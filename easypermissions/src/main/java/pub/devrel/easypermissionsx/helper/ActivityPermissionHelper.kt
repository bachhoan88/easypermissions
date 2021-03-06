package pub.devrel.easypermissionsx.helper


import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentManager

/**
 * Permissions helper for [AppCompatActivity].
 */
internal class ActivityPermissionHelper(host: AppCompatActivity) : BaseFrameworkPermissionsHelper<AppCompatActivity>(host) {

    override val fragmentManager: FragmentManager
        get() = host.supportFragmentManager

    override val context: Context
        get() = host

    override fun directRequestPermissions(requestCode: Int, vararg perms: String) {
        ActivityCompat.requestPermissions(host, perms, requestCode)
    }

    override fun shouldShowRequestPermissionRationale(perm: String): Boolean {
        return ActivityCompat.shouldShowRequestPermissionRationale(host, perm)
    }
}
