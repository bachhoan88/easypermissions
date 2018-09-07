package pub.devrel.easypermissionsx.helper

import android.annotation.SuppressLint
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

/**
 * Permissions helper for [Fragment] from the framework.
 */
internal class FrameworkFragmentPermissionHelper(host: Fragment) : BaseFrameworkPermissionsHelper<Fragment>(host) {


    override val context: Context
        get() = host.activity!!

    //    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    override val fragmentManager: FragmentManager
        get() = host.childFragmentManager

    @SuppressLint("NewApi")
    override fun directRequestPermissions(requestCode: Int, vararg perms: String) {
        host.requestPermissions(perms, requestCode)
    }

    @SuppressLint("NewApi")
    override fun shouldShowRequestPermissionRationale(perm: String): Boolean {
        return host.shouldShowRequestPermissionRationale(perm)
    }
}
