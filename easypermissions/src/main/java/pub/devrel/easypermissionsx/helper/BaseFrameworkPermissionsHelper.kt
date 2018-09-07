package pub.devrel.easypermissionsx.helper


import android.util.Log
import androidx.annotation.StyleRes
import androidx.fragment.app.FragmentManager
import pub.devrel.easypermissionsx.RationaleDialogFragment

/**
 * Implementation of [PermissionHelper] for framework host classes.
 */
abstract class BaseFrameworkPermissionsHelper<T>(host: T) : PermissionHelper<T>(host) {

    abstract val fragmentManager: FragmentManager

    override fun showRequestPermissionRationale(rationale: String,
                                                positiveButton: String,
                                                negativeButton: String,
                                                @StyleRes theme: Int,
                                                requestCode: Int,
                                                vararg perms: String) {
        val fm = fragmentManager

        // Check if fragment is already showing
        val fragment = fm.findFragmentByTag(RationaleDialogFragment.TAG)
        if (fragment is RationaleDialogFragment) {
            Log.d(TAG, "Found existing fragment, not showing rationale.")
            return
        }

        RationaleDialogFragment
                .newInstance(positiveButton, negativeButton, rationale, theme, requestCode, perms)
                .showAllowingStateLoss(fm, RationaleDialogFragment.TAG)
    }

    companion object {

        private val TAG = "BFPermissionsHelper"
    }
}
