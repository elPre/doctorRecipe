package com.recippie.doctor.app.moduleitems

import android.util.Log
import android.view.View
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.beachbody.bod.base.moduleitems.ModuleItemLoadingState
import com.recippie.doctor.app.util.blink


interface IValidateLoadingState {
    val ivLoading: View?

    /**
     *
     * Validates the loading state of the item and hide/show the content container
     * or the loading image as needed, as well as animate it or clear animation.
     *
     * If it should show content, the onShowContent lambda will be invoked.
     * If there's an error the function will call onRemoveModule to remove the current
     * item type from the adapter's list
     *
     *
     * @param item data object of the generic type specified.
     * @param contentContainer View/ViewGroup to be hidden when loading and shown when it's data.
     * @param onShowContent Lambda function to be executed when loading finished.
     * @param useInvisibleForContent Indicator to use INVISIBLE instead of GONE
     */
    fun <T> validateLoadingState(item: ModuleItemDataWrapper<T>, contentContainer: View, useInvisibleForContent: Boolean = false, onShowContent: () -> Unit) {
        val loadingState = item.loadingState

        ivLoading?.isVisible = loadingState == ModuleItemLoadingState.LOADING

        if (useInvisibleForContent) {
            contentContainer.isInvisible = loadingState != ModuleItemLoadingState.LOADED
        } else {
            contentContainer.isVisible = loadingState == ModuleItemLoadingState.LOADED
        }

        when (loadingState) {
            ModuleItemLoadingState.LOADING -> ivLoading?.blink()
            ModuleItemLoadingState.LOADED -> {
                onShowContent()
                ivLoading?.clearAnimation()
            }
            ModuleItemLoadingState.ERROR -> Log.e("HomeBaseViewHolder", "Tried to bind an error item: $item")
        }
    }
}