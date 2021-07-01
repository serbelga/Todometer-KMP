/*
 * Copyright 2021 Sergio Belda
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sergiobelda.todometer.common.di

import com.sergiobelda.todometer.common.remotedatasource.IProjectRemoteDataSource
import com.sergiobelda.todometer.common.remotedatasource.ITagRemoteDataSource
import com.sergiobelda.todometer.common.remotedatasource.ITaskRemoteDataSource
import com.sergiobelda.todometer.common.remotedatasource.ProjectRemoteDataSource
import com.sergiobelda.todometer.common.remotedatasource.TagRemoteDataSource
import com.sergiobelda.todometer.common.remotedatasource.TaskRemoteDataSource
import org.koin.dsl.module

val remoteDataSourceModule = module {
    single<IProjectRemoteDataSource> {
        ProjectRemoteDataSource(get())
    }
    single<ITaskRemoteDataSource> {
        TaskRemoteDataSource(get())
    }
    single<ITagRemoteDataSource> {
        TagRemoteDataSource()
    }
}
