package com.djekagoran.mymovieapp.data

import com.djekagoran.mymovieapp.data.api.APIInterface
import com.djekagoran.mymovieapp.data.local.DbHelperView

interface DataManager : APIInterface, DbHelperView
