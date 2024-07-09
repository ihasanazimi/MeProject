package ir.ha.meproject.ui.fragments.temp1

import android.content.ClipData
import android.content.ClipDescription.MIMETYPE_TEXT_PLAIN
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.ParcelFileDescriptor
import android.util.Log
import android.util.TypedValue.COMPLEX_UNIT_SP
import android.view.View
import android.view.View.DRAG_FLAG_GLOBAL
import android.view.View.DRAG_FLAG_GLOBAL_URI_READ
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.graphics.drawable.toBitmap
import androidx.core.util.component1
import androidx.core.util.component2
import androidx.core.view.DragStartHelper
import androidx.draganddrop.DropHelper
import ir.ha.meproject.R
import ir.ha.meproject.databinding.FragmentTemp1Binding
import ir.ha.meproject.utility.base.BaseFragment
import ir.ha.meproject.utility.extensions.getColor
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream

class Temp1Fragment : BaseFragment<FragmentTemp1Binding>(FragmentTemp1Binding::inflate){



    companion object {
        private const val LOG_TAG = "DragDropSample"
        private const val MAX_LENGTH = 200
        fun logD(message: String) {
            Log.d(LOG_TAG, message)
        }

        fun logE(message: String) {
            Log.e(LOG_TAG, message)
        }
    }

    override fun initializing() {
        super.initializing()


        // Use the DragStartHelper class to easily support initiating drag and drop in response to
        // both long press and mouse drag events. Note the call to attach() at the end. Without it,
        // the listener would never actually be attached to the view. Also note that attach() replaces
        // any OnTouchListener or OnLongClickListener already attached to the view.
        DragStartHelper(binding.textDragItem) { view, _ ->
            val text = (view as TextView).text

            // Create the ClipData to be shared
            val dragClipData = ClipData.newPlainText(/*label*/"Text", text)

            // Use the default drag shadow
            val dragShadowBuilder = View.DragShadowBuilder(view)

            // Initiate the drag. Note the DRAG_FLAG_GLOBAL, which allows for drag events to be listened
            // to by apps other than the source app.
            view.startDragAndDrop(dragClipData, dragShadowBuilder, null, DRAG_FLAG_GLOBAL)
        }.attach()

        DragStartHelper(binding.imageDragItem) { view, _ ->
            val imageFile = File(File("", "images"), "earth.png")

            if (!imageFile.exists()) {
                // Local file doesn't exist, create it
                File("filesDir", "images").mkdirs()
                // Write the file to local storage
                ByteArrayOutputStream().use { bos ->
                    (view as ImageView).drawable.toBitmap()
                        .compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos)
                    FileOutputStream(imageFile).use { fos ->
                        fos.write(bos.toByteArray())
                        fos.flush()
                    }
                }
            }

            val imageUri =
                FileProvider.getUriForFile(
                    requireContext(),
                    "ir.ha.meproject.images",
                    imageFile
                )

            // Sets the appropriate MIME types automatically
            val dragClipData = ClipData.newUri(requireActivity().contentResolver, "Image", imageUri)

            // Set the visual look of the dragged object
            // Can be extended and customized. We use the default here.
            val dragShadow = View.DragShadowBuilder(view)

            // Starts the drag, note: global flag allows for cross-application drag
            view.startDragAndDrop(
                dragClipData,
                dragShadow,
                null,
                // Since this is a "content:" URI and not just plain text, we can use the
                // DRAG_FLAG_GLOBAL_URI_READ to allow other apps to read from our content provider.
                // Without it, other apps won't receive the drag events
                DRAG_FLAG_GLOBAL.or(DRAG_FLAG_GLOBAL_URI_READ)
            )
        }.attach()

        DropHelper.configureView(
            requireActivity(),
            binding.textDropTarget,
            arrayOf(
                MIMETYPE_TEXT_PLAIN,
                "image/*",
                "application/x-arc-uri-list" // Support external items on Chrome OS Android 9
            ),
            DropHelper.Options.Builder()
                .setHighlightColor(getColor(R.color.snackBar_BackgroundColor))
                // Match the radius of the view's background drawable
                .setHighlightCornerRadiusPx(resources.getDimensionPixelSize(R.dimen.space_8))
                .build()
        ) { _, payload ->
            resetDropTarget()

            // For the purposes of this demo, only handle the first ClipData.Item
            val item = payload.clip.getItemAt(0)
            val (_, remaining) = payload.partition { it == item }

            when {
                payload.clip.description.hasMimeType(MIMETYPE_TEXT_PLAIN) ->
                    handlePlainTextDrop(item)
                else ->
                    handleImageDrop(item)
            }

            // Allow the system to handle any remaining ClipData.Item objects if applicable
            remaining
        }

        binding.buttonClear.setOnClickListener {
            resetDropTarget()
        }
    }

    private fun handlePlainTextDrop(item: ClipData.Item) {
        // The text is contained in the ClipData.Item
        if (item.text != null) {
            binding.textDropTarget.setTextSize(COMPLEX_UNIT_SP, 22f)
            binding.textDropTarget.text = getString(
                R.string.drop_text,
                item.text.substring(0, item.text.length.coerceAtMost(MAX_LENGTH))
            )
        } else {
            // The text is in a file pointed to by the ClipData.Item
            val parcelFileDescriptor: ParcelFileDescriptor? = try {
                requireActivity().contentResolver.openFileDescriptor(item.uri, "r")
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
                logE("FileNotFound")
                return
            }

            if (parcelFileDescriptor == null) {
                logE("Could not load file")
                binding.textDropTarget.text =
                    resources.getString(R.string.drop_error, item.uri.toString())
            } else {
                val fileDescriptor = parcelFileDescriptor.fileDescriptor
                val bytes = ByteArray(MAX_LENGTH)

                try {
                    FileInputStream(fileDescriptor).use {
                        it.read(bytes, 0, MAX_LENGTH)
                    }
                } catch (e: java.lang.Exception) {
                    logE("Unable to read file: ${e.message}")
                }

                binding.textDropTarget.setTextSize(COMPLEX_UNIT_SP, 15f)
                binding.textDropTarget.text = getString(R.string.drop_text, String(bytes))
            }
        }
    }

    private fun handleImageDrop(item: ClipData.Item) {
        item.uri?.let { uri ->
            val size = 72.px
            decodeSampledBitmapFromUri(
                requireActivity().contentResolver,
                uri,
                size,
                size
            )?.let { bitmap ->
                binding.textDropTarget.text =
                    getString(R.string.drop_image, bitmap.width, bitmap.height)
                val drawable = BitmapDrawable(resources, bitmap).apply {
                    val ratio =
                        intrinsicHeight.toFloat() / intrinsicWidth.toFloat()
                    setBounds(0, 0, size, (size * ratio).toInt())
                }
                binding.textDropTarget.setCompoundDrawables(
                    drawable,
                    null,
                    null,
                    null
                )
            }
        } ?: run {
            logE("Clip data is missing URI")
        }
    }

    private fun resetDropTarget() {
        binding.textDropTarget.setTextSize(COMPLEX_UNIT_SP, 22f)
        binding.textDropTarget.background =
            ContextCompat.getDrawable(requireActivity(), R.drawable.bg_target_normal)
        binding.textDropTarget.text = resources.getString(R.string.drop_target)
        binding.textDropTarget.setCompoundDrawables(null, null, null, null)
    }



}