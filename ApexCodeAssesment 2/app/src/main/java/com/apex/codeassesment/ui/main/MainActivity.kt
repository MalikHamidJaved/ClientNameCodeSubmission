package com.apex.codeassesment.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apex.codeassesment.R
import com.apex.codeassesment.RandomUserApplication
import com.apex.codeassesment.data.UserRepository
import com.apex.codeassesment.data.local.LocalDataSource
import com.apex.codeassesment.data.model.User
import com.apex.codeassesment.data.remote.RemoteDataSource
import com.apex.codeassesment.databinding.ActivityLocationBinding
import com.apex.codeassesment.databinding.ActivityMainBinding
import com.apex.codeassesment.di.MainComponent
import com.apex.codeassesment.ui.details.DetailsActivity
import com.apex.codeassesment.util.navigateDetails
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import javax.inject.Inject

// done (5 points): Move calls to repository to Presenter or ViewModel.
// done (5 points): Use combination of sealed/Dataclasses for exposing the data required by the view from viewModel .
// TODO (3 points): Add tests for viewmodel or presenter.
// TODO (3 points): Add tests
// TODO (Optional Bonus 10 points): Make a copy of this activity with different name and convert the current layout it is using in
//  Jetpack Compose.
class MainActivity : AppCompatActivity(), UserAdapter.OnItemClickListener {


  private lateinit var userAdapter: UserAdapter
  private var binding: ActivityMainBinding? = null
  private lateinit var viewModel: MainViewModel
  @Inject
  lateinit var localDataSource: LocalDataSource
  @Inject
  lateinit var remoteDataSource: RemoteDataSource

  // done (2 points): Convert to view binding


  @Inject lateinit var userRepository: UserRepository


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding?.root)
    (application as RandomUserApplication).mainComponent.inject(this)

    val userRepository = UserRepository(localDataSource, remoteDataSource)
    viewModel = ViewModelProvider(this, MainViewModelFactory(userRepository))
      .get(MainViewModel::class.java)

    // Observe the user data and update UI accordingly
    viewModel.userData.observe(this) { user ->
     setUserData(user)
    }


    binding?.mainRefreshButton?.setOnClickListener {
      viewModel.refreshUser(true)
    }
    sharedContext = this

    (applicationContext as MainComponent.Injector).mainComponent.inject(this)



    binding?.mainUserList?.layoutManager = LinearLayoutManager(this)
    userAdapter = UserAdapter(ArrayList(),this)
    binding?.mainUserList?.adapter = userAdapter

    viewModel.usersData.observe(this) { user ->
      userAdapter.setData(user)
    }
    binding?.mainSeeDetailsButton?.setOnClickListener {
      viewModel?.userData?.value?.let {
        navigateDetails(it)
      }
    }


    binding?.mainUserListButton?.setOnClickListener {
      val users = viewModel.fetchUsersList()
    }
  }

  private fun setUserData(user: User?) {
    binding?.mainImage?.let {
      Glide.with(this)
        .load(user?.picture?.large)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .placeholder(R.drawable.ic_launcher_background)
        .error(R.drawable.ic_launcher_background)
        .into(it)
    }
    binding?.mainName!!.text = user?.name?.first
    binding?.mainEmail!!.text = user?.email
  }

  companion object {
    var sharedContext: Context? = null
  }

  override fun onItemClick(user: User) {
    navigateDetails(user)
  }
}
