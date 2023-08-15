package com.apex.codeassesment.util

import android.content.Context
import android.content.Intent
import com.apex.codeassesment.data.model.User
import com.apex.codeassesment.ui.details.DetailsActivity


fun Context.navigateDetails(user: User) {
    val putExtra = Intent(this, DetailsActivity::class.java).putExtra("saved-user-key", user)
    startActivity(putExtra)
}