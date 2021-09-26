package com.recippie.doctor.app.moduleitems

import com.beachbody.bod.base.moduleitems.ModuleItemLoadingState

data class ModuleItemDataWrapper<out T>(val data: T, var loadingState: ModuleItemLoadingState)
