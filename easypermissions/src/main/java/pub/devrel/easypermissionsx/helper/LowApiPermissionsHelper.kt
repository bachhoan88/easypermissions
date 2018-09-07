package pub.devrel.easypermissionsx.helper

import android.app.Activity
import android.content.Context
import android.support.annotation.StyleRes

import androidx.fragment.app.Fragment

/**
 * Permissions helper for apps built against API < 23, which do not need runtime permissions.
 */
internal class LowApiPermissionsHelper<T>(host: T) : PermissionHelper<T>(host) {

    override val context: Context
        get() = if (host is Activity) {
            host
        } else if (host is Fragment) {
            (host as Fragment).context!!
        } else {
            throw IllegalStateException("Unknown host: $host")
        }

    override fun directRequestPermissions(requestCode: Int, vararg perms: String) {
        throw IllegalStateException("Should never be requesting permissions on API < 23!")
    }

    override fun shouldShowRequestPermissionRationale(perm: String): Boolean {
        return false
    }

    override fun showRequestPermissionRationale(rationale: String,
                                                positiveButton: String,
                                                negativeButton: String,
                                                @StyleRes theme: Int,
                                                requestCode: Int,
                                                vararg perms: String) {
        throw IllegalStateException("Should never be requesting permissions on API < 23!")
    }
}
