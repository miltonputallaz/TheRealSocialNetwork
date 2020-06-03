package com.sanicorporation.therealsocialnetwork.di.module

import com.sanicorporation.therealsocialnetwork.di.component.MainActivityComponent
import dagger.Module

@Module(subcomponents = arrayOf(MainActivityComponent::class))
class SubComponentsModule {}