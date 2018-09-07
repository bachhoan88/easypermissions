package pub.devrel.easypermissionsx


import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.annotation.RestrictTo
import androidx.annotation.StyleRes
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.FragmentManager

/**
 * [AppCompatDialogFragment] to display rationale for permission requests when the request
 * comes from a Fragment or Activity that can host a Fragment.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
class RationaleDialogFragmentCompat : AppCompatDialogFragment() {

    private var mPermissionCallbacks: EasyPermissions.PermissionCallbacks? = null
    private var mRationaleCallbacks: EasyPermissions.RationaleCallbacks? = null

    /**
     * Version of [.show] that no-ops when an IllegalStateException
     * would otherwise occur.
     */
    fun showAllowingStateLoss(manager: FragmentManager, tag: String) {
        if (manager.isStateSaved) {
            return
        }

        show(manager, tag)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (parentFragment != null) {
            if (parentFragment is EasyPermissions.PermissionCallbacks) {
                mPermissionCallbacks = parentFragment as EasyPermissions.PermissionCallbacks?
            }
            if (parentFragment is EasyPermissions.RationaleCallbacks) {
                mRationaleCallbacks = parentFragment as EasyPermissions.RationaleCallbacks?
            }
        }

        if (context is EasyPermissions.PermissionCallbacks) {
            mPermissionCallbacks = context
        }

        if (context is EasyPermissions.RationaleCallbacks) {
            mRationaleCallbacks = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        mPermissionCallbacks = null
        mRationaleCallbacks = null
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Rationale dialog should not be cancelable
        isCancelable = false

        // Get config from arguments, create click listener
        val config = RationaleDialogConfig(arguments!!)
        val clickListener = RationaleDialogClickListener(this, config, mPermissionCallbacks!!, mRationaleCallbacks!!)

        // Create an AlertDialog
        return config.createSupportDialog(context!!, clickListener)
    }

    companion object {

        val TAG = "RationaleDialogFragmentCompat"

        fun newInstance(
                rationaleMsg: String,
                positiveButton: String,
                negativeButton: String,
                @StyleRes theme: Int,
                requestCode: Int,
                permissions: Array<out String>): RationaleDialogFragmentCompat {

            // Create new Fragment
            val dialogFragment = RationaleDialogFragmentCompat()

            // Initialize configuration as arguments
            val config = RationaleDialogConfig(
                    positiveButton, negativeButton, rationaleMsg, theme, requestCode, permissions)
            dialogFragment.arguments = config.toBundle()

            return dialogFragment
        }
    }
}
