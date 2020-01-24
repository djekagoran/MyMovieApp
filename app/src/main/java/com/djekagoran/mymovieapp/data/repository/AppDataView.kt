package com.djekagoran.mymovieapp.data.repository

import com.djekagoran.mymovieapp.data.api.APIInterface
import com.djekagoran.mymovieapp.data.local.DbHelperView

interface AppDataView : APIInterface, DbHelperView
