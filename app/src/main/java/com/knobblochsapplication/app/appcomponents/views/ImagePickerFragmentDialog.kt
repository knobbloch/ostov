package com.knobblochsapplication.app.appcomponents.views

import android.Manifest.permission.CAMERA
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.appcomponents.di.MyApp
import com.knobblochsapplication.app.databinding.LayoutImagePickerDialogBinding
import com.knobblochsapplication.app.extensions.PickerHandler
import com.knobblochsapplication.app.extensions.PickerHandler.OPEN_CAMERA_REQUEST_CODE
import com.knobblochsapplication.app.extensions.PickerHandler.OPEN_STORAGE_REQUEST_CODE
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.dialogs.SettingsDialog

/**
 * the bottom sheet image picker dialog which allows capturing image from camera and also allows picking image from gallery.
 */
class ImagePickerFragmentDialog : BottomSheetDialogFragment(),
    EasyPermissions.PermissionCallbacks {

    private var binding: LayoutImagePickerDialogBinding? = null

    /**
     * is variable which assigned when [openCamera] is invoke
     */
    private var imageUri: Uri? = null


    /**
     * is the callback method which assigned when [show] method invoke
     * to handle picked/capture image result
     */
    private lateinit var onItemFoundOrSelected: (Uri?) -> Unit

    /**
     * is the callback method which assigned when [requestPermission] method invoke
     * at that time we assign passed onPermissionResultCallback parameter of [requestPermission] method to this
     */
    private var onPermissionResultCallback: ((Boolean) -> Unit)? = null

    /**
     * openCamera method is used to open the camera
     */
    private fun openCamera() {
        imageUri = PickerHandler.createUri(context)
        val takePicture =
            PickerHandler
                .takePictureFromCameraIntent(imageUri)

        executeWithContext {

            if (PickerHandler.isValidCameraIntent(takePicture)) {
                startActivityForResult(
                    takePicture,
                    OPEN_CAMERA_REQUEST_CODE
                )
            } else {
                //Handle Camera not found code
                dismiss()
            }
        }
    }

    /**
     * openGallery method is used to open the gallery
     */
    private fun openGallery() {
        val pickFile =
            PickerHandler
                .filePickerIntent(PickerHandler.MIME_TYPE_IMAGE)
        startActivityForResult(
            pickFile,
            OPEN_STORAGE_REQUEST_CODE
        )
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        initBinding()
        binding?.root?.let { dialog.setContentView(it) }
        return dialog
    }

    /**
     * method which binds the view with [binding] variable
     * also handles the click events of view
     */
    private fun initBinding() {
        context ?: return
        //if context is not null

        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.layout_image_picker_dialog,
            null,
            false
        )

        binding?.textCamera?.setOnClickListener {
            val permissions = arrayOf(CAMERA, READ_EXTERNAL_STORAGE)
            if (!hasPermission(*permissions)) {
                requestPermission(
                    *permissions,
                    message = MyApp.getInstance().getString(R.string.msg_camera_storage_permission),
                ) {
                    val allGranted = hasPermission(*permissions)
                    //we have to consider this because one of this is true at that time this callback invoke
                    //so we have to make sure about both the condition are true
                    if (it && allGranted) {
                        openCamera()
                    } else {
                        dismiss()
                    }
                }
            } else {
                openCamera()
            }
        }

        binding?.textGallery?.setOnClickListener {
            if (!hasPermission(READ_EXTERNAL_STORAGE)) {
                requestPermission(READ_EXTERNAL_STORAGE) {
                    if (it) {
                        openGallery()
                    } else {
                        dismiss()
                    }
                }
            } else {
                openGallery()
            }
        }
    }


    /**
     * show method is used to invoke the picker with callback
     * @param supportFragmentManager The FragmentManager this fragment will be added to.
     * @param callback is method as parameter once user pick any image or capture image from camera callback invoke,
     * callback invoke with the type [Uri] which contains the path of image
     *
     * to use : .show(supportFragmentManager) { path -> }
     */
    fun show(
        supportFragmentManager: FragmentManager,
        callback: (path: Uri?) -> Unit
    ) {
        onItemFoundOrSelected = {
            callback(it)
        }
        val oldDialog = supportFragmentManager.findFragmentByTag("ImagePickerDialog")
        if (oldDialog != null) {
            (oldDialog as? ImagePickerFragmentDialog)?.dismiss()
        }
        show(supportFragmentManager, "ImagePickerDialog")
    }

    /**
     * Receive the result from a previous call to
     * startActivityForResult(Intent, int)
     *
     * here we are handling results for the following request codes
     * [OPEN_CAMERA_REQUEST_CODE] and [OPEN_STORAGE_REQUEST_CODE]
     */
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        `data`: Intent?
    ): Unit {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            OPEN_CAMERA_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    onItemFoundOrSelected(imageUri)
                    dismiss()
                } else {
                    dismiss()
                }
            }
            OPEN_STORAGE_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    val filePath: Uri? = data?.data
                    onItemFoundOrSelected(filePath)
                    dismiss()
                } else {
                    dismiss()
                }
            }
        }
    }

    /**
     * To prevent checking let every time we are using this method if context found,
     * then the passed callback method is invoked.
     *
     * @param callback is which contains the parameter of [Context] once we found context not null then we
     * invoke callback with the not null [Context]
     *
     * to use : executeWithContext { context -> }
     */
    private inline fun executeWithContext(callback: (Context) -> Unit) {
        context?.let { callback(it) }
    }

    /**
     * Callback for the result from requesting permissions. This method
     * invoke when we request for permission by calling [requestPermission]
     *
     * here we are handling permission request code by using [EasyPermissions.onRequestPermissionsResult]
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ): Unit {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PickerHandler.REQUEST_PERMISSION -> {
                EasyPermissions.onRequestPermissionsResult(
                    requestCode, permissions, grantResults,
                    this@ImagePickerFragmentDialog
                )
            }
        }
    }

    /**
     * To check the passed permissions are allowed or not we are using this method.
     * @param permissions which contains Permission Typed Array as Vararg Argument.
     *
     * to use : hasPermission(permission1,permission2...permissionN)
     */
    private fun hasPermission(vararg permissions: String): Boolean {
        var checkPermission = false
        executeWithContext {
            checkPermission =
                EasyPermissions.hasPermissions(it, *permissions)
        }
        return checkPermission
    }

    /**
     * To request the permission method is used.
     * @param permissions which contains Permission Typed Array as Vararg Argument.
     * @param message the string message to pass in [EasyPermissions.requestPermissions] method.
     * @param onPermissionResultCallback is callback if permission allowed or not allowed at that time callback is invoke,
     * with the type [Boolean] if success then true else false
     *
     * to use : requestPermission(permission1,permission2...permissionN,message = "message as string"){ bool-> }
     */
    private fun requestPermission(
        vararg permissions: String,
        message: String = "This Application need Permission",
        onPermissionResultCallback: (Boolean) -> Unit
    ) {
        EasyPermissions.requestPermissions(
            this@ImagePickerFragmentDialog,
            message,
            PickerHandler.REQUEST_PERMISSION,
            *permissions
        )
        this.onPermissionResultCallback = onPermissionResultCallback
    }

    /**
     * callback method of [EasyPermissions.PermissionCallbacks]
     * if permission is denied by the user this method is invoke, here we are handling our callback [onPermissionResultCallback],
     * also we are handling Open Setting Dialog to allow permission
     */
    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(
                this@ImagePickerFragmentDialog,
                *perms.toTypedArray()
            )
        ) {
            executeWithContext {
                SettingsDialog.Builder(it).build().show()
            }
        }
        onPermissionResultCallback?.invoke(false)
    }

    /**
     * callback method of [EasyPermissions.PermissionCallbacks]
     * if permission is granted by the user this method is invoke, here we are handling our callback [onPermissionResultCallback]
     */
    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        onPermissionResultCallback?.invoke(true)
    }

}