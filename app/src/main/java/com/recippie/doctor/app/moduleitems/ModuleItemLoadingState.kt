package com.beachbody.bod.base.moduleitems

enum class ModuleItemLoadingState {
    LOADING,
    LOADED,
    ERROR
}

val ModuleItemLoadingState?.isLoading
    get() = this == ModuleItemLoadingState.LOADING
