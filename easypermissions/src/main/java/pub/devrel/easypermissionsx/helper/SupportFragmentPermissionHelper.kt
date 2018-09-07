package pub.devrel.easypermissionsx.helper

import android.content.Context

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

/**
 * Permissions helper for [Fragment] from the support library.
 */
internal class SupportFragmentPermissionHelper(host: Fragment) : BaseSupportPermissionsHelper<Fragment>(host) {

    override val supportFragmentManager: FragmentManager
        get() = host.childFragmentManager
    override val context: Context
        get() = host.activity!!

    override fun directRequestPermissions(requestCode: Int, vararg perms: String) {
        host.requestPermissions(perms, requestCode)
    }

    override fun shouldShowRequestPermissionRationale(perm: String): Boolean {
        return host.shouldShowRequestPermissionRationale(perm)
    }

}
