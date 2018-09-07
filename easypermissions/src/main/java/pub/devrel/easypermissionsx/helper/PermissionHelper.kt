package pub.devrel.easypermissionsx.helper

import android.app.Activity
import android.content.Context
import android.os.Build
import android.support.annotation.StyleRes

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

/**
 * Delegate class to make permission calls based on the 'host' (Fragment, Activity, etc).
 */
abstract class PermissionHelper<T>
// ============================================================================
// Public concrete methods
// ============================================================================

(val host: T) {

    abstract val context: Context

    private fun shouldShowRationale(vararg perms: String): Boolean {
        for (perm in perms) {
            if (shouldShowRequestPermissionRationale(perm)) {
                return true
            }
        }
        return false
    }

    fun requestPermissions(rationale: String,
                           positiveButton: String,
                           negativeButton: String,
                           @StyleRes theme: Int,
                           requestCode: Int,
                           vararg perms: String) {
        if (shouldShowRationale(*perms)) {
            showRequestPermissionRationale(
                    rationale, positiveButton, negativeButton, theme, requestCode, *perms)
        } else {
            directRequestPermissions(requestCode, *perms)
        }
    }

    fun somePermissionPermanentlyDenied(perms: List<String>): Boolean {
        for (deniedPermission in perms) {
            if (permissionPermanentlyDenied(deniedPermission)) {
                return true
            }
        }

        return false
    }

    fun permissionPermanentlyDenied(perms: String): Boolean {
        return !shouldShowRequestPermissionRationale(perms)
    }

    fun somePermissionDenied(vararg perms: String): Boolean {
        return shouldShowRationale(*perms)
    }

    // ============================================================================
    // Public abstract methods
    // ============================================================================

    abstract fun directRequestPermissions(requestCode: Int, vararg perms: String)

    abstract fun shouldShowRequestPermissionRationale(perm: String): Boolean

    abstract fun showRequestPermissionRationale(rationale: String,
                                                positiveButton: String,
                                                negativeButton: String,
                                                @StyleRes theme: Int,
                                                requestCode: Int,
                                                vararg perms: String)

    companion object {

        fun newInstance(host: AppCompatActivity): PermissionHelper<out AppCompatActivity> {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                return LowApiPermissionsHelper(host)
            }

            return AppCompatActivityPermissionHelper(host)
        }

        fun newInstance(host: Fragment): PermissionHelper<Fragment> {
            return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                LowApiPermissionsHelper(host)
            } else SupportFragmentPermissionHelper(host)

        }
    }

}
